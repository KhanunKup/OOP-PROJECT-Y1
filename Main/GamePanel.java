package Main;

import tile.ForestMap;
import tile.HouseMap;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    public final int xTileSize = 24;
    public final int yTileSize = 24;
    public static int mapX = 800; //Camera size
    public static int mapY = 475;
    public final int maxRow = 100; //How many tile of row
    public final int maxCol = 100;
    GamePanel gp;

    public Thread gameThread;

    public int gameState = UI.MAIN_MENU; // 0 = title , 1 = play

    public TileManager currentTileMap,tileMap1,tileMap2,tileMap3;
    public int[][] map;

    public Player player;
    public KeyHandler keyH;
    public UI ui;
    public MapManager mapM;

    public boolean isQTEActive = false;
    private int qteTimeLeft = 3;
    public String qteSequence = "run";
    public int KeyIndex = 0;
    private long lastQTETime;

    public GamePanel(){
        this.setPreferredSize(new Dimension(mapX, mapY));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.ui = new UI(this, null);
        this.keyH = new KeyHandler(this, ui);
        this.player = new Player(this, keyH);
        ui.setPlayer(player);
        this.mapM = new MapManager(this, player);
        tileMap1 = new ForestMap("res/map/Map1-Final.txt");
        tileMap2 = new ForestMap("res/map/Map2-Final.txt");
        tileMap3 = new HouseMap("res/map/Witch-Hut.txt");
        currentTileMap = tileMap1;

        this.addKeyListener(keyH);

    }

    public void startThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void updateMap(TileManager newMap){
        currentTileMap = newMap;
        map = currentTileMap.getMap();
        player.worldX = Math.min(player.worldX, maxCol * xTileSize - xTileSize);
        player.worldY = Math.min(player.worldY, maxRow * yTileSize - yTileSize);
    }

    public void switchMap() {
        System.out.println("Switching map... from " +
                (currentTileMap == tileMap1 ? "Map1" : (currentTileMap == tileMap2 ? "Map2" : "Map3")));
        if (currentTileMap == tileMap1) {
            updateMap(tileMap2);
        }else if (currentTileMap == tileMap2) {
            updateMap(tileMap3);
        }else {
            updateMap(tileMap1);
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
                player.update();
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
        if (!isQTEActive){
            int fixedX = 25;
            int fixedY = gp.maxRow * gp.yTileSize - gp.yTileSize;

            int screenIdleX = fixedX - player.worldX + player.screenX;
            int screenIdleY = fixedY - player.worldY + player.screenY;

            if ((screenIdleX >= 0 && screenIdleX <= 100 && screenIdleY >= 750 && screenIdleY <= 850)){
                startQTE();
            }
        }
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

        repaint();
    }
    public void checkQTE(char pressedKey) {
        if (isQTEActive) {
            System.out.println("Pressed: " + pressedKey + " | Expected: " + qteSequence.charAt(KeyIndex));


            if (pressedKey == qteSequence.charAt(KeyIndex)) {
                System.out.println("Correct!");
                player.worldX += 50;
            } else {
                System.out.println("Wrong Key!");
                player.worldX -= 50;
            }

            lastQTETime = System.currentTimeMillis();
            qteTimeLeft = 3;
            // ไปยังตัวอักษรถัดไปเสมอ
            KeyIndex++;

            // ถ้าไปถึงตัวสุดท้ายแล้วให้ปิด QTE
            if (KeyIndex >= qteSequence.length()) {
                isQTEActive = false;
                System.out.println("QTE Completed!");
            }

            repaint();
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
                repaint();
            }
        }
    }
}