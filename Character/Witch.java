package Character;

import Main.GamePanel;
import Main.ImageManager;

import java.awt.*;

public class Witch extends Human {
    int screenX;
    int screenY;

    String v2WalkingAnimRight[];

    public static int worldX, worldY, speed, speedDiag;

    public Witch(GamePanel gp, ImageManager imageManager) {
        this.gp = gp;
        playerSize = gp.tileSize * 6;
        this.imageManager = imageManager;
        state = "walking";
        animDirection = "right";
        playerLoading(imageManager);
    }

    public void playerLoading(ImageManager imageManager) {
        idleAnimRight = new String[20]; //-> v1
        walkingAnimRight = new String[6];
        v2WalkingAnimRight = new String[8];

        for (int i = 0; i < 20; i++) {
            idleAnimRight[i] = "res/Character/Witch/WitchV1Walk/Idle/witchv1_idle_right"+(i+1)+".png";
        }
        imageManager.setImage("witchIdleAnimRight", idleAnimRight);

        for (int i = 0; i < 6; i++) {
            walkingAnimRight[i] = "res/Character/Witch/WitchV1Walk/Walk/witchv1_walking_right"+(i+1)+".png";
        }
        imageManager.setImage("witchWalkingAnimRight", walkingAnimRight);

        for (int i = 0; i < 6; i++) {
            v2WalkingAnimRight[i] = "res/Character/Witch/WitchV1Walk/Walk/witchv1_walking_right"+(i+1)+".png";
        }
        imageManager.setImage("witchv2WalkingAnimRight", walkingAnimRight);
    }

    public void animHandler(ImageManager imageManager) {
        currentFrame++;
        if (currentFrame >= walkAnimDelay) {
            currentFrame = 0;
            currentIdleFrame++;
        }

        if (state.equals("idle")) {
            if (currentIdleFrame > walkingAnimRight.length-1) {
                currentIdleFrame = 0;
            }
            if (animDirection.equals("left")) {
//                animImg = imageManager.getImages("witchIdleAnimLeft");
            } else if (animDirection.equals("right")) {
                animImg = imageManager.getImages("witchIdleAnimRight");
            }

        } else if (state.equals("walking")) {
            if (currentIdleFrame > walkingAnimRight.length-1) {
                currentIdleFrame = 0;
            }
            if (animDirection.equals("left")) {
//                animImg = imageManager.getImages("witchWalkingAnimLeft");
            } else if (animDirection.equals("right")) {
                animImg = imageManager.getImages("witchWalkingAnimRight");
            }
        }
    }

    public void update(ImageManager imageManager) {
        animHandler(imageManager);
    }

    public void draw(Graphics g, int x, int y) {
        g.drawImage(animImg[currentIdleFrame], x, y, playerSize, playerSize, null);
    }

}
