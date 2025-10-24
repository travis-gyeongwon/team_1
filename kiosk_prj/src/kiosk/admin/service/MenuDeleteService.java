package kiosk.admin.service;

import kiosk.admin.dao.MenuManageDAO;
import java.util.List;

public class MenuDeleteService {

    private final MenuManageDAO dao = MenuManageDAO.getInstance();

    /**
     * 삭제되지 않은 모든 메뉴의 이름을 조회합니다. (JComboBox 채우기용)
     * @return 메뉴 이름 리스트
     */
    public List<String> getAllMenuNames() {
        return dao.selectAllMenuNames();
    }

    /**
     *메뉴의 delete_flag를 'Y'로 변경하도록 DAO에 요청
     * @param menuName 삭제할 메뉴 이름
     * @return 성공 시 true, 실패 시 false
     */
    public boolean removeMenu(String menuName) {
        int affectedRows = dao.deleteMenu(menuName);
        return affectedRows == 1;
    }
}
