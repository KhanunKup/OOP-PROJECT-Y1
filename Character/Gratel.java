package Character;

import Main.CollisionChecker;
import Main.GamePanel;
import Main.ImageManager;

import javax.swing.text.DefaultCaret;
import java.awt.*;

public class Gratel extends Human {
    int screenX;
    int screenY;

    CollisionChecker collisionChecker;

    public static int worldX, worldY, speed, speedDiag;

    public Gratel(GamePanel gp, ImageManager imageManager) {
        this.gp = gp;
        playerSize = gp.tileSize * 2;
        this.imageManager = imageManager;
        state = "idle";
        animDirection = "right";
        playerLoading(imageManager);
        this.setHitbox((gp.tileSize * 2 - 24) / 2 , (gp.tileSize * 2 - 24) / 2 , 24 , 24);
    }

    public void playerLoading(ImageManager imageManager) {
        idleAnimLeft = new String[12];
        idleAnimRight = new String[12];
        walkingAnimLeft = new String[8];
        walkingAnimRight = new String[8];
        for (int i = 0; i < 9; i++) { //Storing player.png in an array
            idleAnimLeft[i] = "res/Character/Gratel/Gratel_Idle/gratel_idle_left"+(i+1)+".png";
        }
        imageManager.setImage("gratelIdleAnimLeft", idleAnimLeft);

        for (int i = 0; i < 9; i++) {
            idleAnimRight[i] = "res/Character/Gratel/Gratel_Idle/gratel_idle_right"+(i+1)+".png";
        }
        imageManager.setImage("gratelIdleAnimRight", idleAnimRight);

        for (int i = 0; i < 8; i++) {
            walkingAnimLeft[i] = "res/Character/Gratel/Gratel_Walking/gratel_walking_left"+(i+1)+".png";
        }
        imageManager.setImage("gratelWalkingAnimLeft", walkingAnimLeft);

        for (int i = 0; i < 8; i++) {
            walkingAnimRight[i] = "res/Character/Gratel/Gratel_Walking/gratel_walking_right"+(i+1)+".png";
        }
        imageManager.setImage("gratelWalkingAnimRight", walkingAnimRight);
    }

    public void animHandler(ImageManager imageManager) {
        currentFrame++;
        if (currentFrame >= walkAnimDelay) {
            currentFrame = 0;
            currentIdleFrame++;
        }

        if (state.equals("idle")) {
            if (currentIdleFrame > walkingAnimLeft.length-1) {
                currentIdleFrame = 0;
            }
            if (animDirection.equals("left")) {
                animImg = imageManager.getImages("gratelIdleAnimLeft");
            } else if (animDirection.equals("right")) {
                animImg = imageManager.getImages("gratelIdleAnimRight");
            }

        } else if (state.equals("walking")) {
            if (currentIdleFrame > walkingAnimLeft.length-1) {
                currentIdleFrame = 0;
            }
            if (animDirection.equals("left")) {
                animImg = imageManager.getImages("gratelWalkingAnimLeft");
            } else if (animDirection.equals("right")) {
                animImg = imageManager.getImages("gratelWalkingAnimRight");
            }
        }
    }

    public void update(ImageManager imageManager) {
        animHandler(imageManager);
    }

    public void draw(Graphics g, int x, int y) {
        g.drawImage(animImg[currentIdleFrame], x, y, playerSize, playerSize, null);
        g.drawRect(x + hitbox.x, y + hitbox.y, hitbox.width, hitbox.height);
    }

    public void stateUpdate(String state, String animDirection) {
        this.state = state;
        this.animDirection = animDirection;
    }

}
