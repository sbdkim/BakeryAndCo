package customer0204.dao;

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

public class SellerDAO {
	static {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	private static SellerDAO dao = null;

	private SellerDAO() {
	}

	public static SellerDAO getInstance() {
		if (dao == null)
			dao = new SellerDAO();
		return dao;
	}

	// 공통모듈
	// Connection 객체 생성 반환
	private Connection getConnection() {
		Connection conn = null;
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		try {
			conn = DriverManager.getConnection(url, "JAVADB01", "1234");
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
	//비밀번호 암호화
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

	
	
	//  전체 매인 메뉴 [1]로그인 - [2] 판매자
	public SellerVO selectSeller(String sellerID, String pwd) {
		SellerVO vo = null;
		Connection conn = this.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from sellerTBL where sellerID=? and pwd=? and active = '1' ";
		try {
			pstmt = conn.prepareStatement(sql);
			// ? 채우기
			pstmt.setString(1, sellerID);
			pstmt.setString(2, pwdEncrypt(pwd));
			// 쿼리문 전송 결과 받기
			rs = pstmt.executeQuery();
			if (rs.next()) {// 읽은튜플이 있는가?

				vo = new SellerVO(rs.getString("sellerID"), rs.getString("pwd"), rs.getString("name"),
						rs.getString("birthDate"), rs.getString("storeName"), rs.getString("storeMobile"),
						rs.getString("email"), rs.getString("storeAddr"), rs.getInt("regionCode"), rs.getInt("active"),
						rs.getTimestamp("enrollDate"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close(rs, pstmt, conn);
		}
		return vo;
	}

	// 전체 매인 메뉴 [1]로그인 - [2] 판매자 - 비밀번호 제설정 password reset - sellerID, mobile, birthDate를 받아서 password를 reset
	public int passwordReset(String sellerID, String password, String storeName, String mobile, String birthDate) {
		int result = 0;
		Connection conn = this.getConnection();
		PreparedStatement pstmt = null;
		String sql = "update sellerTBL set pwd = ? where sellerID = ? AND storeMobile = ? AND birthDate = ? AND storeName = ? AND active = '1' ";

		try {
			pstmt = conn.prepareStatement(sql);
			// ?채우기
			pstmt.setString(1, pwdEncrypt(password));
			pstmt.setString(2, sellerID);
			pstmt.setString(3, mobile);
			pstmt.setString(4, birthDate);
			pstmt.setString(5, storeName);

			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.close(pstmt, conn);

		return result;
	}// passwordRest

	
	
	// 전체 매인 메뉴 [2]회원가입 - [2] 판매자
	public int createSeller(String sellerID, String pwd, String name, String birthDate, String storeName,
			String storeMobile, String email, String storeAddr, int active, int regionCode) {
		int result = 0;
		Connection conn = this.getConnection();
		String sql = null;
		if (email == null) {
			sql = "insert into sellerTBL(sellerID, pwd, name,  birthDate, storeName, storeMobile,  storeAddr, active, regionCode, enrollDate)"
					+ " values (?,?,?,?,?,?,?,?,?, sysdate)";
		} else {
			sql = "insert into sellerTBL(sellerID, pwd, name,  birthDate, storeName, storeMobile,  storeAddr, active, regionCode, email, enrollDate)"
					+ " values (?,?,?,?,?,?,?,?,?,?, sysdate)";
		}

		PreparedStatement pstmt = null;
		try {
			// 4. PreparedStatement 객체 생성하기
			pstmt = conn.prepareStatement(sql);
			// 5. ? 값 설정하기
			pstmt.setString(1, sellerID);
			pstmt.setString(2, pwdEncrypt(pwd));
			pstmt.setString(3, name);
			pstmt.setString(4, birthDate);
			pstmt.setString(5, storeName);
			pstmt.setString(6, storeMobile);
			pstmt.setString(7, storeAddr);
			pstmt.setInt(8, active);
			pstmt.setInt(9, regionCode);
			if (email != null)
				pstmt.setString(10, email);
			// 쿼리문 전송+결과 받기
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close(pstmt, conn);
		}
		return result;
	} // createSeller

	

	// 전체 매인 메뉴 [2]회원가입 - [2] 판매자 - 등록할떄 sellerID반복 안되는지 확인
	public boolean selectSeller(String sellerID) {
		boolean result = false;
		Connection conn = this.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select sellerID from sellerTBL where sellerID=? ";
		try {
			pstmt = conn.prepareStatement(sql);
			// ? 채우기
			pstmt.setString(1, sellerID);
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


	//SELLER MENU 1 - [1] 주문목록
	public ArrayList<OrderVO> viewOrders(String storeName) {
		ArrayList<OrderVO> list = null;
		Connection conn = this.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from ordertbl where storeName = ? order by orderDate";
		OrderVO vo = null;
		// 3. PreparedStatement 객체생성
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, storeName);
			// 쿼리문 전송 결과 받기
			rs = pstmt.executeQuery();
			if (rs.next()) {// 읽은튜플이 하나이상 있는가?
				list = new ArrayList<OrderVO>();// ArrayList 객체 생성
				do {
					vo = new OrderVO(rs.getInt("orderNo"), rs.getInt("prodNum"), rs.getString("prodName"),
							rs.getString("storeName"), rs.getString("userID"), rs.getInt("quantity"), rs.getInt("cost"),
							rs.getString("shippingCost"), rs.getString("review"), rs.getInt("orderCompleted"),
							rs.getDate("orderDate"));
					list.add(vo);// ArrayList에 vo 객체 담기
				} while (rs.next());
			}
		} catch (SQLException e) {
			// e.printStackTrace();
		} finally {
			this.close(rs, pstmt, conn);
		}
		return list;

	}
	//SELLER MENU 2 - [2] 고객목록
	public ArrayList<CustomerVO> viewCustomers(String storeName) {
		ArrayList<CustomerVO> list = null;
		Connection conn = this.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select distinct c.userID, name,  birthDate, mobile , email, addr, enrollDate from customertbl c join ordertbl o on c.userID=o.userID where o.storeName = ? order by c.userID";
		CustomerVO vo = null;
		// 3. PreparedStatement 객체생성
		try {
			pstmt = conn.prepareStatement(sql);
			// ? 채우기 x
			pstmt.setString(1, storeName);
			// 쿼리문 전송 결과 받기
			rs = pstmt.executeQuery();
			if (rs.next()) {// 읽은튜플이 하나이상 있는가?
				list = new ArrayList<CustomerVO>();// ArrayList 객체 생성
				do {
					vo = new CustomerVO(rs.getString("userID"), rs.getString("name"), rs.getString("birthDate"),
							rs.getString("mobile"), rs.getString("email"), rs.getString("addr"), 1,
							rs.getTimestamp("enrolldate"));
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

	//SELLER MENU 3 - [3] 제품 목록
	public ArrayList<ProductVO> viewProducts(String storeName) {
		ArrayList<ProductVO> list = null;
		Connection conn = this.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select prodNum,category,storeName,prodName,price,inventory,description,registerDate,rating from productTBL where storeName = ? order by prodNum";
		ProductVO vo = null;
		// 3. PreparedStatement 객체생성
		try {
			pstmt = conn.prepareStatement(sql);
			// ? 채우기 x
			pstmt.setString(1, storeName);
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
	
	
	//SELLER MENU 3 [3] 제품 목록 - PRODUCT MENU 1 - [1] 제품등록
		public int registerProduct(String category, String storeName, String prodName, int price, int inventory,
				String description) {
			int result = 0;
			Connection conn = this.getConnection();
			String sql = null;
			sql = "insert into productTBL(prodNum, category, storeName, prodName, price, inventory, description, registerDate) "
					+ "values (prod_seq.nextval, ?, ?,?,?,?,?, sysdate)";

			PreparedStatement pstmt = null;
			try {
				// 4. PreparedStatement 객체 생성하기
				pstmt = conn.prepareStatement(sql);
				// 5. ? 값 설정하기
				pstmt.setString(1, category);
				pstmt.setString(2, storeName);
				pstmt.setString(3, prodName);
				pstmt.setInt(4, price);
				pstmt.setInt(5, inventory);
				pstmt.setString(6, description);

				result = pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				this.close(pstmt, conn);
			}
			return result;
		}
		
		//SELLER MENU 3 [3] 제품 목록 - PRODUCT MENU 1 - [2] 제품수정
		public int editProduct(int prodNum, int price, int inventory, String storeName, String prodName, String description,
				String category) {
			int result = 0;
			Connection conn = this.getConnection();
			PreparedStatement pstmt = null;
			StringBuilder sql = new StringBuilder("update productTBL set ");
			int cnt = 0;
			if (price != -1) {
				sql.append("price=?,");
			}
			if (inventory != -1) {
				sql.append("inventory=?,");
			}
			if (storeName != null) {
				sql.append("storeName=?,");
			}
			if (description != null) {
				sql.append("description=?,");
			}
			if (category != null) {
				sql.append("category=?,");
			}
			sql = sql.delete(sql.length() - 1, sql.length());
			sql.append(" where prodNum=?");
			try {
				pstmt = conn.prepareStatement(sql.toString());
				if (price != -1) {
					cnt++;
					pstmt.setInt(cnt, price);
				}
				if (inventory != -1) {
					cnt++;
					pstmt.setInt(cnt, inventory);
				}
				if (storeName != null) {
					cnt++;
					pstmt.setString(cnt, storeName);
				}
				if (description != null) {
					cnt++;
					pstmt.setString(cnt, description);
				}
				if (category != null) {
					cnt++;
					pstmt.setString(cnt, category);
				}
				pstmt.setInt(++cnt, prodNum);
				result = pstmt.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
			}
			this.close(pstmt, conn);

			return result;
		}// editProduct
		
		//SELLER MENU 3 [3] 제품 목록 - PRODUCT MENU 1 - [3]  제품삭제
		public int deleteProduct(int prodNum) {
			int result = 0;
			Connection conn = this.getConnection();
			PreparedStatement pstmt = null;
			String sql = "delete from productTBL where prodNum = ? ";
			
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, prodNum);
				result = pstmt.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.close(pstmt, conn);
			return result;	
		}
		
		//SELLER MENU 4 - [4] 회원정보 수정
		public int updateSeller(String sellerID, String pwd, String name, String email, String storeMobile,
				String storeAddr, int regionCode) {// String storeName,
			int result = 0;
			Connection conn = this.getConnection();
			PreparedStatement pstmt = null;
			StringBuilder sql = new StringBuilder("update sellerTBL set ");

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
			if (storeMobile != null) {
				sql.append("storeMobile=?,");
			}
			if (storeAddr != null) {
				sql.append("storeAddr=?,");
			}
			if (regionCode != 0) {
				sql.append("regionCode=?,");
			}

			// 마지막 , 없애고
			sql = sql.delete(sql.length() - 1, sql.length());
			// where 이하 붙이기
			sql.append(" where sellerID=?");
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
				if (storeMobile != null) {
					cnt++;
					pstmt.setString(cnt, storeMobile);
				}

				if (storeAddr != null) {
					cnt++;
					pstmt.setString(cnt, storeAddr);
				}
				if (regionCode != 0) {
					cnt++;
					pstmt.setInt(cnt, regionCode);
				}
				pstmt.setString(++cnt, sellerID);
				result = pstmt.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
			}
			this.close(pstmt, conn);

			return result;
		}// updateSeller
		
		
		//SELLER MENU 5 - [5] 회원탈퇴
		public int SellerDelete(String sellerID, String pwd) {
			int result = 0;
			Connection conn = this.getConnection();
			PreparedStatement pstmt = null;
			String sql = "update sellerTBL set active = '0' where SellerID=? and pwd=?";
			//삭제하면 정보가 남아있지 않아서 ACTIVE만 0으로 바꿔주기
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, sellerID);
				pstmt.setString(2, pwdEncrypt(pwd));
				result = pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			this.close(pstmt, conn);

			return result;
		}
		
		

}// sellerDAO