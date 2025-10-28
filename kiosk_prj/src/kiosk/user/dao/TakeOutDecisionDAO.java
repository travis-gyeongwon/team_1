package kiosk.user.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TakeOutDecisionDAO {

	private static TakeOutDecisionDAO todDAO;

	public static TakeOutDecisionDAO getInstance() {
		if (todDAO == null) {
			todDAO = new TakeOutDecisionDAO();
		} // end if

		return todDAO;
	}// getInstance

	public int updateOrderList(String takeout_flg) throws SQLException, IOException {
		int flag = 0;

		GetConnection gc = GetConnection.getInstance();

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = gc.getConnection();

			StringBuilder updateOrderList = new StringBuilder();
			updateOrderList.append("	update 	ORDER_LIST			").append("	set 	TAKEOUT_FLG=?    	")
					.append("	where 	ORDER_NUM=(select 	MAX(ORDER_NUM) from ORDER_LIST)			");

			pstmt = con.prepareStatement(updateOrderList.toString());

			pstmt.setString(1, takeout_flg);

			flag = pstmt.executeUpdate();
		} finally {
			gc.dbClose(con, pstmt, null);
		} // end finally

		return flag;
	}// updateOrderList

}// class
