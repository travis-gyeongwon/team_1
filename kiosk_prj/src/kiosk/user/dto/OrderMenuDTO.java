package kiosk.user.dto;

import java.util.List; // ❗️ List import 추가

public class OrderMenuDTO {
    private String menuName;
    private int price;
    private String status;     // status 필드 유지
    private String deleteFlag;
    private String category;    // category 필드 유지
    // private String imageUrl; // ❗️ imageUrl 필드 제거 또는 주석 처리
    private int quantity;
    private byte[] menuImage; // ❗️ byte[] menuImage 필드 추가 (BLOB 데이터)

    // ❗️ 허용된 옵션 코드 목록 필드 추가
    private List<Integer> allowedTempCodes;
    private List<Integer> allowedSizeCodes;
    private List<Integer> allowedShotCodes;

    // 기본 생성자
    public OrderMenuDTO() {
    }

    // --- Getters and Setters ---
    public String getMenuName() { return menuName; }
    public void setMenuName(String menuName) { this.menuName = menuName; }
    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getDeleteFlag() { return deleteFlag; }
    public void setDeleteFlag(String deleteFlag) { this.deleteFlag = deleteFlag; }
    public String getCategory() { return category; } // category Getter/Setter 유지
    public void setCategory(String category) { this.category = category; }
    // public String getImageUrl() { return imageUrl; } // imageUrl 제거됨
    // public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    // menuImage Getter/Setter
    public byte[] getMenuImage() { return menuImage; }
    public void setMenuImage(byte[] menuImage) { this.menuImage = menuImage; }

    // 허용된 옵션 코드 목록 Getter/Setter
    public List<Integer> getAllowedTempCodes() { return allowedTempCodes; }
    public void setAllowedTempCodes(List<Integer> allowedTempCodes) { this.allowedTempCodes = allowedTempCodes; }
    public List<Integer> getAllowedSizeCodes() { return allowedSizeCodes; }
    public void setAllowedSizeCodes(List<Integer> allowedSizeCodes) { this.allowedSizeCodes = allowedSizeCodes; }
    public List<Integer> getAllowedShotCodes() { return allowedShotCodes; }
    public void setAllowedShotCodes(List<Integer> allowedShotCodes) { this.allowedShotCodes = allowedShotCodes; }

} // class 끝