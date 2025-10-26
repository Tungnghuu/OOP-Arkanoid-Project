package entity;

import java.awt.*;

/** Lop dai dien cho cac doi tuong cua game. **/
public  abstract class GameObject {
    /** cac thuoc tinh cua GameObject. */
    private int x;
    private int y;
    private int height;
    private int width;

    /** getter  cua x.*/
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    /** getter  cua y.*/
    public  int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    /** getter  cua height.*/
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    /** getter cua width.*/
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    /** Constructor cua GameObjaect. */
    public GameObject(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
    }

    public Rectangle getBounds() {
        return new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }
}
