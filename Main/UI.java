package Main;

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

    public String[] optionMenu = {"Volume","Back"};
    public int optionIndex = 0,pointerIndex = 0;

    public boolean checkAlphaText = false;
    public double textDelay,imageDelay;
    private double alpha = 0.0;
    private int alphaText = 0;
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

    public JSlider volumeSlider;
    public int volumeLevel = 100;

    public VolumeChange volumeChange;

    public UI(GamePanel gp, Player player){
        this.gp = gp;
        this.player = player;
        music = new Sound();
        music.playSound("res/sound/SweetTombMainMenu.wav");
        music.setVolume(1.0f);
        volumeSlider = new JSlider(0, 100, volumeLevel);
        volumeSlider.setBounds(250, 250, 300, 50);
        volumeSlider.setOpaque(false);
        volumeSlider.setMajorTickSpacing(10);
        volumeSlider.setMinorTickSpacing(5);
        volumeSlider.setPaintTicks(true);
        volumeSlider.setPaintLabels(true);
        volumeSlider.setFocusable(true);
        volumeChange = new VolumeChange(this);
        volumeSlider.addChangeListener(volumeChange);
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
        ImageIcon icon = new ImageIcon("res/icon/candy.png");
        Image image = icon.getImage();

        g.drawImage(image, pointerPosition[pointerIndex][0], pointerPosition[pointerIndex][1], 52, 52, null);
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
            if (i == selectedIndex) {
                g.setColor(Color.YELLOW);
            } else {
                g.setColor(Color.WHITE);
            }
            g.drawString(menuOptions[i], optionX, optionY);
        }
    }

    public void drawObjectiveText() {
        if (showText && SCENE == 1){
            textDelay += 1;

            if (textDelay > 500){
                //alpha -= alphaSpeed;
                setAlphaText(getAlphaText()-1);
            }

            if (textDelay > 200){
                //alpha -= alphaSpeed;
                setAlpha(getAlpha()-alphaSpeed);
            }

            if (getAlphaText() < 0){
                showText = false;
                setAlphaText(0);
                textDelay = 0;
            }

            g.setFont(new Font(customFont.getFontName(), Font.PLAIN, 44));
            g.setColor(new Color(255, 255, 255, getAlphaText()));

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
                // Fade to black
                if (alpha < 255) {
                    setAlpha(getAlpha() + 5);
                    System.out.println("Fading to black - Alpha: " + getAlpha());
                } else {
                    // When fully black, switch map
                    if (!showText) {
                        gp.switchMap();
                        mapChanged = true;
                        showText = true;
                        checkAlphaText = false;
                        textDelay = 0;
                        System.out.println("Map switched");
                    }
                }
            } else {
                // Fade out after map change
                if (showText) {
                    drawBackScreen(g);
                    gp.gameState = TXT_CUTSCENE;
                } else {
                    // Gradually reduce alpha to fade out
                    if (alpha > 0) {
                        setAlpha(getAlpha() - 5);
                        System.out.println("Fading out - Alpha: " + getAlpha());
                    } else {
                        gp.gameState = MOVING;
                        // Completely faded out
                        Fading = false;
                        System.out.println("Fade complete");
                    }
                }
            }
            gp.repaint();
        }
    }

    public void drawBackScreen(Graphics g) {
        if (getAlpha() > 0) {
            g.setColor(new Color(0, 0, 0, (int)getAlpha()));
            g.fillRect(0, 0, gp.getWidth(), gp.getHeight());
        }
    }

    public void drawText(){
        if (SCENE == 1){
            if (!showImage){
                g.setFont(new Font(customFont.getFontName(), Font.PLAIN, 24));

                // ค่อย ๆ เพิ่ม alpha
                if (getAlpha() < 255 && !checkAlphaText) {
                    setAlpha(getAlpha() + 5);
                }

                if (getAlpha() >= 255 || checkAlphaText) {
                    textDelay += 0.05;
                    checkAlphaText = true;

                    // ค่อย ๆ ลด alpha
                    if (textDelay >= 10.15){
                        setAlpha(getAlpha() - 7);
                    }
                }

                if (getAlpha() < 0){
                    setAlpha(0);
                }

                g.setColor(new Color(255, 255, 255, (int)getAlpha()));

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

                        g.setColor(new Color(0, 0, 0, (int)getAlpha()));
                        g.fillRect(0, 0, gp.getWidth(), gp.getHeight());
                    }
                }
            }
        }

        if (SCENE == 2){
            g.setFont(new Font(customFont.getFontName(), Font.PLAIN, 16));

            // ค่อย ๆ เพิ่ม alpha
            if (getAlphaText() < 255 && !checkAlphaText) {
                setAlphaText(getAlphaText() + 5);
            }

            if (getAlphaText() >= 255 || checkAlphaText) {
                textDelay += 0.05;
                checkAlphaText = true;

                // ค่อย ๆ ลด alpha
                if (textDelay >= 10.15){
                    setAlphaText(getAlphaText() - 3);
                }
                    if (getAlphaText() <= 0){
                        showText = false;
                    }
            }

            if (getAlphaText() < 0){
                setAlphaText(0);
            }

            if (getAlphaText() > 255){
                setAlphaText(255);
            }

            g.setColor(new Color(255, 255, 255, getAlphaText()));

            fm = g.getFontMetrics();
            String text_S2 = "Stumbling into a clearing, they find a house made of sweets, glowing under the moonlight.";
            textWidth = fm.stringWidth(text_S2);
            textHeight = fm.getHeight();
            textX = (gp.getWidth() - textWidth) / 2;
            textY = (gp.getHeight() - textHeight) / 2 + fm.getAscent();
            g.drawString(text_S2, textX, textY);
        }
    }

    public void drawOption() {
        g.setFont(new Font(customFont.getFontName(), Font.BOLD, 36));
        fm = g.getFontMetrics();
        for (int i = 0; i < optionMenu.length; i++) {
            String menuText = optionMenu[i];
            if (i==0){
                menuText = "Volume: " + volumeLevel + "%";
            }
            int optionX = (gp.getWidth() - fm.stringWidth(menuText)) / 2;
            int optionY = 200 + i * 50;
            g.setColor(i == optionIndex ? Color.YELLOW : Color.WHITE);
            g.drawString(menuText, optionX, optionY);
        }
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setAlpha(double alpha){
        this.alpha = Math.max(0, Math.min(255, alpha));
    }

    public double getAlpha(){
        return alpha;
    }

    public void setAlphaText(int alphaText){
        this.alphaText = alphaText;
    }

    public int getAlphaText(){
        return alphaText;
    }

    public void hideVolumeSlider() {
        gp.remove(volumeSlider);
        gp.repaint();
    }

}

