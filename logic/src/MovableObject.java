/** Lop dai dien cho cac doi tuong di chuyen duoc. */
public abstract  class MovableObject {
    /** cac thuoc tinh cua MovableObject. */
    private int dx;
    private int dy;

    /** Constructor cua MovableObject. */
    public MovableObject(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public int getDx() {
        return this.dx;
    }

    public int getDy() {
        return this.dy;
    }

    /** Phuong thuc dai dien cho di chuyen cua cac doi tuong. */
    public abstract void move();
}
