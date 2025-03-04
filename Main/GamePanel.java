package Main;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    public Thread gameThread;
    public GamePanel(){
        this.setPreferredSize(new Dimension(600, 375));
        this.setBackground(Color.BLACK);
    }
    public void startThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        ImageIcon icon = new ImageIcon("res/book.png");
        Image image = icon.getImage();
        g.drawImage(image, 120, 50, this);
        g.setFont(new Font("tahoma", Font.PLAIN, 42));
        g.drawString("Sweet Tomb", 190, 60);
    }
    @Override
    public void run() {}
}
