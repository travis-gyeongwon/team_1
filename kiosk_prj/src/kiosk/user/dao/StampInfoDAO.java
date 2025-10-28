package kiosk.user.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kiosk.user.dto.StampInfoDTO;

public class StampInfoDAO {

	private static StampInfoDAO siDAO;

	public static StampInfoDAO getInstance() {
		if (siDAO == null) {
			siDAO = new StampInfoDAO();
		} // end if

		return siDAO;
	}// getInstance

	public List<StampInfoDTO> selectOrderDetail() throws SQLException, IOException {
		List<StampInfoDTO> list = new ArrayList<StampInfoDTO>();

		GetConnection gc = GetConnection.getInstance();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = gc.getConnection();

			StringBuilder selectOrderDetail = new StringBuilder();
			selectOrderDetail.append("	select 	ORDER_DETAIL_NUM, AMOUNT		")
					.append("	from  	ORDER_DETAIL    	").append("	where 	ORDER_NUM=(select 	MAX(ORDER_NUM) from ORDER_LIST)			");

			pstmt = con.prepareStatement(selectOrderDetail.toString());

			rs = pstmt.executeQuery();

			String order_detail_num = "";
			int amount = 0;

			StampInfoDTO siDTO = null;
			while (rs.next()) {
				order_detail_num = rs.getString("order_detail_num");
				amount = rs.getInt("amount");

				siDTO = new StampInfoDTO(order_detail_num, amount);
				list.add(siDTO);
			} // end while
		} finally {
			gc.dbClose(con, pstmt, rs);
		} // end finally

		return list;
	}// selectOrderDetail

	public int selectMember(String phone) throws SQLException, IOException {
		int pre_stamp = 0;

		GetConnection gc = GetConnection.getInstance();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = gc.getConnection();

			StringBuilder selectMember = new StringBuilder();
			selectMember.append("	select 	stamp		").append("	from  	member    	")
					.append("	where 	phone=?			");

			pstmt = con.prepareStatement(selectMember.toString());
			pstmt.setString(1, phone);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				pre_stamp = rs.getInt("stamp");
			} // end if

		} finally {
			gc.dbClose(con, pstmt, rs);
		} // end finally

		return pre_stamp;
	}// selectMember

	public int updateMember(String phone, int total_stamp) throws SQLException, IOException {
		int flag = 0;

		GetConnection gc = GetConnection.getInstance();

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = gc.getConnection();

			StringBuilder updateMember = new StringBuilder();
			updateMember.append("	update 	member			").append("	set 	stamp=?    	")
					.append("	where 	phone=?			");

			pstmt = con.prepareStatement(updateMember.toString());

			pstmt.setInt(1, total_stamp);
			pstmt.setString(2, phone);

			flag = pstmt.executeUpdate();
		} finally {
			gc.dbClose(con, pstmt, null);
		} // end finally

		return flag;
	}// updateMember

}// class
