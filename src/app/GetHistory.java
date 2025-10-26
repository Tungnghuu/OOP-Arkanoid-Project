package app;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GetHistory {

    public static List<Object[]> getHistory() {
        List<Object[]> records = new ArrayList<>();

        String sql = "SELECT recordTime, score FROM HighScore ORDER BY score DESC LIMIT 10";
        try ( Connection conn = ConnectToDB.connect();
              PreparedStatement preparedStatement = conn.prepareStatement(sql)){
            ResultSet record = preparedStatement.executeQuery();

            int id = 1;
            while (record.next()) {
                int score = record.getInt("score");
                Timestamp recorTime = record.getTimestamp("recordTime");
                records.add(new Object[] {id, recorTime, score });
                id++;
            }
        } catch (SQLException e) {
            System.out.println("Ket noi that bai");
        }
        return records;
    }
}
