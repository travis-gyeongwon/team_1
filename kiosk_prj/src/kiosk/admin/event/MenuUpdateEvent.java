package kiosk.admin.event;

import kiosk.admin.dto.MenuListUpdateDTO;
import kiosk.admin.service.AdminMenuUpdateService;
import kiosk.admin.view.MenuUpdateDesign;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MenuUpdateEvent implements ActionListener {

    private final MenuUpdateDesign mud;
    private final AdminMenuUpdateService service = new AdminMenuUpdateService();

    public MenuUpdateEvent(MenuUpdateDesign mud) {
        this.mud = mud;
        mud.getJbtnImgChoice().addActionListener(this);
        mud.getJbtnUpdate().addActionListener(this);
        mud.getJbtnConfirm().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == mud.getJbtnImgChoice()) {
            JFileChooser chooser = new JFileChooser("c:/dev/temp");
            chooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
                @Override
                public boolean accept(File f) {
                    if (f.isDirectory()) {
                        return true;
                    }
                    String name = f.getName().toLowerCase();
                    return name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png") || name.endsWith(".gif");
                }
                @Override
                public String getDescription() {
                    return "Image Files (JPG, PNG, GIF)";
                }
            });
            
            int result = chooser.showOpenDialog(mud);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = chooser.getSelectedFile();
                mud.getDTO().setImageData(selectedFile.getAbsolutePath());
                
                ImageIcon icon = new ImageIcon(selectedFile.getAbsolutePath());
                Image scaledImage = icon.getImage().getScaledInstance(
                    mud.getJlbPreview().getWidth(), 
                    mud.getJlbPreview().getHeight(), 
                    Image.SCALE_SMOOTH
                );
                
                mud.getJlbPreview().setText("");
                mud.getJlbPreview().setIcon(new ImageIcon(scaledImage));
            }

        } else if (src == mud.getJbtnUpdate()) {
            updateMenuData();
        } else if (src == mud.getJbtnConfirm()) {
            mud.dispose();
        }
    }

    private void updateMenuData() {
        MenuListUpdateDTO dto = mud.getDTO();

        dto.setCategory(mud.getJrbCategoryCoffee().isSelected() ? "커피" : "논커피");
        try {
            dto.setMenuPrice(Integer.parseInt(mud.getJtfMenuPrice().getText().replaceAll("[^0-9]", "")));
        } catch (NumberFormatException ex) {
            dto.setMenuPrice(0);
        }

        List<String> temps = new ArrayList<>();
        if (mud.getJcbTempHot().isSelected()) temps.add("핫");
        if (mud.getJcbTempIce().isSelected()) temps.add("아이스");
        dto.setTempOption(temps);

        List<String> sizes = new ArrayList<>();
        if (mud.getJcbSizeM().isSelected()) sizes.add("M");
        if (mud.getJcbSizeL().isSelected()) sizes.add("L(+500)");
        dto.setSizeOption(sizes);

        List<String> shots = new ArrayList<>();
        if (mud.getJcbShotBasic().isSelected()) shots.add("기본");
        if (mud.getJcbShotLight().isSelected()) shots.add("연하게");
        if (mud.getJcbShotPlus().isSelected()) shots.add("샷추가(+500)");
        dto.setShotOption(shots);
        
       
        
        int result = service.modifyMenu(dto);
        if (result > 0) {
            JOptionPane.showMessageDialog(mud, "메뉴가 성공적으로 수정되었습니다.");
        } else {
            JOptionPane.showMessageDialog(mud, "메뉴 수정에 실패했습니다.");
        }
    }
}