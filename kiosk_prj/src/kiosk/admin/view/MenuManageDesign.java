package kiosk.admin.view;

import kiosk.admin.event.MenuManageEvent;

import javax.swing.*;

public class MenuManageDesign extends JDialog {
    private JButton jbtnMenuList = new JButton("메뉴 목록");
    private JButton jbtnMenuAdd  = new JButton("메뉴 추가");
    private JButton jbtnMenuDelete = new JButton("메뉴 삭제");

    public MenuManageDesign(){
        setTitle("메뉴관리");
        setLayout(null);

        jbtnMenuList.setBounds(140, 80, 120, 40);
        jbtnMenuAdd.setBounds(140, 140, 120, 40);
        jbtnMenuDelete.setBounds(140, 200, 120, 40);

        add(jbtnMenuList);
        add(jbtnMenuAdd);
        add(jbtnMenuDelete);

       
        new MenuManageEvent(this);

        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
    }

    public JButton getJbtnMenuList(){ return jbtnMenuList; }
    public JButton getJbtnMenuAdd(){ return jbtnMenuAdd; }
    public JButton getJbtnMenuDelete(){ return jbtnMenuDelete; }

   
}