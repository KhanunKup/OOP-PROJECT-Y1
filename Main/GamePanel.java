package Main;

import tile.TileMap;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    public final int xTileSize = 16;
    public final int yTileSize = 16;
    public final int tile_size = 16;
    public static int mapX = 800;
    public static int mapY = 475;
    public int maxRow;
    public int maxCol;
    GamePanel gp;

    public Thread gameThread;

    public int gameState = UI.MOVING; // 0 = title , 1 = play

    public TileMap currentTileMap, tileMap1, tileMap2;
    public int[][] map;

    public Player player;
    public KeyHandler keyH;
    public UI ui;
    public MapManager mapM;

    public boolean isQTEActive = false;
    private int qteTimeLeft = 3;
    public String qteSequence = "ddd"; // ลำดับปุ่มที่ต้องกด
    public int currentKeyIndex = 0;
    private long lastQTETime;

    public GamePanel() {
        this.setPreferredSize(new Dimension(mapX, mapY));
        this.gp = this;
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.ui = new UI(this, null);
        this.keyH = new KeyHandler(this, ui);
        this.player = new Player(this, keyH);
        ui.setPlayer(player);
        this.mapM = new MapManager(this, player);
        tileMap1 = new TileMap("res/map/Map1-Final.txt");
        tileMap2 = new TileMap("res/map/Map2-Final.txt");
        currentTileMap = tileMap1;
        this.addKeyListener(keyH);
    }

    public void startThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        map = currentTileMap.getMap();
        mapM.drawMap(g);
        ui.draw(g);

        // Draw QTE if active
        if (isQTEActive) {
            g.setFont(new Font("Arial", Font.BOLD, 24));
            g.setColor(Color.RED);
            g.drawString("Press '" + qteSequence.charAt(currentKeyIndex) + "' Now!", mapX / 2 - 100, mapY / 2);
            g.drawString("Time Left: " + qteTimeLeft + "s", mapX / 2 - 50, mapY / 2 + 30);
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                player.update();
                checkQTETrigger();
                updateQTE();
                repaint();
                Thread.sleep(16); // Approx 60 FPS
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void switchMap() {
        if (currentTileMap == tileMap1) {
            map = tileMap2.getMap();
            maxRow = 50;
            maxCol = 50;
            currentTileMap = tileMap2;
        } else {
            map = tileMap1.getMap();
            maxRow = 100;
            maxCol = 100;
            currentTileMap = tileMap1;
        }

        player.worldX = Math.min(player.worldX, maxCol * xTileSize - xTileSize);
        player.worldY = Math.min(player.worldY, maxRow * yTileSize - yTileSize);
    }

    public void checkQTETrigger() {
        // ตรวา่าไม่ได้เริ่ม
        if (!isQTEActive) {
            // คำนวณตำแหน่งของผู้เล่นบนหน้าจอ
            int fixedX = 25;
            int fixedY = gp.maxRow * gp.yTileSize - gp.yTileSize;

            int screenIdleX = fixedX - player.worldX + player.screenX;
            int screenIdleY = fixedY - player.worldY + player.screenY;

            // หากผู้เล่นอยู่ในช่วงที่กำหนด (X: 0-100, Y: 750-850) อันนี้ลองเทสดู
            if (screenIdleX >= 0 && screenIdleX <= 100 && screenIdleY >= 750 && screenIdleY <= 850) {
                startQTE();
            }
        }
    }

    public void startQTE() {
        qteTimeLeft = 3;
        currentKeyIndex = 0;
        isQTEActive = true;
        lastQTETime = System.currentTimeMillis();  // บันทึกเวลาเริ่มต้น
        repaint();  // อัพเดตหน้าจอเพื่อแสดง
    }

    public void checkQTE(char pressedKey) {
        if (isQTEActive && pressedKey == qteSequence.charAt(currentKeyIndex)) {
            currentKeyIndex++;
            if (currentKeyIndex >= qteSequence.length()) {
                isQTEActive = false;
                System.out.println("Correct!");
            }
        } else if (isQTEActive) {
            isQTEActive = false;
            System.out.println("Wrong Key!");
        }
        repaint();
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
