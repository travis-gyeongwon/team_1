package kiosk.admin.dto;

/**
 * 
 */
public class MenuListAddDTO {

    private String category;
    private String menuName;
    private int menuPrice;
    private String tempOption;
    private String sizeOption;
    private String shotOption;
    private String imageData; // 이미지 파일 경로

    /**
     * 기본 생성자
     */
    public MenuListAddDTO() {
    }

    /**
     * 모든 필드를 초기화하는 생성자
     */
    public MenuListAddDTO(String category, String menuName, int menuPrice, String tempOption,
                          String sizeOption, String shotOption, String imageData) {
        this.category = category;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.tempOption = tempOption;
        this.sizeOption = sizeOption;
        this.shotOption = shotOption;
        this.imageData = imageData;
    }

    // --- Getters and Setters ---
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public int getMenuPrice() {
        return menuPrice;
    }

    public void setMenuPrice(int menuPrice) {
        this.menuPrice = menuPrice;
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

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }
}