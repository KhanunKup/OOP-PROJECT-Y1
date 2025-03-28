package Main;

import java.awt.*;

public class Human{
    protected int worldX, worldY, speed, speedDiag;
    protected Rectangle hitbox;

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
