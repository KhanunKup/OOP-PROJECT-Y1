package Main;

import tile.TileMap;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    public final int xTile = 16;
    public final int yTile = 16;
    public final int mapX = 800;
    public final int mapY = 475;

    public Thread gameThread;

    public UI ui = new UI(this);

    public int gameState = 0; // 0 = title , 1 = play

    public TileMap tileMap;
    public final int tile_size = 16;
    public int[][] map;

    public Player player;
    public KeyHandler keyH;

    public GamePanel(){
        this.setPreferredSize(new Dimension(mapX, mapY));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        tileMap = new TileMap("res/map/Map1.txt");
        this.keyH = new KeyHandler(this,ui);
        this.addKeyListener(keyH);
        this.player = new Player(this, keyH);
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
