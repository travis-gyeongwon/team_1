package kiosk.user.event; // ❗️ 패키지 이름 확인

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList; // ArrayList import 추가
import java.util.List;    // List import 추가
import javax.swing.JButton; // JButton import
import javax.swing.JOptionPane; // JOptionPane import

// ❗️ 수정된 DTO 사용
import kiosk.user.dto.OrderMenuDTO;
import kiosk.user.dto.OrderProductDTO;
import kiosk.user.view.OptionDesign;
import kiosk.user.view.OrderDesign;

/**
 * OptionDesign의 이벤트를 처리하는 컨트롤러 (옵션 제한 기능 포함)
 */
public class OptionEvent implements ActionListener {

    private OptionDesign od;
    private OrderDesign orderDesignView;
    private OrderMenuDTO menuDTO;

    private int amount = 1;
    private int basePrice = 0;
    private int finalPrice = 0;

    // 옵션 변수 (생성자에서 초기화)
    private String tempOption;
    private String sizeOption;
    private String extraOption;

    // 생성자 (기본 옵션 값 받기)
    public OptionEvent(OptionDesign od, OrderDesign orderDesignView, OrderMenuDTO menuDTO,
                       String defaultTemp, String defaultSize, String defaultShot) {
        this.od = od;
        this.orderDesignView = orderDesignView;
        this.menuDTO = menuDTO;
        this.basePrice = menuDTO.getPrice();

        // 전달받은 유효한 기본 옵션으로 초기화 (null 체크)
        this.tempOption = (defaultTemp != null) ? defaultTemp : "";
        // ❗️ sizeOption은 내부적으로 "Medium" 사용
        this.sizeOption = (defaultSize != null) ? defaultSize : "";
        this.extraOption = (defaultShot != null) ? defaultShot : "";

        updatePrice();
        updateMenuLabel();

        // 리스너 등록
        od.getJbtnHot().addActionListener(this);
        od.getJbtnIce().addActionListener(this);
        od.getJbtnMedium().addActionListener(this);
        od.getJbtnLarge().addActionListener(this);
        od.getJbtnNormal().addActionListener(this);
        od.getJbtnMild().addActionListener(this);
        od.getJbtnShot().addActionListener(this);
        od.getJbtnMinus().addActionListener(this);
        od.getJbtnPlus().addActionListener(this);
        od.getJbtnCheck().addActionListener(this);
    }

    /**
     * 가격 계산 및 라벨 업데이트
     */
    private void updatePrice() {
        int price = this.basePrice;
        // ❗️ 사이즈 가격 (DB SIZE_TABLE의 sizeup_charge 사용 고려 - 현재는 500 하드코딩)
        if ("Large".equals(sizeOption)) { price += 500; }
        // ❗️ 샷 가격 (DB SHOT의 shot_charge 사용 고려 - 현재는 500 하드코딩)
        if ("샷추가".equals(extraOption)) { price += 500; }
        this.finalPrice = price * this.amount;
        od.getJlblCounter().setText(this.amount + "개");
        od.getJlblTprice().setText(this.finalPrice + "원");
    }

    /**
     * 상단 메뉴 라벨 업데이트
     */
    private void updateMenuLabel() {
        StringBuilder labelText = new StringBuilder("상품명 : ");
        labelText.append(menuDTO.getMenuName());
        List<String> options = new ArrayList<>();
        // 옵션 값이 유효할 때만 추가
        if (tempOption != null && !tempOption.isEmpty()) options.add(tempOption);
        if (sizeOption != null && !sizeOption.isEmpty()) {
            // ❗️ 화면 표시는 Regular, 내부 값은 Medium
            options.add("Medium".equals(sizeOption) ? "Regular" : sizeOption);
        }
        // "기본" 샷 옵션은 표시하지 않음
        if (extraOption != null && !extraOption.isEmpty() && !"기본".equals(extraOption)) {
            options.add(extraOption);
        }
        if (!options.isEmpty()) {
            labelText.append(" (").append(String.join(", ", options)).append(")");
        }
        od.getJlblMenu().setText(labelText.toString());
    }


    @Override
    public void actionPerformed(ActionEvent ae) {
        Object source = ae.getSource();
        // 비활성화된 버튼 클릭 무시
        if (source instanceof JButton && !((JButton) source).isEnabled()) {
            return;
        }

        boolean optionChanged = false;

        // --- 옵션 선택 ---
        if (source == od.getJbtnHot()) { tempOption = "HOT"; optionChanged = true; }
        else if (source == od.getJbtnIce()) { tempOption = "ICE"; optionChanged = true; }
        // ❗️ 버튼은 Medium이지만 내부 값은 "Medium"으로 설정
        else if (source == od.getJbtnMedium()) { sizeOption = "Medium"; optionChanged = true; }
        else if (source == od.getJbtnLarge()) { sizeOption = "Large"; optionChanged = true; }
        else if (source == od.getJbtnNormal()) { extraOption = "기본"; optionChanged = true; }
        else if (source == od.getJbtnMild()) { extraOption = "연하게"; optionChanged = true; }
        else if (source == od.getJbtnShot()) { extraOption = "샷추가"; optionChanged = true; }

        // --- 수량 변경 ---
        else if (source == od.getJbtnMinus()) {
            if (amount > 1) { amount--; }
        } else if (source == od.getJbtnPlus()) {
            if (amount < 10) { amount++; } // 10개 제한
            else { JOptionPane.showMessageDialog(od, "최대 주문 수량은 10개입니다.", "수량 제한", JOptionPane.INFORMATION_MESSAGE); }
        }

        // --- 확인 버튼 ---
        else if (source == od.getJbtnCheck()) {
            OrderProductDTO productDTO = new OrderProductDTO();
            productDTO.setMenuName(menuDTO.getMenuName());
            productDTO.setTempOption(tempOption);
            productDTO.setSizeOption(sizeOption); // DTO에는 "Medium" 또는 "Large" 저장
            productDTO.setExtraOption(extraOption);
            productDTO.setAmount(amount);
            productDTO.setOrderPrice(finalPrice);

            System.out.println("OptionEvent: 장바구니에 추가할 DTO: " + productDTO);
            orderDesignView.addOrderItemToPanel(productDTO);
            od.dispose();
        }

        // 업데이트
        if (source != od.getJbtnCheck()) {
            updatePrice();
            if (optionChanged) { updateMenuLabel(); }
        }
    } // actionPerformed 끝
} // OptionEvent 클래스 끝