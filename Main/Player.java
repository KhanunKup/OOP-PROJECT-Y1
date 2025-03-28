package Main;

import javax.swing.*;
import java.awt.*;

public class Player extends Human implements Walkable{
    final int animDelay = 7;
    int currentFrame = 0;
    int currentIdleFrame = 0;
    String direction, animDirection, state;
    int footstepTimer = 0;
    final int footstepDelay = 25;
    Sound dirtFootstep;


    int screenX;
    int screenY;

    public static int worldX, worldY, speed, speedDiag;
    KeyHandler keyH;
    GamePanel gp;
    Image[] hansel;
    String[] idleAnimLeft;
    String[] idleAnimRight;
    String[] walkingAnimLeft;
    String[] walkingAnimRight;

    UI ui;

    public Player(GamePanel gamePanel, KeyHandler keyH, ImageManager imageManager) {
        this.setSpeed(2);
        this.setWorldX(665);
        this.setWorldY(817);
        this.gp = gamePanel;
        this.keyH = keyH;
        direction = "right";
        animDirection = "right";
        state = "idle";
        this.setHitbox((gp.xTileSize * 2 - 24) / 2 , (gp.yTileSize * 2 - 24) / 2 , 24 , 24);
        ui = new UI(gp,this);
        dirtFootstep = new Sound();
        dirtFootstep.playSound("res/sound/soundtrack/dirt-footstep.wav");
        dirtFootstep.setVolume(ui.volumeLevel / 100f);
        valuesSetting();
        playerLoading(imageManager);
    }

    public void valuesSetting() {
//    world position -> position จริง ๆ ในเกม ทุก object, character, tile ถูก fixed ไว้แล้ว
//      screen position -> position ที่เราทำการ draw (สั่งให้ java draw แล้วเห็นในจอ ว่ามันคือตรงไหน)
        worldX = this.getWorldX();
        worldY = this.getWorldY();
        speed = this.getSpeed();
        screenX = gp.mapX / 2 - gp.xTileSize / 2; //Center of the screen (because it's placed at top-left corner)
        screenY = gp.mapY / 2 - gp.yTileSize / 2;
        speedDiag = (int) (speed/Math.sqrt(2));
        idleAnimLeft = new String[9];
        idleAnimRight = new String[9];
        walkingAnimLeft = new String[8];
        walkingAnimRight = new String[8];
    }

    public void playerLoading(ImageManager imageManager) {
        for (int i = 0; i < 9; i++) { //Storing player.png in an array
            idleAnimLeft[i] = "res/Character/Hansel/Hansel_Idle/hansel_idle_left"+(i+1)+".png";
        }
        imageManager.setImage("idleAnimLeft",idleAnimLeft);

        for (int i = 0; i < 9; i++) {
            idleAnimRight[i] = "res/Character/Hansel/Hansel_Idle/hansel_idle_right"+(i+1)+".png";
        }
        imageManager.setImage("idleAnimRight",idleAnimRight);

        for (int i = 0; i < 8; i++) {
            walkingAnimLeft[i] = "res/Character/Hansel/Hansel_Walking/hansel_walking_left"+(i+1)+".png";
        }
        imageManager.setImage("walkingAnimLeft",walkingAnimLeft);

        for (int i = 0; i < 8; i++) {
            walkingAnimRight[i] = "res/Character/Hansel/Hansel_Walking/hansel_walking_right"+(i+1)+".png";
        }
        imageManager.setImage("walkingAnimRight",walkingAnimRight);
    }

    public void animationHandler(ImageManager imageManager) {
        currentFrame++;
        if (currentFrame >= animDelay) {
            currentFrame = 0;
            currentIdleFrame++;
        }

        if (state.equals("idle")) {
            if (currentIdleFrame > walkingAnimLeft.length-1) {
                currentIdleFrame = 0;
            }
            if (animDirection.equals("left")) {
                hansel = imageManager.getImages("idleAnimLeft");
            } else if (animDirection.equals("right")) {
                hansel = imageManager.getImages("idleAnimRight");
            }
        } else if (state.equals("walking")) {
            if (currentIdleFrame > walkingAnimLeft.length-1) {
                currentIdleFrame = 0;
            }
            if (animDirection.equals("left")) {
                hansel = imageManager.getImages("walkingAnimLeft");
            } else if (animDirection.equals("right")) {
                hansel = imageManager.getImages("walkingAnimRight");
            }
        }

    }

