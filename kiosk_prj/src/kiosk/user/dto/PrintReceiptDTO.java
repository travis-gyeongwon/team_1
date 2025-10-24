package kiosk.user.dto;

public class PrintReceiptDTO {

	private String order_num, order_detail_num, menu_name;
	private int temp_option, size_option, shot_option, amount, order_price;

	public PrintReceiptDTO() {
		super();
	}// PrintReceiptDTO

	public PrintReceiptDTO(String order_num, String order_detail_num, String menu_name, int temp_option,
			int size_option, int shot_option, int amount, int order_price) {
		super();
		this.order_num = order_num;
		this.order_detail_num = order_detail_num;
		this.menu_name = menu_name;
		this.temp_option = temp_option;
		this.size_option = size_option;
		this.shot_option = shot_option;
		this.amount = amount;
		this.order_price = order_price;
	}// PrintReceiptDTO

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

	public String getMenu_name() {
		return menu_name;
	}// getMenu_name

	public void setMenu_name(String menu_name) {
		this.menu_name = menu_name;
	}// setMenu_name

	public int getTemp_option() {
		return temp_option;
	}// getTemp_option

	public void setTemp_option(int temp_option) {
		this.temp_option = temp_option;
	}// setTemp_option

	public int getSize_option() {
		return size_option;
	}// getSize_option

	public void setSize_option(int size_option) {
		this.size_option = size_option;
	}// setSize_option

	public int getShot_option() {
		return shot_option;
	}// getShot_option

	public void setShot_option(int shot_option) {
		this.shot_option = shot_option;
	}// setShot_option

	public int getAmount() {
		return amount;
	}// getAmount

	public void setAmount(int amount) {
		this.amount = amount;
	}// setAmount

	public int getOrder_price() {
		return order_price;
	}// getOrder_price

	public void setOrder_price(int order_price) {
		this.order_price = order_price;
	}// setOrder_price

	@Override
	public String toString() {
		return "PrintReceiptDTO [order_num=" + order_num + ", order_detail_num=" + order_detail_num + ", menu_name="
				+ menu_name + ", temp_option=" + temp_option + ", size_option=" + size_option + ", shot_option="
				+ shot_option + ", amount=" + amount + ", order_price=" + order_price + "]";
	}// toString

}// class
