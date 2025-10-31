package kiosk.user.dto;

public class MemberDTO {

	private String phone;
	private int stamp, coupon;

	public MemberDTO() {
		super();
	}// MemberDTO

	public MemberDTO(String phone, int stamp, int coupon) {
		super();
		this.phone = phone;
		this.stamp = stamp;
		this.coupon = coupon;
	}// MemberDTO

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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
		return "MemberDTO [phone=" + phone + ", stamp=" + stamp + ", coupon=" + coupon + "]";
	}// toString

}// class
