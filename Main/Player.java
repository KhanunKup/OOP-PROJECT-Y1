package Main;

import javax.swing.*;
import java.awt.*;

public class Player {
//    final int animDelay = 10;
//    int currentFrame = 0;
//    int

    int x, y, speed;
    KeyHandler keyH;
    GamePanel gp;
    Image hansel;
    Image idlesAnim[];

    public Player(GamePanel gamePanel, KeyHandler keyH) {
        this.gp = gamePanel;
        this.keyH = keyH;
        valuesSetting();
        playerLoading();
    }

    public void valuesSetting() {
        x = gp.mapX / 2;
        y = gp.mapY / 2;
        speed = 5;
        idlesAnim = new Image[9];
    }

    public void playerLoading() {
        for (int i = 0; i <= 8; i++) { //Storing player.png in an array
            String path = "res/Character/Hansel/Hansel_Idle/hansel_idle"+(i+1)+".png";
            idlesAnim[i] = new ImageIcon(path).getImage();
        }
        hansel = new ImageIcon("res/Character/Hansel/Hansel_Idle/hansel_idle1.png").getImage();
    }

    public void animationHandler() {

    }

    public void update() {
        int speedDiag = (int) (speed/Math.sqrt(2));
        if (keyH.upPressed && keyH.rightPressed) {
            x += speedDiag;
            y -= speedDiag;
        } else if (keyH.upPressed && keyH.leftPressed) {
            x -= speedDiag;
            y -= speedDiag;
        } else if (keyH.downPressed && keyH.rightPressed) {
            x += speedDiag;
            y += speedDiag;
        } else if (keyH.downPressed && keyH.leftPressed) {
            x -= speedDiag;
            y += speedDiag;
        } else if (keyH.upPressed) {
            y -= speed;
        } else if (keyH.downPressed) {
            y += speed;
        } else if (keyH.leftPressed) {
            x -= speed;
        } else if (keyH.rightPressed)  {
            x += speed;
        }
        animationHandler();
    }

    public void draw(Graphics g) {
        g.fillRect(x, y, gp.xTile*3, gp.yTile*3);
        g.drawImage(hansel, x, y, gp.xTile*3, gp.yTile*3, null);
    }
}
