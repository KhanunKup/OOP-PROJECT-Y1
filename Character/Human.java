package Character;

import Main.GamePanel;
import Main.ImageManager;

import java.awt.*;

public class Human {
    protected GamePanel gp;
    public int worldX, worldY, speed, speedDiag;
    public Rectangle hitbox;
    public int playerSize;

    public String direction, animDirection, state;
    public boolean isCollisionOn = true;

    protected ImageManager imageManager;

    protected final int walkAnimDelay = 7;
    protected int currentFrame = 0;
    protected int currentIdleFrame = 0;

    protected String[] idleAnimLeft;
    protected String[] idleAnimRight;
    protected String[] walkingAnimLeft;
    protected String[] walkingAnimRight;
    protected Image[] animImg;

    public void setSpeed(int speed) {
        this.speed = speed;
        this.speedDiag = (int) (speed / Math.sqrt(2));
    }

    public void setWorldY(int worldY) {
        this.worldY = worldY;
    }

    public void setWorldX(int worldX) {
        this.worldX = worldX;
    }

    public int getWorldX() {
        return worldX;
    }

    public int getWorldY() {
        return worldY;
    }

    public int getSpeed() {
        return speed;
    }

    public void setHitbox(int x, int y, int width, int height) {
        this.hitbox = new Rectangle(x, y, width, height);
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

}
