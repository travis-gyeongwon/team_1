package kiosk.user.view; // 패키지 이름 확인

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
// import javax.swing.JFrame; // OrderDesign을 직접 사용하므로 JFrame 대신 import
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import kiosk.user.dto.OrderMenuDTO;
import kiosk.user.event.OptionEvent; 

public class OptionDesign extends JDialog {

	private JLabel jlblMenu;
	
    private JLabel jlblTemp;
    private JButton jbtnHot;
    private JButton jbtnIce;

    private JLabel jlblSize;
    private JButton jbtnMedium;
    private JButton jbtnLarge;

    private JLabel jlblOption;
    private JButton jbtnNormal;
    private JButton jbtnMild;
    private JButton jbtnShot;

    private JLabel jlblCount;
    private JButton jbtnMinus;
    private JLabel jlblCounter;
    private JButton jbtnPlus;

    private JLabel jlblTotal;
    private JLabel jlblTprice;
    private JButton jbtnCheck;

    // ❗️❗️❗️ 생성자를 OrderEvent 호출에 맞게 수정 (JFrame -> OrderDesign, String 추가)
    public OptionDesign(OrderDesign parent, OrderMenuDTO menuDto) { 
        super(parent, "상품 세부 정보", true); // 부모 프레임(parent)을 super에 전달

        // ❗️❗️ 전달받은 menuName으로 라벨 텍스트 설정
        jlblMenu = new JLabel("상품명 : " + menuDto.getMenuName());
        
        jlblTemp = new JLabel("핫,아이스");
        jbtnHot = new JButton("              HOT              ");
        jbtnIce = new JButton("              ICE              ");

        jlblSize = new JLabel("사이즈");
        jbtnMedium = new JButton("          Regular           ");
        jbtnLarge = new JButton("   Large(+500원)    ");

        jlblOption = new JLabel("추가옵션");
        jbtnNormal = new JButton("  기본  ");
        jbtnMild = new JButton(" 연하게 ");
        jbtnShot = new JButton(" 샷추가(+500원) ");

        jlblCount = new JLabel("개수");
        jbtnMinus = new JButton("-");
        jlblCounter = new JLabel("1개");
        jbtnPlus = new JButton("+");

        jlblTotal = new JLabel("총액");
        jlblTprice = new JLabel(menuDto.getPrice() + "원");
        jbtnCheck = new JButton("확인");

        // 라벨 크기 설정
        Dimension labelSize = new Dimension(80, 30);
        // jlblMenu 크기 설정은 여전히 주석 처리 (자동 크기 조절)
        jlblTemp.setPreferredSize(labelSize);
        jlblSize.setPreferredSize(labelSize);
        jlblOption.setPreferredSize(labelSize);
        jlblCount.setPreferredSize(labelSize);
        jlblTotal.setPreferredSize(labelSize);
        
        jlblMenu.setHorizontalAlignment(SwingConstants.CENTER);
        jlblTemp.setHorizontalAlignment(SwingConstants.CENTER);
        jlblSize.setHorizontalAlignment(SwingConstants.CENTER);
        jlblOption.setHorizontalAlignment(SwingConstants.CENTER);
        jlblCount.setHorizontalAlignment(SwingConstants.CENTER);
        jlblTotal.setHorizontalAlignment(SwingConstants.CENTER);
        jlblCounter.setHorizontalAlignment(SwingConstants.CENTER);

        // 1행 : 메뉴 패널 (FlowLayout.CENTER)
        JPanel jpMenu = new JPanel(new FlowLayout(FlowLayout.CENTER));
        jpMenu.add(jlblMenu);
        
        // 2행: 온도 옵션
        JPanel jpTemp = new JPanel(new FlowLayout(FlowLayout.LEFT));
        jpTemp.add(jlblTemp);
        jpTemp.add(jbtnHot);
        jpTemp.add(jbtnIce);
        
        // 3행: 사이즈 옵션
        JPanel jpSize = new JPanel(new FlowLayout(FlowLayout.LEFT));
        jpSize.add(jlblSize);
        jpSize.add(jbtnMedium);
        jpSize.add(jbtnLarge);
        
        // 4행: 추가 옵션
        JPanel jpOption = new JPanel(new FlowLayout(FlowLayout.LEFT));
        jpOption.add(jlblOption);
        jpOption.add(jbtnNormal);
        jpOption.add(jbtnMild);
        jpOption.add(jbtnShot);
        
        // 5행: 개수 옵션
        JPanel jpCount = new JPanel(new FlowLayout(FlowLayout.LEFT));
        jpCount.add(jlblCount);
        jpCount.add(jbtnMinus);
        jpCount.add(jlblCounter);
        jpCount.add(jbtnPlus);
        
        // 6행: 총액 및 확인
        JPanel jpTotal = new JPanel(new FlowLayout(FlowLayout.LEFT));
        jpTotal.add(jlblTotal);
        jpTotal.add(jlblTprice);
        jpTotal.add(jbtnCheck);
        
        // 메인 패널 (GridLayout 6x1)
        JPanel mainPanel = new JPanel(new GridLayout(6, 1));
        mainPanel.setBorder(new EmptyBorder( 10, 10, 10, 10)); // 여백
        mainPanel.add(jpMenu);
        mainPanel.add(jpTemp);
        mainPanel.add(jpSize);
        mainPanel.add(jpOption);
        mainPanel.add(jpCount);
        mainPanel.add(jpTotal);
        
        add(mainPanel);

        new OptionEvent(this, parent, menuDto);
        // 다이얼로그 설정
        pack(); // 컴포넌트 크기에 맞춰 자동 조절
        setLocationRelativeTo(parent); // 부모 창 중앙에 표시
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); // X 누르면 창만 닫힘
        setVisible(true); 
    }
    
    // --- Getters ---
    public JLabel getJlblMenu() {
    	return jlblMenu;
    }
    
    public JButton getJbtnHot() {
    	return jbtnHot;
    	}
    
    public JButton getJbtnIce() { 
    	return jbtnIce;
    	}
    
    public JButton getJbtnMedium() { 
    	return jbtnMedium; 
    	}
   
    public JButton getJbtnLarge() {
    	return jbtnLarge; 
    	}
    
    public JButton getJbtnNormal() { 
    	return jbtnNormal; 
    	}
    
    public JButton getJbtnMild() { 
    	return jbtnMild; 
    	}
    
    public JButton getJbtnShot() {
    	return jbtnShot; 
    	}
    
    public JButton getJbtnMinus() { 
    	return jbtnMinus; 
    	}
   
    public JLabel getJlblCounter() { 
    	return jlblCounter; 
    	}
   
    
    public JButton getJbtnPlus() { 
    	return jbtnPlus; 
    	}
   
    public JLabel getJlblTprice() { 
    	return jlblTprice; 
    	}
   
    public JButton getJbtnCheck() { 
    	return jbtnCheck; 
    	}

}