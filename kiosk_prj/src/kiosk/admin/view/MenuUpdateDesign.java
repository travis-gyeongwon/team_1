package kiosk.admin.view;

import kiosk.admin.dto.MenuListUpdateDTO;
import kiosk.admin.event.MenuUpdateEvent;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

public class MenuUpdateDesign extends JDialog {

    private JLabel jlbMenuName;
    private JTextField jtfMenuPrice;
    private JLabel jlbPreview;
    private JTextArea jtaPreview;
    private JButton jbtnImgChoice, jbtnUpdate, jbtnConfirm;

    private JRadioButton jrbCategoryCoffee, jrbCategoryNonCoffee;
    private final ButtonGroup grpCategory = new ButtonGroup();

    private JCheckBox jcbTempHot, jcbTempIce;
    private JCheckBox jcbSizeM, jcbSizeL;
    private JCheckBox jcbShotBasic, jcbShotLight, jcbShotPlus;

    private final MenuListUpdateDTO dto;

    public MenuUpdateDesign(MenuListUpdateDTO dto) {
        this.dto = dto;
        setTitle("메뉴 수정");
        setLayout(null);

        List<String> tempOptions = dto.getTempOption() != null ? dto.getTempOption() : new ArrayList<>();
        List<String> sizeOptions = dto.getSizeOption() != null ? dto.getSizeOption() : new ArrayList<>();
        List<String> shotOptions = dto.getShotOption() != null ? dto.getShotOption() : new ArrayList<>();

        JLabel jlbCategory = new JLabel("카테고리");
        jlbCategory.setBounds(30, 20, 80, 30);
        add(jlbCategory);
        jrbCategoryCoffee = new JRadioButton("커피", "커피".equals(dto.getCategory()));
        jrbCategoryNonCoffee = new JRadioButton("논커피", "논커피".equals(dto.getCategory()));
        grpCategory.add(jrbCategoryCoffee);
        grpCategory.add(jrbCategoryNonCoffee);
        jrbCategoryCoffee.setBounds(120, 20, 70, 30);
        add(jrbCategoryCoffee);
        jrbCategoryNonCoffee.setBounds(200, 20, 80, 30);
        add(jrbCategoryNonCoffee);

        JLabel jlbMenuNameLabel = new JLabel("메뉴명");
        jlbMenuNameLabel.setBounds(30, 60, 80, 30);
        add(jlbMenuNameLabel);
        jlbMenuName = new JLabel(dto.getMenuName());
        jlbMenuName.setBounds(120, 60, 180, 30);
        jlbMenuName.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        add(jlbMenuName);

        JLabel jlbPrice = new JLabel("가격");
        jlbPrice.setBounds(30, 100, 80, 30);
        add(jlbPrice);
        jtfMenuPrice = new JTextField(String.valueOf(dto.getMenuPrice()));
        jtfMenuPrice.setBounds(120, 100, 180, 30);
        add(jtfMenuPrice);

        JLabel jlbTemp = new JLabel("온도");
        jlbTemp.setBounds(30, 140, 80, 30);
        add(jlbTemp);
        jcbTempHot = new JCheckBox("핫", tempOptions.contains("핫"));
        jcbTempIce = new JCheckBox("아이스", tempOptions.contains("아이스"));
        jcbTempHot.setBounds(120, 140, 70, 30);
        add(jcbTempHot);
        jcbTempIce.setBounds(200, 140, 80, 30);
        add(jcbTempIce);

        JLabel jlbSize = new JLabel("사이즈");
        jlbSize.setBounds(30, 180, 80, 30);
        add(jlbSize);
        jcbSizeM = new JCheckBox("M", sizeOptions.contains("M"));
        jcbSizeL = new JCheckBox("L(+500)", sizeOptions.contains("L(+500)"));
        jcbSizeM.setBounds(120, 180, 70, 30);
        add(jcbSizeM);
        jcbSizeL.setBounds(200, 180, 100, 30);
        add(jcbSizeL);

        JLabel jlbShot = new JLabel("샷옵션");
        jlbShot.setBounds(30, 220, 80, 30);
        add(jlbShot);
        jcbShotBasic = new JCheckBox("기본", shotOptions.contains("기본"));
        jcbShotLight = new JCheckBox("연하게", shotOptions.contains("연하게"));
        jcbShotPlus = new JCheckBox("샷추가(+500)", shotOptions.contains("샷추가(+500)"));
        jcbShotBasic.setBounds(120, 220, 70, 30);
        add(jcbShotBasic);
        jcbShotLight.setBounds(190, 220, 80, 30);
        add(jcbShotLight);
        jcbShotPlus.setBounds(270, 220, 115, 30);
        add(jcbShotPlus);

        jlbPreview = new JLabel("이미지", SwingConstants.CENTER);
        jlbPreview.setOpaque(true);
        jlbPreview.setBackground(new Color(230,240,250));
        jlbPreview.setBounds(390, 60, 180, 180);
        add(jlbPreview);

        if (dto.getImage() != null) {
            Image dbImage = dto.getImage();
            Image scaledImage = dbImage.getScaledInstance(jlbPreview.getWidth(), jlbPreview.getHeight(), Image.SCALE_SMOOTH);
            jlbPreview.setText("");
            jlbPreview.setIcon(new ImageIcon(scaledImage));
        }

        jbtnImgChoice = new JButton("선택");
        jbtnImgChoice.setBounds(430, 250, 100, 30);
        add(jbtnImgChoice);

        jtaPreview = new JTextArea();
        jtaPreview.setEditable(false);
        jtaPreview.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        jtaPreview.setBounds(30, 310, 540, 70);
        add(jtaPreview);

        jbtnUpdate = new JButton("수정");
        jbtnConfirm = new JButton("확인");
        jbtnUpdate.setBounds(370, 390, 90, 34);
        add(jbtnUpdate);
        jbtnConfirm.setBounds(480, 390, 90, 34);
        add(jbtnConfirm);

        ItemListener itemListener = e -> updatePreviewLabel();
        DocumentListener documentListener = new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { updatePreviewLabel(); }
            public void removeUpdate(DocumentEvent e) { updatePreviewLabel(); }
            public void changedUpdate(DocumentEvent e) { /* Not used */ }
        };

