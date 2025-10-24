package kiosk.admin.event;

import kiosk.admin.dto.MenuListAddDTO;
import kiosk.admin.service.AdminMenuAddService;
import kiosk.admin.view.MenuAddDesign;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MenuAddEvent implements ActionListener {

    private MenuAddDesign mad;
    private String imagePath;

    public MenuAddEvent(MenuAddDesign mad) {
        this.mad = mad;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == mad.getJbtnAddMenu()) {
            addMenuProcess();
        }
        if (e.getSource() == mad.getJbtnCancel()) {
            mad.dispose();
        }
        if (e.getSource() == mad.getJbtnImageSelect()) {
            selectImage();
        }
    }

    private void addMenuProcess() {
        MenuListAddDTO mlaDTO = createDTOFromInput();
        if (mlaDTO == null) {
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(mad, "해당 메뉴를 추가하시겠습니까?", "메뉴 추가 확인", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            AdminMenuAddService ams = new AdminMenuAddService();
            // [수정!] boolean이 아닌 String으로 결과를 받음
            String result = ams.addMenu(mlaDTO);
            
            // [수정!] 결과에 따라 다른 메시지 표시
            switch (result) {
                case "SUCCESS":
                    JOptionPane.showMessageDialog(mad, "메뉴가 성공적으로 추가되었습니다.");
                    mad.setActionSuccess(true);
                    mad.dispose();
                    break;
                case "DUPLICATE":
                    JOptionPane.showMessageDialog(mad, "이미 존재하는 메뉴명입니다. 다른 이름을 사용해주세요.");
                    break;
                case "FAILURE":
                    JOptionPane.showMessageDialog(mad, "메뉴 추가에 실패했습니다. (DB 오류)");
                    break;
            }
        }
    }

    private MenuListAddDTO createDTOFromInput() {
        // 1. 메뉴명 검증
        String menuName = mad.getJtfMenuName().getText().trim();
        if (menuName.isEmpty()) {
            JOptionPane.showMessageDialog(mad, "메뉴명을 입력해주세요.");
            return null;
        }

        // 2. 가격 검증 (텍스트)
        String menuPriceStr = mad.getJtfMenuPrice().getText().trim();
        if (menuPriceStr.isEmpty()) {
            JOptionPane.showMessageDialog(mad, "가격을 입력해주세요.");
            return null;
        }

        // 3. 온도 옵션 검증 (JCheckBox)
        if (!mad.getJcbHot().isSelected() && !mad.getJcbIce().isSelected()) {
            JOptionPane.showMessageDialog(mad, "온도 옵션을 하나 이상 선택해주세요.");
            return null;
        }
        
        // 4. 사이즈 옵션 검증 (JCheckBox)
        if (!mad.getJcbM().isSelected() && !mad.getJcbL().isSelected()) {
            JOptionPane.showMessageDialog(mad, "사이즈 옵션을 하나 이상 선택해주세요.");
            return null;
        }
        
        // 5. 샷 옵션 검증 (JCheckBox)
        if (!mad.getJcbBasic().isSelected() && !mad.getJcbMild().isSelected() && !mad.getJcbAddShot().isSelected()) {
            JOptionPane.showMessageDialog(mad, "샷 옵션을 하나 이상 선택해주세요.");
            return null;
        }

        // 6. 이미지 선택 검증 (imagePath 변수)
        if (imagePath == null || imagePath.isEmpty()) {
            JOptionPane.showMessageDialog(mad, "이미지를 선택해주세요.");
            return null;
        }

        // 7. 가격 검증 (숫자 형식)
        int menuPrice;
        try {
            menuPrice = Integer.parseInt(menuPriceStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(mad, "가격은 숫자로만 입력해주세요.");
            return null;
        }

        // --- 모든 유효성 검증 통과 ---
        MenuListAddDTO dto = new MenuListAddDTO();
        dto.setCategory(mad.getJrbCoffee().isSelected() ? "커피" : "논커피");
        dto.setMenuName(menuName);
        dto.setMenuPrice(menuPrice);
        
        StringBuilder tempOption = new StringBuilder();
        if (mad.getJcbHot().isSelected()) tempOption.append("핫 ");
        if (mad.getJcbIce().isSelected()) tempOption.append("아이스 ");
        dto.setTempOption(tempOption.toString().trim());

        StringBuilder sizeOption = new StringBuilder();
        if (mad.getJcbM().isSelected()) sizeOption.append("M ");
        if (mad.getJcbL().isSelected()) sizeOption.append("L(+500) ");
        dto.setSizeOption(sizeOption.toString().trim());

        StringBuilder shotOption = new StringBuilder();
        if (mad.getJcbBasic().isSelected()) shotOption.append("기본 ");
        if (mad.getJcbMild().isSelected()) shotOption.append("연하게 ");
        if (mad.getJcbAddShot().isSelected()) shotOption.append("샷추가(+500) ");
        dto.setShotOption(shotOption.toString().trim());

        dto.setImageData(imagePath);
        return dto;
    }
    
    private void selectImage() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("이미지 파일", "jpg", "png", "gif", "jpeg");
        fileChooser.setFileFilter(filter);

        int result = fileChooser.showOpenDialog(mad);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            imagePath = selectedFile.getAbsolutePath();
            mad.setImagePreview(imagePath);
        }
    }
}

