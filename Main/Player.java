package Main;

import javax.swing.*;
import java.awt.*;

public class Player {
    final int animDelay = 7;
    int currentFrame = 0;
    int currentIdleFrame = 0;
    String direction;

    int x, y, speed;
    KeyHandler keyH;
    GamePanel gp;
    Image hansel;
    Image idlesAnimLeft[];
    Image idlesAnimRight[];

    public Player(GamePanel gamePanel, KeyHandler keyH) {
        this.gp = gamePanel;
        this.keyH = keyH;
        direction = "left";
        valuesSetting();
        playerLoading();
    }

    public void valuesSetting() {
        x = gp.mapX / 2;
        y = gp.mapY / 2;
        speed = 5;
        idlesAnimLeft = new Image[9];
        idlesAnimRight = new Image[9];
    }

    public void playerLoading() {
        for (int i = 0; i <= 8; i++) { //Storing player.png in an array
            String path = "res/Character/Hansel/Hansel_Idle/hansel_idle_left"+(i+1)+".png";
            idlesAnimLeft[i] = new ImageIcon(path).getImage();
        }
        for (int i = 0; i <= 8; i++) { //Storing player.png in an array
            String path = "res/Character/Hansel/Hansel_Idle/hansel_idle_right"+(i+1)+".png";
            idlesAnimRight[i] = new ImageIcon(path).getImage();
        }
    }

    public void animationHandler() {
        currentFrame++;
        if (currentFrame >= animDelay) {
            currentFrame = 0;
            currentIdleFrame++;
        }

        if (currentIdleFrame > idlesAnimLeft.length-1) {
            currentIdleFrame = 0;
        }

        if (direction.equals("left")) {
            hansel = idlesAnimLeft[currentIdleFrame];
        } else if (direction.equals("right")) {
            hansel = idlesAnimRight[currentIdleFrame];
        }
    }

    public void update() {
        int speedDiag = (int) (speed/Math.sqrt(2));

        if (keyH.upPressed && keyH.rightPressed) {
            x += speedDiag;
            y -= speedDiag;
            direction = "right";
        } else if (keyH.upPressed && keyH.leftPressed) {
            x -= speedDiag;
            y -= speedDiag;
            direction = "left";
        } else if (keyH.downPressed && keyH.rightPressed) {
            x += speedDiag;
            y += speedDiag;
            direction = "right";
        } else if (keyH.downPressed && keyH.leftPressed) {
            x -= speedDiag;
            y += speedDiag;
            direction = "left";
        } else if (keyH.upPressed) {
            y -= speed;
            direction = "up";
        } else if (keyH.downPressed) {
            y += speed;
            direction = "down";
        } else if (keyH.leftPressed) {
            x -= speed;
            direction = "left";
        } else if (keyH.rightPressed)  {
            x += speed;
            direction = "right";
        }
        animationHandler();
    }

    public void draw(Graphics g) {
        g.fillRect(x, y, gp.xTile*3, gp.yTile*3);
        g.drawImage(hansel, x, y, gp.xTile*3, gp.yTile*3, null);
    }
}
