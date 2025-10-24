package kiosk.user.dto;

public class OrderProductDTO {

    // 1. 어떤 메뉴인가? 
    private String menuName;  

    // 2. 어떤 옵션을 선택했는가? (OptionDesign에서 선택)
    private String tempOption;     
    private String sizeOption;    
    private String extraOption;    

    // 3. 몇 개를 주문했는가?
    private int amount;          

    // 4. 그래서 이 1줄의 가격은 얼마인가? 
    private int orderPrice;      

    // 5. 이 항목이 어떤 '메인 주문'에 속해있는가? (DB 저장 시 필요)
    private String orderNum;     

    // 기본 생성자
    public OrderProductDTO() {
    }

    // 모든 필드를 받는 생성자 (DAO에서 DB 조회 시 유용)
    public OrderProductDTO(String menuName, String tempOption, String sizeOption, String extraOption, int amount,
        int orderPrice, String orderNum) {
        this.menuName = menuName;
        this.tempOption = tempOption;
        this.sizeOption = sizeOption;
        this.extraOption = extraOption;
        this.amount = amount;
        this.orderPrice = orderPrice;
        this.orderNum = orderNum;
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

    public String getExtraOption() {
        return extraOption;
    }

    public void setExtraOption(String extraOption) {
        this.extraOption = extraOption;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(int orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    @Override
    public String toString() {
        return "OrderProductDTO [menuName=" + menuName + ", tempOption=" + tempOption + ", sizeOption=" + sizeOption
                + ", extraOption=" + extraOption + ", amount=" + amount + ", orderPrice=" + orderPrice + ", orderNum="
                + orderNum + "]";
    }
}