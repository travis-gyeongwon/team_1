package kiosk.user.dto;

public class StampInfoDTO {

	private String order_num, order_detail_num;
	private int amount;

	public StampInfoDTO() {
		super();
	}// StampInfoDTO

	public StampInfoDTO(String order_num, String order_detail_num, int amount) {
		super();
		this.order_num = order_num;
		this.order_detail_num = order_detail_num;
		this.amount = amount;
	}// StampInfoDTO

	public String getOrder_num() {
		return order_num;
	}// getOrder_num

	public void setOrder_num(String order_num) {
		this.order_num = order_num;
	}// setOrder_num

	public String getOrder_detail_num() {
		return order_detail_num;
	}// getOrder_detail_num

	public void setOrder_detail_num(String order_detail_num) {
		this.order_detail_num = order_detail_num;
	}// setOrder_detail_num

	public int getAmount() {
		return amount;
	}// getAmount

	public void setAmount(int amount) {
		this.amount = amount;
	}// setAmount

	@Override
	public String toString() {
		return "StampInfoDTO [order_num=" + order_num + ", order_detail_num=" + order_detail_num + ", amount=" + amount
				+ "]";
	}// toString

}// class
