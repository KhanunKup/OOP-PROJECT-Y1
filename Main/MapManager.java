package Main;

import javax.swing.*;
import java.awt.*;

public class MapManager {
    GamePanel gp;
    Player player;
    String path,path_2;
    Image idleImage,idleImage_2;

    public MapManager(GamePanel gp, Player player) {
        this.gp = gp;
        this.player = player;
        path = "res/Character/Gratel/Gratel_Idle/gratel_idle1.png";
        idleImage = new ImageIcon(path).getImage();
        path_2 = "res/Visibility-3.png";
        idleImage_2 = new ImageIcon(path_2).getImage();
    }

    public void drawMap(Graphics g) {
        int worldX;
        int worldY;
        int screenX;
        int screenY;

        for (int row = 0; row < gp.maxRow; row++) {
            for (int col = 0; col < gp.maxCol; col++) {
                int tile = gp.map[row][col];
                worldX = col * gp.xTileSize;
                worldY = row * gp.yTileSize;
                screenX = worldX - player.worldX;
                screenY = worldY - player.worldY;

                if (screenX + gp.xTileSize >= 0 && screenX <= GamePanel.mapX &&
                        screenY + gp.yTileSize >= 0 && screenY <= GamePanel.mapY) {
                    g.drawImage(gp.currentTileMap.getTileImage(tile), screenX, screenY, gp.xTileSize, gp.yTileSize, null);
                }
            }
        }

        int fixedX = 25;
        int fixedY = gp.maxRow * gp.yTileSize - gp.yTileSize;

        int screenIdleX = fixedX - player.worldX + player.screenX;
        int screenIdleY = fixedY - player.worldY + player.screenY;

        //System.out.println(screenIdleX+"X");
        //System.out.println(screenIdleY+"Y");

//        g.drawImage(idleImage, screenIdleX, screenIdleY-450, gp.xTileSize*2, gp.yTileSize*2, null);
//        g.drawImage(idleImage_2, 0, 0, gp.getWidth(), gp.getHeight(), null);
    }
}
