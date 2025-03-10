package Main;

import tile.TileMap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePanel extends JPanel implements Runnable, KeyListener {
    public Thread gameThread;

    public UI ui = new UI(this);

    public int gameState = 3; // 0 = title , 1 = play
    public String[] menuOptions = {"Play", "Option", "Exit"};

    public TileMap tileMap;
    public final int tile_size = 16;
    public int[][] map;

    public GamePanel(){
        this.setPreferredSize(new Dimension(800, 475));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(this);
        tileMap = new TileMap("res/map/Map1.txt");
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

        if(gameState == 3){
            for (int row = 0; row < map.length; row++) {
                for (int col = 0; col < map[row].length; col++) {
                    int tile = map[row][col];
                    g.drawImage(tileMap.getTileImage(tile), col * tile_size, row * tile_size, tile_size, tile_size, this);
                }
            }
        }
    }

    @Override
    public void run() {
        try {
            while (true){
                repaint();
                Thread.sleep(16);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if(gameState == 0){
            if(e.getKeyCode() == KeyEvent.VK_W){
                if (ui.selectedIndex > 0){
                    ui.selectedIndex--;
                }else {
                    ui.selectedIndex = ui.menuOptions.length - 1;
                }
            }
            if(e.getKeyCode() == KeyEvent.VK_S){
                if (ui.selectedIndex < ui.menuOptions.length - 1){
                    ui.selectedIndex++;
                } else {
                    ui.selectedIndex = 0;
                }
            }
            if(e.getKeyCode() == KeyEvent.VK_ENTER){
                switch (ui.selectedIndex) {
                    case 0:
                        gameState = 1;
                        repaint();
                        break;

                    case 1:
                        break;

                    case 2:
                        System.exit(0);
                        break;
                }
            }
        }
        if (gameState == 1){
            if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
                gameState = 0;
            }
        }
        if (gameState == 2){
            if(e.getKeyCode() == KeyEvent.VK_ENTER){
                gameState = 3;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
