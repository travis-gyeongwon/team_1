package kiosk.admin.event;

import kiosk.admin.view.MenuAddDesign;
import kiosk.admin.view.MenuDeleteDesign;
import kiosk.admin.view.MenuListDesign;
import kiosk.admin.view.MenuManageDesign;

import java.awt.Frame; // Frame import 추가
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuManageEvent implements ActionListener {
    private final MenuManageDesign mmd;
    private final Frame parentFrame; // [추가] 부모 프레임(AdminMainDesign) 참조
    private MenuListDesign mld; // 메뉴 목록 창 인스턴스 관리

    // [수정!] 생성자에서 부모 Frame을 받도록 변경
    public MenuManageEvent(MenuManageDesign mmd, Frame parent){
        this.mmd = mmd;
        this.parentFrame = parent; // 부모 프레임 저장
        // 이벤트 리스너 등록
        mmd.getJbtnMenuList().addActionListener(this);
        mmd.getJbtnMenuAdd().addActionListener(this);
        mmd.getJbtnMenuDelete().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        
        // 메뉴 목록 버튼 클릭 시
        if(src == mmd.getJbtnMenuList()){
            showOrRefreshMenuList();
        } 
        // 메뉴 추가 버튼 클릭 시
        else if(src == mmd.getJbtnMenuAdd()){
            // MenuAddDesign 생성 시 부모 프레임 전달
            MenuAddDesign mad = new MenuAddDesign(parentFrame); 
            // mad.setModal(true); // 생성자에서 Modal 설정됨
            mad.setVisible(true); // 창이 닫힐 때까지 대기

            // 창이 닫힌 후, 작업 성공 여부 확인
            if (mad.isActionSuccess()) { // 작업이 성공적으로 완료되었다면
                showOrRefreshMenuList(); // 메뉴 목록 창 새로고침
            }
        } 
        // 메뉴 삭제 버튼 클릭 시
        else if(src == mmd.getJbtnMenuDelete()){
            // MenuDeleteDesign 생성 시 부모 프레임 전달
            MenuDeleteDesign mdd = new MenuDeleteDesign(parentFrame);
            // mdd.setModal(true); // 생성자에서 Modal 설정됨
            mdd.setVisible(true); // 창이 닫힐 때까지 대기

            // 창이 닫힌 후, 작업 성공 여부 확인
            if (mdd.isActionSuccess()) { // 작업이 성공적으로 완료되었다면
                showOrRefreshMenuList(); // 메뉴 목록 창 새로고침
            }
        }
    }

    /**
     * 메뉴 목록 창을 보여주거나, 이미 열려있다면 닫고 새로 열어 데이터를 갱신합니다.
     */
    private void showOrRefreshMenuList() {
        // 기존에 열려있는 메뉴 목록 창이 있다면 닫기
        if (mld != null && mld.isDisplayable()) { // isDisplayable()로 창 존재 여부 확인
            mld.dispose();
        }
        // MenuListDesign 생성 시 부모 프레임 전달하여 새로 열기
        mld = new MenuListDesign(parentFrame); 
        mld.setVisible(true);
    }
}

