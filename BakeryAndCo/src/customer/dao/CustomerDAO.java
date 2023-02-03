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
	
	public int createCustomer(String userID, String password, String name, String mobile, String addr) {
		int result = 0;
		Connection conn = this.getConnection();
		String sql = null;
		if (addr == null) {
			sql = "insert into customerTBL(userID, pwd, name, mobile, enrolldate)" + " values (?,?,?,?, sysdate)";
		} else {
			sql = "insert into customerTBL(userID, pwd, name, mobile, addr, enrolldate)" + " values (?,?,?,?,?, sysdate)";
		}
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userID);
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
	} // customerTBLInsert
	
	
		//고객 조회
		public ArrayList<CustomerVO> selectCustomerALL(){
			ArrayList<CustomerVO> list =null;
			Connection conn = this.getConnection();
			PreparedStatement pstmt=null;	
			ResultSet rs=null;
			CustomerVO vo=null;
			String sql="select * from CustomerTBL order by userID";
			//userID,pwd,name,birthDate,mobile,email,addr,active,enrolldate 
			//3. PreparedStatement 객체생성
			try {
				pstmt=conn.prepareStatement(sql);
				//? 채우기 x
				// 쿼리문 전송 결과 받기
				rs=pstmt.executeQuery();
				if(rs.next()) {//읽은튜플이 하나이상 있는가?
					list=new ArrayList<CustomerVO>();//ArrayList 객체 생성
					do {
						vo=new CustomerVO(rs.getString("userID") ,  rs.getString("name") , rs.getString("pwd") ,
								rs.getString("birthDate") , rs.getString("email")  , rs.getString("mobile"),
								rs.getString("addr") ,rs.getInt("active") ,rs.getDate("enrollDate") );
						//String userID, String pwd, String name, String birthDate, String mobile, String email, String addr,
						//int active, Timestamp enrollDate
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
		
		//고객 조회
			public CustomerVO selectCustomer(String userID,String pwd){
				CustomerVO vo=null;
				Connection conn = this.getConnection();
				PreparedStatement pstmt=null;	
				ResultSet rs=null;
				String sql="select userID,pwd,name,email,mobile,addr,birthDate,enrollDate from CustomerTBL where userID=? and pwd=?";
				try {
					pstmt=conn.prepareStatement(sql);
					pstmt.setString(1, userID);pstmt.setString(2, pwdEncrypt(pwd));
					// 쿼리문 전송 결과 받기
					rs=pstmt.executeQuery();
					if(rs.next()) {//읽은튜플이 하나이상 있는가?
						vo=new CustomerVO(rs.getString(1) , rs.getString(2) , rs.getString(3) , rs.getString(4) ,
								rs.getString(5)  , rs.getString(6), rs.getInt(7) ,rs.getDate(8) );
						}
				} catch (SQLException e) {
					e.printStackTrace();
				}finally {
					this.close(rs, pstmt, conn);
				}
				return vo;
			}
			
			//id로 검색  이미 사용중인 id true반환, 없으면 false 반환
			public boolean selectCustomer(String userID) {
				boolean result=false;
				Connection conn=this.getConnection();
				PreparedStatement pstmt=null;	
				ResultSet rs=null;
				String sql=	"select userID from customertbl where userID=? ";
				//3. PreparedStatement 객체생성
				try {
					pstmt=conn.prepareStatement(sql);
					//? 채우기
					pstmt.setString(1, userID);
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
			public int insertCustomer(String userID, String pwd, String name, String birthDate, String mobile, String email, String addr,
					int active, Date enrollDate) {
				int result=0;
				Connection conn=this.getConnection();
				PreparedStatement pstmt=null;	
				String sql=null;
					sql="insert into customertbl(userID, pwd, name, email, mobile,  addr, birthDate, enrollDate)";
					sql+=" values(?,?,?,?,?,?,?,?,sysdate)";
				try {
					pstmt=conn.prepareStatement(sql);
					//?채우기
					pstmt.setString(1, userID);
					pstmt.setString(2, pwdEncrypt(pwd));
					pstmt.setString(3, name);
					pstmt.setString(5, email);
					pstmt.setString(6, mobile);
					pstmt.setString(7, addr);
					pstmt.setString(8, birthDate);
					result=pstmt.executeUpdate();
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
				this.close(pstmt, conn);
				return result;
			}
			
			//비밀번호 바꾸기 or 초기화 //update
			public int passwordReset(String UserID,String pwd) {
				int result=0;
				
				Connection conn=this.getConnection();
				PreparedStatement pstmt=null;	
				String sql="update customertbl set pwd=? where id=?";
				try {
					pstmt=conn.prepareStatement(sql);
					//?채우기			
					pstmt.setString(1, pwdEncrypt(pwd));
					pstmt.setString(2, UserID);
					result=pstmt.executeUpdate();			
				} catch (SQLException e) {
					e.printStackTrace();
				}
				this.close(pstmt, conn);	
				
				return result;
			}
			

//			- unregisterCustomer
			public int CustomerDelete(String userID,String pwd) {
				int result=0;
				Connection conn=this.getConnection();
				PreparedStatement pstmt=null;	
				String sql="delete from customerTBL where userID=? and pwd=?";
				try {
					pstmt=conn.prepareStatement(sql);
					//?채우기
					pstmt.setString(1, userID);
					pstmt.setString(2, pwdEncrypt(pwd));
					result=pstmt.executeUpdate();
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
				this.close(pstmt, conn);	
						
				return result;
			}
			
			
//			- updateCustomer// 
			public int updateCustomer(String userID,  String pwd, String name,  String email, String  mobile, String addr) {
				int result=0;
				Connection conn=this.getConnection();
				PreparedStatement pstmt=null;	
				StringBuilder sql=new StringBuilder("update customerTBL set ");
				int cnt=0;//수정 필드(열) 개수
				if(pwd!=null) {			
					sql.append("pwd=?,");
				}
				if(name!=null) {
					sql.append("name=?,");
				}
				
				if(email!=null) {
					sql.append("email=?,");
				}//email
				if(mobile!=null) {
					sql.append("mobile=?,");
				}
				
				if(addr!=null) {
					sql.append("addr=?,");
				}
//				if(birthDate!=null) {
//					sql.append("birthDate=?,");
//				}//birthDate
				
				//마지막 , 없애고 
				sql=sql.delete(sql.length()-1, sql.length());
				//where 이하 붙이기
				sql.append(" where id=?");
				try {
					pstmt=conn.prepareStatement(sql.toString());
					//?채우기
					if(pwd!=null) {
						cnt++;
						pstmt.setString(cnt, this.pwdEncrypt(pwd));
					}
					if(name!=null) {
						cnt++;
						pstmt.setString(cnt,name);
					}
					
					if(email!=null) {
						cnt++;
						pstmt.setString(cnt,email);
					}
					if(mobile!=null) {
						cnt++;
						pstmt.setString(cnt,mobile);
					}
					if(addr!=null) {
						cnt++;
						pstmt.setString(cnt,addr);
					}
					pstmt.setString(++cnt, userID);
					result=pstmt.executeUpdate();
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
				this.close(pstmt, conn);			
				
				return result;
			}
			
			
			//회원탈퇴를 active에 1이었던 것을 0으로 변경
			public int unregisterCustomer(String userID,  String pwd) {
				int result=0;
				Connection conn=this.getConnection();
				PreparedStatement pstmt=null;	
				String sql="update customerTBL set active = 0 where userid = ? and pwd = ?";
				try {
					pstmt=conn.prepareStatement(sql);
					pstmt.setString(1, userID);
					pstmt.setString(2, pwdEncrypt(pwd));
					result=pstmt.executeUpdate();
					
				} catch (SQLException e) {
					e.printStackTrace();
				}finally {
					this.close(pstmt, conn);			
				}
				return result;
			}
			

}
