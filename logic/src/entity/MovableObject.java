package entity;

/** Lop dai dien cho cac doi tuong di chuyen duoc. */
public abstract  class MovableObject extends GameObject {
    /** cac thuoc tinh cua MovableObject. */
    private double dx;
    private double dy;
    private double speed;

    /** Constructor cua MovableObject. */
    public MovableObject(int x, int y, double dx, double dy, int width, int height, double speed) {
        super(x, y, width, height);
        this.dx = dx;
        this.dy = dy;
        this.speed = speed;
    }

    public MovableObject(int x, int y, int width, int height) {
        super(x, y, width, height);
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

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
