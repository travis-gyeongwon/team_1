package kiosk.user.dao;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;


public class StartDAO {
	
	private static StartDAO startDAO;
	
	static public StartDAO getInstance() {
		if(startDAO==null) {
			startDAO=new StartDAO();
		}//end if
		return startDAO;
		
	}//getInstance
	
	
	   public String selectStoreStatus() throws SQLException, IOException {
		      
	       Connection con=null;
	         PreparedStatement pstmt=null;
	         ResultSet rs=null;
	         
	         GetConnection gc=GetConnection.getInstance();
	         
	         String status="";
	         try {
	            con=gc.getConnection();
	            
	         //3.쿼리문 생성객체 얻기
	            StringBuilder selectStoreStatus=new StringBuilder();
	            selectStoreStatus
	            .append("   select status   ")
	            .append("   from store_status      ");
	            
	            
	            pstmt=con.prepareStatement(selectStoreStatus.toString());
	            
	         //4.쿼리문 수행 후 결과 얻기(커서의 제어권을 얻기)

	            rs=pstmt.executeQuery();
	            
	            
	            while(rs.next()) {//조회결과에 다음 레코드가 존재하는지
	               //resultSet은 커서로 조회권을 가진다

	               status=rs.getString("status");

	            }//end while
	            
	         }finally {
	         //5.연결끊기
	            gc.dbClose(con, pstmt, rs);
	         }//end finally
	          
	          return status;
	      
	   }//selectStoreStatus
	
    public ImageIcon selectBestMenu() throws SQLException, IOException {
    	
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
			String menuName="";
			int amount=0;
			
			ImageIcon menuImg=null;
			while(rs.next()) {//조회결과에 다음 레코드가 존재하는지
				//resultSet은 커서로 조회권을 가진다
				menuName=rs.getString("menu_name");
				amount=rs.getInt("amount");
				InputStream is=rs.getBinaryStream("menu_img");

				if(is!=null) {
					BufferedImage bi=ImageIO.read(is);
					menuImg=new ImageIcon(bi);
				}//end if
				
				is.close();
			}//end while
			
			return menuImg;
		}finally {
		//5.연결끊기
			
			gc.dbClose(con, pstmt, rs);
		}//end finally
    	
    }//selectBestMenu
	
	
}//class
