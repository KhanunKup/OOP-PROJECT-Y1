package Main;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static JFrame frame;
    public static boolean isFullScreen = true;

    public static void main(String[] args) {
        frame = new JFrame("Sweet Tomb");
        GamePanel gamePanel = new GamePanel();
        frame.add(gamePanel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setFullScreen(false);
        frame.setVisible(true);
        gamePanel.startThread();
    }

    public static void setFullScreen(boolean enable) {
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = env.getDefaultScreenDevice();

        frame.dispose();
        frame.setUndecorated(enable);
        if (enable) {
            gd.setFullScreenWindow(frame);
        } else {
            gd.setFullScreenWindow(null);
            frame.setLocationRelativeTo(null);
        }
        frame.setVisible(true);
        isFullScreen = enable;
    }
}