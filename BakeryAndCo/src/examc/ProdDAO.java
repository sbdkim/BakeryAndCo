package examc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProdDAO {
	static {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	public static ProdDAO dao = null;

	private ProdDAO() {
	}

	public static ProdDAO getInstance() {
		if (dao == null) {
			dao = new ProdDAO();
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
	
	public ArrayList<ProdVO> selectProdALL(){//
		ArrayList<ProdVO> list =null;
		Connection conn = this.getConnection();
		PreparedStatement pstmt=null;	
		ResultSet rs=null;
		ProdVO vo=null;
		String sql="select * from prod";
		try {
			pstmt=conn.prepareStatement(sql);
			//? 채우기 x
			// 쿼리문 전송 결과 받기
			rs=pstmt.executeQuery();
			if(rs.next()) {//읽은튜플이 하나이상 있는가?
				list=new ArrayList<ProdVO>();//ArrayList 객체 생성
				do {
					vo=new ProdVO(rs.getString("pname") ,rs.getInt("qty") ,rs.getInt("price")	);
					list.add(vo);
				}while(rs.next());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			this.close(rs, pstmt, conn);
		}
		return list;
	}
	
	
	public ArrayList<ProdVO> selectProd(String pname){//
		ArrayList<ProdVO> list =null;
		Connection conn = this.getConnection();
		PreparedStatement pstmt=null;	
		ResultSet rs=null;
		ProdVO vo=null;
		String sql="select * from prod where pname = ?";
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1,pname);
			
			rs=pstmt.executeQuery();
			if(rs.next()) {//읽은튜플이 하나이상 있는가?
				list=new ArrayList<ProdVO>();//ArrayList 객체 생성
				do {
					vo=new ProdVO(rs.getString("pname") ,rs.getInt("qty") ,rs.getInt("price")	);
					list.add(vo);
				}while(rs.next());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			this.close(rs, pstmt, conn);
		}
		return list;
	}
	////////////////임시
	public ArrayList<ProdVO> qwer(String pname){//
		ArrayList<ProdVO> list =null;
		Connection conn = this.getConnection();
		PreparedStatement pstmt=null;	
		ResultSet rs=null;
		ProdVO vo=null;
		String sql="select * from prod where pname = ?";
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1,pname);
			
			rs=pstmt.executeQuery();
			if(rs.next()) {//읽은튜플이 하나이상 있는가?
				list=new ArrayList<ProdVO>();//ArrayList 객체 생성
				do {
					vo=new ProdVO(rs.getString("pname") ,rs.getInt("qty") ,rs.getInt("price")	);
					list.add(vo);
				}while(rs.next());
			}
			//cust 로그인하기 ( 기능 구현보다 일단 하고)
			
			
			//prod select 하기.
			String sq1 = "select * from prod where pname = ?";
			
			
			//select 된 것
			String pna=rs.getString("pname");
			
			//insert 하기      (ord ====>>> id,name
			String sql2 = "insert into ord values (";
			
			
			
			
			
			
			
			
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			this.close(rs, pstmt, conn);
		}
		return list;
	}
	
	
	
	// 검색 - 책번호(bno) 받아서 BookVO 담아서 반환하는 메소드 
		public ProdVO viewProd(String pname) {
			ProdVO vo=null;
			//DB검색
			//1. 드라이버 로딩 : 이미완료
			//2. 연결
			Connection conn=this.getConnection();
			PreparedStatement pstmt=null;	
			ResultSet rs=null;
			//3. 쿼리문만들기
			String sql="select * from prod where pname=?";
			try {
				//4. pstmt  객체 생성
				pstmt=conn.prepareStatement(sql);
				//5. ? 채우기
				pstmt.setString(1, pname);
				//6. 전송 결과받기
				rs=pstmt.executeQuery();
				if(rs.next()) {//rs 안에 select 수행한 행이 있다
					vo=new ProdVO(rs.getString("pname"),rs.getInt("qty"),
							rs.getInt("price"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				this.close(rs,pstmt,conn);		
			}
			return vo;
		}//selectBook
	
		public int uploadOrd(String pname) {
			int result=0;
			//1. 드라이버로딩 x 정적블럭에 있어서 수행 완료
			//2. 연결 
			Connection conn=this.getConnection();
			PreparedStatement pstmt=null;
			String sql=null;
			//3. 쿼리문 작성
			sql="insert into ord (pname, price, cid, qty) from ord where pname=?";
			//4. PreparedStatement 객체 생성
			try {
				pstmt=conn.prepareStatement(sql);
				//5. ? 채우기
				pstmt.setString(1, pname);
				//6. 쿼리문전송, 실행, 결과받기
				result=pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				//닫기
				this.close(pstmt, conn);
			}
			
			return result;
		}
	
	
	
}
