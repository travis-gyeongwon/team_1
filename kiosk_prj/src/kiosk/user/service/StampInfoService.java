package kiosk.user.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import kiosk.user.dao.StampInfoDAO;
import kiosk.user.dto.StampInfoDTO;

public class StampInfoService {

	public StampInfoService() {
	}// StampInfoService

	public List<StampInfoDTO> searchOrderDetail(String order_num) {
		List<StampInfoDTO> list = null;

		try {
			StampInfoDAO siDAO = StampInfoDAO.getInstance();
			list = siDAO.selectOrderDetail(order_num);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} // end catch

		return list;
	}// searchOrderDetail

	public int searchMember(String order_num) {
		int flag = 0;

		try {
			StampInfoDAO siDAO = StampInfoDAO.getInstance();
			flag = siDAO.selectMember(order_num);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} // end catch

		return flag;
	}// searchMember

	public int modifyMember(String phone, int total_stamp) {
		int flag = 0;

		try {
			StampInfoDAO siDAO = StampInfoDAO.getInstance();
			flag = siDAO.updateMember(phone, total_stamp);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			flag = 2;
			e.printStackTrace();
		} // end catch

		return flag;
	}// modifyMember

}// class
