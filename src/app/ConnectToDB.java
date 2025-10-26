package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectToDB {
    public static Connection connect() {

        String url = "jdbc:mysql://127.0.0.1:3307/classicmodels?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        String user = "root";
        String password = "vu05082006$";

        Connection conn = null;
        try{
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Ket noi den DB thanh cong");

        } catch (SQLException e) {
            System.out.println("Ket noi den DB that bai");
            e.printStackTrace();
        }
        return conn;
    }
}
