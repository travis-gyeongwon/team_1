package kiosk.admin.dto;

public class MenuListSelectDTO {
    private String category;        // "커피", "논커피"
    private String menuName;        // 메뉴명(수정 다이얼로그로 넘기는 키)
    private String[] tempOption;    // 예: {"핫","아이스"}
    private String[] sizeOption;    // 예: {"M","L(+500)"}
    private String menuPrice;       // "4000원" 같은 문자열(목록 표시용)
    private String orderStatus;     // "판매중"/"품절"

    public MenuListSelectDTO(){}

    public MenuListSelectDTO(String category, String menuName, String[] tempOption,
                             String[] sizeOption, String menuPrice, String orderStatus) {
        this.category = category;
        this.menuName = menuName;
        this.tempOption = tempOption;
        this.sizeOption = sizeOption;
        this.menuPrice = menuPrice;
        this.orderStatus = orderStatus;
    }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getMenuName() { return menuName; }
    public void setMenuName(String menuName) { this.menuName = menuName; }
    public String[] getTempOption() { return tempOption; }
    public void setTempOption(String[] tempOption) { this.tempOption = tempOption; }
    public String[] getSizeOption() { return sizeOption; }
    public void setSizeOption(String[] sizeOption) { this.sizeOption = sizeOption; }
    public String getMenuPrice() { return menuPrice; }
    public void setMenuPrice(String menuPrice) { this.menuPrice = menuPrice; }
    public String getOrderStatus() { return orderStatus; }
    public void setOrderStatus(String orderStatus) { this.orderStatus = orderStatus; }
}
