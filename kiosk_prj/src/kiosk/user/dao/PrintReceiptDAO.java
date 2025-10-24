package kiosk.user.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kiosk.user.dto.PrintReceiptDTO;

public class PrintReceiptDAO {

	private static PrintReceiptDAO prDAO;

	public static PrintReceiptDAO getInstance() {
		if (prDAO == null) {
			prDAO = new PrintReceiptDAO();
		} // end if

		return prDAO;
	}// getInstance

	public List<PrintReceiptDTO> selectOrderDetail(String order_num) throws SQLException, IOException {
		List<PrintReceiptDTO> list = new ArrayList<PrintReceiptDTO>();

		GetConnection gc = GetConnection.getInstance();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = gc.getConnection();

			StringBuilder selectOrderDetail = new StringBuilder();
			selectOrderDetail.append(
					"	select 	order_detail_num, menu_name, temp_option, size_option, shot_option, amount, order_price		")
					.append("	from  	ORDER_DETAIL    	").append("	where 	ORDER_NUM=?			");

			pstmt = con.prepareStatement(selectOrderDetail.toString());

			pstmt.setString(1, order_num);

			rs = pstmt.executeQuery();

			String order_detail_num = "";
			String menu_name = "";
			int temp_option = 0;
			int size_option = 0;
			int shot_option = 0;
			int amount = 0;
			int order_price = 0;

			PrintReceiptDTO prDTO = null;
			while (rs.next()) {
				order_detail_num = rs.getString("order_detail_num");
				menu_name = rs.getString("menu_name");
				temp_option = rs.getInt("temp_option");
				size_option = rs.getInt("size_option");
				shot_option = rs.getInt("shot_option");
				amount = rs.getInt("amount");
				order_price = rs.getInt("order_price");

				prDTO = new PrintReceiptDTO(order_num, order_detail_num, menu_name, temp_option, size_option,
						shot_option, amount, order_price);

				list.add(prDTO);
			} // end while
		} finally {
			gc.dbClose(con, pstmt, rs);
		} // end finally

		return list;
	}// selectOrderDetail

}// class
