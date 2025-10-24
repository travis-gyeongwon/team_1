package kiosk.user.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SaveStampDAO {

	private static SaveStampDAO ssDAO;
	
	public static SaveStampDAO getInstance() {
		if (ssDAO == null) {
			ssDAO = new SaveStampDAO();
		} // end if

		return ssDAO;
	}// getInstance
	
	public int selectMember(String phone) throws SQLException, IOException {
		int flag=0;
		
		GetConnection gc=GetConnection.getInstance();
		
		Connection con=null;
		PreparedStatement pstmt=null;
		
		try {
			con=gc.getConnection();
			
			StringBuilder selectMember=new StringBuilder();
			selectMember
			.append("	select 	STAMP			")
			.append("	from 	MEMBER       	")
			.append("	where 	PHONE=?			");
			
			pstmt = con.prepareStatement(selectMember.toString());
			
			pstmt.setString(1, phone);
			
			flag=pstmt.executeUpdate();
		} finally {
			gc.dbClose(con, pstmt, null);
		}// end finally
		
		return flag;
	}// selectMember
	
}// class
