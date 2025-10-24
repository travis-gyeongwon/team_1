package kiosk.admin.service;

import kiosk.admin.dao.MenuManageDAO;
import kiosk.admin.dto.MenuListAddDTO;

/**
 * 메뉴 추가 기능의 비즈니스 로직을 처리하는 서비스 클래스
 */
public class AdminMenuAddService {

    /**
     * DTO를 받아 DAO를 통해 데이터베이스에 메뉴를 추가하는 메서드
     * @param mlaDTO 뷰에서 입력된 메뉴 정보가 담긴 DTO
     * @return "SUCCESS" (성공), "DUPLICATE" (메뉴명 중복), "FAILURE" (DB 오류)
     */
    public String addMenu(MenuListAddDTO mlaDTO) {
        MenuManageDAO dao = MenuManageDAO.getInstance();

        // 1. [수정] DB에 INSERT 하기 전에 메뉴명 중복 검사 먼저 수행
        if (dao.isMenuNameDuplicate(mlaDTO.getMenuName())) {
            return "DUPLICATE"; // 이미 이름이 존재하면 "DUPLICATE" 반환
        }

        // 2. 중복이 아니면 DB에 추가
        int insertCount = dao.insertMenu(mlaDTO);

        // 3. 실행 결과 반환
        if (insertCount == 1) {
            return "SUCCESS"; // 성공
        } else {
            return "FAILURE"; // 실패
        }
    }
}
