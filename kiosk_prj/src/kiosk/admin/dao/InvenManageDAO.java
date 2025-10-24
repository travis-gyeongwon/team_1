package kiosk.admin.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kiosk.admin.dto.InvenManageDTO;
import kiosk.user.dao.GetConnection;

public class InvenManageDAO {
	private static InvenManageDAO imDAO;

	private InvenManageDAO() {

	}// InvenManageDAO

	public static InvenManageDAO getInstance() {
		if (imDAO == null) {
			imDAO = new InvenManageDAO();
		} // end if
		return imDAO;
	}// getInstance

	public List<InvenManageDTO> selectAllInven() throws SQLException, IOException {
		List<InvenManageDTO> listImDTO = new ArrayList<InvenManageDTO>();
		GetConnection gc = GetConnection.getInstance();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = gc.getConnection();
			// 3.쿼리문 생성객체 얻기
			String selectMember = "select * from inventory";
			pstmt = con.prepareStatement(selectMember);
			// 4.바인드 변수 설정
			// 5.쿼리문 수행 후 결과 얻기(cursor의 제어권 얻기)
			// 조회 결과를 움직일 수 있는 cursor의 제어권을 받음.
			rs = pstmt.executeQuery();

			String menuName = "";
			int quantity = 0;
			InvenManageDTO imDTO = null;
			while (rs.next()) {// 조회 결과에 다음 레코드가 존재하는지
				menuName = rs.getString("menu_name");
				quantity = rs.getInt("quantity");
				imDTO = new InvenManageDTO(menuName, quantity);
				listImDTO.add(imDTO);
			} // end while

		} finally {
			// 5.연결 끊기
			gc.dbClose(con, pstmt, rs);
		} // end finally

		return listImDTO;
	}// selectAllInven

	public int updateAllInventory() throws SQLException, IOException {
		int flag = 0;

		GetConnection gc = GetConnection.getInstance();
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = gc.getConnection();

			String updateAllInven = "update inventory set quantity= ?, update_at = sysdate ";

			pstmt = con.prepareStatement(updateAllInven);
			int defalutQuantity = 1000;
			pstmt.setInt(1, defalutQuantity);

			flag = pstmt.executeUpdate();

		} finally {
			gc.dbClose(con, pstmt, null);
		} // finally

		return flag;
	}// updateAllInventory
	
	public int updateOneInventory(InvenManageDTO imDTO) throws SQLException, IOException {
		int flag = 0;
		
		GetConnection gc = GetConnection.getInstance();
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = gc.getConnection();

			String updateAllInven = "update inventory set quantity= ?, update_at = sysdate where menu_name = ?";

			pstmt = con.prepareStatement(updateAllInven);
			pstmt.setInt(1, imDTO.getMenuInventory());
			pstmt.setString(2, imDTO.getMenuName());

			flag = pstmt.executeUpdate();

		} finally {
			gc.dbClose(con, pstmt, null);
		} // finally
		
		return flag;
	}// updateOneInventory

}// InvenManageDAO
