package kiosk.user.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kiosk.user.dto.MemberDTO;

public class PrintDecisionDAO {

	private static PrintDecisionDAO pdDAO;

	public static PrintDecisionDAO getInstance() {
		if (pdDAO == null) {
			pdDAO = new PrintDecisionDAO();
		} // end if

		return pdDAO;
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
			selectOrderDetail.append("	select 	AMOUNT													")
					.append("	from  	ORDER_DETAIL    										")
					.append("	where 	ORDER_NUM=(select 	MAX(ORDER_NUM) from ORDER_LIST)		");

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

	public String selectOrderList() throws SQLException, IOException {
		String phone = "";

		GetConnection gc = GetConnection.getInstance();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = gc.getConnection();

			StringBuilder selectOrderList = new StringBuilder();
			selectOrderList.append("	select 	phone		").append("	from  	ORDER_LIST    	")
					.append("	where 	ORDER_NUM=(select 	MAX(ORDER_NUM) from ORDER_LIST)			");

			pstmt = con.prepareStatement(selectOrderList.toString());

			rs = pstmt.executeQuery();

			while (rs.next()) {
				phone = rs.getString("phone");
			} // end while
		} finally {
			gc.dbClose(con, pstmt, rs);
		} // end finally

		return phone;
	}// selectOrderList

	public MemberDTO selectMember(String phone) throws SQLException, IOException {
		MemberDTO mDTO = null;

		GetConnection gc = GetConnection.getInstance();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = gc.getConnection();

			StringBuilder selectMember = new StringBuilder();
			selectMember.append("	select	stamp, coupon						")
					.append("	from  	MEMBER    								")
					.append("	where 	phone=?									");

			pstmt = con.prepareStatement(selectMember.toString());

			pstmt.setString(1, phone);

			rs = pstmt.executeQuery();

			int stamp = 0;
			int coupon = 0;
			while (rs.next()) {
				stamp = rs.getInt("stamp");
				coupon = rs.getInt("coupon");

				mDTO = new MemberDTO(phone, stamp, coupon);
			} // end while
		} finally {
			gc.dbClose(con, pstmt, rs);
		} // end finally

		return mDTO;
	}// selectMember

	public int updateMember(String phone, int stamp, int coupon) throws SQLException, IOException {
		int flag = 0;

		GetConnection gc = GetConnection.getInstance();

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = gc.getConnection();

			StringBuilder updateMember = new StringBuilder();
			updateMember.append("	update 	member			").append("	set 	stamp=?, coupon=?    	")
					.append("	where 	phone=?			");

			pstmt = con.prepareStatement(updateMember.toString());

			pstmt.setInt(1, stamp);
			pstmt.setInt(2, coupon);
			pstmt.setString(3, phone);

			flag = pstmt.executeUpdate();
		} finally {
			gc.dbClose(con, pstmt, null);
		} // end finally

		return flag;
	}// updateMember

	public String selectOrderNum() throws SQLException, IOException {
		String order_num = "";

		GetConnection gc = GetConnection.getInstance();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = gc.getConnection();

			StringBuilder selectPhone = new StringBuilder();
			selectPhone.append("	select	order_num						")
					.append("	from  	ORDER_LIST    												")
					.append("	where 	ORDER_NUM=(select 	MAX(ORDER_NUM) from ORDER_LIST)			");

			pstmt = con.prepareStatement(selectPhone.toString());

			rs = pstmt.executeQuery();

			while (rs.next()) {
				order_num = rs.getString("order_num");
			} // end while
		} finally {
			gc.dbClose(con, pstmt, rs);
		} // end finally

		return order_num;
	}// selectOrderNum

}// class
