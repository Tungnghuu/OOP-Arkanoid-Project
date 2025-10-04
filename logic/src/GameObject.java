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


    /** getter  cua y.*/
    public  int getY() {
        return y;
    }


    /** getter  cua height.*/
    public int getHeight() {
        return height;
    }

    /** getter cua width.*/
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
    //public abstract void update();

}
