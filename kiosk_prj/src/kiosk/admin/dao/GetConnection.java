package kiosk.admin.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class GetConnection {
    private static GetConnection instance;
    private GetConnection(){}

    public static GetConnection getInstance(){
        if(instance==null) instance = new GetConnection();
        return instance;
    }

    public Connection getConnection() throws SQLException, IOException, ClassNotFoundException {
        // 최신 드라이버명
        Class.forName("oracle.jdbc.OracleDriver");

        // properties 경로는 환경에 맞춰 수정
        File file = new File("src/properties/database.properties");
        Properties prop = new Properties();
        prop.load(new FileInputStream(file));

        return DriverManager.getConnection(
                prop.getProperty("url"),
                prop.getProperty("id"),
                prop.getProperty("pass")
        );
    }

    public void close(Connection con, Statement stmt, ResultSet rs){
        try { if(rs!=null) rs.close(); } catch (Exception ignore){}
        try { if(stmt!=null) stmt.close(); } catch (Exception ignore){}
        try { if(con!=null) con.close(); } catch (Exception ignore){}
    }
}
