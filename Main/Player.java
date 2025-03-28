package Main;

import javax.swing.*;
import java.awt.*;

public class Player implements Walkable {
    final int animDelay = 7;
    int currentFrame = 0;
    int currentIdleFrame = 0;
    String direction;
    String state;
    float moveSpeed = 0.5f;

    int screenX;
    int screenY;

    static int worldX, worldY, speed, speedDiag;
    KeyHandler keyH;
    GamePanel gp;
    Image hansel;
    Image idleAnimLeft[];
    Image idleAnimRight[];
    Image walkingAnimLeft[];
    Image walkingAnimRight[];

    public Player(GamePanel gamePanel, KeyHandler keyH) {
        this.gp = gamePanel;
        this.keyH = keyH;
        direction = "right";
        state = "idle";
        valuesSetting();
        playerLoading();
    }

    public void valuesSetting() {
//    world position -> position จริง ๆ ในเกม ทุก object, character, tile ถูก fixed ไว้แล้ว
//      screen position -> position ที่เราทำการ draw (สั่งให้ java draw แล้วเห็นในจอ ว่ามันคือตรงไหน)
        worldX = 665;
        worldY = 817;
        screenX = gp.mapX / 2 - gp.xTileSize / 2; //Center of the screen (because it's placed at top-left corner)
        screenY = gp.mapY / 2 - gp.yTileSize / 2;
        speed = 3;
        speedDiag = (int) (speed/Math.sqrt(2));
        idleAnimLeft = new Image[9];
        idleAnimRight = new Image[9];
        walkingAnimLeft = new Image[8];
        walkingAnimRight = new Image[8];
    }

    public void playerLoading() {
        for (int i = 0; i < 9; i++) { //Storing player.png in an array
            String path = "res/Character/Hansel/Hansel_Idle/hansel_idle_left"+(i+1)+".png";
            idleAnimLeft[i] = new ImageIcon(path).getImage();
        }

        for (int i = 0; i < 9; i++) {
            String path = "res/Character/Hansel/Hansel_Idle/hansel_idle_right"+(i+1)+".png";
            idleAnimRight[i] = new ImageIcon(path).getImage();
        }

        for (int i = 0; i < 8; i++) {
            String path = "res/Character/Hansel/Hansel_Walking/hansel_walking_left"+(i+1)+".png";
            walkingAnimLeft[i] = new ImageIcon(path).getImage();
        }
        for (int i = 0; i < 8; i++) {
            String path = "res/Character/Hansel/Hansel_Walking/hansel_walking_right"+(i+1)+".png";
            walkingAnimRight[i] = new ImageIcon(path).getImage();
        }
    }

    public void animationHandler() {
        currentFrame++;
        if (currentFrame >= animDelay) {
            currentFrame = 0;
            currentIdleFrame++;
        }

        if (state.equals("idle")) {
            if (currentIdleFrame > walkingAnimLeft.length-1) {
                currentIdleFrame = 0;
            }

            if (direction.equals("left")) {
                hansel = idleAnimLeft[currentIdleFrame];
            } else if (direction.equals("right")) {
                hansel = idleAnimRight[currentIdleFrame];
            }
        } else if (state.equals("walking")) {
            if (currentIdleFrame > walkingAnimLeft.length-1) {
                currentIdleFrame = 0;
            }

            if (direction.equals("left")) {
                hansel = walkingAnimLeft[currentIdleFrame];
            } else if (direction.equals("right")) {
                hansel = walkingAnimRight[currentIdleFrame];
            }
        }

    }

