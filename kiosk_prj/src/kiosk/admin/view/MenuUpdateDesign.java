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
    private JLabel jlbPreview; // 이미지 미리보기 레이블
    private JTextArea jtaPreview; // 텍스트 미리보기 영역
    private JButton jbtnImgChoice, jbtnUpdate, jbtnConfirm;

    private JRadioButton jrbCategoryCoffee, jrbCategoryNonCoffee;
    private final ButtonGroup grpCategory = new ButtonGroup();

    private JCheckBox jcbTempHot, jcbTempIce;
    private JCheckBox jcbSizeM, jcbSizeL;
    private JCheckBox jcbShotBasic, jcbShotLight, jcbShotPlus;

    private final MenuListUpdateDTO dto;
    // [추가] 부모 프레임 참조 변수
    private Frame parentFrame; 

    // [수정!] 생성자에서 부모 Frame과 DTO를 받도록 변경
    public MenuUpdateDesign(Frame parent, MenuListUpdateDTO dto) {
        super(parent, "메뉴 수정", true); // 부모 설정, 제목 설정, Modal 설정
        this.parentFrame = parent; // 부모 프레임 저장
        this.dto = dto;
        
        setLayout(null);

        // DTO에서 옵션 리스트 가져오기 (Null 체크 포함)
        List<String> tempOptions = dto.getTempOption() != null ? dto.getTempOption() : new ArrayList<>();
        List<String> sizeOptions = dto.getSizeOption() != null ? dto.getSizeOption() : new ArrayList<>();
        List<String> shotOptions = dto.getShotOption() != null ? dto.getShotOption() : new ArrayList<>();

        // --- UI 컴포넌트 생성 및 배치 ---
        JLabel jlbCategory = new JLabel("카테고리");
        jlbCategory.setBounds(30, 20, 80, 30); add(jlbCategory);
        jrbCategoryCoffee = new JRadioButton("커피", "커피".equals(dto.getCategory()));
        jrbCategoryNonCoffee = new JRadioButton("논커피", "논커피".equals(dto.getCategory()));
        grpCategory.add(jrbCategoryCoffee); grpCategory.add(jrbCategoryNonCoffee);
        jrbCategoryCoffee.setBounds(120, 20, 70, 30); add(jrbCategoryCoffee);
        jrbCategoryNonCoffee.setBounds(200, 20, 80, 30); add(jrbCategoryNonCoffee);

        JLabel jlbMenuNameLabel = new JLabel("메뉴명");
        jlbMenuNameLabel.setBounds(30, 60, 80, 30); add(jlbMenuNameLabel);
        jlbMenuName = new JLabel(dto.getMenuName());
        jlbMenuName.setBounds(120, 60, 180, 30);
        jlbMenuName.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        add(jlbMenuName);

        JLabel jlbPrice = new JLabel("가격");
        jlbPrice.setBounds(30, 100, 80, 30); add(jlbPrice);
        jtfMenuPrice = new JTextField(String.valueOf(dto.getMenuPrice()));
        jtfMenuPrice.setBounds(120, 100, 180, 30); add(jtfMenuPrice);

        JLabel jlbTemp = new JLabel("온도");
        jlbTemp.setBounds(30, 140, 80, 30); add(jlbTemp);
        jcbTempHot = new JCheckBox("핫", tempOptions.contains("핫"));
        jcbTempIce = new JCheckBox("아이스", tempOptions.contains("아이스"));
        jcbTempHot.setBounds(120, 140, 70, 30); add(jcbTempHot);
        jcbTempIce.setBounds(200, 140, 80, 30); add(jcbTempIce);

        JLabel jlbSize = new JLabel("사이즈");
        jlbSize.setBounds(30, 180, 80, 30); add(jlbSize);
        jcbSizeM = new JCheckBox("M", sizeOptions.contains("M"));
        jcbSizeL = new JCheckBox("L(+500)", sizeOptions.contains("L(+500)"));
        jcbSizeM.setBounds(120, 180, 70, 30); add(jcbSizeM);
        jcbSizeL.setBounds(200, 180, 100, 30); add(jcbSizeL);

        JLabel jlbShot = new JLabel("샷옵션");
        jlbShot.setBounds(30, 220, 80, 30); add(jlbShot);
        jcbShotBasic = new JCheckBox("기본", shotOptions.contains("기본"));
        jcbShotLight = new JCheckBox("연하게", shotOptions.contains("연하게"));
        jcbShotPlus = new JCheckBox("샷추가(+500)", shotOptions.contains("샷추가(+500)"));
        jcbShotBasic.setBounds(120, 220, 70, 30); add(jcbShotBasic);
        jcbShotLight.setBounds(190, 220, 80, 30); add(jcbShotLight);
        jcbShotPlus.setBounds(270, 220, 115, 30); add(jcbShotPlus);

        jlbPreview = new JLabel("이미지", SwingConstants.CENTER); // 이미지 미리보기 레이블
        jlbPreview.setOpaque(true);
        jlbPreview.setBackground(new Color(230,240,250));
        jlbPreview.setBounds(390, 60, 180, 180); add(jlbPreview);
        
        // DB 이미지 표시
        if (dto.getImage() != null) {
            Image dbImage = dto.getImage();
            Image scaledImage = dbImage.getScaledInstance(jlbPreview.getWidth(), jlbPreview.getHeight(), Image.SCALE_SMOOTH);
            jlbPreview.setText("");
            jlbPreview.setIcon(new ImageIcon(scaledImage));
        }

        jbtnImgChoice = new JButton("선택"); // 이미지 선택 버튼
        jbtnImgChoice.setBounds(430, 250, 100, 30); add(jbtnImgChoice);

        jtaPreview = new JTextArea(); // 텍스트 미리보기 영역
        jtaPreview.setEditable(false);
        jtaPreview.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        jtaPreview.setBounds(30, 310, 540, 70); add(jtaPreview);

        jbtnUpdate = new JButton("수정"); // 수정 버튼
        jbtnConfirm = new JButton("확인"); // 확인(닫기) 버튼
        jbtnUpdate.setBounds(370, 390, 90, 34); add(jbtnUpdate);
        jbtnConfirm.setBounds(480, 390, 90, 34); add(jbtnConfirm);

        // --- 실시간 미리보기를 위한 리스너 설정 ---
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

        // --- 이벤트 핸들러 연결 ---
        new MenuUpdateEvent(this);

        // 초기 미리보기 업데이트
        updatePreviewLabel();

        // --- 다이얼로그 기본 설정 ---
        setSize(600, 480);
        // [수정!] 부모 창 기준으로 중앙 정렬
        setLocationRelativeTo(parent); 
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        // setVisible(true); // 외부에서 호출
    }
    
    /**
     * 이미지 미리보기 레이블을 업데이트합니다.
     * @param imagePath 선택된 이미지 파일의 경로, null이면 기본 텍스트 표시
     */
    public void setImagePreview(String imagePath) {
        if (imagePath != null && !imagePath.isEmpty()) {
            ImageIcon icon = new ImageIcon(imagePath);
            Image img = icon.getImage();
            // JLabel 크기에 맞춰 이미지 스케일 조정
            Image scaledImg = img.getScaledInstance(jlbPreview.getWidth(), jlbPreview.getHeight(), Image.SCALE_SMOOTH);
            jlbPreview.setText(""); // 기본 텍스트 제거
            jlbPreview.setIcon(new ImageIcon(scaledImg)); // 아이콘 설정
        } else {
            jlbPreview.setIcon(null); // 아이콘 제거
            jlbPreview.setText("이미지"); // 기본 텍스트 표시
        }
    }
    
    /**
     * 하단의 텍스트 미리보기 영역(JTextArea)을 현재 선택/입력된 값으로 업데이트합니다.
     */
    public void updatePreviewLabel() {
        // 현재 선택된 값들을 가져옵니다.
        String category = jrbCategoryCoffee.isSelected() ? "커피" : "논커피";
        String menuName = jlbMenuName.getText(); // 메뉴명은 JLabel에서 가져옴
        String price = jtfMenuPrice.getText() + "원";

        // 온도 옵션 문자열 생성
        List<String> temps = new ArrayList<>();
        if (jcbTempHot.isSelected()) temps.add("핫");
        if (jcbTempIce.isSelected()) temps.add("아이스");
        String tempStr = String.join(",", temps);
        if (tempStr.isEmpty()) tempStr = "없음"; // 선택된 것이 없으면 "없음" 표시

        // 사이즈 옵션 문자열 생성
        List<String> sizes = new ArrayList<>();
        if (jcbSizeM.isSelected()) sizes.add("M");
        if (jcbSizeL.isSelected()) sizes.add("L(+500)");
        String sizeStr = String.join(",", sizes);
        if (sizeStr.isEmpty()) sizeStr = "없음";

        // 샷 옵션 문자열 생성
        List<String> shots = new ArrayList<>();
        if (jcbShotBasic.isSelected()) shots.add("기본");
        if (jcbShotLight.isSelected()) shots.add("연하게");
        if (jcbShotPlus.isSelected()) shots.add("샷추가(+500)");
        String shotStr = String.join(",", shots);
        if (shotStr.isEmpty()) shotStr = "없음";
        
        // DTO에서 재고 상태 가져오기 (이 값은 UI에서 변경되지 않음)
        String statusText = (dto.getQuantity() <= 0) ? "품절" : "판매중";

        // 최종 미리보기 텍스트 생성 및 JTextArea에 설정
        jtaPreview.setText(String.format("%s/%s/%s\n온도:%s / 사이즈:%s\n샷:%s / 상태:%s",
                category, menuName, price, tempStr, sizeStr, shotStr, statusText));
    }

    // --- Getter 메서드들 ---
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
    
    // [추가] 부모 프레임을 반환하는 getter (필요시 사용)
    public Frame getParentFrame() { return parentFrame; }
}

