package app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetHistory {
    public static List<String> history = new ArrayList<>();

    public static List<String> getHistory() {
        try (BufferedReader br = new BufferedReader(new FileReader("log/history.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                history.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return history;
    }
}
