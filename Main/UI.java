package Main;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import Character.*;

public class UI {
    GamePanel gp;
    Graphics g;

    Player player;

    public String text, text_2,text_3,text_4,minigame;
    public int textX, textY, textWidth, textHeight,imageWidth,imageHeight;
    public FontMetrics fm;
    public Font customFont;
    public String key = "Icantdoit";
    public int total = 0;
    public boolean helpGratel = false;

    public int[][] pointerPosition = {{280,185},{255,235},{280,285}};
    public  int basementCount = 0 ;
    //public int[] whiteBarPosition = {370,450,400};
    public int greenBarPosition = 280 , candyCount = 0 , greenBarSpeed = 2;
    public String[] menuOptions = {"Play", "Option", "Exit"};
    public int selectedIndex = 0, basementIndex = 0 , questionIndex = 0;

    public String[] optionMenu = {"Volume","Display FPS","Back"};
    public int optionIndex = 0,pointerIndex = 0;
    public boolean showFPS;

    public boolean checkAlphaText = false , checkCandy1 = true , checkCandy2 = true , checkCandy3 = true,checkJailBreak = false,checkBasement = false,
            head = true,hand = true,intestines = true, basementText = true , skull = false , checkEscape = true , checkTime = true, checkEnding = true;
    public double textDelay,imageDelay;
    private double alpha = 0.0;
    private int alphaText = 0;
    public double alphaSpeed = 0.02,timer = 0.0;
    public boolean showImage = false,showText = true,showObjText = true,showMiniGame = false,spaceAble = false , showDialog = false;
    public boolean flashScreen = true;
    public boolean Fading = false;
    public boolean mapChanged = false, ending = false;

    public boolean chalkPlayed = false;

    public static final int MAIN_MENU = 0;
    public static int TXT_CUTSCENE = 1,SCENE = 1;
    public static final int MOVING = 2;
    public static final int OPTION = 3;

    public boolean isQTEActive = false;
    private int qteTimeLeft = 3;
    public String qteSequence = "escapefromherethisnotsafe";
    public int KeyIndex = 0;
    private long lastQTETime;

    public Sound mainMenuMusic, cutsceneHiding, cutsceneFrightening, cutsceneCandy, cutsceneEye;
    public Sound map1soundtrack, map2soundtrack, map3soundtrack, map4soundtrack;
    public Sound selectSound, confirmSound, slidebarSound, looting;
    public Sound bookOpening, bookPage, chalk, doorBroken;

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
        imageManager.setImage("cutscene7","res/cutscene/7.JPG");
        imageManager.setImage("cutscene8","res/cutscene/8.JPG");
        imageManager.setImage("cutscene9","res/cutscene/9.JPG");
        imageManager.setImage("cutscene10","res/cutscene/10.JPG");
        imageManager.setImage("blackbar","res/minigame/bar-black-export.png");
        imageManager.setImage("whitebar","res/minigame/bar-white-export.png");
        imageManager.setImage("greenbar","res/minigame/bar-green-export.png");
        imageManager.setImage("hand","res/object/hand.png");
        imageManager.setImage("head","res/object/head.png");
        imageManager.setImage("intestines","res/object/intestines.png");
        imageManager.setImage("skull","res/object/skull.png");
        imageManager.setImage("gore_layer","res/bg/gore_layer.png");


        config = new Config("config.txt");
        config.load();
        volumeLevel = config.getVolumeLevel();

        loadSound();

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

