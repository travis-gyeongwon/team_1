package kiosk.admin.dto;

public class StoreStatusDTO {
	private String status;

	public StoreStatusDTO() {
	}

	public StoreStatusDTO(String satus) {
		this.status = satus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
