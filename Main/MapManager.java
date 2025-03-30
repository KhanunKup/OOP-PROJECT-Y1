package Main;

import java.awt.*;
import java.util.ArrayList;

public class MapManager {
    GamePanel gp;
    Player player;
    int screenIdleX,screenIdleY;
    ImageManager imageManager;
    //static int[][] candyPosition = {{985,1240},{1255,935},{1485,1210}};
    static ArrayList<int[]> candyPosition = new ArrayList<>();
    String overlay;

    public MapManager(GamePanel gp, Player player, ImageManager imageManager) {
        this.gp = gp;
        this.player = player;
        this.imageManager = imageManager;
        candyPosition.add(new int[]{985, 1240});
        candyPosition.add(new int[]{1255, 935});
        candyPosition.add(new int[]{1485, 1210});

        imageManager.setImage("gratel","res/Character/Gratel/Gratel_Idle/gratel_idle1.png");

        imageManager.setImage("VisibleDark", "res/VisibilityLayerDark.png");

        imageManager.setImage("VisibleLight", "res/VisibilityLayerLight.png");

        imageManager.setImage("house","res/bg/House.png");

        imageManager.setImage("carpet","res/object/Carpet.png");

        imageManager.setImage("bed","res/object/Bed.png");

        imageManager.setImage("bedroomfur","res/object/BedRoomFur.png");

        imageManager.setImage("cellarfloor","res/object/CellarFloor.png");

        imageManager.setImage("table","res/object/Table.png");

        imageManager.setImage("topfur","res/object/TopFur.png");

        imageManager.setImage("tree","res/object/Tree.png");

        imageManager.setImage("candy","res/object/Candy.png");

        imageManager.setImage("body","res/object/body.png");

        imageManager.setImage("blood","res/object/blood.png");

        imageManager.setImage("obj_head","res/object/obj_head.png");

        imageManager.setImage("SpiderWeb2","res/object/SpiderWeb2.png");

        imageManager.setImage("objectvie","res/object/objectvie.png");

        imageManager.setImage("obj_interstines","res/object/obj_interstines.png");

        imageManager.setImage("bgImage","res/tiles/forest/Leaf5.png");

        imageManager.setImage("minigamebg","res/map/MiniGameTile.png");

        imageManager.setImage("witch","res/Character/Gratel/Gratel_Idle/gratel_idle1.png");
    }