    public void moveCharacter(Walkable walker){
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            state = "walking";
            if (footstepTimer >= footstepDelay) {
                dirtFootstep.play();
                footstepTimer = 0; // Reset timer
            } else {
                footstepTimer++; // Increment timer
            }
            if (keyH.upPressed && keyH.rightPressed) {
                direction = "upRight";
                animDirection = "right";
            } else if (keyH.upPressed && keyH.leftPressed) {
                direction = "upLeft";
                animDirection = "left";
            } else if (keyH.downPressed && keyH.rightPressed) {
                direction = "downRight";
                animDirection = "right";
            } else if (keyH.downPressed && keyH.leftPressed) {
                direction = "downLeft";
                animDirection = "left";
            } else if (keyH.upPressed) {
                direction = "up";
                //animDirection จะเป็น direction ล่าสุด
            } else if (keyH.downPressed) {
                direction = "down";
            } else if (keyH.leftPressed) {
                direction = "left";
                animDirection = "left";
            } else if (keyH.rightPressed)  {
                direction = "right";
                animDirection = "right";
            }

            if (gp.collChecker.isBlockWalkable(this.direction, this)) { //if walkable
                switch (direction) {
                    case "upRight":
                        walker.walkDiagUpRight();
                        break;
                    case "upLeft":
                        walker.walkDiagUpLeft();
                        break;
                    case "downRight":
                        walker.walkDiagDownRight();
                        break;
                    case "downLeft":
                        walker.walkDiagDownLeft();
                        break;
                    case "up":
                        walker.walkUp();
                        break;
                    case "down":
                        walker.walkDown();
                        break;
                    case "left":
                        walker.walkLeft();
                        break;
                    case "right":
                        walker.walkRight();
                        break;
                }
            }

        } else {
            state = "idle";
        }
    }

    public void update(ImageManager imageManager) {
//        System.out.println("X :" + worldX);
//        System.out.println("Y :" + worldY);
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
                speed = this.getSpeed()+6;
            }else {
                speed = this.getSpeed();
            }
            speedDiag = (int) (speed/Math.sqrt(2));

            moveCharacter(this);

            animationHandler(imageManager);
        }
    }

    public void draw(Graphics g) {
//        g.fillRect(x, y, gp.xTile, gp.yTile);
        if (hansel != null && hansel.length > 0) {
            g.drawImage(hansel[currentIdleFrame], screenX, screenY, gp.xTileSize *2, gp.yTileSize *2, null);
            g.drawRect(screenX + hitbox.x, screenY + hitbox.y, hitbox.width, hitbox.height);
        }
    }

    public void setScreenPosition() {
        screenX = gp.mapX / 2 - gp.xTileSize / 2;
        screenY = gp.mapY / 2 - gp.yTileSize / 2;
    }

    //used for player position(x,y) changes
    @Override
    public void walkUp() {
        worldY -= speed;
    }

    @Override
    public void walkDown() {
        worldY += speed;
    }

    @Override
    public void walkLeft() {
        worldX -= speed;
    }

    @Override
    public void walkRight() {
        worldX += speed;
    }

    @Override
    public void walkDiagUpLeft() {
        worldX -= speedDiag;
        worldY -= speedDiag;
    }

    @Override
    public void walkDiagDownLeft() {
        worldX -= speedDiag;
        worldY += speedDiag;
    }

    @Override
    public void walkDiagUpRight() {
        worldX += speedDiag;
        worldY -= speedDiag;
    }

    @Override
    public void walkDiagDownRight() {
        worldX += speedDiag;
        worldY += speedDiag;
    }
}
