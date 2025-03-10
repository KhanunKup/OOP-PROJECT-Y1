package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.Key;

public class GamePanel extends JPanel implements Runnable, KeyListener {
    public final int xTile = 16;
    public final int yTile = 16;
    public final int mapX = 800;
    public final int mapY = 475;

    private final int MAIN_MENU = 0;
    private final int TXT_CUTSCENE = 1;
    private final int PNG_CUTSCENE = 2;
    private final int MOVING = 3;

    public Thread gameThread;
    public int imageX, imageY;
    public String text, text_2;
    public int textX, textY, textWidth, textHeight;
    public FontMetrics fm;
    public int gameState = 3; // 0 = title , 1 = play
    public int selectedIndex = 0;
    public String[] menuOptions = {"Play", "Option", "Exit"};
    public boolean checkAlphaText = false;
    public double textDelay;

    public Player player;
    public KeyHandler keyH;

    private double alpha = 0.0;
    private double alphaSpeed = 0.02;

    public GamePanel(){
        this.setPreferredSize(new Dimension(mapX, mapY));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.keyH = new KeyHandler();
        this.addKeyListener(keyH);
        this.player = new Player(this, keyH);
    }

    public void startThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.WHITE);

        if (gameState == 0) {
            ImageIcon icon = new ImageIcon("res/book.png");
            Image image = icon.getImage();
            int imgWidth = image.getWidth(this);
            int imgHeight = image.getHeight(this);
            imageX = (getWidth() - imgWidth) / 2;
            imageY = (getHeight() - imgHeight) / 2;
            g.drawImage(image, imageX, imageY, null);

            g.setFont(new Font("Monospaced", Font.PLAIN, 64));
            text = "Sweet Tomb";
            fm = g.getFontMetrics();
            textX = (getWidth() - fm.stringWidth(text)) / 2;
            textY = imageY + 10;
            g.drawString(text, textX, textY + 30);

            g.setFont(new Font("Monospaced", Font.BOLD, 36));
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

        if(gameState == 1) {
            g.setFont(new Font("Monospaced", Font.PLAIN, 24));

            //โค๊ดเพิ่มความจาง-เข้มกากๆของ Text by 67070106 August *มันอาจจะยังมี bug ถ้าหากบางที Thread มันเอ๋อ การลดความจางอาจจะจบก่อนที่หน้าใหม่จะถูกวาด ซึ่งมันก็จะขึ้นเเดงรัวๆจนกว่าหน้าใหม่จะขึ้น*
            if (alpha < 1.0 && !checkAlphaText) {
                alpha += alphaSpeed;
            }

            if (alpha >= 1.0 || checkAlphaText) {
                textDelay += 0.05;
                checkAlphaText = true;
                if (textDelay >= 10.15){
                    alpha -= 0.02;
                }
            }

            g.setColor(new Color(255, 255, 255, (int) (alpha * 255)));

            fm = g.getFontMetrics();
            text = "Han and Gra giggle as they play in the dense forest,";
            textWidth = fm.stringWidth(text);
            textHeight = fm.getHeight();
            textX = (getWidth() - textWidth) / 2;
            textY = (getHeight() - textHeight) / 2 + fm.getAscent();
            g.drawString(text, textX, textY);

            text_2 = "the sun setting behind the trees.";
            textY += textHeight;
            textWidth = fm.stringWidth(text_2);
            g.drawString(text_2, (getWidth() - textWidth) / 2, textY);
        }

        if (gameState == PNG_CUTSCENE) {
            ImageIcon icon = new ImageIcon("res/eyes.JPG");
            Image image = icon.getImage();
            g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        }

        if (gameState == MOVING) {
            player.draw(g);
        }
    }

    @Override
    public void run() {
        try {
            while (true){
                player.update();
                repaint();
                Thread.sleep(16);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (gameState == 0) {
            if (e.getKeyCode() == KeyEvent.VK_W) {
                if (selectedIndex > 0) {
                    selectedIndex--;
                } else {
                    selectedIndex = menuOptions.length - 1;
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_S) {
                if (selectedIndex < menuOptions.length - 1) {
                    selectedIndex++;
                } else {
                    selectedIndex = 0;
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                switch (selectedIndex) {
                    case 0:
                        gameState = 1;
                        repaint();

                        new Thread(() -> {
                            try {
                                Thread.sleep(5000);
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                            gameState = 2;
                            repaint();
                        }).start();
                        break;

                    case 1:
                        break;

                    case 2:
                        System.exit(0);
                        break;
                }
            }
        }
        if (gameState == 1) {
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                gameState = 0;
            }
//            } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
//                gameState = 2;
//            }
        }
        if (gameState == 2) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                gameState = 3;
            }
        }

        if (gameState == 3) {

        }
    }

    @Override
    public void keyReleased (KeyEvent e) {}

}
