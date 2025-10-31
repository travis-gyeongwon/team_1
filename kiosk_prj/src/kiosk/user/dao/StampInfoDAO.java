package kiosk.user.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StampInfoDAO {

	private static StampInfoDAO siDAO;

	public static StampInfoDAO getInstance() {
		if (siDAO == null) {
			siDAO = new StampInfoDAO();
		} // end if

		return siDAO;
	}// getInstance

	public int selectOrderDetail() throws SQLException, IOException {
		int total_amount = 0;

		GetConnection gc = GetConnection.getInstance();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = gc.getConnection();

			StringBuilder selectOrderDetail = new StringBuilder();
			selectOrderDetail.append("	select 	AMOUNT		").append("	from  	ORDER_DETAIL    	")
					.append("	where 	ORDER_NUM=(select 	MAX(ORDER_NUM) from ORDER_LIST)			");

			pstmt = con.prepareStatement(selectOrderDetail.toString());

			rs = pstmt.executeQuery();

			while (rs.next()) {
				total_amount += rs.getInt("amount");
			} // end while
		} finally {
			gc.dbClose(con, pstmt, rs);
		} // end finally

		return total_amount;
	}// selectOrderDetail

	public int updateOrderList(String phone) throws SQLException, IOException {
		int flag = 0;

		GetConnection gc = GetConnection.getInstance();

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = gc.getConnection();

			StringBuilder updateOrderList = new StringBuilder();
			updateOrderList.append("	update 	ORDER_LIST			").append("	set 	phone=?    	")
					.append("	where 	ORDER_NUM=(select 	MAX(ORDER_NUM) from ORDER_LIST)			");

			pstmt = con.prepareStatement(updateOrderList.toString());

			pstmt.setString(1, phone);

			flag = pstmt.executeUpdate();
		} finally {
			gc.dbClose(con, pstmt, null);
		} // end finally

		return flag;
	}// updateOrderList

}// class
