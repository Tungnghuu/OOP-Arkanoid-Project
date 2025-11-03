package logic.myLogic;
import logic.entity.GameObject;

public class Bullet extends GameObject  {

    private int speed;
    public Bullet (int x, int y, int width, int height, int speed) {
        super(x, y, width, height);
        this.speed = speed;
    }

    public int getSpeed() {
        return this.speed;
    }

    public void update() {
        int bulletY = this.getY();
        bulletY -= this.speed;
        this.setY(bulletY);
    }
}
