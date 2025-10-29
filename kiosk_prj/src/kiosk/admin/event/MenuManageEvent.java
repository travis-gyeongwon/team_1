package kiosk.admin.event;

import kiosk.admin.view.AdminMainDesign; // AdminMainDesign import 추가
import kiosk.admin.view.MenuAddDesign;
import kiosk.admin.view.MenuDeleteDesign;
import kiosk.admin.view.MenuListDesign;
import kiosk.admin.view.MenuManageDesign;

import javax.swing.JOptionPane;
// import java.awt.Frame; // Frame 대신 AdminMainDesign 사용
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuManageEvent implements ActionListener {
    private final MenuManageDesign mmd;
    // private final Frame parentFrame; // Frame 대신 AdminMainDesign 사용
    private final AdminMainDesign amd; // AdminMainDesign 참조 변수
    private MenuListDesign mld;

    // 생성자에서 AdminMainDesign 객체(amd) 받기
    public MenuManageEvent(MenuManageDesign mmd, AdminMainDesign parentAmd){
        this.mmd = mmd;
        // this.parentFrame = parentAmd; // Frame 대신 AdminMainDesign 사용
        this.amd = parentAmd; // 전달받은 AdminMainDesign 객체 저장

        mmd.getJbtnMenuList().addActionListener(this);
        mmd.getJbtnMenuAdd().addActionListener(this);
        mmd.getJbtnMenuDelete().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        // 라벨 텍스트를 직접 읽어와 영업 상태 확인
        String currentStatusText = amd.getJlStatus().getText();
        boolean isOpen = currentStatusText != null && currentStatusText.contains("영업 중"); // "영업 중" 포함 여부 확인

        // 메뉴 목록 버튼 클릭 시 (영업 상태 관계없이 항상 허용)
        if(src == mmd.getJbtnMenuList()){
            showOrRefreshMenuList();
        }
        // 메뉴 추가 버튼 클릭 시
        else if(src == mmd.getJbtnMenuAdd()){
            // [수정!] 영업 상태 확인 (영업 중일 때 차단)
            if (isOpen) {
                JOptionPane.showMessageDialog(mmd, "영업 중에는 메뉴 추가가 불가능합니다.\n영업 종료 후에 시도해주세요.");
                return; // 창 열기 중단
            }
            // 영업 종료 상태이면 추가 창 열기
            MenuAddDesign mad = new MenuAddDesign(amd);
            mad.setModal(true);
            mad.setVisible(true);

             if (mad.isActionSuccess()) { 
                 showOrRefreshMenuList();
             }
        }
        // 메뉴 삭제 버튼 클릭 시
        else if(src == mmd.getJbtnMenuDelete()){
            // [수정!] 영업 상태 확인 (영업 중일 때 차단)
            if (isOpen) {
                JOptionPane.showMessageDialog(mmd, "영업 중에는 메뉴 삭제가 불가능합니다.\n영업 종료 후에 시도해주세요.");
                return; // 창 열기 중단
            }
            // 영업 종료 상태이면 삭제 창 열기
            MenuDeleteDesign mdd = new MenuDeleteDesign(amd);
            mdd.setModal(true);
            mdd.setVisible(true);

             if (mdd.isActionSuccess()) {
                 showOrRefreshMenuList();
             }
        }
    }

    /**
     * 메뉴 목록 창을 보여주거나, 이미 열려있다면 닫고 새로 열어 데이터를 갱신
     */
    private void showOrRefreshMenuList() {
        if (mld != null && mld.isDisplayable()) {
            mld.dispose();
        }
        mld = new MenuListDesign(amd); // 부모 프레임(amd) 전달
        mld.setVisible(true);
    }
}

