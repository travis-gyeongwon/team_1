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
    
    private boolean actionSuccess = false;
    // [추가] 부모 프레임 참조 변수
    private Frame parentFrame; 

    // [수정!] 생성자에서 부모 Frame을 받도록 변경
    public MenuDeleteDesign(Frame parent) {
        super(parent, "메뉴 삭제", true); // 부모 설정, 제목 설정, Modal 설정
        this.parentFrame = parent; // 부모 프레임 저장

        setLayout(null);

        JLabel jlblTitle = new JLabel("메뉴명");
        jlblTitle.setOpaque(true);
        jlblTitle.setBackground(new Color(220, 220, 220));
        jlblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        jlblTitle.setBorder(BorderFactory.createEtchedBorder());

        jlblSelectedMenu = new JLabel("선택한 메뉴는 : ");
        jbtnDelete = new JButton("삭제");
        jbtnConfirm = new JButton("확인");

        // 삭제할 메뉴 목록을 DB에서 가져옴
        MenuDeleteService service = new MenuDeleteService();
        List<String> menuNames = service.getAllMenuNames();
        // 콤보박스 모델 생성 및 메뉴 목록 설정
        DefaultComboBoxModel<String> comboModel = new DefaultComboBoxModel<>(menuNames.toArray(new String[0]));
        jcbMenuList = new JComboBox<>(comboModel);

        jlblSelectedMenu.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        jlblSelectedMenu.setHorizontalAlignment(SwingConstants.CENTER);
        jlblSelectedMenu.setBorder(BorderFactory.createEtchedBorder());

        // 컴포넌트 위치 및 크기 설정
        jlblTitle.setBounds(100, 80, 80, 40);
        jcbMenuList.setBounds(190, 80, 250, 40);
        jlblSelectedMenu.setBounds(100, 180, 400, 60);

        // 버튼 위치 오른쪽 하단으로 변경
        jbtnDelete.setBounds(360, 390, 100, 40);
        jbtnConfirm.setBounds(470, 390, 100, 40);

        // 컴포넌트 추가
        add(jlblTitle);
        add(jcbMenuList);
        add(jlblSelectedMenu);
        add(jbtnDelete);
        add(jbtnConfirm);

        // 이벤트 핸들러 연결
        new MenuDeleteEvent(this);

        // 다이얼로그 기본 설정
        setSize(600, 480);
        // [수정!] 부모 창 기준으로 중앙 정렬
        setLocationRelativeTo(parent); 
        setResizable(false);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        // setVisible(true); // 외부에서 호출
    }

    // 작업 성공 여부 설정
    public void setActionSuccess(boolean success) {
        this.actionSuccess = success;
    }

    // 작업 성공 여부 반환
    public boolean isActionSuccess() {
        return this.actionSuccess;
    }

    // Getter 메서드들
    public JComboBox<String> getJcbMenuList() { return jcbMenuList; }
    public JLabel getJlblSelectedMenu() { return jlblSelectedMenu; }
    public JButton getJbtnDelete() { return jbtnDelete; }
    public JButton getJbtnConfirm() { return jbtnConfirm; }
    
    // [추가] 부모 프레임을 반환하는 getter (필요시 사용)
    public Frame getParentFrame() { return parentFrame; }
}

