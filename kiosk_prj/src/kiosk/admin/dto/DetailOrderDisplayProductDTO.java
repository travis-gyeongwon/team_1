package kiosk.admin.dto;

public class DetailOrderDisplayProductDTO {
	private String menuName, tempOption, sizeOption, shotOption;
	private int amount;
	
	public DetailOrderDisplayProductDTO() {
		
	}// DetailOrderDisplayProductDTO

	public DetailOrderDisplayProductDTO(String menuName, String tempOption, String sizeOption, String shotOption, int amount) {
		super();
		this.menuName = menuName;
		this.tempOption = tempOption;
		this.sizeOption = sizeOption;
		this.shotOption = shotOption;
		this.amount = amount;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
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

	
	
	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "DetailOrderDisplayProductDTO [menuName=" + menuName + ", tempOption=" + tempOption + ", sizeOption="
				+ sizeOption + ", shotOption=" + shotOption + ", amount=" + amount + "]";
	}

	
	
}//class 
