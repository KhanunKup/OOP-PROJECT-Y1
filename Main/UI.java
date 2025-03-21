package Main;

import org.w3c.dom.ls.LSOutput;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class UI {
    GamePanel gp;
    Graphics g;

    Player player;

    public int imageX, imageY, imgWidth, imgHeight;
    public String text, text_2;
    public int textX, textY, textWidth, textHeight;
    public FontMetrics fm;
    public Font customFont;

    public String[] menuOptions = {"Play", "Option", "Exit"};
    public int selectedIndex = 0;

    public String[] optionMenu = {"Full Screen: OFF", "Back"};
    public int optionIndex = 0;

    public boolean checkAlphaText = false;
    public double textDelay,imageDelay;
    public double alpha = 0.0;
    public double alphaSpeed = 0.02;
    public boolean showImage = false,showText = true;
    public boolean flashScreen = true;

    public static final int MAIN_MENU = 0;
    public static final int TXT_CUTSCENE = 1;
    public static final int MOVING = 2;
    public static final int OPTION = 3;

    public Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public int screenWidth = screenSize.width;
    public int screenHeight = screenSize.height;


    public UI(GamePanel gp, Player player){
        this.gp = gp;
        this.player = player;
        loadFont();
    }

    public void loadFont(){
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("res/fonts/jmh.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
            System.out.println("Loading Fonts Success!");
        } catch (Exception e){
            e.printStackTrace();
        }
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
            gp.mapM.drawMap(g);
            gp.player.draw(g);

            drawObjectiveText();
        }
        if(gp.gameState == OPTION){
            drawOption();
        }
    }
    public void drawTitle(){
        ImageIcon icon = new ImageIcon("res/bg/sweet.png");
        Image image = icon.getImage();
        g.drawImage(image, 0, 0, gp.getWidth(), gp.getHeight(), null);

        g.setFont(new Font(customFont.getFontName(), Font.PLAIN, 64));

        g.setFont(new Font(customFont.getFontName(), Font.BOLD, 36));
        for (int i = 0; i < menuOptions.length; i++) {
            int optionX = (gp.getWidth() - g.getFontMetrics().stringWidth(menuOptions[i])) / 2;
            int optionY = 200 + i * 50;
            if(Main.isFullScreen){
                optionY = 400 + i * 50;
            }
            if (i == selectedIndex) {
                g.setColor(Color.YELLOW);
            } else {
                g.setColor(Color.WHITE);
            }
            g.drawString(menuOptions[i], optionX, optionY);
        }
    }

    public void drawObjectiveText() {
        if (showText){
            textDelay += 1;

            if (textDelay > 200){
                alpha -= alphaSpeed;
                System.out.println("Current Alpha: "+alpha);
            }
            if (alpha < 0){
                showText = false;
                alpha = 0;
                textDelay = 0;
            }

            g.setFont(new Font(customFont.getFontName(), Font.PLAIN, 44));
            g.setColor(new Color(255, 255, 255, (int) alpha));

            fm = g.getFontMetrics();
            text = "OBJECTIVE:";
            textWidth = fm.stringWidth(text);
            textHeight = fm.getHeight();

            textX = (gp.getWidth() - textWidth) / 2;
            textY = 100;

            g.drawString(text, textX, textY);

            g.setFont(new Font(customFont.getFontName(), Font.PLAIN, 24));
            fm = g.getFontMetrics();
            text_2 = "Find Khanun.";
            textY += textHeight + 5;
            textWidth = fm.stringWidth(text_2);
            g.drawString(text_2, (gp.getWidth() - textWidth) / 2, textY);
        }
    }


    public synchronized void drawText(){
        if (!showImage){
            g.setFont(new Font(customFont.getFontName(), Font.PLAIN, 24));

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

            if (alpha < 0){
                alpha = 0;
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
            textDelay = 0;
            System.out.println(imageDelay);
            if (flashScreen && (imageDelay == 0 || (imageDelay == 10 || imageDelay == 20))) {
                g.setColor(Color.WHITE);
                g.fillRect(0, 0, gp.getWidth(), gp.getHeight());
                flashScreen = false;
                System.out.println("FlashScreen show!");
            }

            else if (imageDelay == 0){
                ImageIcon icon = new ImageIcon("res/eyes.JPG");
                Image image = icon.getImage();
                g.drawImage(image, 0, 0, gp.getWidth(), gp.getHeight(), null);
            }
            else if (imageDelay == 10) {
                ImageIcon icon_2 = new ImageIcon("res/bonus.JPG");
                Image image_2 = icon_2.getImage();
                g.drawImage(image_2, 0, 0, gp.getWidth(), gp.getHeight(), null);
            }
            else if (imageDelay >= 20) {
                imageDelay += 0.1;
                ImageIcon icon_3 = new ImageIcon("res/beam.JPG");
                Image image_3 = icon_3.getImage();
                g.drawImage(image_3, 0, 0, gp.getWidth(), gp.getHeight(), null);

                if (imageDelay >= 40) {
                    alpha = (int)((imageDelay - 40) * 32);

                    if (alpha > 255) {
                        alpha = 255;
                    }

                    if (alpha < 0){
                        alpha = 0;
                    }


                    Color fadeColor = new Color(0, 0, 0, (int)alpha);
                    g.setColor(fadeColor);
                    g.fillRect(0, 0, gp.getWidth(), gp.getHeight());
                }
            }
        }
    }

    public void drawOption() {
        g.setFont(new Font("Monospaced", Font.BOLD, 36));
        for (int i = 0; i < optionMenu.length; i++) {
            int optionX = (gp.getWidth() - g.getFontMetrics().stringWidth(optionMenu[i])) / 2;
            int optionY = 200 + i * 50;
            if(Main.isFullScreen){
                optionY = 400 + i * 50;
            }
            g.setColor(i == optionIndex ? Color.YELLOW : Color.WHITE);
            g.drawString(optionMenu[i], optionX, optionY);
        }
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
