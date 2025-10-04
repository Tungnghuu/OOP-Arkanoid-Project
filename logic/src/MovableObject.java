/** Lop dai dien cho cac doi tuong di chuyen duoc. */
public abstract  class MovableObject extends GameObject {
    /** cac thuoc tinh cua MovableObject. */
    private int dx;
    private int dy;

    /** Constructor cua MovableObject. */
    public MovableObject(int dx, int dy,int x, int y, int height, int length) {
        super(x,y,height, length);
        this.dx = dx;
        this.dy = dy;
    }

    public int getDx() {
        return this.dx;
    }

    public int getDy() {
        return this.dy;
    }
}