    public void loadSound() {
        cutsceneHiding = new Sound(volumeLevel / 100f, "res/sound/soundtrack/cutscenes/cutscene-hiding.wav");
        cutsceneFrightening = new Sound(volumeLevel / 100f, "res/sound/soundtrack/cutscenes/cutscene-girl-running.wav");
        cutsceneCandy = new Sound(volumeLevel / 100f, "res/sound/soundtrack/cutscenes/cutscene-candy.wav");
        cutsceneEye = new Sound(volumeLevel / 100f, "res/sound/soundtrack/cutscenes/cutscene-eye.wav");

        selectSound = new Sound(volumeLevel / 100f, "res/sound/soundEffect/menu-select.wav");
        confirmSound = new Sound(volumeLevel / 100f, "res/sound/soundEffect/menu-confirm.wav");
        slidebarSound = new Sound(volumeLevel / 100f, "res/sound/soundEffect/menu-slidebar.wav");
        bookOpening = new Sound(volumeLevel / 100f, "res/sound/soundEffect/book-opening.wav");
        bookPage = new Sound(volumeLevel / 100f, "res/sound/soundEffect/book-page.wav");
        chalk = new Sound(volumeLevel / 100f, "res/sound/soundEffect/chalkSound.wav");
        looting = new Sound(volumeLevel / 100f, "res/sound/soundEffect/looting.wav");
        doorBroken = new Sound(volumeLevel / 100f, "res/sound/soundEffect/door-broken.wav");

        mainMenuMusic = new Sound(volumeLevel / 100f, "res/sound/soundtrack/background-music/SweetTombMainMenu.wav");
        map1soundtrack = new Sound(volumeLevel / 100f, "res/sound/soundtrack/background-music/map1soundtrack.wav");
        map2soundtrack = new Sound(volumeLevel / 100f, "res/sound/soundtrack/background-music/map2soundtrack.wav");
        map3soundtrack = new Sound(volumeLevel / 100f, "res/sound/soundtrack/background-music/map3soundtrack.wav");
        map4soundtrack = new Sound(volumeLevel / 100f, "res/sound/soundtrack/background-music/map4soundtrack.wav");
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
            mainMenuMusic.loop();
        }
        if(gp.gameState == TXT_CUTSCENE){
            mainMenuMusic.stop();
            drawText();
        }
        if(gp.gameState == MOVING){
            gp.mapM.drawMap(g);
            gp.player.draw(g);

            if(showFPS){
                drawFPS(g,fpsCounter);
            }

            if(devmode){
                devmode(g);
            }

            if (gp.currentTileMap == gp.tileMap1) {
                map1soundtrack.loop();
            }

            if (gp.currentTileMap == gp.tileMap2) {
                map1soundtrack.stop();
                map2soundtrack.loop();
            }

            if(gp.currentTileMap == gp.tileMap3){
                map2soundtrack.stop();
                map3soundtrack.loop();
                if (showMiniGame){
                    drawMiniGameMap();
                }else {
                    if ((((Player.worldX <= 1070 && Player.worldX >= 870) && (Player.worldY <= 1300 && Player.worldY >= 1160)) && checkCandy1) || (((Player.worldX <= 1540 && Player.worldX >= 1400) && (Player.worldY <= 1260 && Player.worldY >= 1120)) && checkCandy3) || (((Player.worldX <= 1300 && Player.worldX >= 1180) && (Player.worldY <= 1015 && Player.worldY >= 880)) && checkCandy2)){
                        drawMiniGameKey();
                    }
                }
            }
            if ((Player.worldX >= 900 && Player.worldX <= 1020) &&
                    (Player.worldY >= 1537 && Player.worldY <= 1540) &&
                    (gp.currentTileMap == gp.tileMap3)) {

                if (Player.speed == 2) {
                    Player.worldY -= 2;
                } else if (Player.speed == 3) {
                    Player.worldY -= 3;
                }
            }

            if ((Player.worldX >= 900 && Player.worldX <= 1020) &&
                    (Player.worldY >= 1537 && Player.worldY <= 1540) &&
                    (gp.currentTileMap == gp.tileMap6)) {

                if (Player.speed == 2) {
                    Player.worldY -= 2;
                } else if (Player.speed == 3) {
                    Player.worldY -= 3;
                }
            }



            if (gp.currentTileMap == gp.tileMap4){
                map3soundtrack.stop();
                map4soundtrack.loop();
                drawDialog();
                if (showMiniGame) {
                    drawMiniGameMap();
                }
                else {
                    if (((Player.worldX >= 1000 && Player.worldX <= 1120) && (Player.worldY <= 1320 && Player.worldY >= 1250)) || ((Player.worldX >= 1615 && Player.worldX <= 1720) && (Player.worldY <= 1370 && Player.worldY >= 1270))){
                        drawMiniGameKey();
                    }
                    else if (((Player.worldX >= 1050 && Player.worldX <= 1100) && (Player.worldY <= 1175 && Player.worldY >= 1120)) && head){
                        basementIndex = 1;
                        drawMiniGameKey();
                        drawObjectiveImage();
                    }
                    else if (((Player.worldX >= 800 && Player.worldX <= 900) && (Player.worldY <= 1285 && Player.worldY >= 1230)) && hand){
                        basementIndex = 2;
                        drawMiniGameKey();
                        drawObjectiveImage();
                    }
                    else if (((Player.worldX >= 800 && Player.worldX <= 860) && (Player.worldY <= 1165 && Player.worldY >= 1130)) && intestines){
                        basementIndex = 3;
                        drawMiniGameKey();
                        drawObjectiveImage();
                    }
                    else {
                        if (!skull){
                            basementIndex = 0;
                        }
                        checkBasement = false;
                        drawObjectiveImage();
                    }
                    drawAchivement();
                }
            }
            if (gp.currentTileMap == gp.tileMap5){
                int screenWidth = gp.getWidth();
                int screenHeight = gp.getHeight();
                int imageWidth = 4800;
                int imageHeight = 475;

                int sourceX = player.worldX - player.screenX;
                sourceX = Math.max(0, Math.min(sourceX, imageWidth - 800));

                gp.player.updateToGratel();
                gp.gratel.draw(g, player.screenX-50,player.screenY);
                g.drawImage(imageManager.getImage("gore_layer"), 0, 0, screenWidth, screenHeight, sourceX, 0, sourceX + 300, imageHeight-300, null);

                gp.keyH.checkMove = false;
                drawDialog();
                if (gp.isQTEActive){
                    drawminiqte();
                }
                else {
                    if (player.worldX <= 2500){
                        gp.keyH.rightPressed = true;
                    }
                    else {
                        gp.keyH.rightPressed = false;
                        drawObjectiveImage();
                    }
                }
                if ((player.worldX == 700 && (player.worldY<= 2000 && player.worldY >= 780))){
                    gp.startQTE();
                    //drawminiqte();
                    //startQTE();
                    //System.out.println("start");
                }
                if (gp.mapM.witchPositionX >= player.screenX-100 && checkEnding){
                    SCENE = 7;
                    showText = false;
                    ending = true;
                    checkEnding = false;
                    startFade();
                }
            }

            if (gp.currentTileMap == gp.tileMap6){
                drawDialog();
                if (!showDialog && !showObjText){
                    drawMiniGameMap();
                }
            }
            drawObjectiveText();
            drawBackScreen(g);

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

    public void drawObjectiveImage(){
        g.setColor(new Color(255, 255, 255, 255));
        g.setFont(new Font(customFont.getFontName(), Font.PLAIN, 24));

        if (checkBasement){
            fm = g.getFontMetrics();
            gp.keyH.checkMove = false;
            if (basementIndex == 1){
                text = "You got a head.";
                textWidth = fm.stringWidth(text);
                textHeight = fm.getHeight();
                textX = (gp.getWidth() - textWidth) / 2;
                textY = (gp.getHeight() - textHeight) + fm.getAscent() - 400;
                g.drawString(text, textX, textY);
                g.drawImage(imageManager.getImage("head"), 370, (gp.getHeight() / 2)-160, imageWidth/4, imageHeight, null);
            }
            else if (basementIndex == 2){
                text = "You got a hand.";
                textWidth = fm.stringWidth(text);
                textHeight = fm.getHeight();
                textX = (gp.getWidth() - textWidth) / 2;
                textY = (gp.getHeight() - textHeight) + fm.getAscent() - 400;
                g.drawString(text, textX, textY);
                g.drawImage(imageManager.getImage("hand"), 370, (gp.getHeight() / 2)-160, imageWidth/4, imageHeight, null);
            }
            else if (basementIndex == 3){
                text = "You got a intestine.";
                textWidth = fm.stringWidth(text);
                textHeight = fm.getHeight();
                textX = (gp.getWidth() - textWidth) / 2;
                textY = (gp.getHeight() - textHeight) + fm.getAscent() - 400;
                g.drawString(text, textX, textY);
                g.drawImage(imageManager.getImage("intestines"), 370, (gp.getHeight() / 2)-160, imageWidth/4, imageHeight, null);
            }
        }
        else {
            if (gp.currentTileMap == gp.tileMap5){
                if (questionIndex == 0){
                    g.setFont(new Font(customFont.getFontName(), Font.PLAIN, 22));
                    text = "Does Java support multiple";
                    textWidth = fm.stringWidth(text);
                    textHeight = fm.getHeight();
                    textX = (gp.getWidth() - textWidth) / 2;
                    textY = (gp.getHeight() - textHeight) + fm.getAscent() - 200;
                    g.drawString(text, textX-35, textY-155);

                    text = "inheritance of classes?";
                    g.drawString(text, textX-15, textY- 120);

                    text = "[ Y ]";
                    g.drawString(text, 675, textY- 180);

                    text = "[ N ]";
                    g.drawString(text, 87, textY- 180);
                }
                else if (questionIndex == 1){
                    g.setFont(new Font(customFont.getFontName(), Font.PLAIN, 22));
                    text = "Can a Java interface have";
                    textWidth = fm.stringWidth(text);
                    textHeight = fm.getHeight();
                    textX = (gp.getWidth() - textWidth) / 2;
                    textY = (gp.getHeight() - textHeight) + fm.getAscent() - 200;
                    g.drawString(text, textX-40, textY-155);

                    text = "private methods?";
                    g.drawString(text, textX-5, textY- 120);

                    text = "[ Y ]";
                    g.drawString(text, 675, textY- 180);

                    text = "[ N ]";
                    g.drawString(text, 87, textY- 180);
                }
                else if (questionIndex == 2){
                    g.setFont(new Font(customFont.getFontName(), Font.PLAIN, 22));
                    text = "Can an abstract class";
                    textWidth = fm.stringWidth(text);
                    textHeight = fm.getHeight();
                    textX = (gp.getWidth() - textWidth) / 2;
                    textY = (gp.getHeight() - textHeight) + fm.getAscent() - 200;
                    g.drawString(text, textX-40, textY-155);

                    text = "have a constructor?";
                    g.drawString(text, textX-25, textY- 120);

                    text = "[ Y ]";
                    g.drawString(text, 675, textY- 180);

                    text = "[ N ]";
                    g.drawString(text, 87, textY- 180);
                }
                else if (questionIndex == 3){
                    g.setFont(new Font(customFont.getFontName(), Font.PLAIN, 22));
                    text = "Is method overloading an";
                    textWidth = fm.stringWidth(text);
                    textHeight = fm.getHeight();
                    textX = (gp.getWidth() - textWidth) / 2;
                    textY = (gp.getHeight() - textHeight) + fm.getAscent() - 200;
                    g.drawString(text, textX-30, textY-155);

                    text = "example of polymorphism?";
                    g.drawString(text, textX-35, textY- 120);

                    text = "[ Y ]";
                    g.drawString(text, 675, textY- 180);

                    text = "[ N ]";
                    g.drawString(text, 87, textY- 180);
                }
                else if(questionIndex == 4){
                    SCENE = 7;
                    showDialog = false;
                    ending = true;
                    startFade();
                }
            }
        }
    }

    public void drawAchivement(){
        g.setColor(new Color(255, 255, 255, 255));
        g.setFont(new Font(customFont.getFontName(), Font.PLAIN, 24));

        if (basementIndex == 4){
            fm = g.getFontMetrics();
            text = "You got a Skull. (try to break out with this)";
            textWidth = fm.stringWidth(text);
            textHeight = fm.getHeight();
            textX = (gp.getWidth() - textWidth) / 2;
            textY = (gp.getHeight() - textHeight) + fm.getAscent() - 400;
            g.drawString(text, textX, textY);
            g.drawImage(imageManager.getImage("skull"), 370, (gp.getHeight() / 2)-160, imageWidth/4, imageHeight, null);
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
                chalk.playOnce();
                //alpha -= alphaSpeed;
                setAlpha(getAlpha()-alphaSpeed);
            }

            if (getAlphaText() < 0){
                showObjText = false;
                setAlphaText(0);
                textDelay = 0;
                chalk.resetPlayOnce();
            }

            g.drawString(text, textX, textY);

            g.setFont(new Font(customFont.getFontName(), Font.PLAIN, 24));
            fm = g.getFontMetrics();
            text_2 = "Find Gretel.";
            textY += textHeight + 5;
            textWidth = fm.stringWidth(text_2);
            g.drawString(text_2, (gp.getWidth() - textWidth) / 2, textY);
        }

        if (showObjText && SCENE == 2){
            textDelay += 1;
            chalk.playOnce();

            if (textDelay > 200){
                setAlphaText(getAlphaText()-1);
            }

            if (getAlphaText() < 0){
                showObjText = false;
                setAlphaText(0);
                textDelay = 0;
                chalk.resetPlayOnce();
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
            chalk.playOnce();

            if (textDelay > 200){
                setAlphaText(getAlphaText()-1);
            }

            if (getAlphaText() < 0){
                showObjText = false;
                setAlphaText(0);
                textDelay = 0;
                chalk.resetPlayOnce();
            }

            g.drawString(text, textX, textY);

            g.setFont(new Font(customFont.getFontName(), Font.PLAIN, 24));
            fm = g.getFontMetrics();
            text_3 = "Eat Candy.";
            textY += textHeight + 5;
            textWidth = fm.stringWidth(text_3);
            g.drawString(text_3, (gp.getWidth() - textWidth) / 2, textY);
        }

        if (showObjText && SCENE == 4){
            if (showDialog){
                new Thread(new Cutscene(gp, this)).start();
            }
            else {
                gp.keyH.checkMove = true;
                textDelay += 1;
                chalk.playOnce();

                if (textDelay > 100){
                    setAlphaText(getAlphaText()-10);
                }

                if (getAlphaText() < 0){
                    showObjText = false;
                    setAlphaText(0);
                    textDelay = 0;
                    chalk.resetPlayOnce();
                }

                g.drawString(text, textX, textY);

                g.setFont(new Font(customFont.getFontName(), Font.PLAIN, 24));
                fm = g.getFontMetrics();
                text_4 = "Find Exit.";
                textY += textHeight + 5;
                textWidth = fm.stringWidth(text_4);
                g.drawString(text_4, (gp.getWidth() - textWidth) / 2, textY);
            }
        }

        if (showObjText && SCENE == 5){
            if (showDialog){
                new Thread(new Cutscene(gp, this)).start();
            }
            else {
                gp.keyH.checkMove = true;
                textDelay += 1;
                chalk.playOnce();

                if (textDelay > 100){
                    setAlphaText(getAlphaText()-10);
                }

                if (getAlphaText() < 0){
                    showObjText = false;
                    setAlphaText(0);
                    textDelay = 0;
                    chalk.resetPlayOnce();
                }

                g.drawString(text, textX, textY);

                g.setFont(new Font(customFont.getFontName(), Font.PLAIN, 24));
                fm = g.getFontMetrics();
                text_4 = "Escape.";
                textY += textHeight + 5;
                textWidth = fm.stringWidth(text_4);
                g.drawString(text_4, (gp.getWidth() - textWidth) / 2, textY);
            }
        }

        if (showObjText && SCENE == 6) {
            if (showDialog) {
                new Thread(new Cutscene(gp, this)).start();
            }
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
                    setAlpha(getAlpha() + 5);
                    System.out.println("Fading to black - Alpha: " + getAlpha());
                } else {
                    if (!showText) {
                        gp.keyH.keyBoolRelease();

                        if (gp.currentTileMap != gp.tileMap5 && !ending){
                            gp.switchMap();
                            System.out.println("Map switched");
                        }

                        mapChanged = true;
                        showText = true;
                        checkAlphaText = false;
                        textDelay = 0;

                        if (gp.currentTileMap == gp.tileMap2){
                            player.worldX = 125;
                            player.worldY = 1730;
                            player.direction = "right";
                        }

                        else if (gp.currentTileMap == gp.tileMap3){
                            player.worldX = 1013;
                            player.worldY = 1470;
                            player.direction = "right";
                        }

                        else if (gp.currentTileMap == gp.tileMap4){
                            player.worldX = 1056;
                            player.worldY = 1248;
                            player.direction = "right";
                        }
                        else if (gp.currentTileMap == gp.tileMap5){
                            player.worldX = 420;
                            player.direction = "right";
                        }
                        else if (gp.currentTileMap == gp.tileMap6){
                            player.worldX = 992;
                            player.worldY = 957;
                            player.direction = "right";
                        }
                    }
                }
            } else {
                if (showText && (SCENE == 2 || SCENE == 3 || SCENE == 4 || SCENE == 7)) {
                    new Thread(new Cutscene(gp, this)).start();
                    callBG();

                } else {
                    showDialog = true;
                    if (getAlpha() > 0) {
                        setAlpha(getAlpha() - 10);
                        System.out.println("Fading out - Alpha: " + getAlpha());
                    } else {
                        if (ending){
                            gp.gameState = MAIN_MENU;
                        }
                        else {
                            showObjText = true;
                            gp.gameState = MOVING;
                            setAlphaText(255);
                        }
                        Fading = false;
                        System.out.println("Fade complete");
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
                text = "Hansel and Gretel giggle as they play in the dense forest,";
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
                g.drawImage(imageManager.getImage("cutscene7"), 0, 0, gp.getWidth(), gp.getHeight(), null);
                g.setFont(new Font(customFont.getFontName(), Font.PLAIN, 22));
                g.setColor(new Color(255, 255, 255, 255));

                fm = g.getFontMetrics();
                text = "Hansel: Oh hey look at that! Is that a candy?!";
                textWidth = fm.stringWidth(text);
                textHeight = fm.getHeight();

                textX = (gp.getWidth() - textWidth) / 2;
                textY = (gp.getHeight() - textHeight) + fm.getAscent() - 50;
                g.drawString(text, textX, textY);

                g.setFont(new Font(customFont.getFontName(), Font.PLAIN, 16));
                text_2 = "// Hansel and Gretel carefully examine the suspicious candy on the desk //";
                g.drawString(text_2, textX-50, textY+30);
            }


        }

        if (SCENE == 4){

            if (imageDelay == 70 && showImage){
                g.drawImage(imageManager.getImage("cutscene8"), 0, 0, gp.getWidth(), gp.getHeight(), null);
                g.setFont(new Font(customFont.getFontName(), Font.PLAIN, 26));
                g.setColor(new Color(255, 255, 255, 255));

                fm = g.getFontMetrics();
                text = "eww. . . what is this!?";
                textWidth = fm.stringWidth(text);
                textHeight = fm.getHeight();

                textX = (gp.getWidth() - textWidth) / 2;
                textY = (gp.getHeight() - textHeight) + fm.getAscent() - 50;
                g.drawString(text, textX, textY);

                g.setFont(new Font(customFont.getFontName(), Font.PLAIN, 14));
                text_2 = "// Hansel pick something up from the floor and it kinda looks like a human eyeball?//";
                g.drawString(text_2, textX-170, textY+30);
            }

            else if (imageDelay == 80 && showImage){
                g.drawImage(imageManager.getImage("cutscene9"), 0, 0, gp.getWidth(), gp.getHeight(), null);
            }
        }

        if (SCENE == 7){
            g.drawImage(imageManager.getImage("cutscene10"), 0, 0, gp.getWidth(), gp.getHeight(), null);

            g.setColor(new Color(255, 0, 0, 255));
            g.setFont(new Font(customFont.getFontName(), Font.PLAIN, 24));

            fm = g.getFontMetrics();
            text = "THE END.";
            textWidth = fm.stringWidth(text);
            textHeight = fm.getHeight();
            textX = (gp.getWidth() - textWidth) / 2;
            textY = (gp.getHeight() - textHeight) / 2 + fm.getAscent();
            g.drawString(text, textX, textY);
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

    public void drawDialog(){
        g.setFont(new Font(customFont.getFontName(), Font.PLAIN, 16));
        g.setColor(new Color(255, 255, 255, getAlphaText()));

        fm = g.getFontMetrics();
        if (showDialog && gp.currentTileMap == gp.tileMap4){
            gp.keyH.checkMove = false;

            text = "Hansel: That old lady captured us . I need to find gretel and get out of here.";
            textWidth = fm.stringWidth(text);
            textHeight = fm.getHeight();

            textX = (gp.getWidth() - textWidth) / 2;
            textY = (gp.getHeight() - textHeight) + fm.getAscent() - 50;
            g.drawString(text, textX, textY);
        }
        else if (showDialog && gp.currentTileMap == gp.tileMap5){
            g.setFont(new Font(customFont.getFontName(), Font.PLAIN, 20));
            text = "Hansel: Gretel run!.";
            textWidth = fm.stringWidth(text);
            textHeight = fm.getHeight();

            textX = (gp.getWidth() - textWidth) / 2;
            textY = (gp.getHeight() - textHeight) + fm.getAscent() - 50;

            g.drawString(text, textX-20, textY+20);
        }
        else if (showDialog && (gp.currentTileMap == gp.tileMap6)){
            gp.keyH.checkMove = false;
            g.setFont(new Font(customFont.getFontName(), Font.PLAIN, 16));
            g.setColor(new Color(255, 255, 255, getAlphaText()));

            fm = g.getFontMetrics();
            text = "Gretel: Brother hurry!, We need to leave.";
            textWidth = fm.stringWidth(text);
            textHeight = fm.getHeight();

            textX = (gp.getWidth() - textWidth) / 2;
            textY = (gp.getHeight() - textHeight) + fm.getAscent() - 50;
            g.drawString(text, textX, textY);

        }
    }

    public void drawMiniGameKey(){
        g.setColor(Color.white);
        g.setFont(new Font(customFont.getFontName(), Font.PLAIN, 24));
        fm = g.getFontMetrics();

        if (gp.currentTileMap == gp.tileMap3){
            minigame = "Press [ E ] to eat candy.";
            textWidth = fm.stringWidth(minigame);
            textHeight = fm.getHeight();
            textX = (gp.getWidth() - textWidth) / 2;
            textY = (gp.getHeight() - textHeight) + fm.getAscent() - 100;
            g.drawString(minigame, textX, textY);
        }
        else if (gp.currentTileMap == gp.tileMap4){
            if (((basementIndex == 1 && head|| basementIndex == 2 && hand|| basementIndex == 3 && intestines)) && basementText){
                String minigame_text = "Press [ E ] to investigate.";
                textWidth = fm.stringWidth(minigame_text);
                textHeight = fm.getHeight();
                textX = (gp.getWidth() - textWidth) / 2;
                textY = (gp.getHeight() - textHeight) + fm.getAscent();
                g.drawString(minigame_text, textX, textY);
            }

            else if (checkJailBreak){
                minigame = "Press [ E ] to break out.";
                textWidth = fm.stringWidth(minigame);
                textHeight = fm.getHeight();
                textX = (gp.getWidth() - textWidth) / 2;
                textY = (gp.getHeight() - textHeight) + fm.getAscent() - 100;
                g.drawString(minigame, textX, textY);
            }
            if ((Player.worldX >= 1000 && Player.worldX <= 1120) && (Player.worldY <= 1320 && Player.worldY >= 1250) && !checkJailBreak){
                g.setColor(Color.white);
                fm = g.getFontMetrics();
                minigame = "The door is locked.";
                textWidth = fm.stringWidth(minigame);
                textHeight = fm.getHeight();
                textX = (gp.getWidth() - textWidth) / 2;
                textY = (gp.getHeight() - textHeight) + fm.getAscent() - 100;
                g.drawString(minigame, textX, textY);
            }
        }
    }

    public void drawMiniGameMap(){
        g.setFont(new Font(customFont.getFontName(), Font.PLAIN, 24));
        if (gp.currentTileMap == gp.tileMap3){
            imageWidth = gp.tileSize * 10;
            imageHeight = gp.tileSize * 2;

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
            greenBarPosition += greenBarSpeed;

            if (greenBarPosition >= 480) {
                greenBarPosition = 280;
            }

            //System.out.println(greenBarPosition);
        }
        else if (gp.currentTileMap == gp.tileMap4){

            g.setColor(Color.white);
            fm = g.getFontMetrics();
            minigame = "Press [ Space ].";
            textWidth = fm.stringWidth(minigame);
            textHeight = fm.getHeight();
            textX = (gp.getWidth() - textWidth) / 2;
            textY = (gp.getHeight() - textHeight) + fm.getAscent() - 100;
            g.drawString(minigame, textX, textY);

            String timer_clock = String.format("%.1f", timer);
            textY = (gp.getHeight() - textHeight) + fm.getAscent()-300;
            g.drawString(timer_clock, textX+90, textY);

            //ท้ายหลอด x = 460
            //ต้นหลอก x = (gp.getWidth() - imageWidth) / 2 || 280
            imageWidth -= 7;

            if (imageWidth < 1) {
                imageWidth = 1;
            }
            g.drawImage(imageManager.getImage("blackbar"), (gp.getWidth() - (gp.tileSize * 10)) / 2, ((gp.getHeight() / 2) + (gp.getHeight() / 4) + 40), gp.tileSize * 10, gp.tileSize * 2, null);
            g.drawImage(imageManager.getImage("whitebar"), 280, ((gp.getHeight() / 2) + (gp.getHeight() / 4) + 40), imageWidth/20, imageHeight, null);

            if (timer <= 0){
                showMiniGame = false;
                helpGratel = false;
                spaceAble = false;
                timer = 10.0;
            }

            if (imageWidth/20 >= 240){
                doorBroken.play();

                spaceAble = false;
                showMiniGame = false;

                if (((Player.worldX >= 1615 && Player.worldX <= 1720) && (Player.worldY <= 1370 && Player.worldY >= 1270))){
                    checkJailBreak = false;
                    helpGratel = true;

                }
                else {
                    player.worldX = 1050;
                    player.worldY = 1390;
                    player.direction = "left";
                }
            }

            timer -= 0.02;

        }

        else if (gp.currentTileMap == gp.tileMap6){

            g.setColor(Color.white);
            fm = g.getFontMetrics();
            String timer_clock = String.format("%.1f", timer);
            textX = (gp.getWidth() - textWidth);
            textY = (gp.getHeight() - textHeight) + fm.getAscent();
            g.drawString(timer_clock, textX-120, textY);
            if (timer <= 0){
                if (checkEscape){
                    timer = 15.0;
                    checkEscape = false;
                }
                else{
                    if (checkTime){
                        SCENE = 7;
                        showText = false;
                        ending = true;
                        checkTime = false;
                        startFade();
                    }
                }
            }
            timer -= 0.02;

            if (timer < 0){
                timer = 0;
            }

        }
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
        g.drawString("Collision: " + (player.isCollisionOn ? "on" : "off"), 20, gp.getHeight() - 230);
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
    public void startQTE(){
        qteTimeLeft = 3;
        KeyIndex = 0;
        isQTEActive = true;
        lastQTETime = System.currentTimeMillis();

        player.state = "idle";
        player.keyH.upPressed = false;
        player.keyH.downPressed = false;
        player.keyH.leftPressed = false;
        player.keyH.rightPressed = false;

        gp.repaint();
    }
    public void checkQTETrigger(){
        if (!isQTEActive){
            int fixedX = 25;
            int fixedY = gp.maxRow * gp.tileSize - gp.tileSize;

            int screenIdleX = fixedX - player.worldX + player.screenX;
            int screenIdleY = fixedY - player.worldY + player.screenY;


            // สำหรับแก้ใน map 5 ถ้าเอามาลงแล้ว
            if (((player.worldX<= 1000 && player.worldX >=700) && (player.worldY<= 2000 && player.worldY >= 780)) && gp.currentTileMap == gp.tileMap5){
//                gp.startQTE();
//                System.out.println("start");

            }
        }
    }
    public void drawminiqte(){
        //System.out.println("Pressed: " + pressedKey + " | Expected: " + qteSequence.charAt(KeyIndex));
        String message = "Press [ " + gp.qteSequence.charAt(gp.KeyIndex) + " ] to escape";
        g.setColor(new Color(255, 255, 255, 255));
        g.setFont(new Font(customFont.getFontName(), Font.PLAIN, 30));
        g.drawString(message, 246, 100);
    }
}