    public void moveCharacter(Walkable walker){
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            state = "walking";
            if (keyH.upPressed && keyH.rightPressed) {
                walker.walkDiagRightUp();
            } else if (keyH.upPressed && keyH.leftPressed) {
                walker.walkDiagLeftUp();
            } else if (keyH.downPressed && keyH.rightPressed) {
                walker.walkDiagRightDown();
            } else if (keyH.downPressed && keyH.leftPressed) {
                walker.walkDiagLeftDown();
            } else if (keyH.upPressed) {
                walker.walkUp();
            } else if (keyH.downPressed) {
                walker.walkDown();
            } else if (keyH.leftPressed) {
                walker.walkLeft();
            } else if (keyH.rightPressed)  {
                walker.walkRight();
            }
        } else {
            state = "idle";
        }
    }

    public void update() {
        System.out.println("X :" + worldX);
        System.out.println("Y :" + worldY);
        if (gp.gameState == UI.MOVING) {
            setScreenPosition();

            //เสายาวซ้าย
            if (((worldX<= 860 && worldX >=829) && (worldY<= 1508 && worldY >= 850)) && gp.currentTileMap == gp.tileMap3) {
                worldX += 3;
            }
            //
            if (((worldX<= 1163 && worldX >=858) && (worldY<= 1150 && worldY >= 1134)) && gp.currentTileMap == gp.tileMap3){
                worldY +=3;
            }
            //
            if (((worldX<= 1163 && worldX >=860) && (worldY<= 1000 && worldY >= 990)) && gp.currentTileMap == gp.tileMap3){
                worldY -=3;
            }
            //
            if (((worldX<= 1100 && worldX >=1088) && (worldY<= 1366 && worldY >= 1150)) && gp.currentTileMap == gp.tileMap3){
                worldX -=3;
            }
            //
            if (((worldX<= 1165 && worldX >=1120) && (worldY<= 1360 && worldY >= 1145)) && gp.currentTileMap == gp.tileMap3){
                worldX +=3;
            }
            //ขอบขนบรรได
            if (((worldX<= 1100 && worldX >=859) && (worldY<= 920 && worldY >= 910)) && gp.currentTileMap == gp.tileMap3){
                worldY +=3;
            }
            //ขอบขวาเสาซ้าย
            if (((worldX<= 1180 && worldX >=1150) && (worldY<= 1129 && worldY >= 1025)) && gp.currentTileMap == gp.tileMap3){
                worldX +=3;
            }
            //ขอบเสาซ้ายเล็กๆ
            if (((worldX<= 1135 && worldX >=1100) && (worldY<= 1390 && worldY >= 1376)) && gp.currentTileMap == gp.tileMap3){
                worldY +=3;
            }
            //ขอบล่าง
            if (((worldX<= 1500 && worldX >=850) && (worldY<= 1513 && worldY >= 1505)) && gp.currentTileMap == gp.tileMap3){
                worldY -=3;
            }
            //ขอบขวา
            if (((worldX<= 1500 && worldX >=1491) && (worldY<= 1503 && worldY >= 918)) && gp.currentTileMap == gp.tileMap3){
                worldX -=3;
            }
            //ขอบประตูขวา
            if (((worldX<= 1500 && worldX >=1470) && (worldY<= 1410 && worldY >= 1400)) && gp.currentTileMap == gp.tileMap3){
                worldY +=3;
            }
            //ขอบประตูขวา ขวา
            if (((worldX<= 1500 && worldX >=1449) && (worldY<= 1384 && worldY >= 1290)) && gp.currentTileMap == gp.tileMap3){
                worldX -=3;
            }
            //ขอบขวา ซ้าย
            if (((worldX<= 1405 && worldX >=1245) && (worldY<= 1405 && worldY >= 1400)) && gp.currentTileMap == gp.tileMap3){
                worldY +=3;
            }
            //ขอบขวา ขวา
            if (((worldX<= 1235 && worldX >=1226) && (worldY<= 1389 && worldY >= 1028)) && gp.currentTileMap == gp.tileMap3){
                worldX -=3;
            }
            //ขอบขวา ซ้าย
            if (((worldX<= 1440 && worldX >=1429) && (worldY<= 1389 && worldY >= 1293)) && gp.currentTileMap == gp.tileMap3){
                worldX +=3;
            }
            //ขวาซ้าย บน
            if (((worldX<= 1400 && worldX >=1286) && (worldY<= 1273 && worldY >= 1268)) && gp.currentTileMap == gp.tileMap3){
                worldY -=3;
            }
            //ขวาซ้าย ซ้าย
            if (((worldX<= 1300 && worldX >=1289) && (worldY<= 1266 && worldY >= 1133)) && gp.currentTileMap == gp.tileMap3){
                worldX +=3;
            }
            //ขอบบนห้องนอนเล็ก
            if (((worldX<= 1490 && worldX >=1280) && (worldY<= 1150 && worldY >= 1148)) && gp.currentTileMap == gp.tileMap3){
                worldY +=3;
            }
            //ขอบล่าง
            if (((worldX<= 1490 && worldX >=1260) && (worldY<= 1020 && worldY >= 1011)) && gp.currentTileMap == gp.tileMap3){
                worldY -=3;
            }
            //ขอบบน
            if (((worldX<= 1500 && worldX >=1085) && (worldY<= 950 && worldY >= 946)) && gp.currentTileMap == gp.tileMap3){
                worldY +=3;
            }
            //
            if (((worldX<= 1100 && worldX >=1085) && (worldY<= 957 && worldY >= 922)) && gp.currentTileMap == gp.tileMap3){
                worldX -=3;
            }


            //code สำหรับ map 1 เมื่อเดินเข้าใกล้ระยะน้อง จะเปลี่ยนเเมพ
            if (((gp.mapM.screenIdleX <= -120 && gp.mapM.screenIdleX >= -280) && (gp.mapM.screenIdleY <= 550 && gp.mapM.screenIdleY >= 400)) && gp.currentTileMap == gp.tileMap1){
                    UI.SCENE = 2;
                    gp.ui.showText = false;
                    gp.ui.startFade();
                    keyH.keyBoolRelease();
            }

            if (((gp.mapM.screenIdleX <= -30 && gp.mapM.screenIdleY >= -280) && (gp.mapM.screenIdleY >= -100)) && gp.currentTileMap == gp.tileMap2){
                UI.SCENE = 3;
                gp.ui.showText = false;
                gp.ui.showImage = true;
                gp.ui.imageDelay = 60;
                gp.ui.startFade();
                keyH.keyBoolRelease();
            }

            if (keyH.shiftPressed) {
                speed = 10;
            } else {
                speed = 2;
            }

            moveCharacter(this);

            animationHandler();
        }
    }

    public void draw(Graphics g) {
//        g.fillRect(x, y, gp.xTile, gp.yTile);
        g.drawImage(hansel, screenX, screenY, gp.xTileSize *2, gp.yTileSize *2, null);
    }

    public void setScreenPosition() {
        screenX = gp.mapX / 2 - gp.xTileSize / 2;
        screenY = gp.mapY / 2 - gp.yTileSize / 2;
    }


    @Override
    public void walkUp() {
        worldY -= speed;
//                direction = "up";
    }

    @Override
    public void walkDown() {
        worldY += speed;
//                direction = "down";
    }

    @Override
    public void walkLeft() {
        worldX -= speed;
        direction = "left";
    }

    @Override
    public void walkRight() {
        worldX += speed;
        direction = "right";
    }

    @Override
    public void walkDiagLeftUp() {
        worldX -= speedDiag;
        worldY -= speedDiag;
        direction = "left";
    }

    @Override
    public void walkDiagLeftDown() {
        worldX -= speedDiag;
        worldY += speedDiag;
        direction = "left";
    }

    @Override
    public void walkDiagRightUp() {
        worldX += speedDiag;
        worldY -= speedDiag;
        direction = "right";
    }

    @Override
    public void walkDiagRightDown() {
        worldX += speedDiag;
        worldY += speedDiag;
        direction = "right";
    }
}
