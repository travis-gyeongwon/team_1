package kiosk.admin.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
	
	public String selectStoreStatus(String id) throws SQLException, IOException, ClassNotFoundException {
		String storeStatus = "";
		
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
			
			if(rs.next()) {
				storeStatus = rs.getString("status");
			}
			
		}finally {
			gc.dbClose(con, pstmt, rs);
		}
		
		
		return storeStatus;
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
	
	public boolean selectOrderListToday() throws SQLException, IOException {
		boolean flag = false;
		
		GetConnection gc = GetConnection.getInstance();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = gc.getConnection();
			
			StringBuilder sbSelect = new StringBuilder();
			sbSelect
			.append("	select order_num	")
			.append("	from order_list	")
			.append("	where status_code in (1,2,3) and trunc(order_time) = trunc(sysdate)	");
			pstmt = con.prepareStatement(sbSelect.toString());
			
			rs = pstmt.executeQuery();
			
			
			if(rs.next()) {
				flag = true;
			}
			
		}finally {
			gc.dbClose(con, pstmt, rs);
		}
		
		return flag;
	}
}
