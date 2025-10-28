package kiosk.user.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EndDAO {
	
	private static EndDAO endDAO;
	
	public static EndDAO getInstance() {
		if(endDAO==null) {
			endDAO=new EndDAO();
		}//end if
		
		return endDAO;
	}//getInstance
	
	
	
	public void updateInventory(String menuName, int amount) throws SQLException, IOException {
	GetConnection gc=GetConnection.getInstance();
		
		Connection con=null;
		PreparedStatement pstmt=null;
		
		try {
			//1.드라이버로딩
			//2.커넥션얻기
		con=gc.getConnection();
		
		StringBuilder updateCheckOut=new StringBuilder();
		updateCheckOut
		.append("	update inventory							")
		.append("	set     quantity=quantity-? 					")
		.append("	 where  menu_name=?     ");
	
		
		//3.쿼리문 생성 객체 얻기
		pstmt=con.prepareStatement(updateCheckOut.toString());
		
		//4.바인드변수에 값 설정
		pstmt.setInt(1, amount);
		pstmt.setString(2, menuName);
		//5.쿼리문 수행 후 결과 얻기
		pstmt.executeUpdate();
		
		}finally {
		//6.연결 끊기
		gc.dbClose(con, pstmt, null);
		}//end finally
	}//updateInventory
	
	public void updateOrderStatus(String orderNum) throws SQLException, IOException {
	GetConnection gc=GetConnection.getInstance();
		
		Connection con=null;
		PreparedStatement pstmt=null;
		
		try {
			//1.드라이버로딩
			//2.커넥션얻기
		con=gc.getConnection();
		
		StringBuilder updateCheckOut=new StringBuilder();
		updateCheckOut
		.append("	update order_list	")
		.append("	set    status_code=1 ")
		.append("	where order_num=?     ");
		
		//3.쿼리문 생성 객체 얻기
		pstmt=con.prepareStatement(updateCheckOut.toString());
		
		//4.바인드변수에 값 설정
		pstmt.setString(1, orderNum);
		//5.쿼리문 수행 후 결과 얻기
		pstmt.executeUpdate();
		
		}finally {
		//6.연결 끊기
		gc.dbClose(con, pstmt, null);
		}//end finally
	}//updateOrderStatus

}//class
