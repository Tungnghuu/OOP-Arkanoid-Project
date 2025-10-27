package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectToDB {
    public static Connection connect() {

        String url = "jdbc:mysql://switchback.proxy.rlwy.net:31181/railway?useSSL=false&serverTimezone=UTC";
        String user = "root";
        String password = "HPUlBDpZgDXslMwJRTANlSHkLkozugaa";

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Kết nối DB thành công");

        } catch (SQLException e) {
            System.out.println("Kết nối DB thất bại");
            e.printStackTrace();
        }
        return conn;
    }
}
