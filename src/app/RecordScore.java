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
        Path logDir = Paths.get(System.getProperty("user.dir"), "log");
        Path historyFile = logDir.resolve("history.txt");
        Path idFile = logDir.resolve("player_id.txt");

        try {
            Files.createDirectories(logDir);
            if (!Files.exists(historyFile)) Files.createFile(historyFile);
            if (!Files.exists(idFile)) Files.createFile(idFile);
        } catch (IOException e) {
            System.out.println("Cannot create log files.");
            e.printStackTrace();
        }

        int playerId;
        String sql = "INSERT INTO player(playerId, recordTime, playerScore) VALUES(?, ?, ?)";

        try {
            // Nếu file player_id.txt trống → tạo ID mới
            String idText = Files.readString(idFile).trim();
            if (idText.isEmpty()) {
                playerId = 100000 + new Random().nextInt(900000);
                Files.writeString(idFile, String.valueOf(playerId));
                System.out.println("Generated new playerId: " + playerId);
            } else {
                playerId = Integer.parseInt(idText);
            }

            // Ghi vào DB
            try (Connection conn = ConnectToDB.getInstance().getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, playerId);
                ps.setTimestamp(2, score.getRecordTime());
                ps.setInt(3, score.getScore());
                ps.execute();
                System.out.println("Ghi điểm lần đầu thành công.");
            } catch (SQLException e) {
                System.out.println("Failed to connect (insert_to_log).");
                e.printStackTrace();
            }

            // Ghi vào file history
            Files.writeString(historyFile, score.toString() + System.lineSeparator(),
                    StandardOpenOption.APPEND);
            System.out.println("Ghi log thành công vào history.txt");

        } catch (IOException e) {
            System.out.println("I/O file ID");
            e.printStackTrace();
        }
    }

    public static void updateScore(Score score) {
        insert_to_log(score);

        String sql = "UPDATE player SET recordTime = ?, playerScore = ? WHERE playerId = ?";
        Path logDir = Paths.get(System.getProperty("user.dir"), "log");
        Path historyPath = logDir.resolve("history.txt");
        Path idPath = logDir.resolve("player_id.txt");

        Score highestScore = new Score(0, null);

        try {
            List<String> lines = Files.readAllLines(historyPath);
            for (String line : lines) {
                String[] parts = line.split(" ");
                if (parts.length < 3) continue;
                int temp = Integer.parseInt(parts[0]);
                String datetime = parts[1] + " " + parts[2];
                if (datetime.contains(".")) datetime = datetime.split("\\.")[0];
                Timestamp recordTime = Timestamp.valueOf(datetime);
                if (temp > highestScore.getScore()) {
                    highestScore.setScore(temp);
                    highestScore.setRecordTime(recordTime);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            int id = Integer.parseInt(Files.readString(idPath).trim());
            try (Connection conn = ConnectToDB.getInstance().getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setTimestamp(1, highestScore.getRecordTime());
                ps.setInt(2, highestScore.getScore());
                ps.setInt(3, id);
                ps.executeUpdate();
                System.out.println("Cập nhật điểm cao thành công.");
            }
        } catch (Exception e) {
            System.out.println("Failed to update score.");
            e.printStackTrace();
        }
    }
}
