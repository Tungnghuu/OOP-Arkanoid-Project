package app;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GetHistory {

    public static List<Object[]> getHistory() {
    List<Object[]> records = new ArrayList<>();

    String sql = "SELECT recordTime, score FROM highScore ORDER BY score DESC LIMIT 10";

    try (Connection conn = ConnectToDB.connect()) {
        if (conn == null) {
            System.out.println("Connection is null. Cannot fetch history.");
            return records;
        }

        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            ResultSet record = preparedStatement.executeQuery();
            int id = 1;
            while (record.next()) {
                int score = record.getInt("score");
                Timestamp recordTime = record.getTimestamp("recordTime");
                records.add(new Object[] {id, recordTime, score});
                id++;
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return records;
}

}