    public void drawMap(Graphics g) {
        int worldX;
        int worldY;
        int screenX;
        int screenY;

        g.drawImage(imageManager.getImage("bgImage"), 0, 0, GamePanel.mapX, GamePanel.mapY, null); //creating default bg

        for (int row = 0; row < gp.maxRow; row++) {
            for (int col = 0; col < gp.maxCol; col++) {
                int tile = gp.map[row][col];
                worldX = col * gp.tileSize;
                worldY = row * gp.tileSize;
                screenX = worldX - player.worldX + player.screenX;
                screenY = worldY - player.worldY + player.screenY;
                if (screenX + gp.tileSize >= 0 && screenX <= GamePanel.mapX &&
                        screenY + gp.tileSize >= 0 && screenY <= GamePanel.mapY) {
                    g.drawImage(gp.currentTileMap.getTileImage(tile), screenX, screenY, gp.tileSize, gp.tileSize, null);
                }
            }
        }

        if (gp.currentTileMap == gp.tileMap1){
            int fixedX = 25;
            int fixedY = gp.maxRow * gp.tileSize - gp.tileSize;

            overlay = "VisibleDark";

            screenIdleX = fixedX - player.worldX + player.screenX;
            screenIdleY = fixedY - player.worldY + player.screenY;

            //System.out.println("X :" + screenIdleX);
            //System.out.println("Y :" + screenIdleY);

            g.drawImage(imageManager.getImage("gratel"), screenIdleX+600, screenIdleY-250, gp.tileSize *2, gp.tileSize *2, null);
        }

        if (gp.currentTileMap == gp.tileMap2){
            screenIdleX = 1225 - player.worldX + player.screenX;
            screenIdleY = 160 - player.worldY + player.screenY;

            overlay = "VisibleDark";

            //System.out.println("X :" + screenIdleX);
            //System.out.println("Y :" + screenIdleY);

            g.drawImage(imageManager.getImage("house"), screenIdleX+400, screenIdleY, 320, 320, null);
        }

        if (gp.currentTileMap == gp.tileMap3){
            screenIdleX = 985 - player.worldX + player.screenX;
            screenIdleY = 1470 - player.worldY + player.screenY;

            overlay = "VisibleDark";

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

            screenIdleX = 1310 - player.worldX + player.screenX;
            screenIdleY = 1410 - player.worldY + player.screenY;
            g.drawImage(imageManager.getImage("tree"), screenIdleX-450, screenIdleY-530, 700, 700, null);

            for (int i = 0; i < candyPosition.size();i++){
                int[] pos = candyPosition.get(i);
                //screenIdleX = candyPosition[i][0] - Player.worldX + player.screenX;
                //screenIdleY = candyPosition[i][1] - Player.worldY + player.screenY;
                screenIdleX = pos[0] - Player.worldX + player.screenX;
                screenIdleY = pos[1] - Player.worldY + player.screenY;

                g.drawImage(imageManager.getImage("candy"), screenIdleX, screenIdleY, gp.tileSize, gp.tileSize, null);
            }

        }

        if (gp.currentTileMap == gp.tileMap4){
            screenIdleX = 1240 - Player.worldX + player.screenX;
            screenIdleY = 1240 - Player.worldY + player.screenY;

            overlay = "VisibleLight";

            g.drawImage(imageManager.getImage("body"), screenIdleX-1240, screenIdleY-1240, 2400, 2400, null);

            screenIdleX = 820 - Player.worldX + player.screenX;
            screenIdleY = 1220 - Player.worldY + player.screenY;

            g.drawImage(imageManager.getImage("blood"), screenIdleX-820, screenIdleY-1220, 2400, 2400, null);

            screenIdleX = 865 - Player.worldX + player.screenX;
            screenIdleY = 1225 - Player.worldY + player.screenY;

            g.drawImage(imageManager.getImage("obj_head"), screenIdleX-865, screenIdleY-1225, 2400, 2400, null);

            //screenIdleX = 895 - Player.worldX + player.screenX;
            //screenIdleY = 1110 - Player.worldY + player.screenY;

            //g.drawImage(imageManager.getImage("CropSack"), screenIdleX, screenIdleY-95, gp.xTileSize*3, gp.yTileSize*2, null);

            screenIdleX = 825 - Player.worldX + player.screenX;
            screenIdleY = 1250 - Player.worldY + player.screenY;

            g.drawImage(imageManager.getImage("objectvie"), screenIdleX-825, screenIdleY-1250, 2400, 2400, null);

            screenIdleX = 800 - Player.worldX + player.screenX;
            screenIdleY = 1113 - Player.worldY + player.screenY;

            g.drawImage(imageManager.getImage("obj_interstines"), screenIdleX-800, screenIdleY-1113, 2400, 2400, null);

            screenIdleX = 903 - Player.worldX + player.screenX;
            screenIdleY = 1113 - Player.worldY + player.screenY;

            g.drawImage(imageManager.getImage("SpiderWeb2"), screenIdleX, screenIdleY, (gp.tileSize*2)-10, gp.tileSize, null);
        }

        if(gp.currentTileMap == gp.tileMap5){
            int screenWidth = gp.getWidth();
            int screenHeight = gp.getHeight();
            int imageWidth = 4800;
            int imageHeight = 475;

            int sourceX = player.worldX - player.screenX;
            sourceX = Math.max(0, Math.min(sourceX, imageWidth - 800));
            player.screenY += 60;

            g.drawImage(imageManager.getImage("minigamebg"), 0, 0, screenWidth, screenHeight, sourceX, 0, sourceX + 300, imageHeight-300, null);
            g.drawImage(imageManager.getImage("witch"), player.screenX-230, player.screenY, player.playerSize, player.playerSize, null);
            //เก็บรายละเอียดเเหว่ง
            g.drawImage(imageManager.getImage("visible"), 0, -355, gp.getWidth(), gp.getHeight(), null);
        }
        
        g.drawImage(imageManager.getImage(overlay), 0, 0, gp.getWidth(), gp.getHeight(), null);
    }
}
