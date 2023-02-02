package customer.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class OrderDAO {
	Scanner sc = new Scanner(System.in);
	
	static {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	public static OrderDAO dao = null;

	private OrderDAO() {
	}

	public static OrderDAO getInstance() {
		if (dao == null) {
			dao = new OrderDAO();
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
	
	
	/*
	 로그인을 했다. -> 로그인 된 사람의 정보를 받아와서 vo에 입력 
	 사용자 정보를 받고  주문목록에 넣어
	 상품목록에서 고른 상품 정보를 받고 주문목록에 넣어
	 
	 
	 보내 삽입부분인 주문목록 DAO에 인서트를 만들어서
	 받아서 들어온 자료를 vo에 넣어
	 최종적으로 vo의 것들을 list로 출력
	*/
	
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
	
	
	
	
	
	
}//OrderDAO
