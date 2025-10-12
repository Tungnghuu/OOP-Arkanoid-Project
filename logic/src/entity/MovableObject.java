package entity;

// import java.awt.*;

/** Lop dai dien cho cac doi tuong di chuyen duoc. */
public abstract  class MovableObject extends GameObject {
    /** cac thuoc tinh cua MovableObject. */
    private double dx;
    private double dy;

    /** Constructor cua MovableObject. */
    public MovableObject(double dx, double dy, int x, int y, int height, int length) {
        super(x,y,height, length);
        this.dx = dx;
        this.dy = dy;
    }

    public MovableObject(int x, int y, int height, int length) {
        super(x, y, height, length);
    }

    public double getDx() {
        return this.dx;
    }

    public double getDy() {
        return this.dy;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

}
