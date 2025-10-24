package kiosk.admin.service;

import kiosk.admin.dao.MenuManageDAO;
import kiosk.admin.dto.MenuListSelectDTO;
import kiosk.admin.dto.MenuListUpdateDTO;

import java.util.List;

public class AdminMenuListService {
    private final MenuManageDAO dao = MenuManageDAO.getInstance();

    public List<MenuListSelectDTO> searchAllMenuList(int categoryCode){
        return dao.selectAllMenuList(categoryCode);
    }
    
    public MenuListUpdateDTO getMenuDetails(String menuName) {
        return dao.getMenuDetails(menuName);
    }
}