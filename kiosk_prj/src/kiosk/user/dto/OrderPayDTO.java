package kiosk.user.dto;

public class OrderPayDTO {
	
	String orderNum;
	
	String menuName;
	int amount;
	String tempOption;
	String sizeOption; 
	String shotOption;
	int orderPrice;
	public OrderPayDTO() {
		super();
	}
	public OrderPayDTO(String orderNum, String menuName, int amount, String tempOption, String sizeOption,
			String shotOption, int orderPrice) {
		super();
		this.orderNum = orderNum;
		this.menuName = menuName;
		this.amount = amount;
		this.tempOption = tempOption;
		this.sizeOption = sizeOption;
		this.shotOption = shotOption;
		this.orderPrice = orderPrice;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getTempOption() {
		return tempOption;
	}
	public void setTempOption(String tempOption) {
		this.tempOption = tempOption;
	}
	public String getSizeOption() {
		return sizeOption;
	}
	public void setSizeOption(String sizeOption) {
		this.sizeOption = sizeOption;
	}
	public String getShotOption() {
		return shotOption;
	}
	public void setShotOption(String shotOption) {
		this.shotOption = shotOption;
	}
	public int getOrderPrice() {
		return orderPrice;
	}
	public void setOrderPrice(int orderPrice) {
		this.orderPrice = orderPrice;
	}
	@Override
	public String toString() {
		return "OrderPayDTO [orderNum=" + orderNum + ", menuName=" + menuName + ", amount=" + amount + ", tempOption="
				+ tempOption + ", sizeOption=" + sizeOption + ", shotOption=" + shotOption + ", orderPrice="
				+ orderPrice + "]";
	}
	

}//class
