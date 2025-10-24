package kiosk.admin.view;

import kiosk.admin.event.MenuAddEvent;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

public class MenuAddDesign extends JDialog {

    private JRadioButton jrbCoffee, jrbNonCoffee;
    private JTextField jtfMenuName, jtfMenuPrice;
    private JCheckBox jcbHot, jcbIce;
    private JCheckBox jcbM, jcbL;
    private JCheckBox jcbBasic, jcbMild, jcbAddShot;
    private JLabel jlblImagePreview;
    private JTextArea jtaPreview;
    private JButton jbtnImageSelect, jbtnAddMenu, jbtnCancel;
    
    // [추가] 작업 성공 여부를 저장할 플래그
    private boolean actionSuccess = false;

    public MenuAddDesign() {
        super();
        initDisplay();
    }

    public void initDisplay() {
        JLabel jlblCategory = new JLabel("카테고리");
        jrbCoffee = new JRadioButton("커피", true);
        jrbNonCoffee = new JRadioButton("논커피");
        ButtonGroup bgCategory = new ButtonGroup();
        bgCategory.add(jrbCoffee);
        bgCategory.add(jrbNonCoffee);

        JLabel jlblMenuName = new JLabel("메뉴명");
        jtfMenuName = new JTextField(20);

        JLabel jlblPrice = new JLabel("가격");
        jtfMenuPrice = new JTextField(20);
        
        JLabel jlblTemp = new JLabel("온도");
        jcbHot = new JCheckBox("핫");
        jcbIce = new JCheckBox("아이스", true);

        JLabel jlblSize = new JLabel("사이즈");
        jcbM = new JCheckBox("M");
        jcbL = new JCheckBox("L(+500)");

        JLabel jlblShot = new JLabel("샷옵션");
        jcbBasic = new JCheckBox("기본");
        jcbMild = new JCheckBox("연하게");
        jcbAddShot = new JCheckBox("샷추가(+500)");

        jlblImagePreview = new JLabel("이미지", SwingConstants.CENTER);
        jlblImagePreview.setBorder(BorderFactory.createEtchedBorder());

        jtaPreview = new JTextArea();
        jtaPreview.setEditable(false);
        jtaPreview.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        jbtnImageSelect = new JButton("선택");
        jbtnAddMenu = new JButton("추가");
        jbtnCancel = new JButton("취소");
        
        setLayout(null);
        
        jlblCategory.setBounds(30, 20, 80, 30);
        jrbCoffee.setBounds(120, 20, 70, 30);
        jrbNonCoffee.setBounds(200, 20, 80, 30);

        jlblMenuName.setBounds(30, 60, 80, 30);
        jtfMenuName.setBounds(120, 60, 180, 30);

        jlblPrice.setBounds(30, 100, 80, 30);
        jtfMenuPrice.setBounds(120, 100, 180, 30);
        
        jlblTemp.setBounds(30, 140, 80, 30);
        jcbHot.setBounds(120, 140, 70, 30);
        jcbIce.setBounds(200, 140, 80, 30);

        jlblSize.setBounds(30, 180, 80, 30);
        jcbM.setBounds(120, 180, 70, 30);
        jcbL.setBounds(200, 180, 100, 30);

        jlblShot.setBounds(30, 220, 80, 30);
        jcbBasic.setBounds(120, 220, 70, 30);
        jcbMild.setBounds(190, 220, 80, 30);
        jcbAddShot.setBounds(270, 220, 115, 30);

        jlblImagePreview.setBounds(390, 60, 180, 180);
        jbtnImageSelect.setBounds(430, 250, 100, 30);
        
        jtaPreview.setBounds(30, 310, 540, 70);
        
        jbtnAddMenu.setBounds(370, 390, 90, 34);
        jbtnCancel.setBounds(480, 390, 90, 34);
        
        add(jlblCategory); add(jrbCoffee); add(jrbNonCoffee);
        add(jlblMenuName); add(jtfMenuName);
        add(jlblPrice); add(jtfMenuPrice);
        add(jlblTemp); add(jcbHot); add(jcbIce);
        add(jlblSize); add(jcbM); add(jcbL);
        add(jlblShot); add(jcbBasic); add(jcbMild); add(jcbAddShot);
        add(jlblImagePreview); add(jbtnImageSelect);
        add(jtaPreview);
        add(jbtnAddMenu); add(jbtnCancel);

        ItemListener itemListener = e -> updatePreviewLabel();
        DocumentListener documentListener = new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { updatePreviewLabel(); }
            public void removeUpdate(DocumentEvent e) { updatePreviewLabel(); }
            public void changedUpdate(DocumentEvent e) { /* Not used */ }
        };
        jrbCoffee.addItemListener(itemListener);
        jrbNonCoffee.addItemListener(itemListener);
        jtfMenuName.getDocument().addDocumentListener(documentListener);
        jtfMenuPrice.getDocument().addDocumentListener(documentListener);
        jcbHot.addItemListener(itemListener);
        jcbIce.addItemListener(itemListener);
        jcbM.addItemListener(itemListener);
        jcbL.addItemListener(itemListener);
        jcbBasic.addItemListener(itemListener);
        jcbMild.addItemListener(itemListener);
        jcbAddShot.addItemListener(itemListener);

