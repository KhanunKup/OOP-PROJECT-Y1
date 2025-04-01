package Main;

import tile.*;

import javax.swing.*;
import java.awt.*;
import Character.*;


public class GamePanel extends JPanel implements Runnable {
    public final int tileSize = 24;
    public static int mapX = 800; //Camera size
    public static int mapY = 475;
    public final int maxRow = 100; //How many tile of row
    public final int maxCol = 100;
    GamePanel gp;
    public Witch witch;
    public Gratel gratel;

    public Thread gameThread;

    public int gameState = UI.MAIN_MENU; // 0 = title , 1 = play

    public TileManager currentTileMap,tileMap1,tileMap2,tileMap3,tileMap4,tileMap5,tileMap6;
    public int[][] map;

    public Player player;
    public KeyHandler keyH;
    public UI ui;
    public MapManager mapM;
    public ImageManager imageManager;

    public boolean isQTEActive = false;
    private int qteTimeLeft = 3;
    public String qteSequence = "escape";
    public int KeyIndex = 0;
    private long lastQTETime;

    public GamePanel(){
        this.setPreferredSize(new Dimension(mapX, mapY));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.ui = new UI(this, null);
        this.keyH = new KeyHandler(this, ui);
        this.imageManager = new ImageManager();
        this.player = new Player(this, keyH, imageManager);
        ui.setPlayer(player);
        this.mapM = new MapManager(this, player, imageManager);
        tileMap1 = new ForestMap("res/map/Map1-Final.txt");
        tileMap2 = new ForestMap("res/map/Map2-Final.txt");
        tileMap3 = new HouseMap("res/map/Witch-Hut.txt");
        tileMap4 = new CellerMap("res/map/CellerRoomEx.txt");
        tileMap5 = new MinigameMap("res/map/Blank.txt");
        tileMap6 = new HouseAfterMap("res/map/Witch-Hut-After.txt");
        currentTileMap = tileMap1;

        this.gratel = new Gratel(this, imageManager);
        this.witch = new Witch(this, imageManager);

        this.addKeyListener(keyH);

    }

    public void startThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void updateMap(TileManager newMap){
        currentTileMap = newMap;
        map = currentTileMap.getMap();
        player.worldX = Math.min(player.worldX, maxCol * tileSize - tileSize);
        player.worldY = Math.min(player.worldY, maxRow * tileSize - tileSize);
    }

    public void switchMap() {
        System.out.println("Switching map... from " +
                (currentTileMap == tileMap1 ? "Map1" : (currentTileMap == tileMap2 ? "Map2" : "Map3")));
        if (currentTileMap == tileMap1) {
            updateMap(tileMap2);
        }else if (currentTileMap == tileMap2) {
            updateMap(tileMap3);
        }else if (currentTileMap == tileMap3){
            updateMap(tileMap4);
        } else if (currentTileMap == tileMap4) {
            updateMap(tileMap6);
        } else if (currentTileMap == tileMap6) {
            updateMap(tileMap5);
        }
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.WHITE);

        map = currentTileMap.getMap();
        mapM.drawMap(g);

        ui.draw(g);
    }

    @Override
    public void run() {
        try {
            while (true){
                player.update(imageManager);
                gratel.update(imageManager);
                witch.update(imageManager);
                //System.out.println(player.worldX+", "+player.worldY);
                ui.updateFade();
                repaint();
                Thread.sleep(16);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void checkQTETrigger(){
        System.out.println("checkQTETrigger() called");
        if (!isQTEActive) {
            int fixedX = 25;
            int fixedY = gp.maxRow * gp.tileSize - gp.tileSize;

            int screenIdleX = fixedX - player.worldX + player.screenX;
            int screenIdleY = fixedY - player.worldY + player.screenY;

            {
            // สำหรับแก้ใน map 5 ถ้าเอามาลงแล้ว
//            if (((py.worldX<= 1000 && py.worldX >=700) && (py.worldY<= 2000 && py.worldY >= 780)) && currentTileMap == tileMap5){
//                startQTE();
//                u.drawminiqte();
//                System.out.println("start gp");
            }
        }
    }
    public void startQTE(){
        keyH.rightPressed = false;
        qteTimeLeft = 3;
        KeyIndex = 0;
        isQTEActive = true;
        lastQTETime = System.currentTimeMillis();

        player.state = "idle";
    }
//    public void drawqte();{
//        String message = "Press " + qteSequence.charAt(KeyIndex) + " to escape";
//        ui.setColor(Color.white);
//        setFont(new Font("Arial", Font.PLAIN, 30));
//        ui.drawqteSequence(message, 450, 300);
//    }
    public void checkQTE(char pressedKey) {
        if (isQTEActive) {
            ui.drawminiqte();
            System.out.println("Pressed: " + pressedKey + " | Expected: " + qteSequence.charAt(KeyIndex));


            if (pressedKey == qteSequence.charAt(KeyIndex)) {
                System.out.println("Correct!");
                keyH.keyBoolRelease();
                mapM.witchPositionX -= 10;
                player.worldX += 20;
            } else {
                System.out.println("Wrong Key!");
                keyH.keyBoolRelease();
                mapM.witchPositionX += 80;
                player.worldX -= 20;
            }
            System.out.println(mapM.witchPositionX+"witch");
            System.out.println(player.screenX+"player");

            lastQTETime = System.currentTimeMillis();
            qteTimeLeft = 3;

            // ไปยังตัวอักษรถัดไปเสมอ
            KeyIndex++;

            // ถ้าไปถึงตัวสุดท้ายแล้วให้ปิด QTE
            if (KeyIndex >= qteSequence.length()) {
                keyH.checkMove = true;
                keyH.rightPressed = true;
                isQTEActive = false;
                //System.out.println("QTE Completed!");
            }
        }
    }
    public void updateQTE() {
        if (isQTEActive) {
            long currentTime = System.currentTimeMillis();
            long elapsedTime = (currentTime - lastQTETime) / 1000;
            qteTimeLeft = 3 - (int) elapsedTime;

            if (qteTimeLeft <= 0) {
                isQTEActive = false;
                System.out.println("Time's up!");
            }
        }
    }
}