package customer.dao;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class CustomerDAO {

	static {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	public static CustomerDAO dao = null;

	private CustomerDAO() {
	}

	public static CustomerDAO getInstance() {
		if (dao == null)
			dao = new CustomerDAO();
		return dao;
	}

	private Connection getConnection() {
		Connection conn = null;
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		try {
			conn = DriverManager.getConnection(url, "JAVADB", "1234");
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		return conn;
	}

	private void close(PreparedStatement pstmt, Connection conn) {
		try {
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void close(ResultSet rs, PreparedStatement pstmt, Connection conn) {
		try {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private String pwdEncrypt(String pwd) {
		MessageDigest md = null;
		String encrypted = "";
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.update(pwd.getBytes());
			encrypted = String.format("%064x", new BigInteger(1, md.digest()));
			// System.out.println(hex);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return encrypted;
	}

	public int createCustomer(String userID, String pwd, String name, String birthDate, String mobile, String email,
			String addr, int active) {
		int result = 0;
		Connection conn = this.getConnection();
		String sql = null;
		if (email != null) {
			sql = "insert into customerTBL(userID, pwd, name, birthDate, mobile, addr, active, email, enrolldate)"
					+ " values (?,?,?,?,?,?,?,?, sysdate)";
		} else {
			sql = "insert into customerTBL(userID, pwd, name, birthDate, mobile, addr, active, enrolldate)"
					+ " values (?,?,?,?,?,?,?, sysdate)";
		}

		PreparedStatement pstmt = null;
		try {
			// 4. PreparedStatement 객체 생성하기
			pstmt = conn.prepareStatement(sql);
			// 5. ? 값 설정하기
			pstmt.setString(1, userID);
			pstmt.setString(2, pwdEncrypt(pwd));
			pstmt.setString(3, name);
			pstmt.setString(4,  birthDate);
			pstmt.setString(5, mobile);
			pstmt.setString(6, addr);
			pstmt.setInt(7, active);

			if (email != null)
				pstmt.setString(8, email);
			// 쿼리문 전송+결과 받기
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close(pstmt, conn);
		}
		return result;
	} // createCustomer

	// password reset - userID, mobile, birthDate를 받아서 password를 reset
	public int resetPassword(String userID, String password, String mobile, String birthDate) {
		int result = 0;
		Connection conn = this.getConnection();
		PreparedStatement pstmt = null;
		String sql = "update customerTBL set pwd = ? where userID = ? AND mobile = ? AND birthDate = ?";

		try {
			pstmt = conn.prepareStatement(sql);
			// ?채우기
			pstmt.setString(1, pwdEncrypt(password));
			pstmt.setString(2, userID);
			pstmt.setString(3, mobile);
			pstmt.setString(4, birthDate);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.close(pstmt, conn);

		return result;
	}// resetPassword

	public int writeReview(String orderNo, String review) {
		int result = 0;
		Connection conn = this.getConnection();
		PreparedStatement pstmt = null;
		String sql = "update orderTBL set review = ? where orderNo = ?";

		try {
			pstmt = conn.prepareStatement(sql);
			// ?채우기
			pstmt.setString(1, review);
			pstmt.setString(2, orderNo);

			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.close(pstmt, conn);

		return result;
	}// writeReview

	// 아직 store에서 완료 안된 주문 출력(미배송)

	// 완료된 주문 출력
	public ArrayList<OrderVO> viewCompletedOrder(String userID) {
		ArrayList<OrderVO> list = null;
		Connection conn = this.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		OrderVO vo = null;
		String sql = "select * from orderTBL where  userID = ? AND orderCompleted = '1' order by  orderNo desc";

		try {
			pstmt = conn.prepareStatement(sql);
			// ?채우기
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				list = new ArrayList<OrderVO>();

				//// int orderNo, int prodNum, String prodName, String storeName, String userID,
				//// int quantity,
				// int cost, String shippingcost, String review, boolean orderCompleted, Date
				//// orderdate
				do {
					vo = new OrderVO(rs.getInt("orderNo"), rs.getInt("prodNum"), rs.getString("prodName"),
							rs.getString("storeName"), rs.getString("userID"), rs.getInt("quantity"), rs.getInt("cost"),
							rs.getString("shippingCost"), rs.getString("review"), rs.getInt("orderCompleted"),
							rs.getDate("orderDate"));
					list.add(vo);
				} while (rs.next());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.close(pstmt, conn);

		return list;
	}// viewCompletedOrder

	// 지역에 있는 가게 출력
	public ArrayList<SellerVO> viewRegionStore(int regionCode) {
		ArrayList<SellerVO> list = null;
		Connection conn = this.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;

		sql = "select sellerID, storeName, regionCode, active from sellerTBL where regionCode = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, regionCode);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				list = new ArrayList<SellerVO>();
				do {
					
				list.add(new SellerVO(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4)));
				
				} while (rs.next());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.close(rs, pstmt, conn);

		return list;
	}
	
	
	
	
	
	public ArrayList<ProductVO> viewCategory(String storeName) {
		ArrayList<ProductVO> list = null;
		Connection conn = this.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;

		sql = "select distinct category from productTBL where storeName = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, storeName);
			
			rs = pstmt.executeQuery();
			if (rs.next()) {
				list = new ArrayList<ProductVO>();
				do {
								
				list.add(new ProductVO(rs.getString(1), storeName));
				
				} while (rs.next());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.close(rs, pstmt, conn);

		return list;
	}
	
	
	
	
	 public ArrayList<CartVO> viewShoppingCart(){
		 ArrayList<CartVO> list = null;
		 Connection conn = this.getConnection();
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = null;
			sql = "select userID, prodName, price, quantity from cartTBL";
			try {
				pstmt = conn.prepareStatement(sql);		
				
				rs = pstmt.executeQuery();
				
				if (rs.next()) {
					list = new ArrayList<CartVO>();
					do {
									
					list.add(new CartVO(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4)));
					
					} while (rs.next());
				}

				
			} catch (SQLException e) {
				e.printStackTrace();
			}

		 return list;
	 }	
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public ArrayList<ProductVO> viewProducts(String storeName ,String category) {
		ArrayList<ProductVO> list = null;
		Connection conn = this.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select prodNum,category,storeName,prodName,price,inventory,description,registerDate,rating from productTBL where storeName = ? AND category = ? order by prodNum";
		ProductVO vo = null;
		// 3. PreparedStatement 객체생성
		try {
			pstmt = conn.prepareStatement(sql);
			// ? 채우기 x
			pstmt.setString(1, storeName);
			pstmt.setString(2, category);
			// 쿼리문 전송 결과 받기
			rs = pstmt.executeQuery();
			if (rs.next()) {// 읽은튜플이 하나이상 있는가?
				list = new ArrayList<ProductVO>();// ArrayList 객체 생성
				do {
					vo = new ProductVO(rs.getInt("prodNum"), rs.getString("category"), rs.getString("storeName"),
							rs.getString("prodName"), rs.getInt("price"), rs.getInt("inventory"),
							rs.getString("description"), rs.getDate("registerDate"), rs.getInt("rating"));
					list.add(vo);// ArrayList에 vo 객체 담기
				} while (rs.next());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close(rs, pstmt, conn);
		}
		return list;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	


	public ArrayList<String> viewProductCategory(String storeName) {
		ArrayList<String> list = null;
		Connection conn = this.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select distinct category from productTBL where storeName = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, storeName);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				list = new ArrayList<String>();
				do {
					list.add(rs.getString(1));
				} while (rs.next());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}
	
	
	
	
	// <METHOD LIST>
	// createCustomer - customer를 만들때 필요한 모든 필드를 받아서 customerTBL에 추가 (SQL Insert)
	// resetPassword - userID, mobile, birthDate 를 입력 받아서 password를 제설정 (SQL Update)
	// writeReview - customer에 orderNumber를 입력 받아서 orderTBL에 review 작성하기 (SQL
	// Update)
	// viewCompletedOrder - orderTBL에서 그 customer에 주문에 completedOrder '1'인 order 출력
	// viewOrderProcessing - orderTBL에서 그 customer에 주문에 completedOrder '0'인 order 출력
	// viewRegionStore - 지격에 있슨 store들 출력
	// viewProductCategory - 들어간 store에 있는 category출력
	// viewProduct - 들어간 store에 category안에 해당되는 모든 product 출력
	// selectCustomer(userID) - 회원가입 - ID 반복되는지 확인
	// selectCustomer(userID, pwd) - 로그인 할때
	// customerDelete - 삭제는 아닌 customer에 active를 1에서 0으로 바꿔지는 메소드
	// updateCustomer - 비밀번호를 입력하면 pwd, email, name, mobile, addr 수정 가능

	// 지역에 있는 가게들 출력 - 스토 번호를 입력하면 그 스토에 관련된 모든 프로덕트 케티고리 출력
	// LIST THE CATEGORY (list of Categories)
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	// 케티고리를 누르면 그 스토에 관련된 모든 케티고리에 해당되는 프로덕트 출력 updateCustomer
	// LIST THE PRODUCTS IN THE CATEGORY
	public ArrayList<ProductVO> viewProduct(int categoryNumber) {
		ArrayList<ProductVO> list = null;
		Connection conn = this.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ProductVO vo = null;
		String sql = "select prodNum, prodName, price, inventory, description, registerDate from productTBL where  category = ?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, categoryNumber);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				list = new ArrayList<ProductVO>();
				do {
					vo = new ProductVO(rs.getInt("prodNum"), rs.getString("category"), rs.getString("storeName"),
							rs.getString("prodName"), rs.getInt("price"), rs.getInt("inventory"),
							rs.getString("description"), rs.getTimestamp("categoryNumber"), rs.getInt("rating"));
				} while (rs.next());
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}
		this.close(pstmt, conn);

		return list;
	}

	public ArrayList<OrderVO> viewOrderProcessing(String userID) {
		ArrayList<OrderVO> list = null;
		Connection conn = this.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		OrderVO vo = null;
		String sql = "select * from orderTBL where  userID = ? AND orderCompleted = '0' order by  orderNo desc";

		try {
			pstmt = conn.prepareStatement(sql);
			// ?채우기
			pstmt.setString(1, userID);
			rs=pstmt.executeQuery();
			if (rs.next()) {
				list = new ArrayList<OrderVO>();

				//// int orderNo, int prodNum, String prodName, String storeName, String userID,
				//// int quantity,
				// int cost, String shippingcost, String review, boolean orderCompleted, Date
				//// orderdate
				do {
					vo = new OrderVO(rs.getInt("orderNo"), rs.getInt("prodNum"), rs.getString("prodName"),
							rs.getString("storeName"), rs.getString("userID"), rs.getInt("quantity"), rs.getInt("cost"),
							rs.getString("shippingCost"), rs.getString("review"), rs.getInt("orderCompleted"),
							rs.getDate("orderDate"));
					list.add(vo);
				} while (rs.next());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.close(pstmt, conn);

		return list;
	}// viewCurrentOrder

	// selectCustomer - 회원가입할때 입력한 아이디가 존제하닌지 확인
	public boolean selectCustomer(String userID) {
		boolean result = false;
		Connection conn = this.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select userID from customerTBL where userID=? ";
		try {
			pstmt = conn.prepareStatement(sql);
			// ? 채우기
			pstmt.setString(1, userID);
			// 쿼리문 전송 결과 받기
			rs = pstmt.executeQuery();
			if (rs.next()) {// 읽은튜플이 있는가?
				result = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close(rs, pstmt, conn);
		}
		return result;
	}

	// customer userID &pwd 로 검색
	public CustomerVO selectCustomer(String userID, String pwd) {
		CustomerVO vo = null;

		Connection conn = this.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select userID, name, birthDate, email, mobile, addr, enrollDate, active from customerTBL where userID=? and pwd=? and active=1 ";
		// 3. PreparedStatement 객체생성
		try {
			pstmt = conn.prepareStatement(sql);
			// ? 채우기
			pstmt.setString(1, userID);
			pstmt.setString(2, pwdEncrypt(pwd));
			// 쿼리문 전송 결과 받기
			rs = pstmt.executeQuery();
			if (rs.next()) {// 읽은튜플이 있는가?
//				vo = new CustomerVO(rs.getString("userID"), rs.getString("name"), rs.getString("birthDate"),
//						rs.getString("mobile"), rs.getString("email"), rs.getString("addr"), rs.getInt("active"),
//						rs.getTimestamp("enrollDate"));
				vo = new CustomerVO(rs.getString(1), rs.getString(2), rs.getString(3),
						rs.getString(5), rs.getString(4), rs.getString(6), rs.getInt(8),
						rs.getTimestamp(7));
				
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close(rs, pstmt, conn);
		}
		return vo;
	}

	// id랑 pwd받아서 customerTBL에 customer계정을 active(false -0)으로 update한다
	// DELETE하는게 아니라 active를 (0 - false)로 바꿔준다
	public int customerDelete(String userID, String pwd) {
		int result = 0;
		Connection conn = this.getConnection();
		PreparedStatement pstmt = null;
		String sql = "update customerTBL set active = '0' where userID=? and pwd=?";
		try {
			pstmt = conn.prepareStatement(sql);
			// ?채우기
			pstmt.setString(1, userID);
			pstmt.setString(2, pwdEncrypt(pwd));
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.close(pstmt, conn);

		return result;
	}

	public int updateCustomer(String userID, String pwd, String name, String email, String mobile, String addr) {
		int result = 0;
		Connection conn = this.getConnection();
		PreparedStatement pstmt = null;
		StringBuilder sql = new StringBuilder("update customerTBL set ");
		int cnt = 0;// 수정 필드(열) 개수
		if (pwd != null) {
			sql.append("pwd=?,");
		}
		if (name != null) {
			sql.append("name=?,");
		}
		if (email != null) {
			sql.append("email=?,");
		} // email
		if (mobile != null) {
			sql.append("mobile=?,");
		}
		if (addr != null) {
			sql.append("addr=?,");
		}
		// 마지막 , 없애고
		sql = sql.delete(sql.length() - 1, sql.length());
		// where 이하 붙이기
		sql.append(" where userID=?");
		try {
			pstmt = conn.prepareStatement(sql.toString());
			// ?채우기
			if (pwd != null) {
				cnt++;
				pstmt.setString(cnt, this.pwdEncrypt(pwd));
			}
			if (name != null) {
				cnt++;
				pstmt.setString(cnt, name);
			}
			if (email != null) {
				cnt++;
				pstmt.setString(cnt, email);
			}
			if (mobile != null) {
				cnt++;
				pstmt.setString(cnt, mobile);
			}
			if (addr != null) {
				cnt++;
				pstmt.setString(cnt, addr);
			}
			pstmt.setString(++cnt, userID);
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.close(pstmt, conn);

		return result;
	}// updateCustomer
	
	
	public int addCart(String userID, String prodName, int price, int quantity) {
		int result = 0;
		Connection conn = this.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "insert into CartTBL (userID, prodName, price, quantity) values (?,?,?,?) ";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userID);
			pstmt.setString(2, prodName);
			pstmt.setInt(3, price);
			pstmt.setInt(4, quantity);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.close(pstmt, conn);

		return result;
		
	}//addCart

	public boolean emptyCart() {
		int run = 0;
		boolean result =false;
		Connection conn = this.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "delete from cartTBL";
		
		try {
			pstmt = conn.prepareStatement(sql);
			run = pstmt.executeUpdate();
			if(run ==1) {result = true;}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	
	

}// customerDAO
