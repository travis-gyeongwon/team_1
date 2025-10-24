package kiosk.admin.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kiosk.admin.dto.SalesReportDTO;

public class SalesReportDAO {
	private static SalesReportDAO srDAO;
	
	private SalesReportDAO() {
	}
	
	public static SalesReportDAO getInstance() {
		if(srDAO == null) {
			srDAO = new SalesReportDAO();
		}
		return srDAO;
	}
	
	public List<SalesReportDTO> selectResult(String startDate, String endDate) throws SQLException, IOException, ClassNotFoundException {
		List<SalesReportDTO> list = new ArrayList<SalesReportDTO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		GetConnection gc = GetConnection.getInstance();
		
		try {
			con = gc.getConnection();
			
			//where 입력받은 Date 범위
			//price는 서브쿼리를 통해서 select where size L + select where plusShot 로 추가 비용까지 더해서 나오기 -> 이미 합해서 저장되어있기 때문에 필요없음
			StringBuilder selectReport = new StringBuilder();
			selectReport
			.append("	select od.amount, od.order_price, od.menu_name		")
			.append("	from order_detail od, order_list ol					")
			.append("	where (od.order_num = ol.order_num)					")
			.append("	and ol.order_time between to_date(?, 'yyyy-mm-dd') and to_date(?, 'yyyy-mm-dd')+1");
			//입력받을 값이 20251020 형식이면 yyyymmdd / 2025-10-20 형식이면 yyyy-mm-dd / 2025.10.20 형식이면 yyyy.mm.dd
			//endDate의 경우 전날 23시 59까지이므로 endDate 범위 주의할 것
			
			pstmt = con.prepareStatement(selectReport.toString());
			
			pstmt.setString(1, startDate);
			pstmt.setString(2, endDate);
			
			rs = pstmt.executeQuery();
			
			int count = 0;
			int price = 0;
			String name = "";
			
			while(rs.next()) {
				count = rs.getInt("amount");
				price = rs.getInt("order_price");
				name = rs.getString("menu_name");
				
				SalesReportDTO srDTO = new SalesReportDTO(count, price, name);
				list.add(srDTO);
			}
			
		}finally {
			gc.close(con, pstmt, rs);
		}
		
		return list;
	}
	
	public List<String> menuNameList() throws SQLException, IOException, ClassNotFoundException {
		List<String> menuName = new ArrayList<String>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		GetConnection gc = GetConnection.getInstance();
		
		try {
			con = gc.getConnection();
			StringBuilder selectMenu = new StringBuilder();
			selectMenu
			.append("	select menu_name	")
			.append("	from menu	");
			
			pstmt = con.prepareStatement(selectMenu.toString());
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				menuName.add(rs.getString("menu_name"));
			}
			
		} finally {
			gc.close(con, pstmt, rs);
		}
		
		return menuName;
	}
}
