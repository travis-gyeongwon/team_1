package kiosk.admin.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kiosk.admin.dto.LoginDTO;
import kiosk.user.dao.GetConnection;

public class AdminLoginDAO {
	private static AdminLoginDAO alDAO;
	private AdminLoginDAO() {
		
	}
	public static AdminLoginDAO getInstance() {
		if(alDAO == null)
			alDAO = new AdminLoginDAO();
		
		return alDAO;
	}
	
	public LoginDTO selectLogin(String id, String pass) throws SQLException, IOException, ClassNotFoundException {
		LoginDTO lDTO = null;

		GetConnection gc = GetConnection.getInstance();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
		con = gc.getConnection();
		
		StringBuilder sbLogin = new StringBuilder();
		sbLogin
		.append("	select id, password, last_login_at	")
		.append("	from admin	")
		.append("	where id = ? and password = ?	");
		
		pstmt = con.prepareStatement(sbLogin.toString());
		
		pstmt.setString(1, id);
		pstmt.setString(2, pass);
		
		rs = pstmt.executeQuery();
		
		if(rs.next()) {
			lDTO = new LoginDTO();
			lDTO.setId(id);
			lDTO.setLoginDate(rs.getDate("last_login_at"));
			updateLoginDate(id);
		}
		}finally {
			gc.dbClose(con, pstmt, rs);
		}
		return lDTO;
	}
	
	private void updateLoginDate(String id) throws SQLException, IOException, ClassNotFoundException {
		GetConnection gc = GetConnection.getInstance();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
		con = gc.getConnection();
		
		StringBuilder upDate = new StringBuilder();
		upDate
		.append("	update admin 	")
		.append("	set last_login_at = sysdate	")
		.append("	where id = ?	");
		
		pstmt = con.prepareStatement(upDate.toString());
		
		pstmt.setString(1, id);
		
		pstmt.execute();
		}finally {
			gc.dbClose(con, pstmt, null);
		}
	}

}
