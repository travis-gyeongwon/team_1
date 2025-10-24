package kiosk.user.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JoinDecisionDAO {

	private static JoinDecisionDAO jdDAO;
	
	public static JoinDecisionDAO getInstance() {
		if (jdDAO == null) {
			jdDAO = new JoinDecisionDAO();
		} // end if

		return jdDAO;
	}// getInstance
	
	public int insertMember(String phone) throws SQLException, IOException {
		int flag=0;
		
		GetConnection gc=GetConnection.getInstance();
		
		Connection con=null;
		PreparedStatement pstmt=null;
		
		try {
			con=gc.getConnection();
			
			StringBuilder insertMember=new StringBuilder();
			insertMember
			.append("	insert 	into	MEMBER(phone, stamp, coupon)		")
			.append("	values(?, 0, 0)    	 								");
			
			pstmt = con.prepareStatement(insertMember.toString());
			
			pstmt.setString(1, phone);
			
			flag=pstmt.executeUpdate();
		} finally {
			gc.dbClose(con, pstmt, null);
		}// end finally
		
		return flag;
	}// insertMember
	
}// class
