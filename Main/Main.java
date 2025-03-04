package Main;

import javax.swing.*;

public class Main {
    public static JFrame frame;

    public static void main(String[] args) {
        frame = new JFrame();
        frame.setTitle("Sweet Tomb");

        GamePanel gamePanel = new GamePanel();
        frame.add(gamePanel);

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);

        gamePanel.startThread();
    }
}
