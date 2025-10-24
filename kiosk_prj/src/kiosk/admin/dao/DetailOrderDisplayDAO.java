package kiosk.admin.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kiosk.admin.dto.DetailOrderDisplayOrderDTO;
import kiosk.admin.dto.DetailOrderDisplayProductDTO;
import kiosk.user.dao.GetConnection;

public class DetailOrderDisplayDAO {

	private static DetailOrderDisplayDAO dodDAO;
	
	private DetailOrderDisplayDAO() {
		
	}// DetailOrderDisplayDAO
	
	public static DetailOrderDisplayDAO getInstance() {
		if (dodDAO == null) {
			dodDAO = new DetailOrderDisplayDAO();
		} // end if
		return dodDAO;
	}// getInstance
	
	public DetailOrderDisplayOrderDTO selecteOneMakingOrder(String orderNum) throws SQLException, IOException{
		DetailOrderDisplayOrderDTO dodoDTO = null;
		GetConnection gc =GetConnection.getInstance();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = gc.getConnection();
			// 3.쿼리문 생성객체 얻기
			StringBuilder selectMember = new StringBuilder();
			selectMember
			.append("	select de.menu_name menu_name, te.temp_text temp_text, si.size_text size_text, sh.shot_text shot_text, de.amount amount, de.order_num order_num, os.status_text status_text,  ol.order_time order_time,  (select sum(order_price) from order_detail where order_num = de.order_num) as total_order_price, ol.takeout_flg takeout_flag  	")
			.append("	from order_detail de, temp te, size_table si, shot sh, order_status os, order_list ol 	")
			.append("	where de.order_num = ? and te.temp_code = de.temp_option and de.size_option = si.size_code and de.shot_option = sh.shot_code and os.status_code = ol.status_code and ol.order_num = de.order_num	");	
			pstmt = con.prepareStatement(selectMember.toString());
			// 4.바인드 변수 설정
			pstmt.setString(1, orderNum);
			// 5.쿼리문 수행 후 결과 얻기(cursor의 제어권 얻기)
			// 조회 결과를 움직일 수 있는 cursor의 제어권을 받음.
			rs = pstmt.executeQuery();

			
			Date orderTime = null;
			List<DetailOrderDisplayProductDTO> listProduct = new ArrayList<DetailOrderDisplayProductDTO>();
			int totalprice = 0;
			String orderStatus = "";
			DetailOrderDisplayProductDTO dodpDTO = null;
			String takeOutFlag = "";
			
			while (rs.next()) {// 조회 결과에 다음 레코드가 존재하는지
				orderTime = rs.getDate("order_time");
				dodpDTO = new DetailOrderDisplayProductDTO(rs.getString("menu_name"), rs.getString("temp_text"), rs.getString("size_text"), rs.getString("shot_text"), rs.getInt("amount"));
				listProduct.add(dodpDTO);
				totalprice = rs.getInt("total_order_price");
				orderStatus = rs.getString("status_text");
				takeOutFlag = rs.getString("takeout_flag");
			} // end while
			dodoDTO = new DetailOrderDisplayOrderDTO(orderNum, totalprice, listProduct, orderStatus, orderTime, takeOutFlag);
		} finally {
			// 5.연결 끊기
			gc.dbClose(con, pstmt, rs);
		} // end finally
		
		return dodoDTO;
	}// selecteOneMakingOrder
	
	public int updateOrderStatus(String orderNum) throws IOException, SQLException{
		int flag = 0;
		
		GetConnection gc = GetConnection.getInstance();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = gc.getConnection();
			// 3.쿼리문 생성객체 얻기
			StringBuilder updateOrderStatus = new StringBuilder();
			updateOrderStatus
			.append("	update order_list	")
			.append("	set status_code= status_code+1	")
			.append("	where order_num = ?	");
			pstmt = con.prepareStatement(updateOrderStatus.toString());
			// 4.바인드 변수 설정
			pstmt.setString(1, orderNum);
			// 5.쿼리문 수행 후 결과 얻기
			flag = pstmt.executeUpdate();

		} finally {
			// 5.연결 끊기
			gc.dbClose(con, pstmt, rs);
		} // end finally
		
		return flag;
	}// updateOrderStatus
	
}// class
