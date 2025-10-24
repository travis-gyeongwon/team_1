package kiosk.admin.view;

import kiosk.admin.event.MenuDeleteEvent;
import kiosk.admin.service.MenuDeleteService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MenuDeleteDesign extends JDialog {

    private JComboBox<String> jcbMenuList;
    private JLabel jlblSelectedMenu;
    private JButton jbtnDelete;
    private JButton jbtnConfirm;

    // [추가] 작업 성공 여부를 저장할 플래그
    private boolean actionSuccess = false;

    public MenuDeleteDesign() {
        setTitle("메뉴 삭제");
        setLayout(null);

        JLabel jlblTitle = new JLabel("메뉴명");
        jlblTitle.setOpaque(true);
        jlblTitle.setBackground(new Color(220, 220, 220));
        jlblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        jlblTitle.setBorder(BorderFactory.createEtchedBorder());

        jlblSelectedMenu = new JLabel("선택한 메뉴는 : ");
        jbtnDelete = new JButton("삭제");
        jbtnConfirm = new JButton("확인");

        MenuDeleteService service = new MenuDeleteService();
        List<String> menuNames = service.getAllMenuNames();
        jcbMenuList = new JComboBox<>(menuNames.toArray(new String[0]));

        jlblSelectedMenu.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        jlblSelectedMenu.setHorizontalAlignment(SwingConstants.CENTER);
        jlblSelectedMenu.setBorder(BorderFactory.createEtchedBorder());

        jlblTitle.setBounds(100, 80, 80, 40);
        jcbMenuList.setBounds(190, 80, 250, 40);
        jlblSelectedMenu.setBounds(100, 180, 400, 60);

        jbtnDelete.setBounds(360, 390, 100, 40);
        jbtnConfirm.setBounds(470, 390, 100, 40);

        add(jlblTitle);
        add(jcbMenuList);
        add(jlblSelectedMenu);
        add(jbtnDelete);
        add(jbtnConfirm);

        new MenuDeleteEvent(this);

        setSize(600, 480);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    // [추가] 외부에서 성공 여부를 설정하는 메서드
    public void setActionSuccess(boolean success) {
        this.actionSuccess = success;
    }

    // [추가] 외부에서 성공 여부를 확인하는 메서드
    public boolean isActionSuccess() {
        return this.actionSuccess;
    }

    public JComboBox<String> getJcbMenuList() { return jcbMenuList; }
    public JLabel getJlblSelectedMenu() { return jlblSelectedMenu; }
    public JButton getJbtnDelete() { return jbtnDelete; }
    public JButton getJbtnConfirm() { return jbtnConfirm; }
}

