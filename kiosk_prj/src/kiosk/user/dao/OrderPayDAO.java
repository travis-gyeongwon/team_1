package kiosk.user.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;

import kiosk.user.dto.OrderPayDTO;

public class OrderPayDAO {
	
private static OrderPayDAO opDAO;
public List<OrderPayDTO> orderList;

	
	static public OrderPayDAO getInstance() {
		if(opDAO==null) {
			opDAO=new OrderPayDAO();
		}//end if
		return opDAO;
		
	}//getInstance
	
	
	public String selectMaxOrderNum() throws SQLException, IOException {
		Connection con=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        
        GetConnection gc=GetConnection.getInstance();
        
        try {
           con=gc.getConnection();
           
        //3.쿼리문 생성객체 얻기
           StringBuilder selectMaxOrderNum=new StringBuilder();
           selectMaxOrderNum
           .append("	select max(to_number(ORDER_NUM)) as order_num	")
           .append("	from  order_detail	");
           pstmt=con.prepareStatement(selectMaxOrderNum.toString());
           
        //4.쿼리문 수행 후 결과 얻기(커서의 제어권을 얻기)
           String orderNum="";

           rs=pstmt.executeQuery();
           
           while(rs.next()) {//조회결과에 다음 레코드가 존재하는지
        	  orderNum= rs.getString("order_num");
           }//end while
           System.out.println(orderNum);
           return orderNum;
           
        }finally {
        //5.연결끊기
           gc.dbClose(con, pstmt, rs);
        }//end finally
        
		
	}//selectMaxOrderNum
	
	public List<OrderPayDTO> selectOrderDetail(String orderNum) throws SQLException, IOException{
        orderList=new ArrayList<OrderPayDTO>();
		
		Connection con=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        
        GetConnection gc=GetConnection.getInstance();
        
        try {
           con=gc.getConnection();
           
        //3.쿼리문 생성객체 얻기
           StringBuilder selectOrderDetail=new StringBuilder();
           selectOrderDetail
           .append("	select ORDER_NUM,	")
           .append("	(select temp_text from temp where temp_code=TEMP_OPTION) as temp_option,	")
           .append("	( select size_text from size_table where size_code=SIZE_OPTION) as SIZE_OPTION,	")
           .append("	(select shot_text from shot where shot_code=shot_option) as shot_option , AMOUNT, ORDER_PRICE,	")
           .append("	MENU_NAME, ORDER_NUM	")
           .append("	from  order_detail	")
           .append("	where order_num=?	");
           pstmt=con.prepareStatement(selectOrderDetail.toString());
           
        //4.쿼리문 수행 후 결과 얻기(커서의 제어권을 얻기)

           pstmt.setString(1, orderNum);
           rs=pstmt.executeQuery();
           
           String menuName="";
           int amount=0;
           String tempOption="";
           String sizeOption="";
           String shotOption="";
           int orderPrice=0;
           
           while(rs.next()) {//조회결과에 다음 레코드가 존재하는지
              //resultSet은 커서로 조회권을 가진다
              OrderPayDTO opDTO=null;
        	   
             opDTO=new OrderPayDTO(orderNum, menuName, amount, tempOption, sizeOption, shotOption, orderPrice);
             opDTO.setOrderNum(orderNum);
             opDTO.setMenuName(rs.getString("menu_name"));
             opDTO.setAmount(rs.getInt("amount"));
             opDTO.setTempOption(rs.getString("temp_option"));
             opDTO.setSizeOption(rs.getString("size_option"));
             opDTO.setShotOption(rs.getString("shot_option"));
             opDTO.setOrderPrice(rs.getInt("order_price"));
              
             
             menuName=rs.getString("menu_name");

              //조회결과값을 하나로 묶어서 관리하기위해 DTO(VO)생성,저장
              //같은 이름의 객체가 사라지지 않게 리스트에 추가
              orderList.add(opDTO);
              
           }//end while
           //while이 끝나면 마지막 레코드의 값 가지고있음
//           System.out.println(list.size());
//           System.out.println(list.isEmpty());
           
        }finally {
        //5.연결끊기
           gc.dbClose(con, pstmt, rs);
        }//end finally
    
    
    return orderList;
	}//selectOrderDetail
	
	
	
	public void updateCheckout(String orderNum,int checkout) throws SQLException, IOException {
		
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
		.append("	set  checkout_typecode=? ")
		.append("	where order_num=?     ");
		
		//3.쿼리문 생성 객체 얻기
		pstmt=con.prepareStatement(updateCheckOut.toString());
		
		//4.바인드변수에 값 설정
		pstmt.setInt(1, checkout);
		pstmt.setString(2, orderNum);
		//5.쿼리문 수행 후 결과 얻기
		pstmt.executeUpdate();
		
		}finally {
		//6.연결 끊기
		gc.dbClose(con, pstmt, null);
		}//end finally
		
		
	}//updateCheckOut
	
	
	public void deleteOrderDetail(String orderNum) throws SQLException, IOException {
		
		
		Connection con=null;
		PreparedStatement pstmt=null;
		GetConnection gc=GetConnection.getInstance();
		
		try {
		con=gc.getConnection();
		
		StringBuilder deleteOrderDetail=new StringBuilder();
		deleteOrderDetail
		.append("	delete from   order_detail	")
		.append("	where order_num=?");
		
		//3.쿼리문 생성 객체 얻기
		pstmt=con.prepareStatement(deleteOrderDetail.toString());
		
		//바인드 변수에 값 설정
		pstmt.setString(1, orderNum);
		
		pstmt.executeUpdate();
		}finally {
		//5.연결 끊기
		gc.dbClose(con, pstmt, null);
		}//end finally
		
		
		
	}//deleteOrderDetail
	
	
	public void deleteOrderList(String orderNum) throws SQLException, IOException {
		
		Connection con=null;
		PreparedStatement pstmt=null;
		GetConnection gc=GetConnection.getInstance();
		
		try {
		con=gc.getConnection();
		
		StringBuilder deleteOrderList=new StringBuilder();
		deleteOrderList
		.append("	delete from order_list	")
		.append("	where order_num=?");
		//3.쿼리문 생성 객체 얻기
		pstmt=con.prepareStatement(deleteOrderList.toString());
		//바인드 변수에 값 설정
		pstmt.setString(1, orderNum);
		
		pstmt.executeUpdate();
		}finally {
		//5.연결 끊기
		gc.dbClose(con, pstmt, null);
		}//end finally
		
		
	}//deleteOrderList

}//class
