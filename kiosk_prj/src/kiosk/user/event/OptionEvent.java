package kiosk.user.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import kiosk.user.dto.OrderMenuDTO;
import kiosk.user.dto.OrderProductDTO;
import kiosk.user.view.OptionDesign;
import kiosk.user.view.OrderDesign;

/**
 * OptionDesign의 이벤트를 처리하는 컨트롤러
 */
public class OptionEvent implements ActionListener {

    private OptionDesign od;
    private OrderDesign orderDesignView;
    private OrderMenuDTO menuDTO;

    private int amount = 1;
    private int basePrice = 0;
    private int finalPrice = 0;

    private String tempOption = "ICE"; 
    private String sizeOption = "Regular"; 
    private String extraOption = "기본"; 

    public OptionEvent(OptionDesign od, OrderDesign orderDesignView, OrderMenuDTO menuDTO) {
        this.od = od;
        this.orderDesignView = orderDesignView;
        this.menuDTO = menuDTO;
        this.basePrice = menuDTO.getPrice();
        
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
     * 옵션에 따라 가격을 다시 계산하고 라벨을 업데이트하는 메서드
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
     * ❗️ 2. 현재 선택된 옵션을 기반으로 상단 메뉴 라벨을 업데이트하는 새 메서드
     */
    private void updateMenuLabel() {
        StringBuilder labelText = new StringBuilder("상품명 : ");
        labelText.append(menuDTO.getMenuName()); 
        List<String> options = new ArrayList<>();
        options.add(tempOption);
        if (!"Regular".equals(sizeOption)) options.add(sizeOption); 
        if (!"기본".equals(extraOption)) options.add(extraOption); 
        if (!options.isEmpty()) {
            labelText.append(" (");
            labelText.append(String.join(", ", options)); 
            labelText.append(")");
        }
        od.getJlblMenu().setText(labelText.toString());
    }


    @Override
    public void actionPerformed(ActionEvent ae) {
        Object source = ae.getSource();
        boolean optionChanged = false; 

        if (source == od.getJbtnHot()) {
            tempOption = "HOT";
            optionChanged = true;
        } else if (source == od.getJbtnIce()) {
            tempOption = "ICE";
            optionChanged = true;
        } else if (source == od.getJbtnMedium()) {
            sizeOption = "Regular";
            optionChanged = true;
        } else if (source == od.getJbtnLarge()) {
            sizeOption = "Large";
            optionChanged = true;
        } else if (source == od.getJbtnNormal()) {
            extraOption = "기본";
            optionChanged = true;
        } else if (source == od.getJbtnMild()) {
            extraOption = "연하게";
            optionChanged = true;
        } else if (source == od.getJbtnShot()) {
            extraOption = "샷추가";
            optionChanged = true;
        }

        else if (source == od.getJbtnMinus()) {
            if (amount > 1) {
                amount--;
            }
        } else if (source == od.getJbtnPlus()) {
            if (amount < 10) {
                amount++;
            } else {
                JOptionPane.showMessageDialog(od, "최대 주문 수량은 10개입니다.", "수량 제한", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        else if (source == od.getJbtnCheck()) {
            OrderProductDTO productDTO = new OrderProductDTO();
            productDTO.setMenuName(menuDTO.getMenuName());
            productDTO.setTempOption(tempOption);
            productDTO.setSizeOption(sizeOption);
            productDTO.setExtraOption(extraOption);
            productDTO.setAmount(amount);
            productDTO.setOrderPrice(finalPrice); 

            System.out.println("OptionEvent: 장바구니에 추가할 DTO: " + productDTO);
            orderDesignView.addOrderItemToPanel(productDTO);
            od.dispose();
        }
        if (source != od.getJbtnCheck()) {
            updatePrice();
            if (optionChanged) {
                updateMenuLabel(); 
            }
        }
    }
} 