package kiosk.user.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TakeOutDecisionDAO {

	private static TakeOutDecisionDAO todDAO;

	public static TakeOutDecisionDAO getInstance() {
		if (todDAO == null) {
			todDAO = new TakeOutDecisionDAO();
		} // end if

		return todDAO;
	}// getInstance

	public String selectOrderList() throws SQLException, IOException {
		String order_num = "";

		GetConnection gc = GetConnection.getInstance();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = gc.getConnection();

			StringBuilder selectOrderList = new StringBuilder();
			selectOrderList.append("	select 	MAX(ORDER_NUM)			").append("	from 	ORDER_LIST			");

			pstmt = con.prepareStatement(selectOrderList.toString());

			rs = pstmt.executeQuery();
			
			if (rs.next()) {
                order_num = rs.getString("MAX(ORDER_NUM)");
            }// end if
			
		} finally {
			gc.dbClose(con, pstmt, null);
		} // end finally

		System.out.println(order_num);
		return order_num;
	}// selectOrderList

	public int updateOrderList(String takeout_flg) throws SQLException, IOException {
		int flag = 0;

		GetConnection gc = GetConnection.getInstance();

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = gc.getConnection();

			StringBuilder updateOrderList = new StringBuilder();
			updateOrderList.append("	update 	ORDER_LIST			").append("	set 	TAKEOUT_FLG=?    	")
					.append("	where 	ORDER_NUM=?			");

			pstmt = con.prepareStatement(updateOrderList.toString());

			pstmt.setString(1, takeout_flg);
			pstmt.setString(2, todDAO.selectOrderList());

			flag = pstmt.executeUpdate();
		} finally {
			gc.dbClose(con, pstmt, null);
		} // end finally

		return flag;
	}// updateOrderList

}// class
