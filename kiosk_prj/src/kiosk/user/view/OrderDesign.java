package kiosk.user.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList; 
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.Border; 
import javax.swing.border.TitledBorder;

import kiosk.user.dto.OrderMenuDTO;
import kiosk.user.dto.OrderProductDTO; 
import kiosk.user.event.OrderEvent;
import kiosk.user.service.OrderService;

/**
 * View (화면) 클래스 - 동적 주문 목록 기능 추가
 */
public class OrderDesign extends JFrame {

   private JButton jbtnHome, jbtnCoffee, jbtnBeverage;
   private JPanel jpNorth;
   private JPanel jpCenter;
   private CardLayout clMain;
   private JPanel jpHome; 

   private JPanel jpCoffeeMain;
   private CardLayout clCoffee;
   private JPanel jpCoffeeMenuCards;
   private JButton jbtnCoffeePrev, jbtnCoffeeNext;
   private JButton[] jbtnCoffeeMenus;
   private JLabel jlblCoffeePage1, jlblCoffeePage2;

   private JPanel jpBeverageMain;
   private CardLayout clBeverage;
   private JPanel jpBeverageMenuCards;
   private JButton jbtnBeveragePrev, jbtnBeverageNext;
   private JButton[] jbtnBeverageMenus;
   private JLabel jlblBeveragePage1, jlblBeveragePage2;
   
   private JPanel jpSouth;
   private JPanel itemsContainer; 
   private JScrollPane scrollPane;
   private JLabel lblTotal;
   private JButton btnPayment; 
   
   private List<OrderProductDTO> shoppingCart; 
   private OrderEvent orderEvent;

   private static final Font FONT_COMMON = new Font("Malgun Gothic", Font.BOLD, 16);
   private static final Font FONT_HEADER = new Font("Malgun Gothic", Font.BOLD, 20);
   private static final Color COLOR_MEGA_YELLOW = new Color(255, 204, 0);
   private static final Color COLOR_BG = Color.WHITE;
   private static final Color COLOR_TAB_ACTIVE = COLOR_MEGA_YELLOW;
   private static final Color COLOR_TAB_INACTIVE = new Color(245, 245, 245);
   private static final Color COLOR_FONT_ACTIVE = Color.BLACK; 
   private static final Color COLOR_FONT_INACTIVE = Color.BLACK;


   public OrderDesign() {
      super("키오스크 메인");

      this.shoppingCart = new ArrayList<>(); 
      
      jbtnHome = new JButton("홈");
      jbtnCoffee = new JButton("커피");
      jbtnBeverage = new JButton("음료수");
      jpNorth = new JPanel(new GridLayout(1, 3));
      jpNorth.add(jbtnHome);
      jpNorth.add(jbtnCoffee);
      jpNorth.add(jbtnBeverage);

      // === 2. 센터 패널 (메인 CardLayout) ===
      clMain = new CardLayout();
      jpCenter = new JPanel(clMain);
      jpCenter.setBackground(COLOR_BG);

      jpHome = new JPanel();
      jpHome.setBackground(COLOR_BG);
      JLabel lblHome = new JLabel("홈 화면입니다.");
      lblHome.setFont(FONT_HEADER);
      jpHome.add(lblHome);

      List<OrderMenuDTO> coffeeMenus = OrderService.getInstance().getMenus("커피");
      jpCoffeeMain = createMenuPanel("Coffee", coffeeMenus); 

      List<OrderMenuDTO> beverageMenus = OrderService.getInstance().getMenus("논커피");
      jpBeverageMain = createMenuPanel("Beverage", beverageMenus); 

      jpCenter.add(jpHome, "Home");
      jpCenter.add(jpCoffeeMain, "Coffee");
      jpCenter.add(jpBeverageMain, "Beverage");
      clMain.show(jpCenter, "Coffee"); 

      // === 3. 하단 주문 목록 패널 ===
      jpSouth = createOrderListPanel(); 

      // === 4. 이벤트 핸들러 등록 ===
      this.orderEvent = new OrderEvent(this); 
      updateTabStyles(jbtnCoffee);
      
      // === 5. 프레임에 패널 추가 ===
      add("North", jpNorth);
      add("Center", jpCenter);
      add("South", jpSouth);
      getContentPane().setBackground(COLOR_BG);
      setSize(700, 1000); 
      setLocationRelativeTo(null); 
      setVisible(true);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   }