        MenuAddEvent event = new MenuAddEvent(this);
        jbtnAddMenu.addActionListener(event);
        jbtnCancel.addActionListener(event);
        jbtnImageSelect.addActionListener(event);

        setTitle("메뉴 추가");
        setSize(600, 480);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        updatePreviewLabel();
    }
    
    public void setImagePreview(String imagePath) {
        if (imagePath != null && !imagePath.isEmpty()) {
            ImageIcon icon = new ImageIcon(imagePath);
            Image img = icon.getImage();
            Image scaledImg = img.getScaledInstance(jlblImagePreview.getWidth(), jlblImagePreview.getHeight(), Image.SCALE_SMOOTH);
            jlblImagePreview.setText("");
            jlblImagePreview.setIcon(new ImageIcon(scaledImg));
        } else {
            jlblImagePreview.setIcon(null);
            jlblImagePreview.setText("이미지");
        }
    }
    
    public void updatePreviewLabel() {
        String category = jrbCoffee.isSelected() ? "커피" : "논커피";
        String menuName = jtfMenuName.getText();
        String price = jtfMenuPrice.getText() + "원";

        List<String> temps = new ArrayList<>();
        if (jcbHot.isSelected()) temps.add("핫");
        if (jcbIce.isSelected()) temps.add("아이스");
        String tempStr = String.join(",", temps);

        List<String> sizes = new ArrayList<>();
        if (jcbM.isSelected()) sizes.add("M");
        if (jcbL.isSelected()) sizes.add("L(+500)");
        String sizeStr = String.join(",", sizes);

        List<String> shots = new ArrayList<>();
        if (jcbBasic.isSelected()) shots.add("기본");
        if (jcbMild.isSelected()) shots.add("연하게");
        if (jcbAddShot.isSelected()) shots.add("샷추가(+500)");
        String shotStr = String.join(",", shots);
        
        jtaPreview.setText(String.format("%s/%s/%s\n온도:%s / 사이즈:%s\n샷:%s",
                category, menuName, price, tempStr, sizeStr, shotStr));
    }

    // [추가] 외부에서 성공 여부를 설정하는 메서드
    public void setActionSuccess(boolean success) {
        this.actionSuccess = success;
    }

    // [추가] 외부에서 성공 여부를 확인하는 메서드
    public boolean isActionSuccess() {
        return this.actionSuccess;
    }

    public JRadioButton getJrbCoffee() { return jrbCoffee; }
    public JTextField getJtfMenuName() { return jtfMenuName; }
    public JTextField getJtfMenuPrice() { return jtfMenuPrice; }
    public JCheckBox getJcbHot() { return jcbHot; }
    public JCheckBox getJcbIce() { return jcbIce; }
    public JCheckBox getJcbM() { return jcbM; }
    public JCheckBox getJcbL() { return jcbL; }
    public JCheckBox getJcbBasic() { return jcbBasic; }
    public JCheckBox getJcbMild() { return jcbMild; }
    public JCheckBox getJcbAddShot() { return jcbAddShot; }
    public JButton getJbtnImageSelect() { return jbtnImageSelect; }
    public JButton getJbtnAddMenu() { return jbtnAddMenu; }
    public JButton getJbtnCancel() { return jbtnCancel; }
}

