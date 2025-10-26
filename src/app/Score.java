package app;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Score {
    private int score;
    private Timestamp recordTime;

    public Score(int score, Timestamp recordTime) {
        this.score = score;
        this.recordTime = recordTime;
    }

    public int getScore() {
        return score;
    }

    public Timestamp getRecordTime() {
        return recordTime;
    }
}
