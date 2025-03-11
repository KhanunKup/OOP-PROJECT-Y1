package Main;

import javax.swing.*;
import java.awt.*;

public class Player {
    final int animDelay = 7;
    int currentFrame = 0;
    int currentIdleFrame = 0;
    String direction;
    String state;

    int screenX;
    int screenY;

    int worldX, worldY, speed;
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
        direction = "left";
        state = "idle";
        valuesSetting();
        playerLoading();
    }

    public void valuesSetting() {
//    world position -> position จริง ๆ ในเกม ทุก object, character, tile ถูก fixed ไว้แล้ว
//      screen position -> position ที่เราทำการ draw (สั่งให้ java draw แล้วเห็นในจอ ว่ามันคือตรงไหน)
        worldX = gp.mapX / 2;
        worldY = gp.mapY / 2;
        screenX = gp.mapX / 2 - gp.xTileSize / 2; //Center of the screen (because it's placed at top-left corner)
        screenY = gp.mapY / 2 - gp.yTileSize / 2;
        speed = 5;
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

    public void update() {
        int speedDiag = (int) (speed/Math.sqrt(2));

        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            state = "walking";
            if (keyH.upPressed && keyH.rightPressed) {
                worldX += speedDiag;
                worldY -= speedDiag;
                direction = "right";
            } else if (keyH.upPressed && keyH.leftPressed) {
                worldX -= speedDiag;
                worldY -= speedDiag;
                direction = "left";
            } else if (keyH.downPressed && keyH.rightPressed) {
                worldX += speedDiag;
                worldY += speedDiag;
                direction = "right";
            } else if (keyH.downPressed && keyH.leftPressed) {
                worldX -= speedDiag;
                worldY += speedDiag;
                direction = "left";
            } else if (keyH.upPressed) {
                worldY -= speed;
                direction = "up";
            } else if (keyH.downPressed) {
                worldY += speed;
                direction = "down";
            } else if (keyH.leftPressed) {
                worldX -= speed;
                direction = "left";
            } else if (keyH.rightPressed)  {
                worldX += speed;
                direction = "right";
            }
        } else {
            state = "idle";
        }
        animationHandler();
    }

    public void draw(Graphics g) {
//        g.fillRect(x, y, gp.xTile, gp.yTile);
        g.drawImage(hansel, screenX, screenY, gp.xTileSize *2, gp.yTileSize *2, null);
    }
}
