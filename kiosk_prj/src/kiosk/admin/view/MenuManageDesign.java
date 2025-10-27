package kiosk.admin.view;

import kiosk.admin.event.MenuManageEvent;
import java.awt.Frame; // Frame import 추가
import javax.swing.*;

public class MenuManageDesign extends JDialog {
    private JButton jbtnMenuList = new JButton("메뉴 목록");
    private JButton jbtnMenuAdd  = new JButton("메뉴 추가");
    private JButton jbtnMenuDelete = new JButton("메뉴 삭제");
    

    private Frame parentFrame; 


    public MenuManageDesign(Frame parent){
        super(parent, "메뉴관리", true); // 부모 설정, 제목 설정, Modal 설정
        this.parentFrame = parent; // 부모 프레임 저장

        setLayout(null);

        jbtnMenuList.setBounds(140, 80, 120, 40);
        jbtnMenuAdd.setBounds(140, 140, 120, 40);
        jbtnMenuDelete.setBounds(140, 200, 120, 40);

        add(jbtnMenuList);
        add(jbtnMenuAdd);
        add(jbtnMenuDelete);
       
     
        new MenuManageEvent(this, parent);

        setSize(400, 400);
     
        setLocationRelativeTo(parent); 
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true); // 생성자에서 호출하지 않고 외부(AdminMainEvent)에서 호출
    }

    public JButton getJbtnMenuList(){ return jbtnMenuList; }
    public JButton getJbtnMenuAdd(){ return jbtnMenuAdd; }
    public JButton getJbtnMenuDelete(){ return jbtnMenuDelete; }
    
   
    public Frame getParentFrame() { return parentFrame; }
}

