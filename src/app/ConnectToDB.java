package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectToDB {
    private static final String URL = "jdbc:mysql://switchback.proxy.rlwy.net:31181/railway?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "HPUlBDpZgDXslMwJRTANlSHkLkozugaa";

    private static ConnectToDB instance = null;

    private Connection connection;

    private ConnectToDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            System.out.println("Failed to connect");
            e.printStackTrace();
        }
    }

    public static ConnectToDB getInstance() {
        if (instance == null) {
            instance = new ConnectToDB();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}

