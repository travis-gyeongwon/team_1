package kiosk.user.dto;

public class OrderDetailDTO {

	private String menu_name, temp_text, size_text, shot_text;
	private int amount, order_price;

	public OrderDetailDTO() {
		super();
	}// OrderDetailDTO

	public OrderDetailDTO(String menu_name, String temp_text, String size_text, String shot_text, int amount,
			int order_price) {
		super();
		this.menu_name = menu_name;
		this.temp_text = temp_text;
		this.size_text = size_text;
		this.shot_text = shot_text;
		this.amount = amount;
		this.order_price = order_price;
	}// OrderDetailDTO

	public String getMenu_name() {
		return menu_name;
	}

	public void setMenu_name(String menu_name) {
		this.menu_name = menu_name;
	}

	public String getTemp_text() {
		return temp_text;
	}

	public void setTemp_text(String temp_text) {
		this.temp_text = temp_text;
	}

	public String getSize_text() {
		return size_text;
	}

	public void setSize_text(String size_text) {
		this.size_text = size_text;
	}

	public String getShot_text() {
		return shot_text;
	}

	public void setShot_text(String shot_text) {
		this.shot_text = shot_text;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getOrder_price() {
		return order_price;
	}

	public void setOrder_price(int order_price) {
		this.order_price = order_price;
	}

	@Override
	public String toString() {
		return "OrderDetailDTO [menu_name=" + menu_name + ", temp_text=" + temp_text + ", size_text=" + size_text
				+ ", shot_text=" + shot_text + ", amount=" + amount + ", order_price=" + order_price + "]";
	}// toString

}// class
