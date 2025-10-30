package kiosk.user.dto;

import java.sql.Timestamp;

public class PrintReceiptDTO {

	private String phone, takeout_flg;
	private Timestamp order_time;
	private int checkout_code, stamp, coupon;

	public PrintReceiptDTO() {
		super();
	}// PrintReceiptDTO

	public PrintReceiptDTO(String phone, String takeout_flg, Timestamp order_time, int checkout_code, int stamp,
			int coupon) {
		super();
		this.phone = phone;
		this.takeout_flg = takeout_flg;
		this.order_time = order_time;
		this.checkout_code = checkout_code;
		this.stamp = stamp;
		this.coupon = coupon;
	}// PrintReceiptDTO

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

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

	public int getStamp() {
		return stamp;
	}

	public void setStamp(int stamp) {
		this.stamp = stamp;
	}

	public int getCoupon() {
		return coupon;
	}

	public void setCoupon(int coupon) {
		this.coupon = coupon;
	}

	@Override
	public String toString() {
		return "PrintReceiptDTO [phone=" + phone + ", takeout_flg=" + takeout_flg + ", order_time=" + order_time
				+ ", checkout_code=" + checkout_code + ", stamp=" + stamp + ", coupon=" + coupon + "]";
	}// toString

}// class
