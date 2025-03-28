package Main;

public class CollisionChecker {
    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public boolean isBlockWalkable(String direction, Player player) {
        //x, y 4 sides
        int leftX = player.worldX + player.hitbox.x;
        int rightX = player.worldX + player.hitbox.x + player.hitbox.width;
        int topY = player.worldY + player.hitbox.y;
        int bottomY = player.worldY + player.hitbox.y + player.hitbox.height;

        //หาว่าอยู่ใน row, col ที่เท่าไหร่ เพื่อไปหา block ต่อ
        int leftCol = (leftX - player.speed) / gp.xTileSize;
        int rightCol = (rightX + player.speed) / gp.xTileSize;
        int topRow = (topY - player.speed) / gp.yTileSize; //พวก speed เหมือนคำนวณล่วงหน้าไปก่อนที่จะบวก
        int bottomRow = (bottomY + player.speed) / gp.yTileSize;

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
}
