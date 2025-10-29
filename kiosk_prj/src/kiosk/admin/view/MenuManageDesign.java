package kiosk.admin.view;

import kiosk.admin.event.MenuManageEvent;
// import java.awt.Frame; // Frame 대신 AdminMainDesign 사용
import javax.swing.*;

public class MenuManageDesign extends JDialog {
    private JButton jbtnMenuList = new JButton("메뉴 목록");
    private JButton jbtnMenuAdd  = new JButton("메뉴 추가");
    private JButton jbtnMenuDelete = new JButton("메뉴 삭제");
    // private Frame parentFrame; // Frame 대신 AdminMainDesign 사용
    private AdminMainDesign amd; // [수정!] AdminMainDesign 참조 변수

    // [수정!] 생성자에서 AdminMainDesign 객체(amd) 받기
    public MenuManageDesign(AdminMainDesign parentAmd){
        super(parentAmd, "메뉴관리", true); // 부모 JFrame 설정, 제목, Modal
        this.amd = parentAmd; // 전달받은 AdminMainDesign 객체 저장

        setLayout(null);

        jbtnMenuList.setBounds(140, 80, 120, 40);
        jbtnMenuAdd.setBounds(140, 140, 120, 40);
        jbtnMenuDelete.setBounds(140, 200, 120, 40);

        add(jbtnMenuList);
        add(jbtnMenuAdd);
        add(jbtnMenuDelete);

        // [수정!] MenuManageEvent 생성 시 AdminMainDesign 객체 전달
        new MenuManageEvent(this, amd);

        setSize(400, 400);
        // [수정!] 부모 창(AdminMainDesign) 위에 중앙 정렬
        setLocationRelativeTo(amd); 
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true); // 외부(AdminMainEvent)에서 호출됨
    }

    public JButton getJbtnMenuList(){ return jbtnMenuList; }
    public JButton getJbtnMenuAdd(){ return jbtnMenuAdd; }
    public JButton getJbtnMenuDelete(){ return jbtnMenuDelete; }
    
    // [수정!] AdminMainDesign 객체를 반환하는 getter (필요시 사용)
    public AdminMainDesign getAdminMainDesign() { return amd; }
}

