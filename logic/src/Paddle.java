/** Lop dai dien cho thanh truot. */
public class Paddle extends MovableObject {

    /** Cac thuoc tinh gom.
     * speed : toc do di chuyen
     * currentPower: hieu ung hien tai hien tai
     */
    private int speed;

    /** constructor cua paddle.*/
    public Paddle(int dx, int dy, int speed,int x, int y, int height, int length) {
        super(dx, dy, x, y, height, length);
        this.speed = speed;
    }
    /** Copy Constructor cua paddle.*/
    public Paddle(Paddle other) {
        super(other.getDx(), other.getDy(), other.getX(), other.getY(),
                other.getHeight(), other.getWidth());
        this.speed = other.speed;
    }
    /** Cac phuong thuc di chuyen trai phai. */
    public void moveLeft() {
         int Px= getX() ; // Px la vi tri khoi tao cua paddle
         Px -= getDx() * speed; // di chuyen sang trai
         if (Px <= 0) {
             Px = 0; // xu li khi qua gioi han man hinh
         }
         setX(Px);
    }
    public void moveRight() {
        int Px = getX();
        Px += getDx() * this.speed; // di chuyen sang phai
        if (Px <= 768 - getWidth()) {
            Px = 768 - getHeight(); // xu li khi vuot gioi han man hinh,
            // 768 la kich thuoc chieu rong cua man
        }
        setX(Px);
    }

}
