package edu.hitsz.swing;

import edu.hitsz.dao.Score;
import edu.hitsz.dao.ScoreDao;
import edu.hitsz.dao.ScoreDaoImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class RankFrame extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private List<Score> scoreList;

    public RankFrame(List<Score> scoreList) {
        this.scoreList = scoreList;
        initUI();
    }

    private void initUI() {
        setTitle("游戏得分排行榜");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        String[] columnNames = {"排名", "玩家姓名", "得分"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        table.setDefaultEditor(Object.class, null);

        loadTableData();

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JButton deleteBtn = new JButton("删除选中记录");
        deleteBtn.addActionListener(e -> deleteSelectedRow());
        JPanel btnPanel = new JPanel();
        btnPanel.add(deleteBtn);
        add(btnPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void loadTableData() {
        tableModel.setRowCount(0);
        for (int i = 0; i < scoreList.size(); i++) {
            Score s = scoreList.get(i);
            tableModel.addRow(new Object[]{
                    i + 1,
                    s.getUsername(),
                    s.getScore()
            });
        }
    }

    private void deleteSelectedRow() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请先选中要删除的记录！");
            return;
        }

        int opt = JOptionPane.showConfirmDialog(this,
                "确定要删除这条记录吗？",
                "删除确认",
                JOptionPane.YES_NO_OPTION);
        if (opt != JOptionPane.YES_OPTION) {
            return;
        }

        Score needDelete = scoreList.get(selectedRow);
        ScoreDao scoreDao = new ScoreDaoImpl();
        scoreDao.deleteScore(needDelete);

        scoreList = scoreDao.getAllScore();
        loadTableData();
    }
}