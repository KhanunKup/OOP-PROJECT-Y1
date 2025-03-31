package Main;

import java.awt.*;
import java.util.ArrayList;
import Character.*;

public class MapManager {
    GamePanel gp;
    Player player;
    public int screenIdleX,screenIdleY,witchPositionX,witchPositionY,gratelPositionX,gratelPositionY;
    ImageManager imageManager;
    //static int[][] candyPosition = {{985,1240},{1255,935},{1485,1210}};
    static ArrayList<int[]> candyPosition = new ArrayList<>();
    String overlay;
    int yOverlay;

    public MapManager(GamePanel gp, Player player, ImageManager imageManager) {
        this.gp = gp;
        this.player = player;
        this.imageManager = imageManager;
        witchPositionX = player.screenX-230;
        witchPositionY = 260;
        gratelPositionX = player.screenX-50;
        gratelPositionY = player.screenY;
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

        imageManager.setImage("bgImage2","res/tiles/celler/Celler-Room-Final1.png");

        imageManager.setImage("minigamebg","res/map/MiniGameTile.png");

        imageManager.setImage("tree-atfer","res/object/tree-atfer.png");

        imageManager.setImage("top-after","res/object/top-after.png");

        imageManager.setImage("cellerWayAfter","res/object/cellerWayAfter.png");

        imageManager.setImage("table-after","res/object/table-after.png");

        imageManager.setImage("carpetAfter","res/object/carpetAfter.png");

        imageManager.setImage("bedRoomZoneAfter","res/object/bedRoomZoneAfter.png");

        imageManager.setImage("bedRoomAfter","res/object/bedRoomAfter.png");

    }

    public void drawMap(Graphics g) {
        int worldX;
        int worldY;
        int screenX;
        int screenY;

        if(gp.currentTileMap == gp.tileMap1 || gp.currentTileMap == gp.tileMap2){
            g.drawImage(imageManager.getImage("bgImage"), 0, 0, GamePanel.mapX, GamePanel.mapY, null); //creating default bg
        }else {
            g.drawImage(imageManager.getImage("bgImage2"), 0, 0, GamePanel.mapX, GamePanel.mapY, null); //creating default bg
        }

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

            yOverlay = 0;
            overlay = "VisibleDark";

            screenIdleX = fixedX - player.worldX + player.screenX;
            screenIdleY = fixedY - player.worldY + player.screenY;

            //System.out.println("X :" + screenIdleX);
            //System.out.println("Y :" + screenIdleY);
            gp.gratel.draw(g, screenIdleX+600, screenIdleY-250);
//            g.drawImage(imageManager.getImage("gratel"), screenIdleX+600, screenIdleY-250, gp.tileSize *2, gp.tileSize *2, null);
        }

        if (gp.currentTileMap == gp.tileMap2){
            screenIdleX = 1225 - player.worldX + player.screenX;
            screenIdleY = 160 - player.worldY + player.screenY;

            yOverlay = 0;
            overlay = "VisibleDark";

            //System.out.println("X :" + screenIdleX);
            //System.out.println("Y :" + screenIdleY);

            g.drawImage(imageManager.getImage("house"), screenIdleX+400, screenIdleY, 320, 320, null);
            gp.gratel.draw(g, player.screenX-50,player.screenY);
        }

        if (gp.currentTileMap == gp.tileMap3){
            screenIdleX = 985 - player.worldX + player.screenX;
            screenIdleY = 1470 - player.worldY + player.screenY;

            yOverlay = 0;
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

            screenIdleX = 950 - Player.worldX + player.screenX;
            screenIdleY = 1470 - Player.worldY + player.screenY;

            gp.gratel.draw(g, screenIdleX, screenIdleY);

        }

