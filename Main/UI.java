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

    public int[][] pointerPosition = {{280,185},{255,235},{280,285}};
    public String[] menuOptions = {"Play", "Option", "Exit"};
    public int selectedIndex = 0;

    public String[] optionMenu = {"Full Screen: OFF", "Back"};
    public int optionIndex = 0,pointerIndex = 0;

    public boolean checkAlphaText = false;
    public double textDelay,imageDelay;
    private double alpha = 0.0;
    public double alphaSpeed = 0.02;
    public boolean showImage = false,showText = true;
    public boolean flashScreen = true;
    public boolean Fading = false;
    public boolean mapChanged = false;

    public static final int MAIN_MENU = 0;
    public static int TXT_CUTSCENE = 1,SCENE = 1;
    public static final int MOVING = 2;
    public static final int OPTION = 3;

    public Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public int screenWidth = screenSize.width;
    public int screenHeight = screenSize.height;

    public Sound music;

    public UI(GamePanel gp, Player player){
        this.gp = gp;
        this.player = player;
        music = new Sound();
        music.playSound("res/sound/SweetTombMainMenu.wav");
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
            drawPointer();
            music.loop();
        }
        if(gp.gameState == TXT_CUTSCENE){
            drawText();
        }
        if(gp.gameState == MOVING){
            gp.mapM.drawMap(g);
            gp.player.draw(g);

            drawObjectiveText();
            drawBackScreen(g);
        }
        if(gp.gameState == OPTION){
            drawOption();
        }
    }
    public void drawPointer(){
        ImageIcon icon = new ImageIcon("res/bg/send.png");
        Image image = icon.getImage();

        g.drawImage(image, pointerPosition[pointerIndex][0], pointerPosition[pointerIndex][1], 64, 64, null);
    }

    public void drawTitle(){
        ImageIcon icon = new ImageIcon("res/bg/sweet.png");
        Image image = icon.getImage();

        g.drawImage(image, 0, 0, gp.getWidth(), gp.getHeight(), null);

        g.setFont(new Font(customFont.getFontName(), Font.PLAIN, 64));

        g.setFont(new Font(customFont.getFontName(), Font.BOLD, 36));
        for (int i = 0; i < menuOptions.length; i++) {
            int optionX = (gp.getWidth() - g.getFontMetrics().stringWidth(menuOptions[i])) / 2;
            int optionY = 230 + i * 50;
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

            if (textDelay > 300){
                //alpha -= alphaSpeed;
                setAlpha(getAlpha()-alphaSpeed);
                System.out.println("Current Alpha: "+getAlpha());
            }
            if (getAlpha() < 0){
                showText = false;
                setAlpha(0);
                textDelay = 0;
            }

            g.setFont(new Font(customFont.getFontName(), Font.PLAIN, 44));
            g.setColor(new Color(255, 255, 255, (int) getAlpha()));

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

    public void startFade() {
        if (!Fading) {
            Fading = true;
            mapChanged = false;
        }
    }


    //fade เปลี่ยน map
    public void updateFade() {
        if (Fading) {
            if (!mapChanged) {
                if (alpha < 255) {
                    setAlpha(getAlpha() + 5);
                } else {
                    gp.switchMap();
                    mapChanged = true;
                }
            } else {
                drawText();
                if (!showText){
                    if (alpha > 0) {
                        setAlpha(getAlpha() - 5);
                    } else {
                        Fading = false;
                    }
                }
            }
            gp.repaint();
        }
    }

    public void drawBackScreen(Graphics g) {
        if (getAlpha() > 0) {
            Color fadeColor = new Color(0, 0, 0, (int) getAlpha());
            g.setColor(fadeColor);
            g.fillRect(0, 0, gp.getWidth(), gp.getHeight());
        }
    }

    public void drawText(){
        if (SCENE == 1){
            if (!showImage){
                g.setFont(new Font(customFont.getFontName(), Font.PLAIN, 24));

                //โค๊ดเพิ่มความจาง-เข้มกากๆของ Text by 67070106 August *มันอาจจะยังมี bug ถ้าหากบางที Thread มันเอ๋อ การลดความจางอาจจะจบก่อนที่หน้าใหม่จะถูกวาด ซึ่งมันก็จะขึ้นเเดงรัวๆจนกว่าหน้าใหม่จะขึ้น*
                if (getAlpha() < 1.0 && !checkAlphaText) {
                    //alpha += alphaSpeed;
                    setAlpha(getAlpha()+alphaSpeed);
                }

                if (getAlpha() >= 1.0 || checkAlphaText) {
                    textDelay += 0.05;
                    checkAlphaText = true;
                    if (textDelay >= 10.15){
                        //alpha -= 0.02;
                        setAlpha(getAlpha()-0.02);
                    }
                }

                if (getAlpha() < 0){
                    setAlpha(0);
                }

                g.setColor(new Color(255, 255, 255, (int) (getAlpha() * 255)));

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
                        setAlpha((int)((imageDelay - 40) * 32));

                        if (getAlpha() > 255) {
                            setAlpha(255);
                        }

                        if (getAlpha() < 0){
                            setAlpha(0);
                        }

                        Color fadeColor = new Color(0, 0, 0, (int)getAlpha());
                        g.setColor(fadeColor);
                        g.fillRect(0, 0, gp.getWidth(), gp.getHeight());
                    }
                }
            }
        }

        if (SCENE == 2 && showText){
            g.setFont(new Font(customFont.getFontName(), Font.PLAIN, 18));

            if (getAlpha() < 1.0 && !checkAlphaText) {
                setAlpha(getAlpha()+alphaSpeed);
            }

            if (getAlpha() >= 1.0 || checkAlphaText) {
                textDelay += 0.05;
                checkAlphaText = true;
                if (textDelay >= 10.15){
                    setAlpha(getAlpha()-0.02);
                }
            }

            if (getAlpha() < 0){
                setAlpha(0);
                showText = false;
            }

            g.setColor(new Color(255, 255, 255, (int) (getAlpha() * 255)));

            fm = g.getFontMetrics();
            text = "Stumbling into a clearing, they find a house made of sweets, glowing under the moonlight.";
            textWidth = fm.stringWidth(text);
            textHeight = fm.getHeight();
            textX = (gp.getWidth() - textWidth) / 2;
            textY = (gp.getHeight() - textHeight) / 2 + fm.getAscent();
            g.drawString(text, textX, textY);
        }
    }

    public void drawOption() {
        g.setFont(new Font(customFont.getFontName(), Font.BOLD, 36));
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

    public void setAlpha(double alpha){
        this.alpha = alpha;
    }

    public double getAlpha(){
        return alpha;
    }

}

