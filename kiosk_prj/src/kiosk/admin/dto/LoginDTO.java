package kiosk.admin.dto;

import java.sql.Date;

public class LoginDTO {
	private String id;
	private Date loginDate;
	
	public LoginDTO() {
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getLoginDate() {
		return loginDate;
	}
	
	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}
}
