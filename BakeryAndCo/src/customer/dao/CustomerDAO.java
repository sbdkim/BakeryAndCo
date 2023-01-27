package customer.dao;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
  
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
		if (dao == null) {
			dao = new CustomerDAO();
		}
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
	

	private String pwdEncrypt(String password) {
		MessageDigest md = null;
		String encrypted = "";
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.update(password.getBytes());
			encrypted = String.format("%064x", new BigInteger(1, md.digest()));
			// System.out.println(hex);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return encrypted;
	}
	
	//메소드 여기에 추가 
	
	public int createCustomer(String id, String password, String name, String mobile, String addr) {
		int result = 0;
		Connection conn = this.getConnection();
		String sql = null;
		if (addr == null) {
			sql = "insert into customerTBL(id, password, name, mobile, reg_date)" + " values (?,?,?,?, sysdate)";
		} else {
			sql = "insert into customerTBL(id, password, name, mobile, addr, reg_date)" + " values (?,?,?,?,?, sysdate)";
		}

		PreparedStatement pstmt = null;
		try {
			// 4. PreparedStatement 객체 생성하기
			pstmt = conn.prepareStatement(sql);
			// 5. ? 값 설정하기
			pstmt.setString(1, id);
			pstmt.setString(2, pwdEncrypt(password));
			pstmt.setString(3, name);
			pstmt.setString(4, mobile);
			if (addr != null)
				pstmt.setString(5, addr);
			// 쿼리문 전송+결과 받기
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close(pstmt, conn);
		}
		return result;
	} // memberInsert
	
	
	
	
	
	
	
	
	
	

}
