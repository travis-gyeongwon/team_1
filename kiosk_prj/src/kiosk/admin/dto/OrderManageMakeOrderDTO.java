package kiosk.admin.dto;

import java.sql.Date;

public class OrderManageMakeOrderDTO {
	private String orderNum, representMenu, orderStatus;
	private Date orderTime;
	private int price;
	
	public OrderManageMakeOrderDTO() {
		
	}// OrderManageMakeOrderDTO

	

	public OrderManageMakeOrderDTO(String orderNum, String representMenu, String orderStatus, Date orderTime,
			int price) {
		super();
		this.orderNum = orderNum;
		this.representMenu = representMenu;
		this.orderStatus = orderStatus;
		this.orderTime = orderTime;
		this.price = price;
	}



	public String getOrderNum() {
		return orderNum;
	}



	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}



	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getRepresentMenu() {
		return representMenu;
	}

	public void setRepresentMenu(String representMenu) {
		this.representMenu = representMenu;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}



	public Date getOrderTime() {
		return orderTime;
	}



	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}


	
	
}// class
