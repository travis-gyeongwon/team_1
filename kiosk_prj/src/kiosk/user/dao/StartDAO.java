package kiosk.user.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class StartDAO {
	
	private static StartDAO startDAO;
	
	static public StartDAO getInstance() {
		if(startDAO==null) {
			startDAO=new StartDAO();
		}//end if
		return startDAO;
		
	}//getInstance
	
    public String selectBestMenu() throws SQLException, IOException {
    	
    	Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		GetConnection gc=GetConnection.getInstance();
		
		try {
			con=gc.getConnection();
			
		//3.쿼리문 생성객체 얻기
			StringBuilder selectBestMenu=new StringBuilder();
			selectBestMenu
			.append("	select menu_img	")
			.append("	from menu	")
			.append("	where menu_name=(select menu_name	")
			.append("	                 from (	")
			.append("	   select  menu_name,sum(amount) as total	")
			.append("	   from     order_detail	")
			.append("	    group by menu_name	")
			.append("	    order by  total desc)	")
			.append("	                 where rownum=1)	");
			
			
			pstmt=con.prepareStatement(selectBestMenu.toString());
			
		//4.쿼리문 수행 후 결과 얻기(커서의 제어권을 얻기)

			rs=pstmt.executeQuery();
			String menuImg="";
			String menuName="";
			int amount=0;
			
			
			while(rs.next()) {//조회결과에 다음 레코드가 존재하는지
				//resultSet은 커서로 조회권을 가진다

				menuImg=rs.getString("menu_img");
				menuName=rs.getString("menu_name");
				amount=rs.getInt(amount);

			}//end while
			
		}finally {
		//5.연결끊기
			gc.dbClose(con, pstmt, rs);
		}//end finally
    	
    	return "";
    }//selectBestMenu
	
	
}//class
