package customer0204.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

	// 상품목록 조회
	public ArrayList<ProductVO> selectProductALL() {
		ArrayList<ProductVO> list = null;
		Connection conn = this.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from productTBL order by prodNum";
		ProductVO vo = null;
		// 3. PreparedStatement 객체생성
		try {
			pstmt = conn.prepareStatement(sql);
			// ? 채우기 x
			// 쿼리문 전송 결과 받기
			rs = pstmt.executeQuery();
			if (rs.next()) {// 읽은튜플이 하나이상 있는가?
				list = new ArrayList<ProductVO>();// ArrayList 객체 생성
				do {
					vo = new ProductVO(rs.getInt("prodNum"), rs.getString("category"), rs.getString("storeName"),
							rs.getString("prodName"), rs.getInt("price"), rs.getInt("inventory"),
							rs.getString("description"), rs.getDate("registerDate"), rs.getInt("rating"));
					list.add(vo);// ArrayList에 vo 객체 담기
				} while (rs.next());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close(rs, pstmt, conn);
		}

		return list;
	}

}