package Main;

import javax.swing.*;
import java.awt.*;

public class MapManager {
    GamePanel gp;
    Player player;
    String path,path_2,path_3,path_4,path_5,path_6,path_7,path_8,path_9,path_10;
    Image idleImage,idleImage_2,idleImage_3,idleImage_4,idleImage_5,idleImage_6,idleImage_7,idleImage_8,idleImage_9,idleImage_10;
    int screenIdleX,screenIdleY;

    public MapManager(GamePanel gp, Player player) {
        this.gp = gp;
        this.player = player;
        path = "res/Character/Gratel/Gratel_Idle/gratel_idle1.png";
        idleImage = new ImageIcon(path).getImage();

        path_2 = "res/VisibilityL.png";
        idleImage_2 = new ImageIcon(path_2).getImage();

        path_3 = "res/bg/House.png";
        idleImage_3 = new ImageIcon(path_3).getImage();

        path_4 = "res/object/Carpet.png";
        idleImage_4 = new ImageIcon(path_4).getImage();

        path_5 = "res/object/Bed.png";
        idleImage_5 = new ImageIcon(path_5).getImage();

        path_6 = "res/object/BedRoomFur.png";
        idleImage_6 = new ImageIcon(path_6).getImage();

        path_7 = "res/object/CellarFloor.png";
        idleImage_7 = new ImageIcon(path_7).getImage();

        path_8 = "res/object/Table.png";
        idleImage_8 = new ImageIcon(path_8).getImage();

        path_9 = "res/object/TopFur.png";
        idleImage_9 = new ImageIcon(path_9).getImage();

        path_10 = "res/object/Tree.png";
        idleImage_10 = new ImageIcon(path_10).getImage();
    }

    public void drawMap(Graphics g) {
        int worldX;
        int worldY;
        int screenX;
        int screenY;

        ImageIcon bgImage = new ImageIcon("res/tiles/forest/Leaf5.png");
        g.drawImage(bgImage.getImage(), 0, 0, GamePanel.mapX, GamePanel.mapY, null); //creating default bg

        for (int row = 0; row < gp.maxRow; row++) {
            for (int col = 0; col < gp.maxCol; col++) {
                int tile = gp.map[row][col];
                worldX = col * gp.xTileSize;
                worldY = row * gp.yTileSize;
                screenX = worldX - player.worldX + player.screenX;
                screenY = worldY - player.worldY + player.screenY;
                if (screenX + gp.xTileSize >= 0 && screenX <= GamePanel.mapX &&
                        screenY + gp.yTileSize >= 0 && screenY <= GamePanel.mapY) {
                    g.drawImage(gp.currentTileMap.getTileImage(tile), screenX, screenY, gp.xTileSize, gp.yTileSize, null);
                }
            }
        }

        if (gp.currentTileMap == gp.tileMap1){
            int fixedX = 25;
            int fixedY = gp.maxRow * gp.yTileSize - gp.yTileSize;

            screenIdleX = fixedX - player.worldX + player.screenX;
            screenIdleY = fixedY - player.worldY + player.screenY;

            //System.out.println("X :" + screenIdleX);
            //System.out.println("Y :" + screenIdleY);

            g.drawImage(idleImage, screenIdleX+600, screenIdleY-250, gp.xTileSize*2, gp.yTileSize*2, null);
        }

        if (gp.currentTileMap == gp.tileMap2){
            screenIdleX = 1225 - player.worldX + player.screenX;
            screenIdleY = 160 - player.worldY + player.screenY;

            //System.out.println("X :" + screenIdleX);
            //System.out.println("Y :" + screenIdleY);

            g.drawImage(idleImage_3, screenIdleX+400, screenIdleY, 320, 320, null);
        }

        if (gp.currentTileMap == gp.tileMap3){
            screenIdleX = 985 - player.worldX + player.screenX;
            screenIdleY = 1470 - player.worldY + player.screenY;

            g.drawImage(idleImage_4, screenIdleX-145, screenIdleY-590, 700, 700, null);

            screenIdleX = 1460 - player.worldX + player.screenX;
            screenIdleY = 1200 - player.worldY + player.screenY;
            g.drawImage(idleImage_5, screenIdleX-550, screenIdleY-320, 650, 650, null);

            g.drawImage(idleImage_6, screenIdleX-600, screenIdleY-320, 700, 700, null);

            g.drawImage(idleImage_9, screenIdleX-610, screenIdleY-350, 700, 700, null);

            g.drawImage(idleImage_7, screenIdleX-620, screenIdleY-665, 700, 1000, null);

            screenIdleX = 970 - player.worldX + player.screenX;
            screenIdleY = 1260 - player.worldY + player.screenY;
            g.drawImage(idleImage_8, screenIdleX-125, screenIdleY-400, 700, 700, null);

            screenIdleX = 1225 - player.worldX + player.screenX;
            screenIdleY = 160 - player.worldY + player.screenY;
            g.drawImage(idleImage_10, screenIdleX+400, screenIdleY, 124, 124, null);

        }

        g.drawImage(idleImage_2, 0, 0, gp.getWidth(), gp.getHeight(), null);
    }
}