        if (gp.currentTileMap == gp.tileMap4){
            screenIdleX = 1240 - Player.worldX + player.screenX;
            screenIdleY = 1240 - Player.worldY + player.screenY;

            yOverlay = 0;
            overlay = "VisibleLight";

            g.drawImage(imageManager.getImage("body"), screenIdleX-1240, screenIdleY-1240, 2400, 2400, null);

            screenIdleX = 820 - Player.worldX + player.screenX;
            screenIdleY = 1220 - Player.worldY + player.screenY;

            g.drawImage(imageManager.getImage("blood"), screenIdleX-820, screenIdleY-1220, 2400, 2400, null);

            screenIdleX = 1080 - Player.worldX + player.screenX;
            screenIdleY = 1225 - Player.worldY + player.screenY;

            g.drawImage(imageManager.getImage("obj_head"), screenIdleX-885, screenIdleY-1200, 2400, 2400, null);

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

            if (!gp.ui.helpGratel){
                screenIdleX = 1648 - Player.worldX + player.screenX;
                screenIdleY = 1225 - Player.worldY + player.screenY;

                gp.gratel.draw(g, screenIdleX, screenIdleY);
            }
            else {
                gp.player.updateToGratel();
                gp.gratel.draw(g, gratelPositionX, gratelPositionY);

            }

            if (Player.worldX >= 2300 && Player.worldY >= 1345){
                UI.SCENE = 5;
                gp.ui.showText = false;
                gp.ui.startFade();
                gp.keyH.keyBoolRelease();
            }
        }

        if(gp.currentTileMap == gp.tileMap5){
            int screenWidth = gp.getWidth();
            int screenHeight = gp.getHeight();
            int imageWidth = 4800;
            int imageHeight = 475;

            yOverlay = 120;
            overlay = "VisibleLight";

            int sourceX = player.worldX - player.screenX;
            sourceX = Math.max(0, Math.min(sourceX, imageWidth - 800));
            player.screenY += 60;
            System.out.println("Player:"+player.screenY);
            System.out.println("Witch"+witchPositionY);

            g.drawImage(imageManager.getImage("minigamebg"), 0, 0, screenWidth, screenHeight, sourceX, 0, sourceX + 300, imageHeight-300, null);
            gp.witch.draw(g,witchPositionX, witchPositionY);
            //เก็บรายละเอียดเเหว่ง
            g.drawImage(imageManager.getImage(overlay), 0, -355, gp.getWidth(), gp.getHeight(), null);
            //g.drawImage(imageManager.getImage(overlay), 0, 120, gp.getWidth(), gp.getHeight(), null);
        }

        if (gp.currentTileMap == gp.tileMap6){
            screenIdleX = 985 - player.worldX + player.screenX;
            screenIdleY = 1470 - player.worldY + player.screenY;

            yOverlay = 0;
            overlay = "VisibleDark";

            g.drawImage(imageManager.getImage("carpetAfter"), screenIdleX-985, screenIdleY-1470, 2400, 2400, null);

            screenIdleX = 1415 - player.worldX + player.screenX;
            screenIdleY = 1117 - player.worldY + player.screenY;

            g.drawImage(imageManager.getImage("bedRoomAfter"), screenIdleX-1410, screenIdleY-1140, 2400, 2400, null);

            g.drawImage(imageManager.getImage("bedRoomZoneAfter"), screenIdleX-1415, screenIdleY-1117, 2400, 2400, null);

            screenIdleX = 1460 - player.worldX + player.screenX;
            screenIdleY = 1200 - player.worldY + player.screenY;

            g.drawImage(imageManager.getImage("top-after"), screenIdleX-610, screenIdleY-350, 700, 700, null);

            screenIdleX = 907 - player.worldX + player.screenX;
            screenIdleY = 962 - player.worldY + player.screenY;

            g.drawImage(imageManager.getImage("cellerWayAfter"), screenIdleX-930, screenIdleY-918, 2400, 2300, null);

            screenIdleX = 970 - player.worldX + player.screenX;
            screenIdleY = 1260 - player.worldY + player.screenY;
            g.drawImage(imageManager.getImage("table-after"), screenIdleX-125, screenIdleY-400, 700, 700, null);

            screenIdleX = 1310 - player.worldX + player.screenX;
            screenIdleY = 1410 - player.worldY + player.screenY;
            g.drawImage(imageManager.getImage("tree-atfer"), screenIdleX-450, screenIdleY-530, 700, 700, null);

            if ((player.worldX <= 1085 && player.worldX >= 850) && (player.worldY <= 1510 && player.worldY >= 1410)){
                UI.SCENE = 6;
                gp.ui.showText = false;
                gp.ui.startFade();
                gp.keyH.keyBoolRelease();
            }
        }

        g.drawImage(imageManager.getImage(overlay), 0, yOverlay, gp.getWidth(), gp.getHeight(), null);
    }
}
