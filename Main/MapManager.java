package Main;

import javax.swing.*;
import java.awt.*;

public class MapManager {
    GamePanel gp;
    Player player;
    int screenIdleX,screenIdleY;
    ImageManager imageManager;

    public MapManager(GamePanel gp, Player player, ImageManager imageManager) {
        this.gp = gp;
        this.player = player;
        this.imageManager = imageManager;

        imageManager.setImage("gratel","res/Character/Gratel/Gratel_Idle/gratel_idle1.png");

        imageManager.setImage("visible","res/VisibilityL.png");

        imageManager.setImage("house","res/bg/House.png");

        imageManager.setImage("carpet","res/object/Carpet.png");

        imageManager.setImage("bed","res/object/Bed.png");

        imageManager.setImage("bedroomfur","res/object/BedRoomFur.png");

        imageManager.setImage("cellarfloor","res/object/CellarFloor.png");

        imageManager.setImage("table","res/object/Table.png");

        imageManager.setImage("topfur","res/object/TopFur.png");

        imageManager.setImage("tree","res/object/Tree.png");
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

            g.drawImage(imageManager.getImage("gratel"), screenIdleX+600, screenIdleY-250, gp.xTileSize*2, gp.yTileSize*2, null);
        }

        if (gp.currentTileMap == gp.tileMap2){
            screenIdleX = 1225 - player.worldX + player.screenX;
            screenIdleY = 160 - player.worldY + player.screenY;

            //System.out.println("X :" + screenIdleX);
            //System.out.println("Y :" + screenIdleY);

            g.drawImage(imageManager.getImage("house"), screenIdleX+400, screenIdleY, 320, 320, null);
        }

        if (gp.currentTileMap == gp.tileMap3){
            screenIdleX = 985 - player.worldX + player.screenX;
            screenIdleY = 1470 - player.worldY + player.screenY;

            g.drawImage(imageManager.getImage("carpet"), screenIdleX-145, screenIdleY-590, 700, 700, null);

            screenIdleX = 1460 - player.worldX + player.screenX;
            screenIdleY = 1200 - player.worldY + player.screenY;
            g.drawImage(imageManager.getImage("bed"), screenIdleX-550, screenIdleY-320, 650, 650, null);

            g.drawImage(imageManager.getImage("bedroomfur"), screenIdleX-600, screenIdleY-320, 700, 700, null);

            g.drawImage(imageManager.getImage("topfur"), screenIdleX-610, screenIdleY-350, 700, 700, null);

            g.drawImage(imageManager.getImage("cellarfloor"), screenIdleX-620, screenIdleY-665, 700, 1000, null);

            screenIdleX = 970 - player.worldX + player.screenX;
            screenIdleY = 1260 - player.worldY + player.screenY;
            g.drawImage(imageManager.getImage("table"), screenIdleX-125, screenIdleY-400, 700, 700, null);

            screenIdleX = 1225 - player.worldX + player.screenX;
            screenIdleY = 160 - player.worldY + player.screenY;
            g.drawImage(imageManager.getImage("tree"), screenIdleX+400, screenIdleY, 124, 124, null);

        }

        g.drawImage(imageManager.getImage("visible"), 0, 0, gp.getWidth(), gp.getHeight(), null);
    }
}
