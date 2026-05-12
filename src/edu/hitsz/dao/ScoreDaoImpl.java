package edu.hitsz.dao;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ScoreDaoImpl implements ScoreDao {
    private final String fileName = "scores.txt";
    private List<Score> scoreList;

    public ScoreDaoImpl() {
        scoreList = new ArrayList<>();
        loadFromFile();
    }

    // 从文件加载
    private void loadFromFile() {
        try {
            File file = new File(fileName);
            if (!file.exists()) return;

            List<String> lines = Files.readAllLines(Paths.get(fileName));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            for (String line : lines) {
                if (line.isBlank()) continue;
                String[] parts = line.split(",");
                String name = parts[0];
                int score = Integer.parseInt(parts[1]);
                LocalDateTime time = LocalDateTime.parse(parts[2], formatter);
                // 这里改回 3个参数！
                scoreList.add(new Score(name, score, time));
            }
            sortList();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 保存到文件
    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Score s : scoreList) {
                writer.write(s.toString());
                writer.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 排序
    private void sortList() {
        scoreList.sort((a, b) -> Integer.compare(b.getScore(), a.getScore()));
    }

    @Override
    public void addScore(Score score) {
        scoreList.add(score);
        sortList();
        saveToFile();
    }

    @Override
    public void deleteByRank(int rank) {
        if (rank >= 0 && rank < scoreList.size()) {
            scoreList.remove(rank);
            saveToFile();
        }
    }

    // ==========================
    // 删除方法（改好）
    // ==========================
    @Override
    public void deleteScore(Score score) {
        scoreList.removeIf(s ->
                s.getUsername().equals(score.getUsername()) &&
                        s.getScore() == score.getScore()
        );
        saveToFile();
    }

    @Override
    public List<Score> getAllScore() {
        return new ArrayList<>(scoreList);
    }

    @Override
    public List<Score> getScoreList() {
        return scoreList;
    }

    // ==========================
    // 控制台打印（改好）
    // ==========================
    @Override
    public void printRankList() {
        System.out.println("====== 得分排行榜 ======");
        for (int i = 0; i < scoreList.size(); i++) {
            Score s = scoreList.get(i);
            System.out.printf("第%d名: %s, %d分\n",
                    i + 1,
                    s.getUsername(),
                    s.getScore()
            );
        }
    }
}