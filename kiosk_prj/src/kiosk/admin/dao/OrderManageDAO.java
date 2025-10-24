package kiosk.admin.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kiosk.admin.dto.OrderManageMakeOrderDTO;
import kiosk.user.dao.GetConnection;

public class OrderManageDAO {
	private static OrderManageDAO omDAO;

	private OrderManageDAO() {

	}// OrderManageDAO

	public static OrderManageDAO getInstance() {
		if (omDAO == null) {
			omDAO = new OrderManageDAO();
		} // end if
		return omDAO;
	}// getInstance

	public List<OrderManageMakeOrderDTO> selectAllMakingOrder() throws IOException, SQLException {
		List<OrderManageMakeOrderDTO> listMakeOrder = new ArrayList<OrderManageMakeOrderDTO>();
		GetConnection gc = GetConnection.getInstance();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = gc.getConnection();
			// 3.쿼리문 생성객체 얻기
			StringBuilder selectMakeOrder = new StringBuilder();
			selectMakeOrder
			.append("	select ol.order_num order_num, ol.order_time order_time, detail.menu_name menu_name, sum(detail.order_price) total_price, os.status_text status_text	")
			.append("	from order_list ol, order_detail detail, order_status os	")
			.append("	where detail.order_num = ol.order_num and os.status_code = ol.status_code and (ol.status_code in (1,2,3)) and ol.order_time >= trunc(sysdate)  and ol.order_time < trunc(sysdate) + 1	")
			.append("	group by ol.order_num, ol.order_time, detail.menu_name, os.status_text	");
			pstmt = con.prepareStatement(selectMakeOrder.toString());
			// 4.바인드 변수 설정
			// 5.쿼리문 수행 후 결과 얻기(cursor의 제어권 얻기)
			// 조회 결과를 움직일 수 있는 cursor의 제어권을 받음.
			rs = pstmt.executeQuery();

			String orderNum = "";
			Date orderTime = null;
			String representMenu ="";
			int price = 0;
			String order_status = "";
			
			OrderManageMakeOrderDTO ommoDTO = null;
			while (rs.next()) {// 조회 결과에 다음 레코드가 존재하는지
				orderNum = rs.getString("order_num");
				orderTime = rs.getDate("order_time");
				representMenu = rs.getString("menu_name");
				price = rs.getInt("total_price");
				order_status = rs.getString("status_text");
				ommoDTO = new OrderManageMakeOrderDTO(orderNum, representMenu, order_status, orderTime, price);
				listMakeOrder.add(ommoDTO);
			} // end while

		} finally {
			// 5.연결 끊기
			gc.dbClose(con, pstmt, rs);
		} // end finally
		return listMakeOrder;
		
	}//selectAllMakingOrder
	
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
