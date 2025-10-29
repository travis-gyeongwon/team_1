package kiosk.user.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SaveStampDAO {

	private static SaveStampDAO ssDAO;

	public static SaveStampDAO getInstance() {
		if (ssDAO == null) {
			ssDAO = new SaveStampDAO();
		} // end if

		return ssDAO;
	}// getInstance

	public int selectMember(String phone) throws SQLException, IOException {
		int flag = 0;

		GetConnection gc = GetConnection.getInstance();

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = gc.getConnection();

			StringBuilder selectMember = new StringBuilder();
			selectMember.append("	select 	STAMP			").append("	from 	MEMBER       	")
					.append("	where 	PHONE=?			");

			pstmt = con.prepareStatement(selectMember.toString());

			pstmt.setString(1, phone);

			flag = pstmt.executeUpdate();
		} finally {
			gc.dbClose(con, pstmt, null);
		} // end finally

		return flag;
	}// selectMember

	public int deleteOrderList() throws SQLException, IOException {
		int flag = 0;

		GetConnection gc = GetConnection.getInstance();

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = gc.getConnection();

			StringBuilder deleteOrderList = new StringBuilder();
			deleteOrderList.append("	delete 	from	ORDER_LIST			                            ")
					.append("	where 	ORDER_NUM=(select 	MAX(ORDER_NUM) from ORDER_LIST)		");

			pstmt = con.prepareStatement(deleteOrderList.toString());

			flag = pstmt.executeUpdate();
		} finally {
			gc.dbClose(con, pstmt, null);
		} // end finally

		return flag;
	}// deleteOrderList

	public int deleteOrderDetail() throws SQLException, IOException {
		int flag = 0;

		GetConnection gc = GetConnection.getInstance();

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = gc.getConnection();

			StringBuilder deleteOrderDetail = new StringBuilder();
			deleteOrderDetail.append("	delete 	from	ORDER_DETAIL			                        ")
					.append("	where 	ORDER_NUM=(select 	MAX(ORDER_NUM) from ORDER_LIST)		");

			pstmt = con.prepareStatement(deleteOrderDetail.toString());

			flag = pstmt.executeUpdate();
		} finally {
			gc.dbClose(con, pstmt, null);
		} // end finally

		return flag;
	}// deleteOrderDetail

}// class
