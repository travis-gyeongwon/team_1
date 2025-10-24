package kiosk.user.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List; 

import javax.swing.JButton;
import javax.swing.JOptionPane; // ❗️ JOptionPane import 추가

import kiosk.user.dto.OrderMenuDTO;
import kiosk.user.dto.OrderProductDTO; // ❗️ OrderProductDTO import 추가
import kiosk.user.service.OrderService;
import kiosk.user.view.OptionDesign;
import kiosk.user.view.OrderDesign;

/**
 * Controller (이벤트) 클래스
 * - 메인 화면의 모든 이벤트를 처리 (탭, 페이지, 메뉴, 장바구니, 결제)
 */
public class OrderEvent implements ActionListener {

	private OrderDesign ud;
	private List<OrderProductDTO> shoppingCart; // 장바구니 리스트(Model)에 직접 접근

	public OrderEvent(OrderDesign ud) {
		this.ud = ud;
		this.shoppingCart = ud.getShoppingCart(); // OrderDesign의 장바구니 리스트 참조

		// === 리스너 등록 ===
		// 1. 메인 탭 버튼
		ud.getJbtnHome().addActionListener(this);
		ud.getJbtnCoffee().addActionListener(this);
		ud.getJbtnBeverage().addActionListener(this);

		// 2. 커피 메뉴/페이지 버튼 (null 체크 포함)
		if (ud.getJbtnCoffeePrev() != null) ud.getJbtnCoffeePrev().addActionListener(this);
		if (ud.getJbtnCoffeeNext() != null) ud.getJbtnCoffeeNext().addActionListener(this);
		if (ud.getJbtnCoffeeMenus() != null) {
			for(JButton btn : ud.getJbtnCoffeeMenus()) {
				if(btn != null) btn.addActionListener(this);
			}
		}

		// 3. 음료수 메뉴/페이지 버튼 (null 체크 포함)
		if (ud.getJbtnBeveragePrev() != null) ud.getJbtnBeveragePrev().addActionListener(this);
		if (ud.getJbtnBeverageNext() != null) ud.getJbtnBeverageNext().addActionListener(this);
		if (ud.getJbtnBeverageMenus() != null) {
			for(JButton btn : ud.getJbtnBeverageMenus()) {
				if(btn != null) btn.addActionListener(this);
			}
		}

		// 4. 결제 버튼
		ud.getBtnPayment().addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		Object source = ae.getSource();
		String command = ae.getActionCommand(); // ActionCommand 미리 받아오기

		// === 1. 메인 탭 전환 ===
		if (source == ud.getJbtnHome()) {
			ud.getClMain().show(ud.getJpCenter(), "Home");
			ud.updateTabStyles(ud.getJbtnHome());
		} else if (source == ud.getJbtnCoffee()) {
			ud.getClMain().show(ud.getJpCenter(), "Coffee");
			ud.updateTabStyles(ud.getJbtnCoffee());
		} else if (source == ud.getJbtnBeverage()) {
			ud.getClMain().show(ud.getJpCenter(), "Beverage");
			ud.updateTabStyles(ud.getJbtnBeverage());
		}

		// === 2. 커피 페이지 전환 ===
		else if (source == ud.getJbtnCoffeePrev()) {
			ud.getClCoffee().show(ud.getJpCoffeeMenuCards(), "Page1");
			ud.updatePageIndicator("Coffee", 1);
		} else if (source == ud.getJbtnCoffeeNext()) {
			ud.getClCoffee().show(ud.getJpCoffeeMenuCards(), "Page2");
			ud.updatePageIndicator("Coffee", 2);
		}

		// === 3. 음료수 페이지 전환 ===
		else if (source == ud.getJbtnBeveragePrev()) {
			ud.getClBeverage().show(ud.getJpBeverageMenuCards(), "Page1");
			ud.updatePageIndicator("Beverage", 1);
		} else if (source == ud.getJbtnBeverageNext()) {
			ud.getClBeverage().show(ud.getJpBeverageMenuCards(), "Page2");
			ud.updatePageIndicator("Beverage", 2);
		}

		// === ❗️ 4. 장바구니 UI 이벤트 처리 (DELETE, MINUS, PLUS) ===
		else if (command != null && command.startsWith("DELETE_")) {
			try {
				int index = Integer.parseInt(command.substring(7)); // "DELETE_" 다음의 숫자(index) 추출
				shoppingCart.remove(index); // 데이터 모델에서 삭제
				ud.refreshCart(); // UI 새로고침
			} catch (NumberFormatException | IndexOutOfBoundsException e) {
				System.err.println("장바구니 삭제 오류: " + e.getMessage());
			}
		}
		else if (command != null && command.startsWith("MINUS_")) {
			try {
				int index = Integer.parseInt(command.substring(6));
				OrderProductDTO dto = shoppingCart.get(index);
				if (dto.getAmount() > 1) {
					// (단가) = (현재 총 가격) / (현재 수량)
					int singleItemPrice = dto.getOrderPrice() / dto.getAmount();
					dto.setAmount(dto.getAmount() - 1); // 수량 1 감소
					dto.setOrderPrice(singleItemPrice * dto.getAmount()); // 새 가격 계산
					ud.refreshCart(); // UI 새로고침
				} 
			} catch (NumberFormatException | IndexOutOfBoundsException | ArithmeticException e) {
				System.err.println("장바구니 수량 감소 오류: " + e.getMessage());
			}
		}
		else if (command != null && command.startsWith("PLUS_")) {
			try {
				int index = Integer.parseInt(command.substring(5));
				OrderProductDTO dto = shoppingCart.get(index);

				// ❗️ 1. 현재 수량이 10개 미만인지 확인
				if (dto.getAmount() < 10) {
					// (단가 계산은 그대로)
					int singleItemPrice = (dto.getAmount() > 0) ? (dto.getOrderPrice() / dto.getAmount()) : 0;
					
					// ❗️ 2. 10개 미만일 때만 수량 증가 및 가격 재계산
					dto.setAmount(dto.getAmount() + 1); 
					dto.setOrderPrice(singleItemPrice * dto.getAmount()); 
					ud.refreshCart(); // UI 새로고침
				} else {
					// ❗️ 3. (선택) 이미 10개일 경우 메시지 표시
					JOptionPane.showMessageDialog(ud, "최대 주문 수량은 10개입니다.", "수량 제한", JOptionPane.INFORMATION_MESSAGE);
				}

			} catch (NumberFormatException | IndexOutOfBoundsException | ArithmeticException e) {
				System.err.println("장바구니 수량 증가 오류: " + e.getMessage());
			}
		}

		else if (source == ud.getBtnPayment()) {
			if(shoppingCart.isEmpty()) {
				JOptionPane.showMessageDialog(ud, "주문할 상품을 담아주세요.");
				return;
			}
			boolean success = OrderService.getInstance().saveOrder(shoppingCart);
			
			if (success) {
				JOptionPane.showMessageDialog(ud, "주문이 성공적으로 저장되었습니다.");
				shoppingCart.clear(); // 데이터 비우기
				ud.refreshCart(); // UI 비우기
			} else {
				JOptionPane.showMessageDialog(ud, "주문 저장 중 오류가 발생했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
			}
			// System.out.println("결제 시도 항목들: " + shoppingCart);
		}

		else if(command != null && !command.isEmpty() && !command.startsWith("<html>")) {
			OrderMenuDTO menuDto = OrderService.getInstance().getMenuByName(command);

			if (menuDto != null) {
				System.out.println(command + " 클릭됨. OptionDesign 창을 엽니다.");
				new OptionDesign(ud, menuDto);
			} else {
				System.err.println(command + " 메뉴 정보를 DB에서 찾을 수 없습니다.");
			}
		}
	} // actionPerformed 끝
} // OrderEvent 클래스 끝