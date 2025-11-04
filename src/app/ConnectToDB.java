package app;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectToDB {
    private static final String URL = "jdbc:mysql://switchback.proxy.rlwy.net:31181/railway?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "HPUlBDpZgDXslMwJRTANlSHkLkozugaa";

    public static Connection connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            System.out.println("Failed to connect");
            e.printStackTrace();
            return null;
        }
    }
}

