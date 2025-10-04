/** Lop dai dien cho bong. */
public class Ball extends MovableObject {
    /** Cac thuoc tinh. */
    private int speed;
    private int radius;

    /** Constructor cua Ball.*/
    public Ball(int speed, int dx, int dy, int x, int y, int radius ) {
        super(x, y, dx, dy, radius * 2, radius * 2);
        this.speed = speed;
        this.radius = radius;
    }

    /** copy Constructor cua Ball.*/
    public Ball(Ball other) {
        super(other.getX(), other.getY(), other.getDx(),
                other.getDy(), other.radius * 2, other.radius * 2);
        this.speed = other.speed;
        this.speed = other.radius;
    }
    //public void bouceOff() {}
    //public boolean checkCollision {}
}
