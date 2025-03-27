package Main;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static JFrame frame;

    public static void main(String[] args) {
        frame = new JFrame("Sweet Tomb");
        GamePanel gamePanel = new GamePanel();
        ImageManager imageManager = new ImageManager();
        imageManager.setImage("logo","res/Logo.png");
        frame.add(gamePanel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setIconImage(imageManager.getImage("logo"));
        gamePanel.startThread();
    }
}