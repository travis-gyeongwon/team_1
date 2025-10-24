package kiosk.user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
 

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
     */
    public Connection getConnection() throws SQLException {
        Connection con = null;
        
        // 1. 드라이버 로딩
        try {
            Class.forName("oracle.jdbc.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // 2. DB 연결 정보 
        /////////////////////////////////////////////////// 수정 필요 
        String url = "jdbc:oracle:thin:@localhost:1521:orcl1"; 
        String user = "scott"; 
        String pass = "1234"; 
         
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