package app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.io.IOException;
import java.nio.file.*;

public class RecordScore {
    public static void insert_to_log(Score score) {
        Path historyFile = Paths.get(System.getProperty("user.dir"), "src", "log", "history.txt");

        try {
            if (!Files.exists(historyFile)) {
                System.out.println("history.txt does not exist!");
                throw new IOException("File does not exist!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Files.writeString(historyFile, score.toString(), StandardOpenOption.APPEND);
            System.out.println("Wrote to history.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static  void insertScore(Score score) {
        insert_to_log(score);

        String sql = "INSERT INTO highScore(recordTime, score) VALUES(?,?)";

        try ( Connection conn = ConnectToDB.connect();
        PreparedStatement preparedStatement = conn.prepareStatement(sql)){

            preparedStatement.setInt(2, score.getScore());
            preparedStatement.setTimestamp(1, score.getRecordTime());

            preparedStatement.execute();
            System.out.println("Ket noi va ghi diem thanh cong");
        } catch (SQLException e) {
            System.out.println("Failed to Connect.");
        }
    }
}