   /**
    * 메뉴 패널 생성 (커피, 음료수)
    */
   private JPanel createMenuPanel(String type, List<OrderMenuDTO> menuList) {
      JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
      mainPanel.setBackground(COLOR_BG);
      mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 

      JPanel pageHeader = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
      pageHeader.setBackground(COLOR_BG);
      JLabel page1Label = new JLabel("1페이지");
      JLabel page2Label = new JLabel("2페이지");
      stylePageIndicator(page1Label, true);
      stylePageIndicator(page2Label, false);
      pageHeader.add(page1Label);
      pageHeader.add(page2Label);

      CardLayout cardLayout = new CardLayout();
      JPanel menuCards = new JPanel(cardLayout);
      menuCards.setBackground(COLOR_BG);
      
      JPanel page1 = new JPanel(new GridLayout(2, 3, 15, 15)); 
      page1.setBackground(COLOR_BG);
      JPanel page2 = new JPanel(new GridLayout(2, 3, 15, 15));
      page2.setBackground(COLOR_BG);
      
      JButton[] buttons = new JButton[12];
      
      for (int i = 0; i < 12; i++) {
         if(i < menuList.size()) {
            OrderMenuDTO dto = menuList.get(i);
            buttons[i] = createMenuButton(dto); 
         } else {
            buttons[i] = new JButton();
            buttons[i].setVisible(false); 
         }

         if (i < 6) {
            page1.add(buttons[i]);
         } else {
            page2.add(buttons[i]);
         }
      }
      
      menuCards.add(page1, "Page1");
      menuCards.add(page2, "Page2");

      JButton prevButton = new JButton("<");
      JButton nextButton = new JButton(">");
      styleArrowButton(prevButton);
      styleArrowButton(nextButton);
      prevButton.addActionListener(this.orderEvent);
      nextButton.addActionListener(this.orderEvent);

      if (type.equals("Coffee")) {
         clCoffee = cardLayout;
         jpCoffeeMenuCards = menuCards;
         jbtnCoffeePrev = prevButton;
         jbtnCoffeeNext = nextButton;
         jbtnCoffeeMenus = buttons;
         jlblCoffeePage1 = page1Label;
         jlblCoffeePage2 = page2Label;
      } else {
         clBeverage = cardLayout;
         jpBeverageMenuCards = menuCards;
         jbtnBeveragePrev = prevButton;
         jbtnBeverageNext = nextButton;
         jbtnBeverageMenus = buttons;
         jlblBeveragePage1 = page1Label;
         jlblBeveragePage2 = page2Label;
      }
      
      mainPanel.add(pageHeader, "North");
      mainPanel.add(menuCards, "Center");
      mainPanel.add(prevButton, "West");
      mainPanel.add(nextButton, "East");

      return mainPanel;
   }

   /**
    * 메뉴 버튼 생성 (DTO 기반)
    */
   private JButton createMenuButton(OrderMenuDTO dto) {
      String text = "<html><center>" + dto.getMenuName() + "<br>" + dto.getPrice() + "원</center></html>";
      if (dto.getQuantity() <= 10) {
         text = "<html><center>" + dto.getMenuName() + "<br><font color='red'>(품절)</font></center></html>";
      }
      
      JButton button = new JButton(text);
      ImageIcon icon = null; 
      byte[] imageBytes = dto.getMenuImage(); 

      try {
         if (imageBytes != null && imageBytes.length > 0) {
            ImageIcon originalIcon = new ImageIcon(imageBytes);
            Image scaledImage = originalIcon.getImage().getScaledInstance(100, 150, Image.SCALE_SMOOTH);
            icon = new ImageIcon(scaledImage);
         } else {
            System.err.println("이미지 바이트가 null입니다: " + dto.getMenuName());
            Image img = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
            icon = new ImageIcon(img);
         }
      } catch (Exception e) {
         System.err.println("이미지 변환 중 오류: " + dto.getMenuName());
         e.printStackTrace();
         Image img = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
         icon = new ImageIcon(img);
      }
      button.setIcon(icon); 
      button.setFont(FONT_COMMON);
      button.setForeground(Color.BLACK);
      button.setBackground(COLOR_BG);
      button.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
      button.setVerticalTextPosition(SwingConstants.BOTTOM);
      button.setHorizontalTextPosition(SwingConstants.CENTER);
      button.setFocusPainted(false);
      button.setActionCommand(dto.getMenuName());
      
      if (dto.getQuantity() <= 10) {
         button.setEnabled(false);
      } else {
         if(this.orderEvent != null) {
            button.addActionListener(this.orderEvent);
         }
      }
      
      return button;
   }
   
