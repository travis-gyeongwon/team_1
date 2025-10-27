package kiosk.user.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kiosk.user.dao.OrderPayDAO;
import kiosk.user.dto.OrderPayDTO;

public class OrderPayService {
	
	List<OrderPayDTO>tempList;
	List<OrderPayDTO> orderDetailList;
	
	

	public OrderPayService() {
		super();
		
	}
	
	
	public String showMaxOrderNum() {
		
		String orderNum="";
		try {
			orderNum=OrderPayDAO.getInstance().selectMaxOrderNum();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}//end catch
		
		return orderNum;
	}//showMaxOrderNum


	//메뉴명(옵션1,옵션2,옵션3)  수량  금액
	public List<OrderPayDTO> showOrderDetail(String orderNum){ 

		
		try {
			orderDetailList = OrderPayDAO.getInstance().selectOrderDetail(orderNum);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return orderDetailList;
	}//showOrderDetail
	
	
	//수량과 금액의 합계
	public int[] amountPriceTotal(String orderNum) {
	
		    tempList=null;
		try {
			tempList=OrderPayDAO.getInstance().selectOrderDetail(orderNum);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}//end catch
		int quantity=0;
		int price=0;
		
		for(OrderPayDTO opDTO: tempList) {
			quantity+=opDTO.getAmount();
			price+=opDTO.getOrderPrice();
		}//end for
		
		
		int[]total= {quantity,price};
		
		return total;
	}//quantityPrice
	
	public void changeCheckout(String orderNum,int checkout) {
		try {
			OrderPayDAO.getInstance().updateCheckout(orderNum, checkout);
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
	}//changeCheckout
	
	public void removeOrderDetail(String orderNum) {
		try {
			OrderPayDAO.getInstance().deleteOrderDetail(orderNum);
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}//removeOrderDetail
	
	public void removeOrderList(String orderNum) {
		try {
			OrderPayDAO.getInstance().deleteOrderList(orderNum);
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}//removeOrderList
	
	
	
}//class
