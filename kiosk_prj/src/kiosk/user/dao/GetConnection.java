package kiosk.user.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
 

public class GetConnection {
    private static GetConnection dbConn;

    private GetConnection() {
    }

    public static GetConnection getInstance() {
        if (dbConn == null) {
            dbConn = new GetConnection();
        }
        return dbConn;
    }

    /**
     * Oracle DB 연결을 위한 Connection 객체를 반환합니다.
     * @return Connection
     * @throws SQLException
     * @throws IOException 
     * @throws  
     */
    public Connection getConnection() throws SQLException, IOException {
        Connection con = null;
        
        // 최신 드라이버명
        try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        // 2. DB 연결 정보 
        // properties 경로는 환경에 맞춰 수정
        File file = new File("C:\\Users\\user\\git\\team_1\\kiosk_prj\\src\\properties\\database.properties");
        Properties prop = new Properties();
        prop.load(new FileInputStream(file));

        String url = prop.getProperty("url"); 
        String user = prop.getProperty("id"); 
        String pass = prop.getProperty("pass"); 
       
        // 3. 연결
        con = DriverManager.getConnection(url, user, pass);
        return con;
    }

    /**
     * 연결 객체를 닫습니다. (Connection, PreparedStatement, ResultSet)
     * @param con
     * @param pstmt
     * @param rs
     */
    public void dbClose(Connection con, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}