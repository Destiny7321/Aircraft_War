package edu.hitsz.dao;

import java.util.List;

public interface ScoreDao {
    // 添加一条记录
    void addScore(Score score);

    // 删除一条记录（按名次）
    void deleteByRank(int rank);

    // 获取所有记录（已按分数从高到低排序）
    List<Score> getScoreList();

    // 打印排行榜
    void printRankList();

    //删除分数
    void deleteScore(Score score);
    List<Score> getAllScore();

}