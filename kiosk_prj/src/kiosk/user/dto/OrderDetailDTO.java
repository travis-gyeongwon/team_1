package kiosk.user.dto;

public class OrderDetailDTO {

	private String menu_name;
	private int temp_option, size_option, shot_option, amount, order_price;

	public OrderDetailDTO() {
		super();
	}// OrderDetailDTO

	public OrderDetailDTO(String menu_name, int temp_option, int size_option, int shot_option, int amount,
			int order_price) {
		super();
		this.menu_name = menu_name;
		this.temp_option = temp_option;
		this.size_option = size_option;
		this.shot_option = shot_option;
		this.amount = amount;
		this.order_price = order_price;
	}// OrderDetailDTO

	public String getMenu_name() {
		return menu_name;
	}

	public void setMenu_name(String menu_name) {
		this.menu_name = menu_name;
	}

	public int getTemp_option() {
		return temp_option;
	}

	public void setTemp_option(int temp_option) {
		this.temp_option = temp_option;
	}

	public int getSize_option() {
		return size_option;
	}

	public void setSize_option(int size_option) {
		this.size_option = size_option;
	}

	public int getShot_option() {
		return shot_option;
	}

	public void setShot_option(int shot_option) {
		this.shot_option = shot_option;
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
		return "OrderDetailDTO [menu_name=" + menu_name + ", temp_option=" + temp_option + ", size_option="
				+ size_option + ", shot_option=" + shot_option + ", amount=" + amount + ", order_price=" + order_price
				+ "]";
	}// toString

}// class
