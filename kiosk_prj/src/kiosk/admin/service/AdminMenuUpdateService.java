package kiosk.admin.service;

import kiosk.admin.dao.MenuManageDAO;
import kiosk.admin.dto.MenuListUpdateDTO;

public class AdminMenuUpdateService {
    
    // DAO 객체를 생성
    private final MenuManageDAO dao = MenuManageDAO.getInstance();

    public int modifyMenu(MenuListUpdateDTO dto) {
        // DAO의 updateMenu 메소드를 호출하여 DB 작업을 수행하고 결과를 반환
        return dao.updateMenu(dto);
    }
}