/** Lop dai dien cho cac doi tuong cua game. **/
public  abstract class GameObject {
    /** cac thuoc tinh cua GameObject. */
    private int x;
    private int y;
    private int height;
    private int width;


    public int getX() {
        return x;
    }

    public  int getY() {
        return y;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
    /** Constructor cua GameObjaect. */
    public GameObject(int x, int y, int height, int width) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
    }

    /** Phuong thu cap nhat doi tuong. */
    public abstract void update();

}
