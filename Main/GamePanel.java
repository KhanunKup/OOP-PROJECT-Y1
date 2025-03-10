package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePanel extends JPanel implements Runnable, KeyListener {
    public Thread gameThread;
    public int imageX, imageY;
    public String text, text_2;
    public int textX, textY, textWidth, textHeight;
    public FontMetrics fm;
    public int gameState = 0; // 0 = title , 1 = play
    public int selectedIndex = 0;
    public String[] menuOptions = {"Play", "Option", "Exit"};
    public boolean checkAlphaText = false;
    public double textDelay,imageDelay;
    public boolean showImage = false;

    private double alpha = 0.0;
    private double alphaSpeed = 0.02;

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

        if(gameState == 1){
            if (!showImage){
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

            if (showImage) {

                if (imageDelay >= 0 && imageDelay < 10){
                    ImageIcon icon = new ImageIcon("res/eyes.JPG");
                    Image image = icon.getImage();
                    g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
                }
                else if (imageDelay >= 10 && imageDelay < 20) {
                    ImageIcon icon_2 = new ImageIcon("res/woody.JPG");
                    Image image_2 = icon_2.getImage();
                    g.drawImage(image_2, 0, 0, getWidth(), getHeight(), null);
                }
                else if (imageDelay >= 20 && imageDelay < 30) {
                    ImageIcon icon_3 = new ImageIcon("res/nugget.JPG");
                    Image image_3 = icon_3.getImage();
                    g.drawImage(image_3, 0, 0, getWidth(), getHeight(), null);
                }
            }
        }

        if(gameState == 2){
            g.setColor(Color.WHITE);
            g.setFont(new Font("Monospaced", Font.PLAIN, 36));

            fm = g.getFontMetrics();
            text = "WAITING FOR TILE MAP.";
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
    public void keyTyped(KeyEvent e) {}

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
            if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                switch (selectedIndex) {
                    case 0:
                        gameState = 1;
                        repaint();

                        new Thread(() -> {
                            try {

                                repaint();
                                Thread.sleep(5000);

                                imageDelay = 0;
                                showImage = true;
                                repaint();
                                Thread.sleep(5000);


                                imageDelay = 10;
                                repaint();
                                Thread.sleep(5000);

                                imageDelay = 20;
                                repaint();
                                Thread.sleep(5000);

                                showImage = false;
                                gameState = 2;
                                repaint();

                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
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
        if (gameState == 1){
            if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
                gameState = 0;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
