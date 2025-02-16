package entity;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity{
    KeyHandler keyH;
//    public int hasKey = 0;
    int standCounter = 0;
    boolean moving = false;
    int pixelCounter = 0;

    public final int screenX;
    public final int screenY;

    public Player(GamePanel gp, KeyHandler keyH){
        super(gp);

        this.keyH=keyH;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        solidArea = new Rectangle();
        solidArea.x = 1;
        solidArea.y = 1;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 46;
        solidArea.height = 46;

        setDefaultValues();
        getPlayerImage();
    }
    public  void setDefaultValues(){
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";
    }
    public void getPlayerImage(){
        up1 = setup("/player/boy_up_1");
        up2 = setup("/player/boy_up_2");
        down1 = setup("/player/boy_down_1");
        down2 = setup("/player/boy_down_2");
        left1 = setup("/player/boy_left_1");
        left2 = setup("/player/boy_left_2");
        right1 = setup("/player/boy_right_1");
        right2 = setup("/player/boy_right_2");
    }
    public void update() {
        if(!moving){
            if(keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed){
                if(keyH.upPressed){
                    direction = "up";
                } else if (keyH.downPressed) {
                    direction = "down";
                }else if (keyH.leftPressed){
                    direction = "left";
                } else if (keyH.rightPressed) {
                    direction = "right";
                }

                moving = true;

                // CHECK TILE COLLISION
                collisionOn = false;
                gp.cChecker.checkTile(this);

                // CHECK OBJECT COLLISION
                int objIndex = gp.cChecker.checkObject(this, true);
                pickUpobject(objIndex);

                // CHECK NPC COLLISION
                int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
                interactNPC(npcIndex);

            }
        }else {
            standCounter++;

            if(standCounter == 20){
                spriteNum = 1;
                standCounter = 0;
            }
        }
        if(moving) {
            // IF COLLISION IS FALSE, PLAYER CAN MOVE
            if(!collisionOn) {
                for(int i = 0; i < speed; i++) {
                    switch (direction) {
                        case "up":
                            worldY -= 1;
                            break;
                        case "down":
                            worldY += 1;
                            break;
                        case "left":
                            worldX -= 1;
                            break;
                        case "right":
                            worldX += 1;
                            break;
                    }
                    pixelCounter++;

                    if (pixelCounter == gp.tileSize) {
                        moving = false;
                        pixelCounter = 0;
                        break;
                    }
                }

                spriteCounter++;
                if (spriteCounter > 10 - speed) {
                    if (spriteNum == 1) {
                        spriteNum = 2;
                    } else if (spriteNum == 2) {
                        spriteNum = 1;
                    }
                    spriteCounter = 0;
                }
            }else {
                moving = false;
            }
        }
    }
    public void pickUpobject(int i){
//        if(i != 999){
//            String objectName = gp.obj[i].name;
//
//            switch(objectName){
//                case "Key":
//                    gp.playSE(1);
//                    hasKey++;
//                    gp.obj[i] = null;
//                    gp.ui.showMessage("You Got A Key!");
//                    break;
//                case "Door":
//                    if(hasKey > 0){
//                        gp.playSE(4);
//                        gp.obj[i] = null;
//                        hasKey--;
//                        gp.ui.showMessage("You Open The Door!");
//                    } else {
//                        gp.ui.showMessage("You Need A Key!");
//                    }
//                    break;
//                case "Boots":
//                    gp.playSE(3);
//                    speed += 1;
//                    gp.obj[i] = null;
//                    break;
//                case "Chest":
//                    gp.ui.gameFinished = true;
//                    gp.stopMusic();
//                    gp.playSE(2);
//                    break;
//            }
//        }
    }
    public void interactNPC(int i){
        if(i != 999){
            System.out.println("npc");
        }
    }

    public void draw(Graphics g2){
        BufferedImage image = null;

        switch(direction){
            case "up":
                if(spriteNum == 1) {
                    image = up1;
                }
                if(spriteNum ==2) {
                    image = up2;
                }
                break;
            case "down":
                if(spriteNum == 1) {
                    image = down1;
                }
                if(spriteNum ==2) {
                    image = down2;
                }
                break;
            case "left":
                if(spriteNum == 1) {
                    image = left1;
                }
                if(spriteNum ==2) {
                    image = left2;
                }
                break;
            case "right":
                if(spriteNum == 1) {
                    image = right1;
                }
                if(spriteNum ==2) {
                    image = right2;
                }
                break;
        }
        g2.drawImage(image, screenX, screenY, null);
        g2.setColor(Color.RED);
        g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
    }
}
