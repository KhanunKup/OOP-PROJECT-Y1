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

    public Thread gameThread;

    public int gameState = UI.MOVING; // 0 = title , 1 = play

    public TileManager currentTileMap,tileMap1,tileMap2,tileMap3;
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
}
