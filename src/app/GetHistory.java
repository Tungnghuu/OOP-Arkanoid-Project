    package app;

    import java.sql.*;
    import java.util.ArrayList;
    import java.util.List;

    public class GetHistory {

        public static List<Object[]> getHistory() {
            List<Object[]> records = new ArrayList<>();

            String sql = "SELECT playerId, recordTime, playerScore FROM player ORDER BY playerScore DESC LIMIT 10";

            try (Connection conn = ConnectToDB.connect()) {
                if (conn == null) {
                    System.out.println("Connection is null. Cannot fetch history.");
                    return records;
                }

                try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                    ResultSet record = preparedStatement.executeQuery();
                    int stt = 1;
                    while (record.next()) {
                        int playerId = record.getInt("playerId");
                        int score = record.getInt("playerScore");
                        Timestamp recordTime = record.getTimestamp("recordTime");
                        records.add(new Object[] {stt, playerId, recordTime, score});
                        stt++;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return records;
        }
    }
