package Main;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class UI {
    GamePanel gp;
    Graphics g;

    Player player;

    public String text, text_2,text_3,minigame;
    public int textX, textY, textWidth, textHeight;
    public FontMetrics fm;
    public Font customFont;

    public int[][] pointerPosition = {{280,185},{255,235},{280,285}};
    public int[] whiteBarPosition = {370,450,400};
    public int greenBarPosition = 280;
    public String[] menuOptions = {"Play", "Option", "Exit"};
    public int selectedIndex = 0;

    public String[] optionMenu = {"Volume","Display FPS","Back"};
    public int optionIndex = 0,pointerIndex = 0;
    public boolean showFPS;

    public boolean checkAlphaText = false;
    public double textDelay,imageDelay;
    private double alpha = 0.0;
    private int alphaText = 0;
    public double alphaSpeed = 0.02;
    public boolean showImage = false,showText = true,showObjText = true,showMiniGame = false,spaceAble = false;
    public boolean flashScreen = true;
    public boolean Fading = false;
    public boolean mapChanged = false;

    public static final int MAIN_MENU = 0;
    public static int TXT_CUTSCENE = 1,SCENE = 1;
    public static final int MOVING = 2;
    public static final int OPTION = 3;

    public Sound music, cutscene, selectSound, confirmSound, slidebarSound;

    public JSlider volumeSlider;
    public int volumeLevel;

    public VolumeChange volumeChange;

    public FPSCounter fpsCounter;

    public Config config;

    public ImageManager imageManager;

    public boolean devmode;

    public UI(GamePanel gp, Player player){
        this.gp = gp;
        this.player = player;

        this.imageManager = new ImageManager();
        imageManager.setImage("pointer","res/icon/candy.png");
        imageManager.setImage("bg","res/bg/sweet.png");
        imageManager.setImage("cutscene1","res/cutscene/1.JPG");
        imageManager.setImage("cutscene2","res/cutscene/2.JPG");
        imageManager.setImage("cutscene3","res/cutscene/3.JPG");
        imageManager.setImage("cutscene4","res/cutscene/4.JPG");
        imageManager.setImage("cutscene5","res/cutscene/5.JPG");
        imageManager.setImage("cutscene6","res/cutscene/6.JPG");
        imageManager.setImage("blackbar","res/minigame/bar-black-export.png");
        imageManager.setImage("whitebar","res/minigame/bar-white-export.png");
        imageManager.setImage("greenbar","res/minigame/bar-green-export.png");

        config = new Config("config.txt");
        config.load();
        volumeLevel = config.getVolumeLevel();
        music = new Sound();
        music.playSound("res/sound/SweetTombMainMenu.wav");
        music.setVolume(volumeLevel / 100f);
        cutscene = new Sound();
        cutscene.playSound("res/sound/Cutscene-1and2-Boy-Girl-Hiding.wav");
        cutscene.setVolume(volumeLevel / 100f);
        selectSound = new Sound();
        selectSound.playSound("res/sound/menu-select.wav");
        selectSound.setVolume(volumeLevel / 100f);
        confirmSound = new Sound();
        confirmSound.playSound("res/sound/menu-confirm.wav");
        confirmSound.setVolume(volumeLevel / 100f);
        slidebarSound = new Sound();
        slidebarSound.playSound("res/sound/menu-slidebar.wav");
        slidebarSound.setVolume(volumeLevel / 100f);
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

        fpsCounter = new FPSCounter();
        showFPS = config.isShowFPS();

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
            music.stop();
            drawText();
        }
        if(gp.gameState == MOVING){
            gp.mapM.drawMap(g);
            gp.player.draw(g);

            //System.out.println(showObjText);
            //System.out.println("TextDelay :" + textDelay);
            //System.out.println("Alpha Text :"+getAlphaText());
            drawObjectiveText();
            drawBackScreen(g);
            if(showFPS){
                drawFPS(g,fpsCounter);
            }

            if(devmode){
                devmode(g);
            }

            if(gp.currentTileMap == gp.tileMap3){
                if (showMiniGame){
                    drawMiniGameMap3();
                }else {
                    if (((Player.worldX <= 1070 && Player.worldX >= 870) && (Player.worldY <= 1300 && Player.worldY >= 1160)) || ((Player.worldX <= 1540 && Player.worldX >= 1400) && (Player.worldY <= 1260 && Player.worldY >= 1120)) || ((Player.worldX <= 1300 && Player.worldX >= 1180) && (Player.worldY <= 1015 && Player.worldY >= 880))){
                        drawMiniGameKey();
                    }
                }
            }
        }
        if(gp.gameState == OPTION){
            drawOption();
        }
    }
    public void drawPointer(){
        g.drawImage(imageManager.getImage("pointer"), pointerPosition[pointerIndex][0], pointerPosition[pointerIndex][1], 52, 52, null);
    }

    public void drawTitle(){
        g.drawImage(imageManager.getImage("bg"), 0, 0, gp.getWidth(), gp.getHeight(), null);

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

        g.setFont(new Font(customFont.getFontName(), Font.PLAIN, 44));
        g.setColor(new Color(255, 255, 255, getAlphaText()));

        fm = g.getFontMetrics();
        text = "OBJECTIVE:";
        textWidth = fm.stringWidth(text);
        textHeight = fm.getHeight();

        textX = (gp.getWidth() - textWidth) / 2;
        textY = 100;

        if (showObjText && SCENE == 1){
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
                showObjText = false;
                setAlphaText(0);
                textDelay = 0;
            }

            g.drawString(text, textX, textY);

            g.setFont(new Font(customFont.getFontName(), Font.PLAIN, 24));
            fm = g.getFontMetrics();
            text_2 = "Find Gratel.";
            textY += textHeight + 5;
            textWidth = fm.stringWidth(text_2);
            g.drawString(text_2, (gp.getWidth() - textWidth) / 2, textY);
        }

        if (showObjText && SCENE == 2){
            textDelay += 1;

            if (textDelay > 200){
                setAlphaText(getAlphaText()-1);
            }

            if (getAlphaText() < 0){
                showObjText = false;
                setAlphaText(0);
                textDelay = 0;
            }

            g.drawString(text, textX, textY);

            g.setFont(new Font(customFont.getFontName(), Font.PLAIN, 24));
            fm = g.getFontMetrics();
            text_2 = "Walk Around.";
            textY += textHeight + 5;
            textWidth = fm.stringWidth(text_2);
            g.drawString(text_2, (gp.getWidth() - textWidth) / 2, textY);
        }

        if (showObjText && SCENE == 3){
            textDelay += 1;

            if (textDelay > 200){
                setAlphaText(getAlphaText()-1);
            }

            if (getAlphaText() < 0){
                showObjText = false;
                setAlphaText(0);
                textDelay = 0;
            }

            g.drawString(text, textX, textY);

            g.setFont(new Font(customFont.getFontName(), Font.PLAIN, 24));
            fm = g.getFontMetrics();
            text_3 = "Eat Candy.";
            textY += textHeight + 5;
            textWidth = fm.stringWidth(text_3);
            g.drawString(text_3, (gp.getWidth() - textWidth) / 2, textY);
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
                if (getAlpha() < 255) {
                    setAlpha(getAlpha() + 10);
                    System.out.println("Fading to black - Alpha: " + getAlpha());
                } else {
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
                    if (SCENE == 3){
                        new Thread(new Cutscene(gp, this)).start();
                    }
                    callBG();

                } else {
                    if (getAlpha() > 0) {
                        setAlpha(getAlpha() - 10);
                        System.out.println("Fading out - Alpha: " + getAlpha());
                    } else {
                        showObjText = true;
                        gp.gameState = MOVING;
                        setAlphaText(255);
                        Fading = false;
                        System.out.println("Fade complete");

                        if (gp.currentTileMap == gp.tileMap2){
                            player.worldX = 42;
                            player.worldY = 1730;
                            player.direction = "right";
                        }

                        if (gp.currentTileMap == gp.tileMap3){
                            player.worldX = 985;
                            player.worldY = 1500;
                            player.direction = "right";
                        }

                    }
                }
            }
            gp.repaint();
        }
    }

    public void callBG(){
        drawBackScreen(g);
        gp.gameState = TXT_CUTSCENE;
    }

    public void drawBackScreen(Graphics g) {
        if (getAlpha() > 0) {
            g.setColor(new Color(0, 0, 0, (int)getAlpha()));
            g.fillRect(0, 0, gp.getWidth(), gp.getHeight());
        }
    }

    public void drawText(){
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, gp.getWidth(), gp.getHeight());

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
                if (flashScreen && (imageDelay == 0 || (imageDelay == 10 || imageDelay == 20 || imageDelay == 30 || imageDelay == 40 || imageDelay == 50))) {
                    g.setColor(Color.WHITE);
                    g.fillRect(0, 0, gp.getWidth(), gp.getHeight());
                    flashScreen = false;
                    System.out.println("FlashScreen show!");
                }

                else if (imageDelay == 0){
                    g.drawImage(imageManager.getImage("cutscene1"), 0, 0, gp.getWidth(), gp.getHeight(), null);
                }
                else if (imageDelay == 10) {
                    g.drawImage(imageManager.getImage("cutscene2"), 0, 0, gp.getWidth(), gp.getHeight(), null);
                }
                else if (imageDelay == 20) {
                    g.drawImage(imageManager.getImage("cutscene3"), 0, 0, gp.getWidth(), gp.getHeight(), null);
                }
                else if (imageDelay == 30) {
                    g.drawImage(imageManager.getImage("cutscene4"), 0, 0, gp.getWidth(), gp.getHeight(), null);
                }
                else if (imageDelay == 40) {
                    g.drawImage(imageManager.getImage("cutscene5"), 0, 0, gp.getWidth(), gp.getHeight(), null);
                }

                else if (imageDelay == 50) {
                    g.drawImage(imageManager.getImage("cutscene6"), 0, 0, gp.getWidth(), gp.getHeight(), null);

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

        if (SCENE == 3){

            if (imageDelay == 60 && showImage){
                g.drawImage(imageManager.getImage("cutscene1"), 0, 0, gp.getWidth(), gp.getHeight(), null);
            }

            System.out.println(getAlpha());

        }
    }

    public void drawOption() {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, gp.getWidth(), gp.getHeight());

        g.setFont(new Font(customFont.getFontName(), Font.BOLD, 36));
        fm = g.getFontMetrics();
        for (int i = 0; i < optionMenu.length; i++) {
            String menuText = optionMenu[i];
            if (i==0){
                menuText = "Volume: " + volumeLevel + "%";
            } else if (i==1) {
                menuText = showFPS ? "FPS : On" : "FPS : Off";
            }
            int optionX = (gp.getWidth() - fm.stringWidth(menuText)) / 2;
            int optionY = 200 + i * 50;
            g.setColor(i == optionIndex ? Color.YELLOW : Color.WHITE);
            g.drawString(menuText, optionX, optionY);
        }
    }

    public void drawMiniGameKey(){
        g.setColor(Color.white);
        fm = g.getFontMetrics();
        minigame = "Press [ E ] to eat candy.";
        textWidth = fm.stringWidth(minigame);
        textHeight = fm.getHeight();
        textX = (gp.getWidth() - textWidth) / 2;
        textY = (gp.getHeight() - textHeight) + fm.getAscent() - 100;
        g.drawString(minigame, textX, textY);
    }

    public void drawMiniGameMap3(){
        int imageWidth = gp.xTileSize * 10;
        int imageHeight = gp.yTileSize * 2;

        //System.out.println(((gp.getWidth() - imageWidth) / 2));

        g.setColor(Color.white);
        fm = g.getFontMetrics();
        minigame = "Press [ Space ] to eat candy.";
        textWidth = fm.stringWidth(minigame);
        textHeight = fm.getHeight();
        textX = (gp.getWidth() - textWidth) / 2;
        textY = (gp.getHeight() - textHeight) + fm.getAscent() - 100;
        g.drawString(minigame, textX, textY);

        //ท้ายหลอด x = 460
        //ต้นหลอก x = (gp.getWidth() - imageWidth) / 2 || 280
        g.drawImage(imageManager.getImage("blackbar"), (gp.getWidth() - imageWidth) / 2, ((gp.getHeight() / 2) + (gp.getHeight() / 4) + 40), imageWidth, imageHeight, null);
        g.drawImage(imageManager.getImage("whitebar"), 370, ((gp.getHeight() / 2) + (gp.getHeight() / 4) + 40), imageWidth/4, imageHeight, null);
        g.drawImage(imageManager.getImage("greenbar"), greenBarPosition, ((gp.getHeight() / 2) + (gp.getHeight() / 4) + 40), imageWidth/6, imageHeight, null);
        greenBarPosition += 2;

        if (greenBarPosition >= 465) {
            greenBarPosition = 280;
        }

        System.out.println(greenBarPosition);
    }

    public void drawFPS(Graphics g,FPSCounter fpsCounter){
        fpsCounter.update();
        g.setColor(Color.GREEN);
        g.setFont(new Font(customFont.getFontName(), Font.PLAIN, 24));
        g.drawString("FPS: " + fpsCounter.getFps(), 20, gp.getHeight() - 20);
    }

    public void devmode(Graphics g){
        g.setColor(Color.GREEN);
        g.setFont(new Font(customFont.getFontName(), Font.PLAIN, 24));
        g.drawString("X: "+ Player.worldX, 20, gp.getHeight() - 200);
        g.drawString("Y: "+ Player.worldY, 20, gp.getHeight() - 170);
        g.drawString("Speed: "+ Player.speed, 20, gp.getHeight() - 140);
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

    public void saveConfig() {
        config.save(volumeLevel, showFPS);
    }

}

