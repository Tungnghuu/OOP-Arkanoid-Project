/** Class dai dien cho 1 vien gach. */
public class Brick {

    /** Cac thuoc tinh cua Brick.
     * hitPoints la so lan de gach bi pha khi bong dap trung
     * type la loai gach
     * */
    private int hitPoints;
    private String type;

    /** Constructor cua Brick. */
    public Brick(int hitPoints, String type) {
        this.hitPoints = hitPoints;
        this.type = type;
    }

    public int getHitPoints() {
        return this.hitPoints;
    }

    public String getType() {
        return this.type;
    }

    /**Phuong thuc giam so hitPoints khi bong va cham.
     * Neu hitPoins <= 0 ,gach bi pha
     */
    //public void takeHits() {}

    /** Phuong thuc kiem tra xem gach da bi pha chua. */
    public boolean isDestroy() {
        return this.hitPoints <= 0;
    }
}
