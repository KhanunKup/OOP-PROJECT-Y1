package Main;

import java.awt.*;

public class MapManager {
    GamePanel gp;
    Player player;

    public MapManager(GamePanel gp, Player player) {
        this.gp = gp;
        this.player = player;
    }

    public void drawMap(Graphics g){
        int worldX;
        int worldY;
        int screenX;
        int screenY;
        for (int row = 0; row < gp.maxRow; row++) {
            for (int col = 0; col < gp.maxCol; col++) {
                int tile = gp.map[row][col];
                worldX = col * gp.xTileSize;
                worldY = row * gp.yTileSize;
//                screenX = worldX - player.worldX + player.screenX;
//                screenY = worldY - player.worldY + player.screenY;
                screenX = worldX - player.worldX;
                screenY = worldY - player.worldY;
                if (screenX + gp.xTileSize >= 0 && screenX <= GamePanel.mapX && //screenY + gp.yTileSize >= 0 คือเหมือนเพิ่มขอบไป 1 tile size จะได้ render ทัน ไม่ให้เกิดขอบดำ
                        screenY + gp.yTileSize >= 0 && screenY <= GamePanel.mapY) {
                    g.drawImage(gp.tileMap.getTileImage(tile), screenX, screenY, gp.xTileSize, gp.yTileSize, null);
                }
            }
        }
    }
}
