package kiosk.admin.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kiosk.admin.dao.SalesReportDAO;
import kiosk.admin.dto.SalesReportDTO;

public class SalesReportService {
	public SalesReportDTO[] searchReport(String startDate, String endDate) {
		SalesReportDTO[] sumArr = null;

		//입력받은 날짜를 통해서 검색하기
		SalesReportDAO srDAO = SalesReportDAO.getInstance();
		
		try {
			List<SalesReportDTO> resultList = srDAO.selectResult(startDate, endDate);
			if(resultList.size() == 0) {
				return null;
			}
			Map<String, SalesReportDTO> map = new HashMap<String, SalesReportDTO>();
		
			//메뉴명에 맞춰서 수량과 가격 계산하여 반환해야함.
			int count = 0;
			int price = 0;
			for(SalesReportDTO srDTO : resultList) {
				String key = srDTO.getName();
				if(!map.containsKey(key)) {
					map.put(srDTO.getName(), srDTO);
					continue;
				}
				count = map.get(srDTO.getName()).getCount()+srDTO.getCount();
				map.get(srDTO.getName()).setCount(count);
				price = map.get(srDTO.getName()).getPrice()+srDTO.getPrice();
				map.get(srDTO.getName()).setPrice(price);
			}//for
			
			List<String> menuList = srDAO.menuNameList();
			int index = menuList.size();
			sumArr = new SalesReportDTO[index];
			//map에 있는 DTO를 배열에 저장
			//판매된적 없는 매뉴는 수량 0, 가격 0으로 새로 만들어서 저상
			String menuName = "";
			for(int i = 0; i < index ; i++) {
				menuName = menuList.get(i);
				
				SalesReportDTO srDTO = new SalesReportDTO(0, 0, menuName);
				if(map.containsKey(menuName)) {
					srDTO = map.get(menuName);
				}
				
				sumArr[i] = srDTO;
			}//for
			
			//많이 판매된 메뉴부터 위로 올라오기 위함
//			for(int i = 0; i < index; i++){
//				for(int j = 0; j < index-1 ; j++) {
//					if(sumArr[j].getCount() < sumArr[j+1].getCount()) {
//						SalesReportDTO temp = sumArr[j];
//						sumArr[j] = sumArr[j+1];
//						sumArr[j+1] = temp;
//					}//if
//				}//for		
//			}
			Arrays.sort(sumArr,(a,b) -> Integer.compare(b.getCount(), a.getCount()));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sumArr;
	}
}
