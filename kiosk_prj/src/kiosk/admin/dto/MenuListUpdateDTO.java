package kiosk.admin.dto;

import java.awt.Image;
import java.util.List;

public class MenuListUpdateDTO {
    private String category;
    private String menuName;
    private int menuPrice;
    private List<String> tempOption;
    private List<String> sizeOption;
    private List<String> shotOption;
    private String statusOption; 
    private String imageData;
    private Image image;
    
    private int quantity; 

    public MenuListUpdateDTO(){}

    // Getters & Setters
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getMenuName() { return menuName; }
    public void setMenuName(String menuName) { this.menuName = menuName; }
    public int getMenuPrice() { return menuPrice; }
    public void setMenuPrice(int menuPrice) { this.menuPrice = menuPrice; }
    public List<String> getTempOption() { return tempOption; }
    public void setTempOption(List<String> tempOption) { this.tempOption = tempOption; }
    public List<String> getSizeOption() { return sizeOption; }
    public void setSizeOption(List<String> sizeOption) { this.sizeOption = sizeOption; }
    public List<String> getShotOption() { return shotOption; }
    public void setShotOption(List<String> shotOption) { this.shotOption = shotOption; }
    public String getStatusOption() { return statusOption; }
    public void setStatusOption(String statusOption) { this.statusOption = statusOption; }
    public String getImageData() { return imageData; }
    public void setImageData(String imageData) { this.imageData = imageData; }
    public Image getImage() { return image; }
    public void setImage(Image image) { this.image = image; }
    
    // ##### quantity 필드의 getter & setter 추가 #####
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}