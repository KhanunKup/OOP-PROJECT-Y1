package Main;

import javax.swing.*;
import java.awt.*;

public class UI {
    GamePanel gp;
    Graphics g;

    public int imageX, imageY, imgWidth, imgHeight;
    public String text, text_2;
    public int textX, textY, textWidth, textHeight;
    public FontMetrics fm;

    public String[] menuOptions = {"Play", "Option", "Exit"};
    public int selectedIndex = 0;

    public boolean checkAlphaText = false;
    public double textDelay,imageDelay;;
    private double alpha = 0.0;
    private double alphaSpeed = 0.02;
    public boolean showImage = false;

    public final int MAIN_MENU = 0;
    public final int TXT_CUTSCENE = 1;
    public final int MOVING = 2;


    public UI(GamePanel gp){
        this.gp = gp;
    }

    public void draw(Graphics g){
        this.g = g;

        if(gp.gameState == MAIN_MENU){
            drawTitle();
        }
        if(gp.gameState == TXT_CUTSCENE){
            drawText();
        }
        if(gp.gameState == MOVING){
            drawMap();
            gp.player.draw(g);
        }
    }
    public void drawTitle(){
        ImageIcon icon = new ImageIcon("res/book.png");
        Image image = icon.getImage();
        imgWidth = image.getWidth(null);
        imgHeight = image.getHeight(null);
        imageX = (gp.getWidth() - imgWidth) / 2;
        imageY = (gp.getHeight() - imgHeight) / 2;
        g.drawImage(image, imageX, imageY, null);

        g.setFont(new Font("Monospaced", Font.PLAIN, 64));
        String text = "Sweet Tomb";
        FontMetrics fm = g.getFontMetrics();
        int textX = (gp.getWidth() - fm.stringWidth(text)) / 2;
        int textY = imageY + 10;
        g.drawString(text, textX, textY + 30);

        g.setFont(new Font("Monospaced", Font.BOLD, 36));
        for (int i = 0; i < menuOptions.length; i++) {
            int optionX = (gp.getWidth() - g.getFontMetrics().stringWidth(menuOptions[i])) / 2;
            int optionY = 200 + i * 50;
            if (i == selectedIndex) {
                g.setColor(Color.YELLOW);
            } else {
                g.setColor(Color.WHITE);
            }
            g.drawString(menuOptions[i], optionX, optionY);
        }
    }

    public void drawText(){
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
            textX = (gp.getWidth() - textWidth) / 2;
            textY = (gp.getHeight() - textHeight) / 2 + fm.getAscent();
            g.drawString(text, textX, textY);

            text_2 = "the sun setting behind the trees.";
            textY += textHeight;
            textWidth = fm.stringWidth(text_2);
            g.drawString(text_2, (gp.getWidth() - textWidth) / 2, textY);
        }

        if (showImage) {

            if (imageDelay >= 0 && imageDelay < 10){
                ImageIcon icon = new ImageIcon("res/eyes.JPG");
                Image image = icon.getImage();
                g.drawImage(image, 0, 0, gp.getWidth(), gp.getHeight(), null);
            }
            else if (imageDelay >= 10 && imageDelay < 20) {
                ImageIcon icon_2 = new ImageIcon("res/woody.JPG");
                Image image_2 = icon_2.getImage();
                g.drawImage(image_2, 0, 0, gp.getWidth(), gp.getHeight(), null);
            }
            else if (imageDelay >= 20 && imageDelay < 30) {
                ImageIcon icon_3 = new ImageIcon("res/nugget.JPG");
                Image image_3 = icon_3.getImage();
                g.drawImage(image_3, 0, 0, gp.getWidth(), gp.getHeight(), null);
            }
        }
    }

    public void drawMap(){
        for (int row = 0; row < gp.map.length; row++) {
            for (int col = 0; col < gp.map[row].length; col++) {
                int tile = gp.map[row][col];
                g.drawImage(gp.tileMap.getTileImage(tile), col * gp.tile_size, row * gp.tile_size, gp.tile_size, gp.tile_size, null);
            }
        }
    }
}
