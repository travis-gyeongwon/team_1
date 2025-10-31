package kiosk.user.event; 

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList; 
import java.util.List;   
import javax.swing.JButton; 
import javax.swing.JOptionPane; 

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

    private String tempOption;
    private String sizeOption;
    private String extraOption;

    public OptionEvent(OptionDesign od, OrderDesign orderDesignView, OrderMenuDTO menuDTO,
                       String defaultTemp, String defaultSize, String defaultShot) {
        this.od = od;
        this.orderDesignView = orderDesignView;
        this.menuDTO = menuDTO;
        this.basePrice = menuDTO.getPrice();
        this.tempOption = (defaultTemp != null) ? defaultTemp : "";
        this.sizeOption = (defaultSize != null) ? defaultSize : "";
        this.extraOption = (defaultShot != null) ? defaultShot : "";

        updatePrice();
        updateMenuLabel();

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
        if ("Large".equals(sizeOption)) { price += 500; }
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
        if (tempOption != null && !tempOption.isEmpty()) options.add(tempOption);
        if (sizeOption != null && !sizeOption.isEmpty()) {
            options.add("Medium".equals(sizeOption) ? "Regular" : sizeOption);
        }
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
        if (source instanceof JButton && !((JButton) source).isEnabled()) {
            return;
        }

        boolean optionChanged = false;

        if (source == od.getJbtnHot()) { tempOption = "HOT"; optionChanged = true; }
        else if (source == od.getJbtnIce()) { tempOption = "ICE"; optionChanged = true; }
        else if (source == od.getJbtnMedium()) { sizeOption = "Medium"; optionChanged = true; }
        else if (source == od.getJbtnLarge()) { sizeOption = "Large"; optionChanged = true; }
        else if (source == od.getJbtnNormal()) { extraOption = "기본"; optionChanged = true; }
        else if (source == od.getJbtnMild()) { extraOption = "연하게"; optionChanged = true; }
        else if (source == od.getJbtnShot()) { extraOption = "샷추가"; optionChanged = true; }

        else if (source == od.getJbtnMinus()) {
            if (amount > 1) { amount--; }
        } else if (source == od.getJbtnPlus()) {
            if (amount < 10) { amount++; } 
            else { JOptionPane.showMessageDialog(od, "최대 주문 수량은 10개입니다.", "수량 제한", JOptionPane.INFORMATION_MESSAGE); }
        }

        else if (source == od.getJbtnCheck()) {
            OrderProductDTO productDTO = new OrderProductDTO();
            productDTO.setMenuName(menuDTO.getMenuName());
            productDTO.setTempOption(tempOption);
            productDTO.setSizeOption(sizeOption); 
            productDTO.setExtraOption(extraOption);
            productDTO.setAmount(amount);
            productDTO.setOrderPrice(finalPrice);
            orderDesignView.addOrderItemToPanel(productDTO);
            od.dispose();
        }


        if (source != od.getJbtnCheck()) {
            updatePrice();
            if (optionChanged) { updateMenuLabel(); }
        }
    } 
} 