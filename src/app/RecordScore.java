package app;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Random;

public class RecordScore {
    public static void insert_to_log(Score score) {

            String sql = "INSERT INTO player(playerId, recordTime, playerScore) VALUES(?, ?, ?)";
            Path historyFile = Paths.get(System.getProperty("user.dir"), "src", "log", "history.txt");
            Path id_file = Paths.get(System.getProperty("user.dir"),  "src", "log", "player_id.txt");
            int playerId = 0;

        try {
            if (!Files.exists(historyFile)) {
                System.out.println("history.txt does not exist!");
                Random rand = new Random();
                playerId = 100000 + rand.nextInt(900000);
                Files.writeString(id_file, String.valueOf(playerId));

                try ( Connection conn = ConnectToDB.connect();
                      PreparedStatement preparedStatement = conn.prepareStatement(sql)){

                    preparedStatement.setInt(1, playerId);
                    preparedStatement.setTimestamp(2, score.getRecordTime());
                    preparedStatement.setInt(3, score.getScore());
                    preparedStatement.execute();

                    System.out.println("Ket noi va ghi diem thanh cong");

                } catch (SQLException e) {
                    System.out.println("Failed to Connect.");
                }
                throw new IOException("File does not exist!");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Files.writeString(historyFile, score.toString(), StandardOpenOption.CREATE
                    , StandardOpenOption.APPEND);
            System.out.println("Wrote to history.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static  void updateScore(Score score) {
        insert_to_log(score);

        String sql = "UPDATE player SET recordTime = ?, playerScore = ? WHERE playerId = ?";

        Path path_history = Paths.get("src/log/history.txt");
        Path path_id = Paths.get("src/log/player_id.txt");
        Score highestScore = new Score(0, null);


        try {
            List<String> lines = Files.readAllLines(path_history);
            for (String line : lines) {

                String[] parts = line.split(" ");
                if (parts.length < 3) continue;
                int temp = Integer.parseInt(parts[0]);
                String datetime = parts[1] + " " + parts[2];
                if (datetime.contains(".")) {
                    datetime = datetime.split("\\.")[0];
                }

                Timestamp recordTime = Timestamp.valueOf(datetime);
                if (temp > highestScore.getScore()) {
                    highestScore.setScore(temp);
                    highestScore.setRecordTime(recordTime);
                }
            }
            System.out.println("diem cao nhat:" + highestScore.getScore());
        } catch (IOException e) {
            e.printStackTrace();
        }

        int id = 0;
        try {
            String idText = Files.readString(path_id).trim();
            id = Integer.parseInt(idText);
            System.out.println("Đọc playerId từ file: " + id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try ( Connection conn = ConnectToDB.connect();
            PreparedStatement preparedStatement = conn.prepareStatement(sql)){

            preparedStatement.setTimestamp(1, highestScore.getRecordTime());
            preparedStatement.setInt(2, highestScore.getScore());
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();

            System.out.println("Ket noi va ghi diem thanh cong");
        } catch (SQLException e) {
            System.out.println("Failed to Connect.");
        }
    }
}
