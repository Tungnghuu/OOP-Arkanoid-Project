package app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GetHistory {

    public static List<Object[]> getHistory() {
        List<Object[]> history = new ArrayList<>();
        Path path_history = Paths.get("src/log/history.txt");

        try {
            List<String> lines = Files.readAllLines(path_history);
            Collections.reverse(lines);
            for (String line : lines) {
                String[] parts = line.split(" ");
                if (parts.length < 3) continue;
                int score = Integer.parseInt(parts[0]);
                String datetime = parts[1] + " " + parts[2];
                if (datetime.contains(".")) {
                    datetime = datetime.split("\\.")[0];
                }
                Timestamp recordTime = Timestamp.valueOf(datetime);
                history.add(new Object[] {datetime, score});
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return history;
    }
}
