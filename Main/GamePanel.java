package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePanel extends JPanel implements Runnable, KeyListener {
    public Thread gameThread;
    public int imageX,imageY;
    public String text;
    public int textX,textY,textWidth,textHeight;
    public FontMetrics fm;
    public int gameState = 0; // 0 = title , 1 = play
    public int selectedIndex = 0;
    public String[] menuOptions = {"Play","Option","Exit"};
    public GamePanel(){
        this.setPreferredSize(new Dimension(800, 475));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(this);
    }
    public void startThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        if(gameState == 0){
            ImageIcon icon = new ImageIcon("res/book.png");
            Image image = icon.getImage();
            int imgWidth = image.getWidth(this);
            int imgHeight = image.getHeight(this);
            imageX = (getWidth() - imgWidth) / 2;
            imageY = (getHeight() - imgHeight) / 2;
            g.drawImage(image, imageX, imageY, null);

            g.setFont(new Font("tahoma", Font.PLAIN, 42));
            text = "Sweet Tomb";
            fm = g.getFontMetrics();
            textX = (getWidth() - fm.stringWidth(text)) / 2;
            textY = imageY + 10;
            g.drawString(text, textX, textY);

            g.setFont(new Font("Tahoma", Font.PLAIN, 36));
            for (int i = 0; i < menuOptions.length; i++) {
                int textX = (getWidth() - g.getFontMetrics().stringWidth(menuOptions[i])) / 2;
                int textY = 200 + i * 50;
                if (i == selectedIndex) {
                    g.setColor(Color.YELLOW);
                } else {
                    g.setColor(Color.WHITE);
                }
                g.drawString(menuOptions[i], textX, textY);
            }
        }
        if(gameState == 1){
            g.setFont(new Font("tahoma", Font.PLAIN, 42));
            g.setColor(Color.WHITE);
            text = "Kuy Cave";
            fm = g.getFontMetrics();
            textWidth = fm.stringWidth(text);
            textHeight = fm.getHeight();
            textX = (getWidth() - textWidth) / 2;
            textY = (getHeight() - textHeight) / 2 + fm.getAscent();
            g.drawString(text, textX, textY);
        }
    }
    @Override
    public void run() {
        try {
            while (true){
                repaint();
                Thread.sleep(16);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(gameState == 0){
            if(e.getKeyCode() == KeyEvent.VK_W){
                if (selectedIndex > 0){
                    selectedIndex--;
                }else {
                    selectedIndex = menuOptions.length - 1;
                }
            }
            if(e.getKeyCode() == KeyEvent.VK_S){
                if (selectedIndex < menuOptions.length - 1){
                    selectedIndex++;
                } else {
                    selectedIndex = 0;
                }
            }
            if(e.getKeyCode() == KeyEvent.VK_ENTER){
                switch (selectedIndex){
                    case 0:
                        gameState = 1;
                        repaint();
                        break;
                    case 1:
                        break;
                    case 2:
                        System.exit(0);
                        break;
                }
            }
        }
        if (gameState == 1){
            if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
                gameState = 0;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
