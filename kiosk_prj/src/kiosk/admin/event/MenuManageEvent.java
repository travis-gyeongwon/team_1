package kiosk.admin.event;

import kiosk.admin.view.MenuAddDesign;
import kiosk.admin.view.MenuDeleteDesign;
import kiosk.admin.view.MenuListDesign;
import kiosk.admin.view.MenuManageDesign;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuManageEvent implements ActionListener {
    private final MenuManageDesign mmd;
    private MenuListDesign mld;

    public MenuManageEvent(MenuManageDesign mmd){
        this.mmd = mmd;
        mmd.getJbtnMenuList().addActionListener(this);
        mmd.getJbtnMenuAdd().addActionListener(this);
        mmd.getJbtnMenuDelete().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if(src == mmd.getJbtnMenuList()){
            showOrRefreshMenuList();
        } else if(src == mmd.getJbtnMenuAdd()){
            MenuAddDesign mad = new MenuAddDesign();
            mad.setModal(true); // 다른 창과 상호작용 불가
            mad.setVisible(true); // 이 창이 닫힐 때까지 대기

            // 추가 창이 닫힌 후, 성공했을 때만 목록 새로고침
            if (mad.isActionSuccess()) {
                showOrRefreshMenuList();
            }
        } else if(src == mmd.getJbtnMenuDelete()){
            MenuDeleteDesign mdd = new MenuDeleteDesign();
            mdd.setModal(true); // 다른 창과 상호작용 불가
            mdd.setVisible(true); // 이 창이 닫힐 때까지 대기

            // 삭제 창이 닫힌 후, 성공했을 때만 목록 새로고침
            if (mdd.isActionSuccess()) {
                showOrRefreshMenuList();
            }
        }
    }

    private void showOrRefreshMenuList() {
        if (mld != null) {
            mld.dispose();
        }
        mld = new MenuListDesign();
        mld.setVisible(true);
    }
}

