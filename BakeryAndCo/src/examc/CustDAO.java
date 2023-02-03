package examc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class CustDAO {
	static {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	public static CustDAO dao = null;

	private CustDAO() {
	}

	public static CustDAO getInstance() {
		if (dao == null) {
			dao = new CustDAO();
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
	
	//cust회원가입 완료
	public int createCust(String id, String pwd, String name, String mobile) {
		int result = 0;
		Connection conn = this.getConnection();
		String sql = null;
			sql = "insert into cust values (?,?,?,?)";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pwd);
			pstmt.setString(3, name);
			pstmt.setString(4, mobile);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close(pstmt, conn);
		}
		return result;
		
		
	} // customerTBLInsert
	
	
		//고객 조회
		public ArrayList<CustVO> selectCustALL(){//
			ArrayList<CustVO> list =null;
			Connection conn = this.getConnection();
			PreparedStatement pstmt=null;	
			ResultSet rs=null;
			CustVO vo=null;
			String sql="select * from cust";   //order by id
			try {
				pstmt=conn.prepareStatement(sql);
				//? 채우기 x
				// 쿼리문 전송 결과 받기
				rs=pstmt.executeQuery();
				if(rs.next()) {//읽은튜플이 하나이상 있는가?
					list=new ArrayList<CustVO>();//ArrayList 객체 생성
					do {
						vo=new CustVO(rs.getString("id") ,  rs.getString("pwd") , rs.getString("name") ,
								rs.getString("mobile") ,rs.getInt("active") );
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
		
		//고객 조회
		public CustVO selectCust(){
			CustVO vo=null;
			Connection conn = this.getConnection();
			PreparedStatement pstmt=null;	
			ResultSet rs=null;
			String sql="select * from Cust";
			try {
				pstmt=conn.prepareStatement(sql);
				rs=pstmt.executeQuery();
				if(rs.next()) {
					vo=new CustVO(rs.getString(1) , rs.getString(2) , rs.getString(3) , rs.getString(4) );
					}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				this.close(rs, pstmt, conn);
			}
			return vo;
		}
		
		
		
			public CustVO selectCust(String id,String pwd){
				CustVO vo=null;
				Connection conn = this.getConnection();
				PreparedStatement pstmt=null;	
				ResultSet rs=null;
				String sql="select * from Cust where id=? and pwd=?";
				try {
					pstmt=conn.prepareStatement(sql);
					pstmt.setString(1, id);pstmt.setString(2, pwd);
					rs=pstmt.executeQuery();
					if(rs.next()) {
						vo=new CustVO(rs.getString(1) , rs.getString(2) , rs.getString(3) , rs.getString(4) );
						}
				} catch (SQLException e) {
					e.printStackTrace();
				}finally {
					this.close(rs, pstmt, conn);
				}
				return vo;
			}
			
			//id로 검색  이미 사용중인 id true반환, 없으면 false 반환
			public boolean selectCust(String id) {
				boolean result=false;
				Connection conn=this.getConnection();
				PreparedStatement pstmt=null;	
				ResultSet rs=null;
				String sql=	"select id from cust where id=? ";
				//3. PreparedStatement 객체생성
				try {
					pstmt=conn.prepareStatement(sql);
					//? 채우기
					pstmt.setString(1, id);
					// 쿼리문 전송 결과 받기
					rs=pstmt.executeQuery();
					if(rs.next()) {//읽은튜플이 있는가?	
						result=true;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}finally {
					this.close(rs, pstmt, conn);
				}		
				return result;
			}
			
			
		
			//Create Customer .. 회원가입
			public int insertCust(String id, String pwd, String name, String mobile) {
				int result=0;
				Connection conn=this.getConnection();
				PreparedStatement pstmt=null;	
				String sql=null;
					sql="insert into cust values(id, pwd, name,  mobile)";
				try {
					pstmt=conn.prepareStatement(sql);
					//?채우기
					pstmt.setString(1, id);
					pstmt.setString(2, pwd);
					pstmt.setString(3, name);
					pstmt.setString(4, mobile);
					result=pstmt.executeUpdate();
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
				this.close(pstmt, conn);
				return result;
			}
			
			public int passwordReset(String id,String pwd) {
				int result=0;
				
				Connection conn=this.getConnection();
				PreparedStatement pstmt=null;	
				String sql="update cust set pwd=? where id=?";
				try {
					pstmt=conn.prepareStatement(sql);
					//?채우기			
					pstmt.setString(1, pwd);
					pstmt.setString(2, id);
					result=pstmt.executeUpdate();			
				} catch (SQLException e) {
					e.printStackTrace();
				}
				this.close(pstmt, conn);	
				
				return result;
			}
			

//			- unregisterCustomer
			public int CustDelete(String id,String pwd) {
				int result=0;
				Connection conn=this.getConnection();
				PreparedStatement pstmt=null;	
				String sql="delete from cust where id=? and pwd=?";
				try {
					pstmt=conn.prepareStatement(sql);
					//?채우기
					pstmt.setString(1, id);
					pstmt.setString(2, pwd);
					result=pstmt.executeUpdate();
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
				this.close(pstmt, conn);	
						
				return result;
			}
			
			
//			- updateCustomer// 
			public int updateCust(String id,  String pwd, String name,   String  mobile) {
				int result=0;
				Connection conn=this.getConnection();
				PreparedStatement pstmt=null;	
				StringBuilder sql=new StringBuilder("update cust set ");
				int cnt=0;//수정 필드(열) 개수
				if(pwd!=null) {			
					sql.append("pwd=?,");
				}
				if(name!=null) {
					sql.append("name=?,");
				}
				if(mobile!=null) {
					sql.append("mobile=?,");
				}
				//마지막 , 없애고 
				sql=sql.delete(sql.length()-1, sql.length());
				//where 이하 붙이기
				sql.append(" where id=?");
				try {
					pstmt=conn.prepareStatement(sql.toString());
					//?채우기
					if(pwd!=null) {
						cnt++;
						pstmt.setString(cnt, pwd);
					}
					if(name!=null) {
						cnt++;
						pstmt.setString(cnt,name);
					}
					
					if(mobile!=null) {
						cnt++;
						pstmt.setString(cnt,mobile);
					}
					pstmt.setString(++cnt, id);
					result=pstmt.executeUpdate();
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
				this.close(pstmt, conn);			
				
				return result;
			}
	
			public int updateCust(String id,  String pwd) {
				int result=0;
				Connection conn=this.getConnection();
				PreparedStatement pstmt=null;	
				String sql="update cust set active = 0 where id = ? ";
				try {
					pstmt=conn.prepareStatement(sql);
					pstmt.setString(1, id);
					result=pstmt.executeUpdate();
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
				this.close(pstmt, conn);			
				
				return result;
			}
	
}
