package kiosk.admin.dao;

import kiosk.admin.dto.MenuListAddDTO;
import kiosk.admin.dto.MenuListSelectDTO;
import kiosk.admin.dto.MenuListUpdateDTO;
import kiosk.user.dao.GetConnection;

import java.io.File;
import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MenuManageDAO {
    private static MenuManageDAO mmDAO;
    private MenuManageDAO(){}

    public static MenuManageDAO getInstance(){
        if(mmDAO==null) mmDAO = new MenuManageDAO();
        return mmDAO;
    }

    /**
     * 
     * 메뉴 이름이 DB에 이미 존재
     * @param menuName 검사할 메뉴 이름
     * @return 중복이면 true, 아니면 false
     */
    public boolean isMenuNameDuplicate(String menuName) {
        boolean isDuplicate = false;
        String sql = "SELECT COUNT(*) FROM menu WHERE menu_name = ?";
        
        try (Connection con = GetConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, menuName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    if (rs.getInt(1) > 0) { // 1개 이상 존재하면
                        isDuplicate = true; // 중복
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isDuplicate;
    }

    public List<String> selectAllMenuNames() {
        List<String> menuNames = new ArrayList<>();
        String sql = "SELECT menu_name FROM menu WHERE delete_flag = 'N' ORDER BY menu_name";
        try (Connection con = GetConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                menuNames.add(rs.getString("menu_name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return menuNames;
    }

    public int deleteMenu(String menuName) {
        int affectedRows = 0;
        String sql = "UPDATE menu SET delete_flag = 'Y' WHERE menu_name = ?";
        try (Connection con = GetConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, menuName);
            affectedRows = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return affectedRows;
    }
    
    public int insertMenu(MenuListAddDTO dto) {
        Connection con = null;
        FileInputStream fis = null;
        try {
            con = GetConnection.getInstance().getConnection();
            con.setAutoCommit(false);

            String insertMenuSql = "INSERT INTO MENU(menu_name, price, menu_img, delete_flag) VALUES (?, ?, ?, 'N')";
            try (PreparedStatement ps = con.prepareStatement(insertMenuSql)) {
                ps.setString(1, dto.getMenuName());
                ps.setInt(2, dto.getMenuPrice());
                String imgPath = dto.getImageData();
                if (imgPath != null && !imgPath.isEmpty()) {
                    File imgFile = new File(imgPath);
                    fis = new FileInputStream(imgFile);
                    ps.setBinaryStream(3, fis, (int) imgFile.length());
                } else {
                    ps.setNull(3, Types.BLOB);
                }
                ps.executeUpdate();
            }

            String insertCategorySql = "INSERT INTO CATEGORY_OPTION(menu_name, category_code) VALUES (?, ?)";
            try (PreparedStatement ps = con.prepareStatement(insertCategorySql)) {
                ps.setString(1, dto.getMenuName());
                ps.setInt(2, "커피".equals(dto.getCategory()) ? 1 : 2);
                ps.executeUpdate();
            }

            String insertTempSql = "INSERT INTO TEMP_OPTION(menu_name, temp_code) VALUES (?, ?)";
            try (PreparedStatement ps = con.prepareStatement(insertTempSql)) {
                Map<String, Integer> tempMap = Map.of("핫", 1, "아이스", 2);
                if (dto.getTempOption() != null && !dto.getTempOption().isEmpty()) {
                    for (String tempOpt : dto.getTempOption().split(" ")) {
                        ps.setString(1, dto.getMenuName());
                        ps.setInt(2, tempMap.get(tempOpt));
                        ps.addBatch();
                    }
                    ps.executeBatch();
                }
            }

            String insertSizeSql = "INSERT INTO SIZE_OPTION(menu_name, size_code) VALUES (?, ?)";
            try (PreparedStatement ps = con.prepareStatement(insertSizeSql)) {
                Map<String, Integer> sizeMap = Map.of("M", 1, "L(+500)", 2);
                if (dto.getSizeOption() != null && !dto.getSizeOption().isEmpty()) {
                    for (String sizeOpt : dto.getSizeOption().split(" ")) {
                        ps.setString(1, dto.getMenuName());
                        ps.setInt(2, sizeMap.get(sizeOpt));
                        ps.addBatch();
                    }
                    ps.executeBatch();
                }
            }

            String insertShotSql = "INSERT INTO SHOT_OPTION(menu_name, shot_code) VALUES (?, ?)";
            try (PreparedStatement ps = con.prepareStatement(insertShotSql)) {
                Map<String, Integer> shotMap = Map.of("기본", 1, "연하게", 2, "샷추가(+500)", 3);
                if (dto.getShotOption() != null && !dto.getShotOption().isEmpty()) {
                    for (String shotOpt : dto.getShotOption().split(" ")) {
                        ps.setString(1, dto.getMenuName());
                        ps.setInt(2, shotMap.get(shotOpt));
                        ps.addBatch();
                    }
                    ps.executeBatch();
                }
            }
            

            con.commit();
            return 1;

        } catch (Exception e) {
            e.printStackTrace();
            try { if (con != null) con.rollback(); } catch (SQLException se) { se.printStackTrace(); }
            return 0;
        } finally {
            try { if (fis != null) fis.close(); } catch (Exception e) { e.printStackTrace(); }
            try { if (con != null) con.setAutoCommit(true); } catch (SQLException se) { se.printStackTrace(); }
            try { if (con != null) con.close(); } catch (SQLException se) { se.printStackTrace(); }
        }
    }
    
    public List<MenuListSelectDTO> selectAllMenuList(int categoryCode){
        List<MenuListSelectDTO> list = new ArrayList<>();
        String sql =
            "SELECT c.category_text AS category, m.menu_name, m.price, " +
            "CASE WHEN NVL(i.quantity, 0) <= 0 THEN '품절' ELSE '판매중' END AS status " +
            "FROM menu m " +
            "LEFT JOIN inventory i ON m.menu_name = i.menu_name " +
            "JOIN category_option co ON m.menu_name = co.menu_name " +
            "JOIN category c ON co.category_code = c.category_code " +
            "WHERE m.delete_flag = 'N' AND c.category_code = ? " +
            "ORDER BY m.menu_name";
            
        try (Connection con = GetConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, categoryCode);
            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()){
                    String category = rs.getString("category");
                    String menuName = rs.getString("menu_name");
                    String priceStr = String.valueOf(rs.getInt("price")) + "원";
                    String status = rs.getString("status");
                    String[] temp = getOptions(con, "TEMP_OPTION", "TEMP", "temp_code", "temp_text", menuName).toArray(new String[0]);
                    String[] size = getOptions(con, "SIZE_OPTION", "SIZE_TABLE", "size_code", "size_text", menuName).toArray(new String[0]);
                    list.add(new MenuListSelectDTO(category, menuName, temp, size, priceStr, status));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public int updateMenu(MenuListUpdateDTO dto) {
        Connection con = null;
        FileInputStream fis = null;
        try {
            con = GetConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            
            String baseUpdateSql = "UPDATE MENU SET price = ? WHERE menu_name = ?";
            try (PreparedStatement ps = con.prepareStatement(baseUpdateSql)) {
                ps.setInt(1, dto.getMenuPrice());
                ps.setString(2, dto.getMenuName());
                ps.executeUpdate();
            }

            String imgPath = dto.getImageData();
            if (imgPath != null && !imgPath.isEmpty()) {
                String imageUpdateSql = "UPDATE MENU SET menu_img = ? WHERE menu_name = ?";
                try (PreparedStatement ps = con.prepareStatement(imageUpdateSql)) {
                    File imgFile = new File(imgPath);
                    fis = new FileInputStream(imgFile);
                    ps.setBinaryStream(1, fis, (int) imgFile.length());
                    ps.setString(2, dto.getMenuName());
                    ps.executeUpdate();
                }
            }

            String updateCategorySql = "UPDATE CATEGORY_OPTION SET category_code = ? WHERE menu_name = ?";
            try (PreparedStatement ps = con.prepareStatement(updateCategorySql)) {
                ps.setInt(1, "커피".equals(dto.getCategory()) ? 1 : 2);
                ps.setString(2, dto.getMenuName());
                ps.executeUpdate();
            }

            String[] deleteSqls = {
                "DELETE FROM TEMP_OPTION WHERE menu_name = ?",
                "DELETE FROM SIZE_OPTION WHERE menu_name = ?",
                "DELETE FROM SHOT_OPTION WHERE menu_name = ?"
            };
            for (String sql : deleteSqls) {
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setString(1, dto.getMenuName());
                    ps.executeUpdate();
                }
            }

            String insertTempSql = "INSERT INTO TEMP_OPTION(menu_name, temp_code) VALUES (?, ?)";
            try (PreparedStatement ps = con.prepareStatement(insertTempSql)) {
                Map<String, Integer> tempMap = Map.of("핫", 1, "아이스", 2);
                if(dto.getTempOption() != null) for (String tempOpt : dto.getTempOption()) {
                    ps.setString(1, dto.getMenuName());
                    ps.setInt(2, tempMap.get(tempOpt));
                    ps.addBatch();
                }
                ps.executeBatch();
            }

            String insertSizeSql = "INSERT INTO SIZE_OPTION(menu_name, size_code) VALUES (?, ?)";
            try (PreparedStatement ps = con.prepareStatement(insertSizeSql)) {
                Map<String, Integer> sizeMap = Map.of("M", 1, "L(+500)", 2);
                if(dto.getSizeOption() != null) for (String sizeOpt : dto.getSizeOption()) {
                    ps.setString(1, dto.getMenuName());
                    ps.setInt(2, sizeMap.get(sizeOpt));
                    ps.addBatch();
                }
                ps.executeBatch();
            }

            String insertShotSql = "INSERT INTO SHOT_OPTION(menu_name, shot_code) VALUES (?, ?)";
            try (PreparedStatement ps = con.prepareStatement(insertShotSql)) {
                Map<String, Integer> shotMap = Map.of("기본", 1, "연하게", 2, "샷추가(+500)", 3);
                if(dto.getShotOption() != null) for (String shotOpt : dto.getShotOption()) {
                    ps.setString(1, dto.getMenuName());
                    ps.setInt(2, shotMap.get(shotOpt));
                    ps.addBatch();
                }
                ps.executeBatch();
            }

            con.commit();
            return 1;

        } catch (Exception e) {
            e.printStackTrace();
            try { if (con != null) con.rollback(); } catch (SQLException se) { se.printStackTrace(); }
            return 0;
        } finally {
            try { if (fis != null) fis.close(); } catch (Exception e) { e.printStackTrace(); }
            try { if (con != null) con.setAutoCommit(true); } catch (SQLException se) { se.printStackTrace(); }
            try { if (con != null) con.close(); } catch (SQLException se) { se.printStackTrace(); }
        }
    }

    public MenuListUpdateDTO getMenuDetails(String menuName) {
        MenuListUpdateDTO dto = new MenuListUpdateDTO();
        dto.setMenuName(menuName);

        String baseInfoSql = 
            "SELECT m.price, c.category_text, m.menu_img, NVL(i.quantity, 0) AS quantity " +
            "FROM MENU m " +
            "JOIN CATEGORY_OPTION co ON m.menu_name = co.menu_name " +
            "JOIN CATEGORY c ON c.category_code = co.category_code " +
            "LEFT JOIN INVENTORY i ON m.menu_name = i.menu_name " +
            "WHERE m.menu_name = ?";
        
        try (Connection con = GetConnection.getInstance().getConnection()) {
            try (PreparedStatement ps = con.prepareStatement(baseInfoSql)) {
                ps.setString(1, menuName);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        dto.setMenuPrice(rs.getInt("price"));
                        dto.setCategory(rs.getString("category_text"));
                        dto.setQuantity(rs.getInt("quantity"));
                        
                        Blob blob = rs.getBlob("menu_img");
                        if (blob != null) {
                            byte[] imgBytes = blob.getBytes(1, (int) blob.length());
                            java.awt.Image image = javax.imageio.ImageIO.read(new java.io.ByteArrayInputStream(imgBytes));
                            dto.setImage(image);
                        }
                    }
                }
            }
            dto.setTempOption(getOptions(con, "TEMP_OPTION", "TEMP", "temp_code", "temp_text", menuName));
            dto.setSizeOption(getOptions(con, "SIZE_OPTION", "SIZE_TABLE", "size_code", "size_text", menuName));
            dto.setShotOption(getOptions(con, "SHOT_OPTION", "SHOT", "shot_code", "shot_text", menuName));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return dto;
    }

    private List<String> getOptions(Connection con, String optionTable, String textTable, String codeColumn, String textColumn, String menuName) throws SQLException {
        List<String> options = new ArrayList<>();
        String sql = String.format(
            "SELECT t.%s FROM %s t JOIN %s o ON t.%s = o.%s WHERE o.menu_name = ?",
            textColumn, textTable, optionTable, codeColumn, codeColumn
        );
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, menuName);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    options.add(rs.getString(1));
                }
            }
        }
        
        for (int i = 0; i < options.size(); i++) {
            String opt = options.get(i);
            if ("Hot".equalsIgnoreCase(opt)) options.set(i, "핫");
            else if ("Ice".equalsIgnoreCase(opt)) options.set(i, "아이스");
            else if ("Regular".equalsIgnoreCase(opt)) options.set(i, "M");
            else if ("Large".equalsIgnoreCase(opt)) options.set(i, "L(+500)");
            else if ("샷 추가".equalsIgnoreCase(opt)) options.set(i, "샷추가(+500)");
        }
        
        return options;
    }
}

