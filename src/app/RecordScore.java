package app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RecordScore {

    public static  void insertScore(Score score) {
        String sql = "INSERT INTO HighScore(recordTime, score) VALUES(?,?)";

        try ( Connection conn = ConnectToDB.connect();
        PreparedStatement preparedStatement = conn.prepareStatement(sql)){

            preparedStatement.setInt(2, score.getScore());
            preparedStatement.setTimestamp(1, score.getRecordTime());

            preparedStatement.execute();
            System.out.println("Ket noi va ghi diem thanh cong");
        } catch (SQLException e) {
            System.out.println("Ket noi that bai");
        }
    }
}
