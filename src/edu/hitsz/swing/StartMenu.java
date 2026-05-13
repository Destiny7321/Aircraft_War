package edu.hitsz.swing;

import edu.hitsz.application.SurvivorGame;
import edu.hitsz.application.TravellerGame;
import edu.hitsz.application.WarriorGame;
import javax.swing.*;

public class StartMenu {
    private JPanel mainPanel;
    private JButton travellerButton;
    private JButton survivorButton;
    private JButton warriorButton;
    private JLabel 选择难度;
    private JLabel titleLabel;

    public StartMenu() {

        // 旅人模式
        travellerButton.addActionListener(e -> {
            // 关闭菜单（系统自带函数）
            SwingUtilities.getWindowAncestor(mainPanel).dispose();
            // 启动游戏（你自己写的 action）
            new Thread(() -> new TravellerGame().action()).start();
        });

        // 求生模式
        survivorButton.addActionListener(e -> {
            SwingUtilities.getWindowAncestor(mainPanel).dispose();
            new Thread(() -> new SurvivorGame().action()).start();
        });

        // 战士模式
        warriorButton.addActionListener(e -> {
            SwingUtilities.getWindowAncestor(mainPanel).dispose();
            new Thread(() -> new WarriorGame().action()).start();
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Who Do You Want To Be");
        frame.setContentPane(new StartMenu().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}