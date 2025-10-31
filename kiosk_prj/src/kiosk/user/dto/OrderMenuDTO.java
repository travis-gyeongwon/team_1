package kiosk.user.dto;

import java.util.List; 

public class OrderMenuDTO {
    private String menuName;
    private int price;
    private String status;    
    private String deleteFlag;
    private String category;    
    private int quantity;
    private byte[] menuImage; 

    private List<Integer> allowedTempCodes;
    private List<Integer> allowedSizeCodes;
    private List<Integer> allowedShotCodes;


    public OrderMenuDTO() {
    }

    public String getMenuName() { return menuName; }
    public void setMenuName(String menuName) { this.menuName = menuName; }
    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getDeleteFlag() { return deleteFlag; }
    public void setDeleteFlag(String deleteFlag) { this.deleteFlag = deleteFlag; }
    public String getCategory() { return category; } 
    public void setCategory(String category) { this.category = category; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public byte[] getMenuImage() { return menuImage; }
    public void setMenuImage(byte[] menuImage) { this.menuImage = menuImage; }

    public List<Integer> getAllowedTempCodes() { return allowedTempCodes; }
    public void setAllowedTempCodes(List<Integer> allowedTempCodes) { this.allowedTempCodes = allowedTempCodes; }
    public List<Integer> getAllowedSizeCodes() { return allowedSizeCodes; }
    public void setAllowedSizeCodes(List<Integer> allowedSizeCodes) { this.allowedSizeCodes = allowedSizeCodes; }
    public List<Integer> getAllowedShotCodes() { return allowedShotCodes; }
    public void setAllowedShotCodes(List<Integer> allowedShotCodes) { this.allowedShotCodes = allowedShotCodes; }

}