        jrbCategoryCoffee.addItemListener(itemListener);
        jrbCategoryNonCoffee.addItemListener(itemListener);
        jtfMenuPrice.getDocument().addDocumentListener(documentListener);
        jcbTempHot.addItemListener(itemListener);
        jcbTempIce.addItemListener(itemListener);
        jcbSizeM.addItemListener(itemListener);
        jcbSizeL.addItemListener(itemListener);
        jcbShotBasic.addItemListener(itemListener);
        jcbShotLight.addItemListener(itemListener);
        jcbShotPlus.addItemListener(itemListener);

        new MenuUpdateEvent(this);

        updatePreviewLabel();

        setSize(600, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public void updatePreviewLabel() {
        String category = jrbCategoryCoffee.isSelected() ? "커피" : "논커피";
        String menuName = jlbMenuName.getText();
        String price = jtfMenuPrice.getText() + "원";

        List<String> temps = new ArrayList<>();
        if (jcbTempHot.isSelected()) temps.add("핫");
        if (jcbTempIce.isSelected()) temps.add("아이스");
        String tempStr = String.join(",", temps);

        List<String> sizes = new ArrayList<>();
        if (jcbSizeM.isSelected()) sizes.add("M");
        if (jcbSizeL.isSelected()) sizes.add("L(+500)");
        String sizeStr = String.join(",", sizes);

        List<String> shots = new ArrayList<>();
        if (jcbShotBasic.isSelected()) shots.add("기본");
        if (jcbShotLight.isSelected()) shots.add("연하게");
        if (jcbShotPlus.isSelected()) shots.add("샷추가(+500)");
        String shotStr = String.join(",", shots);

        String statusText = (dto.getQuantity() <= 0) ? "품절" : "판매중";

        jtaPreview.setText(String.format("%s/%s/%s\n온도:%s / 사이즈:%s\n샷:%s / 상태:%s",
                category, menuName, price, tempStr, sizeStr, shotStr, statusText));
    }

    public JTextField getJtfMenuPrice() { return jtfMenuPrice; }
    public JLabel getJlbPreview() { return jlbPreview; }
    public JButton getJbtnImgChoice() { return jbtnImgChoice; }
    public JButton getJbtnUpdate() { return jbtnUpdate; }
    public JButton getJbtnConfirm() { return jbtnConfirm; }
    public JRadioButton getJrbCategoryCoffee() { return jrbCategoryCoffee; }
    public JRadioButton getJrbCategoryNonCoffee() { return jrbCategoryNonCoffee; }
    public JCheckBox getJcbTempHot() { return jcbTempHot; }
    public JCheckBox getJcbTempIce() { return jcbTempIce; }
    public JCheckBox getJcbSizeM() { return jcbSizeM; }
    public JCheckBox getJcbSizeL() { return jcbSizeL; }
    public JCheckBox getJcbShotBasic() { return jcbShotBasic; }
    public JCheckBox getJcbShotLight() { return jcbShotLight; }
    public JCheckBox getJcbShotPlus() { return jcbShotPlus; }
    public MenuListUpdateDTO getDTO() { return dto; }
}