package kiosk.user.dto;

import java.sql.Timestamp;

public class OrderListDTO {

	private String takeout_flg;
	private Timestamp order_time;
	private int checkout_code;

	public OrderListDTO() {
		super();
	}// OrderListDTO

	public OrderListDTO(String takeout_flg, Timestamp order_time, int checkout_code) {
		super();
		this.takeout_flg = takeout_flg;
		this.order_time = order_time;
		this.checkout_code = checkout_code;
	}// OrderListDTO

	public String getTakeout_flg() {
		return takeout_flg;
	}

	public void setTakeout_flg(String takeout_flg) {
		this.takeout_flg = takeout_flg;
	}

	public Timestamp getOrder_time() {
		return order_time;
	}

	public void setOrder_time(Timestamp order_time) {
		this.order_time = order_time;
	}

	public int getCheckout_code() {
		return checkout_code;
	}

	public void setCheckout_code(int checkout_code) {
		this.checkout_code = checkout_code;
	}

	@Override
	public String toString() {
		return "OrderListDTO [takeout_flg=" + takeout_flg + ", order_time=" + order_time + ", checkout_code="
				+ checkout_code + "]";
	}// toString

}// class
