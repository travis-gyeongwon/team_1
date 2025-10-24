package kiosk.admin.event;

import kiosk.admin.service.MenuDeleteService;
import kiosk.admin.view.MenuDeleteDesign;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class MenuDeleteEvent implements ActionListener, ItemListener {

    private final MenuDeleteDesign mdd;

    public MenuDeleteEvent(MenuDeleteDesign mdd) {
        this.mdd = mdd;
        mdd.getJcbMenuList().addItemListener(this);
        mdd.getJbtnDelete().addActionListener(this);
        mdd.getJbtnConfirm().addActionListener(this);
        updateSelectedMenuLabel();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == mdd.getJbtnConfirm()) {
            mdd.dispose(); // '확인'은 창만 닫음
        }
        if (source == mdd.getJbtnDelete()) {
            deleteMenuProcess();
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            updateSelectedMenuLabel();
        }
    }

    private void updateSelectedMenuLabel() {
        Object selectedItem = mdd.getJcbMenuList().getSelectedItem();
        if (selectedItem != null) {
            mdd.getJlblSelectedMenu().setText("선택한 메뉴는 : " + selectedItem.toString());
        } else {
            mdd.getJlblSelectedMenu().setText("선택한 메뉴는 : ");
        }
    }

    private void deleteMenuProcess() {
        Object selectedItem = mdd.getJcbMenuList().getSelectedItem();
        if (selectedItem == null) {
            JOptionPane.showMessageDialog(mdd, "삭제할 메뉴를 선택해주세요.");
            return;
        }
        String menuName = selectedItem.toString();

        int confirm = JOptionPane.showConfirmDialog(mdd,
                "'" + menuName + "' 메뉴를 정말 삭제하시겠습니까?", "삭제 확인", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            MenuDeleteService service = new MenuDeleteService();
            boolean success = service.removeMenu(menuName);

            if (success) {
                JOptionPane.showMessageDialog(mdd, "메뉴가 성공적으로 삭제되었습니다.");
                mdd.setActionSuccess(true); // 성공 플래그 설정
                mdd.dispose();
            } else {
                JOptionPane.showMessageDialog(mdd, "메뉴 삭제에 실패했습니다.");
            }
        }
    }
}

