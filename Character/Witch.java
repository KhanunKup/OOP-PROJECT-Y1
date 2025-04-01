package Character;

import Main.GamePanel;
import Main.ImageManager;

import java.awt.*;

public class Witch extends Human {
    int screenX;
    int screenY;

    boolean resetAnimFrame = false;
    boolean isTransformFinised = false;

    String v2WalkingAnimRight[];
    String transformAnim[];

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
        idleAnimRight = new String[9]; //-> v1
        walkingAnimRight = new String[6];
        v2WalkingAnimRight = new String[8];
        transformAnim = new String[61];

        for (int i = 0; i < 9; i++) {
            idleAnimRight[i] = "res/Character/Witch/WitchV1Walk/Idle/witchv1_idle_right"+(i+1)+".png";
        }
        imageManager.setImage("witchIdleAnimRight", idleAnimRight);

        for (int i = 0; i < 6; i++) {
            walkingAnimRight[i] = "res/Character/Witch/WitchV1Walk/Walk/witchv1_walking_right"+(i+1)+".png";
        }
        imageManager.setImage("witchWalkingAnimRight", walkingAnimRight);

        for (int i = 0; i < 8; i++) {
            v2WalkingAnimRight[i] = "res/Character/Witch/WitchV2Walk/witchv2_walking_right"+(i+1)+".png";
        }
        imageManager.setImage("witchv2WalkingAnimRight", v2WalkingAnimRight);

        for (int i = 0 ; i < 61; i++) {
            transformAnim[i] = "res/Character/Witch/WitchTransformed/witchtransform"+(i+1)+".png";
        }
        imageManager.setImage("transformAnim", transformAnim);
    }

    public void animHandler(ImageManager imageManager) {
        currentFrame++;
        if (currentFrame >= walkAnimDelay) {
            currentFrame = 0;
            currentAnimFrame++;
        }

        if (state.equals("idle")) {
            if (currentAnimFrame > walkingAnimRight.length-1) {
                currentAnimFrame = 0;
            }
            animImg = imageManager.getImages("witchIdleAnimRight");

        } else if (state.equals("walking")) {
            if (currentAnimFrame > walkingAnimRight.length-1) {
                currentAnimFrame = 0;
            }
            animImg = imageManager.getImages("witchWalkingAnimRight");

        } else if (state.equals("transform")) {
            if (!resetAnimFrame) {
                currentAnimFrame = 0;
                currentFrame = 0;
                resetAnimFrame = true;
            }

            if (currentAnimFrame > transformAnim.length-1) {
                isTransformFinised = true;
                currentFrame = 0;
                currentAnimFrame = 0;
            }
            animImg = imageManager.getImages("transformAnim");

        } else if (state.equals("running")) {
            if (currentAnimFrame > v2WalkingAnimRight.length-1) {
                currentAnimFrame = 0;
            }
            animImg = imageManager.getImages("witchv2WalkingAnimRight");
        }
    }

    public void update(ImageManager imageManager) {
        if (gp.currentTileMap == gp.tileMap6) {
            state = "idle";
        } else if (gp.currentTileMap == gp.tileMap5) {
            state = "walking";
            if (gp.player.worldX >= 1500 && !isTransformFinised) {
                state = "transform";
            } else if (isTransformFinised) {
                state = "running";
            }
        }
        animHandler(imageManager);
    }

    public void draw(Graphics g, int x, int y) {
        g.drawImage(animImg[currentAnimFrame], x, y, playerSize, playerSize, null);
    }

}
