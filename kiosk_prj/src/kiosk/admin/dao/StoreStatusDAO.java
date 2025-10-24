package kiosk.admin.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kiosk.admin.dto.StoreStatusDTO;
import kiosk.user.dao.GetConnection;

public class StoreStatusDAO {
	private static StoreStatusDAO ssDAO;
	private StoreStatusDAO() {
	}
	
	public static StoreStatusDAO getInstance() {
		if(ssDAO == null) {
			ssDAO = new StoreStatusDAO();
		}
		return ssDAO;
	}
	
	public StoreStatusDTO selectStoreStatus(String id) throws SQLException, IOException, ClassNotFoundException {
		StoreStatusDTO ssDTO = null;
		
		GetConnection gc = GetConnection.getInstance();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = gc.getConnection();
			
			StringBuilder sbSelect = new StringBuilder();
			sbSelect
			.append("	select status	")
			.append("	from store_status	")
			.append("	where id = ?	");
			pstmt = con.prepareStatement(sbSelect.toString());
			
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			String status = "";
			if(rs.next()) {
				ssDTO = new StoreStatusDTO();
				status = rs.getString("status");
				ssDTO.setStatus(status);
			}
			
		}finally {
			gc.dbClose(con, pstmt, rs);
		}
		
		
		return ssDTO;
	}
	
	public int updateStoreStatus(String openStatus, String id) throws SQLException, IOException, ClassNotFoundException {
		int cnt = 0;
		
		GetConnection gc = GetConnection.getInstance();
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = gc.getConnection();
			
			StringBuilder sbSelect = new StringBuilder();
			sbSelect
			.append("	update store_status 	")
			.append("	set status = ?	")
			.append("	where id = ?	")
			;
			pstmt = con.prepareStatement(sbSelect.toString());
			
			pstmt.setString(1, openStatus);
			pstmt.setString(2, id);
			
			cnt = pstmt.executeUpdate();
			
		}finally {
			gc.dbClose(con, pstmt, null);
		}
		
		return cnt;
	}
}
