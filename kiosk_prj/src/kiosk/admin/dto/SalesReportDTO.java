package kiosk.admin.dto;

public class SalesReportDTO {
	private int count;
	private int price;
	private String name;
	
	public SalesReportDTO() {
	}

	public SalesReportDTO(int count, int price, String name) {
		this.count = count;
		this.price = price;
		this.name = name;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "SalesReportDTO [count=" + count + ", price=" + price + ", name=" + name + "]";
	}

}
