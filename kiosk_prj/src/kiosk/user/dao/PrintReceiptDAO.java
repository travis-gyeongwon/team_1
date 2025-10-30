package kiosk.user.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kiosk.user.dto.OrderDetailDTO;

public class PrintReceiptDAO {

	private static PrintReceiptDAO prDAO;

	public static PrintReceiptDAO getInstance() {
		if (prDAO == null) {
			prDAO = new PrintReceiptDAO();
		} // end if

		return prDAO;
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
			selectOrderList.append("	select	MAX(ORDER_NUM)			").append("	from  	ORDER_LIST    			");

			pstmt = con.prepareStatement(selectOrderList.toString());

			rs = pstmt.executeQuery();

			while (rs.next()) {
				order_num = rs.getString("MAX(ORDER_NUM)");
			} // end while
		} finally {
			gc.dbClose(con, pstmt, rs);
		} // end finally

		return order_num;
	}// selectOrderList

	public List<OrderDetailDTO> selectOrderDetail() throws SQLException, IOException {
		List<OrderDetailDTO> list = new ArrayList<OrderDetailDTO>();

		GetConnection gc = GetConnection.getInstance();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = gc.getConnection();

			StringBuilder selectOrderDetail = new StringBuilder();
			selectOrderDetail.append(
					"	select 	order_detail_num, menu_name, temp_option, size_option, shot_option, amount, order_price		")
					.append("	from  	ORDER_DETAIL    	")
					.append("	where 	ORDER_NUM=(select 	MAX(ORDER_NUM) from ORDER_LIST)			");

			pstmt = con.prepareStatement(selectOrderDetail.toString());

			rs = pstmt.executeQuery();

			String order_detail_num = "";
			String menu_name = "";
			int temp_option = 0;
			int size_option = 0;
			int shot_option = 0;
			int amount = 0;
			int order_price = 0;

			OrderDetailDTO prDTO = null;
			while (rs.next()) {
				order_detail_num = rs.getString("order_detail_num");
				menu_name = rs.getString("menu_name");
				temp_option = rs.getInt("temp_option");
				size_option = rs.getInt("size_option");
				shot_option = rs.getInt("shot_option");
				amount = rs.getInt("amount");
				order_price = rs.getInt("order_price");

//				prDTO = new OrderDetailDTO(order_detail_num, menu_name, temp_option, size_option, shot_option, amount,
//						order_price);

				list.add(prDTO);
			} // end while
		} finally {
			gc.dbClose(con, pstmt, rs);
		} // end finally

		return list;
	}// selectOrderDetail

}// class
