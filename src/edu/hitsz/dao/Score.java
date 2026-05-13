package edu.hitsz.dao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Score {
    private String username;
    private int score;
    private LocalDateTime recordTime;

    public Score(String username, int score, LocalDateTime recordTime) {
        this.username = username;
        this.score = score;
        this.recordTime = recordTime;
    }

    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }

    public LocalDateTime getRecordTime() {
        return recordTime;
    }

    @Override
    public String toString() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return username + "," + score + "," + recordTime.format(formatter);
    }
}