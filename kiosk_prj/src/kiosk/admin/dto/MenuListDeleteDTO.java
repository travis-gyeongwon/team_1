package kiosk.admin.dto;

public class MenuListDeleteDTO {
    private String menuName;

    public MenuListDeleteDTO() {
    }

    public MenuListDeleteDTO(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }
}