   /**
    * 하단 주문 목록 패널 생성
    */
   private JPanel createOrderListPanel() {
      JPanel southPanel = new JPanel(new BorderLayout());
      southPanel.setBackground(COLOR_BG);
      southPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEmptyBorder(5, 5, 5, 5), 
            "주문내역 리스트", TitledBorder.CENTER, TitledBorder.TOP, FONT_COMMON, Color.BLACK));

      itemsContainer = new JPanel();
      itemsContainer.setLayout(new BoxLayout(itemsContainer, BoxLayout.Y_AXIS));
      itemsContainer.setBackground(COLOR_BG);
      
      scrollPane = new JScrollPane(itemsContainer);
      scrollPane.setBorder(BorderFactory.createEmptyBorder());
      scrollPane.setBackground(COLOR_BG);
      scrollPane.setPreferredSize(new Dimension(580, 120)); 
      southPanel.add(scrollPane, "Center");

      JPanel paymentPanel = new JPanel(new BorderLayout());
      paymentPanel.setBackground(COLOR_BG);
      paymentPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

      lblTotal = new JLabel("총 금액 : 0원"); 
      lblTotal.setFont(new Font("Malgun Gothic", Font.BOLD, 22));
      
      btnPayment = new JButton("결제");
      btnPayment.setFont(FONT_HEADER);
      btnPayment.setBackground(COLOR_TAB_ACTIVE); 
      btnPayment.setForeground(COLOR_FONT_ACTIVE); 
      btnPayment.setOpaque(true);
      btnPayment.setBorderPainted(false);
      btnPayment.setFocusPainted(false);
      btnPayment.setPreferredSize(new Dimension(150, 60));
      
      paymentPanel.add(lblTotal, "Center");
      paymentPanel.add(btnPayment, "East");
      southPanel.add(paymentPanel, "South");

      return southPanel;
   }

   /**
    * (Helper) OrderProductDTO를 받아 주문 1줄 패널 생성 (index 추가)
    */
   private JPanel createSingleOrderItemPanel(OrderProductDTO dto, int index) {
      JPanel itemPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
      itemPanel.setBackground(COLOR_BG);
      itemPanel.setMaximumSize(new Dimension(Short.MAX_VALUE, 40)); 

      String menuDesc = String.format("상품명: %s (%s), %s", 
                                        dto.getMenuName(), 
                                        dto.getTempOption(), 
                                        dto.getSizeOption());
        if (!"기본".equals(dto.getExtraOption())) {
            menuDesc += ", " + dto.getExtraOption();
        }
      
      itemPanel.add(new JLabel(menuDesc));
      itemPanel.add(new JLabel("수량:"));
      
      JButton btnMinus = new JButton("-");
      JLabel lblQty = new JLabel(dto.getAmount() + "개");
      JButton btnPlus = new JButton("+");
      btnMinus.setActionCommand("MINUS_" + index);
      btnPlus.setActionCommand("PLUS_" + index);
      
      itemPanel.add(btnMinus);
      itemPanel.add(lblQty);
      itemPanel.add(btnPlus);
      itemPanel.add(new JLabel("금액: " + dto.getOrderPrice() + "원"));
      
      JButton btnDelete = new JButton("X");
      btnDelete.setForeground(Color.RED);
      btnDelete.setBackground(COLOR_BG);
      btnDelete.setActionCommand("DELETE_" + index);
      if(this.orderEvent != null) {
         btnMinus.addActionListener(this.orderEvent);
         btnPlus.addActionListener(this.orderEvent);
         btnDelete.addActionListener(this.orderEvent);
      }
      
      itemPanel.add(btnDelete);
      
      return itemPanel;
   }
   
   /**
    * (Public) OptionEvent에서 "확인"을 누르면 이 메서드가 호출됨.
    */
   public void addOrderItemToPanel(OrderProductDTO dto) {
      shoppingCart.add(dto);
      refreshCart();
   }

   /**
    * (Public) 장바구니 UI를 통째로 새로고침하는 메서드
    */
   public void refreshCart() {
      itemsContainer.removeAll();
      for(int i=0; i < shoppingCart.size(); i++) {
         OrderProductDTO dto = shoppingCart.get(i);
         JPanel newItemPanel = createSingleOrderItemPanel(dto, i);
         itemsContainer.add(newItemPanel);
      }
      updateTotalPrice();
      itemsContainer.revalidate();
      itemsContainer.repaint();
      scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
   }
   
   /**
    * (Private Helper) 장바구니 리스트(shoppingCart)를 기반으로 총액을 다시 계산
    */
   private void updateTotalPrice() {
      int total = 0;
      for (OrderProductDTO item : shoppingCart) {
         total += item.getOrderPrice();
      }
      lblTotal.setText("총 금액 : " + total + "원");
   }
   /**
    * (Helper) 좌우 화살표 버튼 스타일
    */
   private void styleArrowButton(JButton button) {
      button.setFont(FONT_HEADER);
      button.setBackground(COLOR_BG);
      button.setBorderPainted(false);
      button.setFocusPainted(false);
      button.setPreferredSize(new Dimension(50, 50));
   }

   /**
    * (Helper) 상단 탭 버튼 스타일
    */
   private void styleTabButton(JButton button, boolean active) {
      button.setFont(FONT_HEADER);
      button.setOpaque(true);
      button.setBorderPainted(false);
      button.setFocusPainted(false);
      button.setPreferredSize(new Dimension(200, 60));
      
      if(active) {
         button.setBackground(COLOR_TAB_ACTIVE); 
         button.setForeground(COLOR_FONT_ACTIVE); 
      } else {
         button.setBackground(COLOR_TAB_INACTIVE); 
         button.setForeground(COLOR_FONT_INACTIVE);
      }
   }
   /**
    * (Public) 탭 버튼 스타일 업데이트 (OrderEvent가 호출)
    */
   public void updateTabStyles(JButton activeTab) {
      styleTabButton(jbtnHome, activeTab == jbtnHome);
      styleTabButton(jbtnCoffee, activeTab == jbtnCoffee);
      styleTabButton(jbtnBeverage, activeTab == jbtnBeverage);
   }
   /**
    * (Helper) 페이지 표시 라벨 스타일 ("1페이지", "2페이지")
    */
   private void stylePageIndicator(JLabel label, boolean active) {
      label.setFont(FONT_COMMON);
      label.setOpaque(true);
      label.setBackground(COLOR_BG);
      Border border; 
      if(active) {
         border = BorderFactory.createMatteBorder(0, 0, 3, 0, COLOR_MEGA_YELLOW); // [수정 후]
      } else {
         border = BorderFactory.createMatteBorder(0, 0, 3, 0, COLOR_BG);
      }
      label.setBorder(BorderFactory.createCompoundBorder(
            border,
            BorderFactory.createEmptyBorder(5, 5, 5, 5) 
      ));
   }
   
   /**
    * (Public) 페이지 표시기 스타일 업데이트 (OrderEvent가 호출)
    */
   public void updatePageIndicator(String panelType, int page) {
      if(panelType.equals("Coffee")) {
         stylePageIndicator(jlblCoffeePage1, page == 1);
         stylePageIndicator(jlblCoffeePage2, page == 2);
      } else if(panelType.equals("Beverage")) {
         stylePageIndicator(jlblBeveragePage1, page == 1);
         stylePageIndicator(jlblBeveragePage2, page == 2);
      }
   }

   public List<OrderProductDTO> getShoppingCart() {
      return shoppingCart;
   }
   public JButton getBtnPayment() {
      return btnPayment;
   }
   public JButton getJbtnHome() { return jbtnHome; }
   public JButton getJbtnCoffee() { return jbtnCoffee; }
   public JButton getJbtnBeverage() { return jbtnBeverage; }
   public JPanel getJpCenter() { return jpCenter; }
   public CardLayout getClMain() { return clMain; }
   
   public CardLayout getClCoffee() { return clCoffee; }
   public JPanel getJpCoffeeMenuCards() { return jpCoffeeMenuCards; }
   public JButton getJbtnCoffeePrev() { return jbtnCoffeePrev; }
   public JButton getJbtnCoffeeNext() { return jbtnCoffeeNext; }
   public JButton[] getJbtnCoffeeMenus() { return jbtnCoffeeMenus; }
   public JLabel getJlblCoffeePage1() { return jlblCoffeePage1; } 
   public JLabel getJlblCoffeePage2() { return jlblCoffeePage2; } 
   public CardLayout getClBeverage() { return clBeverage; }
   public JPanel getJpBeverageMenuCards() { return jpBeverageMenuCards; }
   public JButton getJbtnBeveragePrev() { return jbtnBeveragePrev; }
   public JButton getJbtnBeverageNext() { return jbtnBeverageNext; }
   public JButton[] getJbtnBeverageMenus() { return jbtnBeverageMenus; }
   public JLabel getJlblBeveragePage1() { return jlblBeveragePage1; } 
   public JLabel getJlblBeveragePage2() { return jlblBeveragePage2; } 

}