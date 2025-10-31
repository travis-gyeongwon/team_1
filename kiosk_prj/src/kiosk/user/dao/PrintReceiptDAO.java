package kiosk.user.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import kiosk.user.dto.MemberDTO;
import kiosk.user.dto.OrderDetailDTO;
import kiosk.user.dto.OrderListDTO;

public class PrintReceiptDAO {

	private static PrintReceiptDAO prDAO;

	public static PrintReceiptDAO getInstance() {
		if (prDAO == null) {
			prDAO = new PrintReceiptDAO();
		} // end if

		return prDAO;
	}// getInstance

	public OrderListDTO selectOrderList() throws SQLException, IOException {
		OrderListDTO olDTO = null;

		GetConnection gc = GetConnection.getInstance();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = gc.getConnection();

			StringBuilder selectOrderList = new StringBuilder();
			selectOrderList
			.append("	select	takeout_flg, order_time, checkout_typecode					")
			.append("	from  	ORDER_LIST    												")
			.append("	where 	ORDER_NUM=(select 	MAX(ORDER_NUM) from ORDER_LIST)			");

			pstmt = con.prepareStatement(selectOrderList.toString());

			rs = pstmt.executeQuery();

			String takeout_flg = "";
			Timestamp order_time = null;
			int checkout_code = 0;
			while (rs.next()) {
				takeout_flg = rs.getString("takeout_flg");
				order_time = rs.getTimestamp("order_time");
				checkout_code = rs.getInt("checkout_typecode");

				olDTO = new OrderListDTO(takeout_flg, order_time, checkout_code);
			} // end while
		} finally {
			gc.dbClose(con, pstmt, rs);
		} // end finally

		return olDTO;
	}// selectOrderList

	public String selectPhone() throws SQLException, IOException {
		String phone = "";

		GetConnection gc = GetConnection.getInstance();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = gc.getConnection();

			StringBuilder selectPhone = new StringBuilder();
			selectPhone.append("	select	phone						")
					.append("	from  	ORDER_LIST    												")
					.append("	where 	ORDER_NUM=(select 	MAX(ORDER_NUM) from ORDER_LIST)			");

			pstmt = con.prepareStatement(selectPhone.toString());

			rs = pstmt.executeQuery();

			while (rs.next()) {
				phone = rs.getString("phone");
			} // end while
		} finally {
			gc.dbClose(con, pstmt, rs);
		} // end finally

		return phone;
	}// selectPhone

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

	public List<OrderDetailDTO> selectOrderDetail() throws SQLException, IOException {
		List<OrderDetailDTO> list = new ArrayList<OrderDetailDTO>();

		GetConnection gc = GetConnection.getInstance();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = gc.getConnection();

			StringBuilder selectOrderDetail = new StringBuilder();
			selectOrderDetail
					.append("	select  menu_name, temp_option, size_option, shot_option, amount, order_price		")
					.append("	from  	ORDER_DETAIL    															")
					.append("	where 	ORDER_NUM=(select 	MAX(ORDER_NUM) from ORDER_LIST)							");

			pstmt = con.prepareStatement(selectOrderDetail.toString());

			rs = pstmt.executeQuery();

			String menu_name = "";
			int temp_option = 0;
			int size_option = 0;
			int shot_option = 0;
			int amount = 0;
			int order_price = 0;
			OrderDetailDTO odDTO = null;
			while (rs.next()) {
				menu_name = rs.getString("menu_name");
				temp_option = rs.getInt("temp_option");
				size_option = rs.getInt("size_option");
				shot_option = rs.getInt("shot_option");
				amount = rs.getInt("amount");
				order_price = rs.getInt("order_price");

				odDTO = new OrderDetailDTO(menu_name, temp_option, size_option, shot_option, amount, order_price);

				list.add(odDTO);
			} // end while
		} finally {
			gc.dbClose(con, pstmt, rs);
		} // end finally

		return list;
	}// selectOrderDetail

}// class
