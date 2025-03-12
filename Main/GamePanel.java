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
    public final int maxRow = 100; //How many tile of row
    public final int maxCol = 100;

    public Thread gameThread;

    public int gameState = UI.MAIN_MENU; // 0 = title , 1 = play

    public TileMap tileMap;
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
        tileMap = new TileMap("res/map/Map1.txt");
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
        map = tileMap.getMap();

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

}
