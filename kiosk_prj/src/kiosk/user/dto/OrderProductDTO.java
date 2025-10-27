package kiosk.user.dto;

public class OrderProductDTO {

    private String menuName;  
    private String tempOption;     
    private String sizeOption;    
    private String extraOption;    
    private int amount;          
    private int orderPrice;      
    private String orderNum;     

    public OrderProductDTO() {
    }

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