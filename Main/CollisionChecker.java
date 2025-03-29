package Main;

import tile.CellerMap;
import tile.ForestMap;
import tile.HouseMap;
import tile.TileManager;

public class CollisionChecker {
    GamePanel gp;
    Player player;
    int leftX, rightX, topY, bottomY, leftCol, rightCol, topRow, bottomRow;

    public CollisionChecker(GamePanel gp, Player player) {
        this.gp = gp;
        this.player = player;
        //x, y 4 sides
    }

    public boolean isBlockWalkable(String direction) {
        leftX = player.worldX + player.hitbox.x;
        rightX = player.worldX + player.hitbox.x + player.hitbox.width;
        topY = player.worldY + player.hitbox.y;
        bottomY = player.worldY + player.hitbox.y + player.hitbox.height;

        //หาว่าอยู่ใน row, col ที่เท่าไหร่ เพื่อไปหา block ต่อ
        leftCol = (leftX - player.speed) / gp.tileSize;
        rightCol = (rightX + player.speed) / gp.tileSize;
        topRow = (topY - player.speed) / gp.tileSize; //พวก speed เหมือนคำนวณล่วงหน้าไปก่อนที่จะบวก
        bottomRow = (bottomY + player.speed) / gp.tileSize;
        if (leftCol <= 0) { //ทำให้อยู่ใน 100x100 map (สร้าง boundary)
                player.worldX += player.speed;
            }
            if (rightCol >= gp.maxCol - 1) {
                player.worldX -= player.speed;
            }
            if (topRow <= 0) {
                player.worldY += player.speed;
            }
            if (bottomRow >= gp.maxRow - 1) {
                player.worldY -= player.speed;
            }


        int tile1, tile2; //2 tiles used for checking for collision

        switch (player.direction) {
            case "up":
                tile1 = gp.currentTileMap.getMap()[topRow][leftCol];
                tile2 = gp.currentTileMap.getMap()[topRow][rightCol];
                return !(gp.currentTileMap.solidTiles.contains(tile1) || gp.currentTileMap.solidTiles.contains(tile2));

            case "down":
                tile1 = gp.currentTileMap.getMap()[bottomRow][leftCol];
                tile2 = gp.currentTileMap.getMap()[bottomRow][rightCol];
                return !(gp.currentTileMap.solidTiles.contains(tile1) || gp.currentTileMap.solidTiles.contains(tile2));

            case "left":
                tile1 = gp.currentTileMap.getMap()[topRow][leftCol];
                tile2 = gp.currentTileMap.getMap()[bottomRow][leftCol];
                return !(gp.currentTileMap.solidTiles.contains(tile1) || gp.currentTileMap.solidTiles.contains(tile2));

            case "right":
                tile1 = gp.currentTileMap.getMap()[topRow][rightCol];
                tile2 = gp.currentTileMap.getMap()[bottomRow][rightCol];
                return !(gp.currentTileMap.solidTiles.contains(tile1) || gp.currentTileMap.solidTiles.contains(tile2));

            // Diagonal Movements ต้องเช็คทั้งสองทิศ
            case "upLeft":
                tile1 = gp.currentTileMap.getMap()[topRow][leftCol]; //จุดซ้ายบน
                return !(gp.currentTileMap.solidTiles.contains(tile1));

            case "upRight":
                tile1 = gp.currentTileMap.getMap()[topRow][rightCol]; //จุดขวาบน
                return !(gp.currentTileMap.solidTiles.contains(tile1));

            case "downLeft":
                tile2 = gp.currentTileMap.getMap()[bottomRow][leftCol];
                return !(gp.currentTileMap.solidTiles.contains(tile2));

            case "downRight":
                tile2 = gp.currentTileMap.getMap()[bottomRow][rightCol];
                return !(gp.currentTileMap.solidTiles.contains(tile2));

            default:
                return true; //ถือว่าเดินได้ไปก่อน
        }
    }

    public String footstepChecker() {
        //used for checking if tile is dirt/grass
        int centerX = player.worldX + (player.playerSize / 2);
        int centerY = player.worldY + (player.playerSize / 2);
        int centerRow = centerY / gp.tileSize;
        int centerCol = centerX / gp.tileSize;
        int tileNum = gp.currentTileMap.getMap()[centerRow][centerCol];
        if (gp.currentTileMap instanceof ForestMap) {
            if (((ForestMap) gp.currentTileMap).grassTiles.contains(tileNum)) {
                return "grass";
            } else if (((ForestMap) gp.currentTileMap).dirtTiles.contains(tileNum)) {
                return "dirt";
            } else {
                return "";
            }
        } else if (gp.currentTileMap instanceof  HouseMap) {
            return "";
        } else if (gp.currentTileMap instanceof CellerMap) {
            return "";
        } else {
            return "";
        }
    }

    public void setCollisionPlusX(int worldX1 , int worldX2, int worldY1, int worldY2, TileManager tilemap){
        if (((Player.worldX <= worldX1 && Player.worldX >= worldX2) && (Player.worldY <= worldY1 && Player.worldY >= worldY2)) && gp.currentTileMap == tilemap) {
            Player.worldX += 3;
        }
    }

    public void setCollisionMinusX(int worldX1 , int worldX2, int worldY1, int worldY2, TileManager tilemap){
        if (((Player.worldX <= worldX1 && Player.worldX >= worldX2) && (Player.worldY <= worldY1 && Player.worldY >= worldY2)) && gp.currentTileMap == tilemap) {
            Player.worldX -= 3;
        }
    }

    public void setCollisionPlusY(int worldX1 , int worldX2, int worldY1, int worldY2, TileManager tilemap){
        if (((Player.worldX <= worldX1 && Player.worldX >= worldX2) && (Player.worldY<= worldY1 && Player.worldY >= worldY2)) && gp.currentTileMap == tilemap){
            Player.worldY +=3;
        }
    }

    public void setCollisionMinusY(int worldX1 , int worldX2, int worldY1, int worldY2, TileManager tilemap){
        if (((Player.worldX <= worldX1 && Player.worldX >= worldX2) && (Player.worldY<= worldY1 && Player.worldY >= worldY2)) && gp.currentTileMap == tilemap){
            Player.worldY -= 3;
        }
    }
}
