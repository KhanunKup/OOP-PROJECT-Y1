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
    public int maxRow; //How many tile of row
    public int maxCol;

    public Thread gameThread;

    public int gameState = UI.MOVING; // 0 = title , 1 = play

    public TileMap currentTileMap,tileMap1,tileMap2;
    public int[][] map;

    public Player player;
    public KeyHandler keyH;
    public UI ui;
    public MapManager mapM;

    public GamePanel(){
        this.setPreferredSize(new Dimension(mapX, mapY));
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

    public void startThread(){
        gameThread = new Thread(this);
        gameThread.start();
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
                repaint();
                Thread.sleep(16);
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


}
