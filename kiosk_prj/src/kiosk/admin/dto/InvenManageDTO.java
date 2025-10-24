package kiosk.admin.dto;

public class InvenManageDTO {
	private String menuName;
	private int menuInventory;
	
	public InvenManageDTO() {
		
	}// InvenManageDTO

	public InvenManageDTO(String menuName, int menuInventory) {
		super();
		this.menuName = menuName;
		this.menuInventory = menuInventory;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public int getMenuInventory() {
		return menuInventory;
	}

	public void setMenuInventory(int menuInventory) {
		this.menuInventory = menuInventory;
	}

	@Override
	public String toString() {
		return "InvenManageDTO [menuName=" + menuName + ", menuInventory=" + menuInventory + "]";
	}

	
	
	
}// class
