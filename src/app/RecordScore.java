package app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.io.IOException;
import java.nio.file.*;
// import java.security.Timestamp;

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

        Path path = Paths.get("src/log/history.txt");
        Score highestScore = new Score(0, null);

        try {
            List<String> lines = Files.readAllLines(path);
            for (String line : lines) {
                int temp = Integer.parseInt(line.split("")[0]);
                if (temp > highestScore.getScore()) {
                    highestScore.setScore(temp);
                    // highestScore.setRecordTime(line.split("")[1]);
                    //TODO: include timestamp
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try ( Connection conn = ConnectToDB.connect();
            PreparedStatement preparedStatement = conn.prepareStatement(sql)){

            preparedStatement.setInt(2, highestScore.getScore());
            preparedStatement.setTimestamp(1, highestScore.getRecordTime());
            preparedStatement.execute();

            System.out.println("Ket noi va ghi diem thanh cong");
        } catch (SQLException e) {
            System.out.println("Failed to Connect.");
        }
    }
}
