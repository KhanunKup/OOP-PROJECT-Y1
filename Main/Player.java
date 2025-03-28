package Main;

import javax.swing.*;
import java.awt.*;

public class Player implements Walkable {
    final int animDelay = 7;
    int currentFrame = 0;
    int currentIdleFrame = 0;
    String direction, animDirection, state;

    Rectangle hitbox;

    int screenX;
    int screenY;

    static int worldX, worldY, speed, speedDiag;
    KeyHandler keyH;
    GamePanel gp;
    Image[] hansel;
    String[] idleAnimLeft;
    String[] idleAnimRight;
    String[] walkingAnimLeft;
    String[] walkingAnimRight;

    public Player(GamePanel gamePanel, KeyHandler keyH, ImageManager imageManager) {
        this.gp = gamePanel;
        this.keyH = keyH;
        direction = "right";
        animDirection = "right";
        state = "idle";
        hitbox = new Rectangle();
        valuesSetting();
        playerLoading(imageManager);
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
        idleAnimLeft = new String[9];
        idleAnimRight = new String[9];
        walkingAnimLeft = new String[8];
        walkingAnimRight = new String[8];
        int hitboxWidth = 24;
        int hitboxHeight = 24;
        int hitboxXOffset = (gp.xTileSize * 2 - hitboxWidth) / 2;
        int hitboxYOffset = (gp.yTileSize * 2 - hitboxHeight) / 2;
        hitbox.x = hitboxXOffset;
        hitbox.y = hitboxYOffset;
        hitbox.width = hitboxWidth;
        hitbox.height = hitboxHeight;
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

            if (gp.collChecker.isBlockWalkable(this.direction, this) == true) { //if walkable
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
                speed = 3;
                speedDiag = (int) (speed/Math.sqrt(2));
            } else {
                speed = 2;
                speedDiag = (int) (speed/Math.sqrt(2));
            }

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
