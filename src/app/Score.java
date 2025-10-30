package app;

import java.sql.Timestamp;

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

    public void setRecordTime(Timestamp recordTime) {
        this.recordTime = recordTime;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Timestamp getRecordTime() {
        return recordTime;
    }

    public String toString() {
        return (score + " " + recordTime + '\n');
    }
}
