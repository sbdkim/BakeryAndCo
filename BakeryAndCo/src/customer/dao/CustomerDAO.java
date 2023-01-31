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

	// 닫기 - rs, pstmt, conn
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

	// 메소드 여기에 추가

	public int createCustomer(String userID, String pwd, String name, String gender, Date birthDate, String email,
			String mobile, String addr) {
		int result = 0;
		Connection conn = this.getConnection();
		String sql = null;
		if (email == null) {
			sql = "insert into customerTBL(userID, pwd, name, gender, birthDate, mobile, addr, enrollDate)"
					+ " values (?,?,?,?,?,?,?, sysdate)";
		} else {
			sql = "insert into customerTBL(userID, pwd, name, gender, birthDate,  mobile, addr,email, enrollDate)"
					+ " values (?,?,?,?,?,?,?,?, sysdate)";
		}

		PreparedStatement pstmt = null;
		try {
			// 4. PreparedStatement 객체 생성하기
			pstmt = conn.prepareStatement(sql);
			// 5. ? 값 설정하기
			pstmt.setString(1, userID);
			pstmt.setString(2, pwdEncrypt(pwd));
			pstmt.setString(3, name);
			pstmt.setString(4, gender);
			pstmt.setDate(5, (java.sql.Date) birthDate);

			pstmt.setString(6, mobile);
			pstmt.setString(7, addr);

			if (addr != null)
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

	/*
	 * 생성해야하는 메소드 LIST: 
	 * (DONE) createCustomer //memberDAO insertMember이랑 비슷합니다 -
	 * (DONE) passwordReset 
	 * viewCompletedOrder - orderTBL (orderCompleted - True) -
	 * (DONE) writeReview  
	 *  viewCurrentOrder - orderCompleted - false
	 *  viewRegionStore - 지역에 있는 가게들 출력
	 *  viewProductCategory - 스토 번호를 입력하면 그 스토에 관련된 모든 프로덕트 케티고리 출력	 
	 *  viewProduct - 케티고리를 누르면 그 스토에 관련된 모든 케티고리에 해당되는 프로덕트 출력
	 * updateCustomer 
	 * unregisterCustomer
	 * 
	 */

	//password reset - userID, mobile, birthDate를 받아서 password를 reset
	public int passwordReset(String userID, String password, String mobile, Date birthDate) {
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
			pstmt.setDate(4, (java.sql.Date) birthDate);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.close(pstmt, conn);

		return result;
	}//passwordRest
	
	
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
	}//writeReview
	
	//아직 store에서 완료 안된 주문 출력(미배송)
	public int viewCurrentOrder(String userID) {
		int result = 0;
		Connection conn = this.getConnection();
		PreparedStatement pstmt = null;
		String sql = "select * from orderTBL where  userID = ? AND orderCompleted = 'false' ";
		
		try {
			pstmt = conn.prepareStatement(sql);
			// ?채우기
			pstmt.setString(1, userID);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.close(pstmt, conn);

		
		return result;
	}//viewCurrentOrder
	
	//완료된 주문 출력 
	public int viewCompletedOrder(String userID) {
		int result = 0;
		Connection conn = this.getConnection();
		PreparedStatement pstmt = null;
		String sql = "select * from orderTBL where  userID = ? AND orderCompleted = 'true' order by  orderDate desc";
		
		try {
			pstmt = conn.prepareStatement(sql);
			// ?채우기
			pstmt.setString(1, userID);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.close(pstmt, conn);

		
		return result;
	}//viewCurrentOrder
	
	
	
	//지역에 있는 가게 출력
	public int viewRegionStore() {
		int result = 0;
		
		
		return result;
	}

	
	//customer userID &pwd 로 검색
	public CustomerVO selectCustomer(String userID, String pwd) {
		CustomerVO vo = null;
		
		Connection conn=this.getConnection();
		PreparedStatement pstmt=null;	
		ResultSet rs=null;
		String sql=
		"select userID, name, gender, birthDate, email, mobile, addr, enrollDate from customerTBL where userID=? and pwd=?";
		//3. PreparedStatement 객체생성
		try {
			pstmt=conn.prepareStatement(sql);
			//? 채우기
			pstmt.setString(1, userID);pstmt.setString(2, pwdEncrypt(pwd));
			// 쿼리문 전송 결과 받기
			rs=pstmt.executeQuery();
			if(rs.next()) {//읽은튜플이 있는가?		
				vo=new CustomerVO(rs.getString("userID"),
						rs.getString("name"),rs.getString("gender"),
						rs.getDate("birthDate"), rs.getString("email"),
						rs.getString("mobile"), rs.getString("addr"), rs.getTimestamp("enrollDate"));		
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			this.close(rs, pstmt, conn);
		}		
		return vo;
	}
	
	
	
	
	
	

}//customerDAO
