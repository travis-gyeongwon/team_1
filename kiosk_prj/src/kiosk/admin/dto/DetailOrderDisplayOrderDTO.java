package kiosk.admin.dto;

import java.sql.Date;
import java.util.List;

public class DetailOrderDisplayOrderDTO {
	private int price;
	private List<DetailOrderDisplayProductDTO> detailMenu;
	private String orderNum, orderStatus, takeOutFlag;
	private Date orderTime;
	public DetailOrderDisplayOrderDTO() {

	}// DetailOrderDisplayOrderDTO

	public DetailOrderDisplayOrderDTO(String orderNum, int price, List<DetailOrderDisplayProductDTO> detailMenu,
			String orderStatus, Date orderTime, String takeOutFlag) {
		super();
		this.orderNum = orderNum;
		this.price = price;
		this.detailMenu = detailMenu;
		this.orderStatus = orderStatus;
		this.orderTime = orderTime;
		this.takeOutFlag = takeOutFlag;
	}

	
	
	public String getTakeOutFlag() {
		return takeOutFlag;
	}

	public void setTakeOutFlag(String takeOutFlag) {
		this.takeOutFlag = takeOutFlag;
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

	

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public List<DetailOrderDisplayProductDTO> getDetailMenu() {
		return detailMenu;
	}

	public void setDetailMenu(List<DetailOrderDisplayProductDTO> detailMenu) {
		this.detailMenu = detailMenu;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}




}// class
