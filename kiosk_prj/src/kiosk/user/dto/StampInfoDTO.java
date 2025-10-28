package kiosk.user.dto;

public class StampInfoDTO {

	private String order_detail_num;
	private int amount;

	public StampInfoDTO() {
		super();
	}// StampInfoDTO

	public StampInfoDTO(String order_detail_num, int amount) {
		super();
		this.order_detail_num = order_detail_num;
		this.amount = amount;
	}// StampInfoDTO

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
		return "StampInfoDTO [order_detail_num=" + order_detail_num + ", amount=" + amount + "]";
	}// toString

}// class
