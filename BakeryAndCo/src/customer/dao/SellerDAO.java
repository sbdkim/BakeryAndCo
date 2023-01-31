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
		MessageDigest md=null;
		String password=null;
		try {
			md=MessageDigest.getInstance("SHA512");
			md.update(pwd.getBytes());
			password=String.format("%0128x", new BigInteger(1,md.digest()));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}		
		return password;
	}

	//비밀번호 바꾸기
	public int insertMember(String id,String password,String name
		,String mobile,String addr) {
	int result=0;
	Connection conn=this.getConnection();
	PreparedStatement pstmt=null;	
	String sql=null;
	if(addr==null) {
		sql="insert into membertbl(id,password,name,mobile,reg_date)";
		sql+=" values(?,?,?,?,sysdate)";
	
	}else {
		sql="insert into membertbl(id,password,name,mobile,addr,reg_date)";
		sql+=" values(?,?,?,?,?,sysdate)";
	}
	try {
		pstmt=conn.prepareStatement(sql);
		//?채우기
		pstmt.setString(1, id);
		pstmt.setString(2, pwdEncrypt(password));
		pstmt.setString(3, name);
		pstmt.setString(4, mobile);
		if(addr!=null)pstmt.setString(5, addr);
		result=pstmt.executeUpdate();
		
	} catch (SQLException e) {
		e.printStackTrace();
	}
	this.close(pstmt, conn);
	return result;
	}	
}