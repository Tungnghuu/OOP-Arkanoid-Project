package myLogic;

/** Class dai dien cho 1 vien gach. */
public class Brick  extends GameObject {

    /** Cac thuoc tinh cua Brick.
     * hitPoints la so lan de gach bi pha khi bong dap trung
     * type la loai gach
     * */
    private int hitPoints;
    private BrickType type;
    public final static int WIDTH = 70;
    public final static int HEIGHT = 30;

    /** Constructor cua Brick. */
    public Brick(int hitPoints, BrickType type, int x, int y) {
        super(x, y, HEIGHT, WIDTH);
        this.hitPoints = hitPoints;
        this.type = type;

    }

    /** getter va setter cua thuoc tinh private.*/
    public int getHitPoints() {
        return this.hitPoints;
    }

    public BrickType getType() {
        return this.type;
    }


    /**Phuong thuc giam so hitPoints khi bong va cham.
     * Neu hitPoins <= 0 ,gach bi pha
     */
    public void takeHits() {
        this.hitPoints--;
    }

    /** Phuong thuc kiem tra xem gach da bi pha chua. */
    public boolean isDestroy() {
        return this.hitPoints <= 0;
    }
}
