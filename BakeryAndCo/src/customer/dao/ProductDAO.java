package customer.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class ProductDAO {
	static {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	public static ProductDAO dao = null;

	private ProductDAO() {
	}

	public static ProductDAO getInstance() {
		if (dao == null) {
			dao = new ProductDAO();
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
	
	//상품목록 조회
			public ArrayList<ProductVO> selectProductALL(){
				ArrayList<ProductVO> list =null;
				Connection conn = this.getConnection();
				PreparedStatement pstmt=null;	
				ResultSet rs=null;
				String sql="select * from ProductTBL order by prodNum";
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
							vo=new ProductVO(rs.getInt("prodNum"), rs.getString("scategory") , rs.getString("storename") , rs.getString("prodName"), rs.getInt("price") , rs.getInt("inventory") ,
									rs.getString("description") , rs.getDate("registerdate") , rs.getInt("rating")  );
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
	
			public ProductVO selectProduct(int Orderno) {
				ProductVO vo=null;
				//DB검색
				//1. 드라이버 로딩 : 이미완료
				//2. 연결
				Connection conn=this.getConnection();
				PreparedStatement pstmt=null;	
				ResultSet rs=null;
				//3. 쿼리문만들기
				String sql="select * from booktbl where bno=?";
				try {
					//4. pstmt  객체 생성
					pstmt=conn.prepareStatement(sql);
					//5. ? 채우기
					pstmt.setInt(1, Orderno);
					//6. 전송 결과받기
					rs=pstmt.executeQuery();
					if(rs.next()) {//rs 안에 select 수행한 행이 있다
						vo=new ProductVO(rs.getInt("prodNum"), rs.getString("scategory") , rs.getString("storename") , rs.getString("prodName"), rs.getInt("price") , rs.getInt("inventory") ,
								rs.getString("description") , rs.getDate("registerdate") , rs.getInt("rating")  );
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}finally {
					this.close(rs,pstmt,conn);		
				}
				return vo;
			}//
			
			//주문목록 조회
			public ArrayList<OrderVO> selectOrderALL(){
				ArrayList<OrderVO> list =null;
				Connection conn = this.getConnection();
				PreparedStatement pstmt=null;	
				ResultSet rs=null;
				String sql="select * from OrderTBL order by orderNo";
				OrderVO vo=null;
				//3. PreparedStatement 객체생성
				try {
					pstmt=conn.prepareStatement(sql);
					//? 채우기 x
					// 쿼리문 전송 결과 받기
					rs=pstmt.executeQuery();
					if(rs.next()) {//읽은튜플이 하나이상 있는가?
						list=new ArrayList<OrderVO>();//ArrayList 객체 생성
						do {
							vo=new OrderVO(rs.getInt("orderNo") , rs.getInt("prodNum") , rs.getString("prodName") , rs.getString("storeName") , rs.getString("userID") 
									, rs.getInt("quantity") , rs.getInt("cost") , rs.getString("shippingCost") , rs.getString("review") ,
									rs.getBoolean("orderCompleted") , rs.getDate("orderDate")  );
							//int orderNo, int prodNum, String prodName, String storeName, String userID, int quantity,
							//int cost, String shippingCost, String review, boolean orderCompleted, Date orderDate
							
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
			
			//주문등록
			public int insertOrder(int orderNo, int prodNum, String prodName, String storeName, String userID, int quantity,
					int cost, String shippingCost, String review, boolean orderCompleted, Date orderDate) {
				int result=0;
				Connection conn=this.getConnection();
				PreparedStatement pstmt=null;	
				String sql=null;
				// orderNo,  prodNum,  prodName,  storeName,  userID,  quantity,
				// cost,  shippingCost,  review,  orderCompleted,  orderDate
					sql="insert into OrderTBL values(?,?,?,?,?,?,?,?,?,?,sysdate)";
				try {
					pstmt=conn.prepareStatement(sql);
					//?채우기
					pstmt.setInt(1, orderNo);
					pstmt.setInt(2, prodNum);
					pstmt.setString(3, prodName);
					pstmt.setString(4, storeName);
					pstmt.setString(5, userID);
					pstmt.setInt(6, quantity);
					pstmt.setInt(7, cost);
					pstmt.setString(8, shippingCost);
					pstmt.setString(9, review);
					pstmt.setBoolean(10, orderCompleted);
					pstmt.setDate(11, (java.sql.Date)orderDate);
					result=pstmt.executeUpdate();
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
				this.close(pstmt, conn);
				return result;
			}
			
			
			
	
	
	
}//ProductDAO
