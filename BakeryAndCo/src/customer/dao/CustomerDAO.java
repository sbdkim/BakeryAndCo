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
	
	
		
		//고객 조회
		public ArrayList<CustomerVO> selectCustomerALL(){
			ArrayList<CustomerVO> list =null;
			Connection conn = this.getConnection();
			PreparedStatement pstmt=null;	
			ResultSet rs=null;
			String sql="select userID,name,grade,pwd,BirthDate,gender,email,mobile,addr,enrolldate"
					+ " from CustomerTBL order by userID";
			CustomerVO vo=null;
			//3. PreparedStatement 객체생성
			try {
				pstmt=conn.prepareStatement(sql);
				//? 채우기 x
				// 쿼리문 전송 결과 받기
				rs=pstmt.executeQuery();
				if(rs.next()) {//읽은튜플이 하나이상 있는가?
					list=new ArrayList<CustomerVO>();//ArrayList 객체 생성
					do {
						vo=new CustomerVO(rs.getString("userID") ,  rs.getString("pwd") , rs.getString("name") , rs.getString("gender") ,
								 rs.getString("email"),  rs.getString("mobile")  , 
								rs.getString("addr") ,rs.getDate("birthDate")  , rs.getTimestamp("enrollDate") );
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
		
		//String userID, String pwd, String name, String gender, String email, String mobile, String addr,
				//Date birthDate, Timestamp enrollDate
		//고객 조회
			public CustomerVO selectCustomer(String userID,String pwd){
				Connection conn = this.getConnection();
				PreparedStatement pstmt=null;	
				ResultSet rs=null;
				String sql="select userID,name,grade,pwd,BirthDate,gender,email,mobile,addr,enrolldate"
						+ " from CustomerTBL where userID=? and pwd=?";
				CustomerVO vo=null;
				//3. PreparedStatement 객체생성
				try {
					pstmt=conn.prepareStatement(sql);
					pstmt.setString(1, userID);pstmt.setString(2, pwdEncrypt(pwd));
					//? 채우기 x
					// 쿼리문 전송 결과 받기
					rs=pstmt.executeQuery();
					if(rs.next()) {//읽은튜플이 하나이상 있는가?
							vo=new CustomerVO(rs.getString("userID") ,  rs.getString("pwd") , rs.getString("name") , rs.getString("gender") ,
									 rs.getString("email"),  rs.getString("mobile")  , 
										rs.getString("addr") ,rs.getDate("birthDate")  , rs.getTimestamp("enrollDate")  );
						}while(rs.next());
					
				} catch (SQLException e) {
					e.printStackTrace();
				}finally {
					this.close(rs, pstmt, conn);
				}
				
				return vo;
			}
		
			//Create Customer .. 회원가입
			public int insertCustomer(String userID, String pwd, String name, String gender, String email, String mobile, String addr,
					Date birthDate, Timestamp enrollDate
					) {
				int result=0;
				Connection conn=this.getConnection();
				PreparedStatement pstmt=null;	
				String sql=null;
				/*if(addr==null) {
					sql="insert into membertbl(id,password,name,mobile,reg_date)";
					sql+=" values(?,?,?,?,sysdate)";
				
				}else {*/
					sql="insert into membertbl(userID, pwd, name, gender, email, mobile,  addr, birthDate, enrollDate)";
					sql+=" values(?,?,?,?,?,?,?,?,sysdate)";
				//}
				try {
					pstmt=conn.prepareStatement(sql);
					//?채우기
					pstmt.setString(1, userID);
					pstmt.setString(2, pwdEncrypt(pwd));
					pstmt.setString(3, name);
					pstmt.setString(4, gender);
//					if(addr!=null)pstmt.setString(5, addr);
					pstmt.setString(5, email);
					pstmt.setString(6, mobile);
					pstmt.setString(7, addr);
					pstmt.setDate(8, (java.sql.Date)birthDate);
					pstmt.setTimestamp(9, enrollDate);
					result=pstmt.executeUpdate();
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
				this.close(pstmt, conn);
				return result;
			}
			
			/*생성해야하는 메소드 LIST: 
			- DONE: createCustomer //memberDAO insertMember이랑 비슷합니다//
			
			
			- passwordReset 
			
				 */
			
			//비밀번호 바꾸기 or 초기화 //update
			public int passwordReset(String UserID,String pwd) {
				int result=0;
				
				Connection conn=this.getConnection();
				PreparedStatement pstmt=null;	
				String sql="update membertbl set pwd=? where id=?";
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
			
			
//			- viewCompletedOrder - orderTBL (orderCompleted - True)
			
			
			
			
			
			
			
			
//			- writeReview
//			- viewCurrentOrder
//			- viewRegionStore - 지역에 있는 가게들 출력
			
			
//			- viewProductCategory - 스토 번호를 입력하면 그 스토에 관련된 모든 프로덕트 케티고리 출력
//			- viewProduct - 케티고리를 누르면 그 스토에 관련된 모든 케티고리에 해당되는 프로덕트 출력
//			- updateCustomer 
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
	

}
