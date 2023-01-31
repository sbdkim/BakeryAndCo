package customer.dao;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;


public class SellerDAO {
	//드라이버로딩 정적 블록
	static {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();System.exit(0);
		}
	}
	//싱글톤 생성자~
	// 싱글톤
	//1. static memberDAO 객체 필드선언 
	//2. 생성자 private 
	//3. static 인 메소드하나를 만든다 getInstance : 생성자를 호출해서 객체생성 반환
	private static SellerDAO dao=null;
	private SellerDAO() {}
	public static SellerDAO getInstance() {
		if(dao==null)dao=new SellerDAO();
		return dao;
	}
	//공통모듈
	//Connection 객체 생성 반환
	private Connection getConnection() {
		Connection conn=null;
		String url="jdbc:oracle:thin:@localhost:1521:xe";
		try {
			conn=DriverManager.getConnection(url, "JAVADB", "1234");
		} catch (SQLException e) {
			e.printStackTrace();System.exit(0);
		}
		return conn;
	}
	
	//닫기 - pstmt, conn
	private void close(PreparedStatement pstmt,Connection conn) {		
		try {
			if(pstmt!=null)	pstmt.close();
			if(conn!=null)	conn.close();		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//닫기 - rs, pstmt, conn
	private void close(ResultSet rs,PreparedStatement pstmt,Connection conn) {		
		try {
			if(rs!=null)	rs.close();
			if(pstmt!=null)	pstmt.close();
			if(conn!=null)	conn.close();		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	
	
	//암호화 모듈
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

	
	/*생성해야하는 메소드 LIST:
- (DONE) createSeller
- (DONE) registerProduct
- (DONE) passwordReset
- viewOrders - orderTBL 참고하고 그 스토에 들어온 주문 출력 날짜 시간
- viewCustomers
- viewProducts
- editProduct
- unregisterSeller

	 * 
	 * 
	 * */	

	//password reset - sellerID, mobile, birthDate를 받아서 password를 reset
	public int passwordReset(String sellerID, String password, String storeName, String mobile, Date birthDate) {
		int result = 0;
		Connection conn = this.getConnection();
		PreparedStatement pstmt = null;
		String sql = "update sellerTBL set pwd = ? where sellerID = ? AND mobile = ? AND birthDate = ? AND storeName = ?";

		try {
			pstmt = conn.prepareStatement(sql);
			// ?채우기
			pstmt.setString(1, pwdEncrypt(password));
			pstmt.setString(2, sellerID);
			pstmt.setString(3, mobile);
			pstmt.setDate(4, (java.sql.Date) birthDate);
			pstmt.setString(5, storeName);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.close(pstmt, conn);

		return result;
	}//passwordRest
	
	
	
	
//seller생성
	public int createSeller(String sellerID, String pwd, String name, Date birthDate, String gender, 
			String storeName, String storeMobile, String email, String  mobile, String storeAddr, int regionCode) {
		int result = 0;
		Connection conn = this.getConnection();
		String sql = null;
		if (email == null) {
			sql = "insert into sellerTBL(sellerID, pwd, name, gender, birthDate, storeName, storeMobile, mobile, storeAddr, enrollDate)"
					+ " values (?,?,?,?,?,?,?,?,?, sysdate)";
		} else {
			sql = "insert into sellerTBL(sellerID, pwd, name, gender, birthDate, storeName, storeMobile, mobile, storeAddr, email enrollDate)"
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
			pstmt.setString(4, gender);
			pstmt.setDate(5, (java.sql.Date) birthDate);
			pstmt.setString(6, storeName);
			pstmt.setString(7, storeMobile);
			pstmt.setString(8, mobile);
			pstmt.setString(9, storeAddr);

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
	
	
	//product을 등록하려면 등록할떄 필요한 필드들을 다 입력 받아서 sql문에 넣어야 한다.
public int registerProduct(String prodCategory, String storeName, String prodName, int price, int inventory, String description) {
	int result = 0;
	Connection conn = this.getConnection();
	String sql = null;
	sql = "insert into productTBL(prodNum, prodCategory, storeName, prodName, price, inventory, description, registrationDate) "
			+ "values (prod_seq, ?, ?,?,?,?,?, sysdate)";
	
	PreparedStatement pstmt = null;
	try {
		// 4. PreparedStatement 객체 생성하기
		pstmt = conn.prepareStatement(sql);
		// 5. ? 값 설정하기
		pstmt.setString(1, prodCategory);
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


public ArrayList<ProductVO> viewProducts(){
	ArrayList<ProductVO> list=null;
	Connection conn=this.getConnection();
	PreparedStatement pstmt=null;	
	ResultSet rs=null;
	String sql="select prodNum,scategory,storename,prodName,price,Inventory,description,registerdate,rating from productTBL order by prodNum";
	ProductVO vo=null;
	//3. PreparedStatement 객체생성
	try {
		pstmt=conn.prepareStatement(sql);
		//? 채우기 x
		// 쿼리문 전송 결과 받기
		rs=pstmt.executeQuery();
		if(rs.next()) {//읽은튜플이 하나이상 있는가?
			list=new ArrayList<ProductVO>();//ArrayList 객체 생성
			do {
				vo=new ProductVO(rs.getInt("prodNum"),
						rs.getString("scategory"),rs.getString("storename"),
						rs.getString("prodName"),rs.getInt("price"),
						rs.getInt("Inventory"),rs.getString("description"),
						rs.getDate("registerdate"),rs.getInt("rating"));
				list.add(vo);//ArrayList에 vo 객체 담기
			}while(rs.next());
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}finally {
		this.close(rs, pstmt, conn);
	}
	return list;
}






	
	
}//sellerDAO