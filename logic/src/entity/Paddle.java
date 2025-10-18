package entity;

import myLogic.PowerUp;
import myLogic.PowerUpType;

/** Lop dai dien cho thanh truot. */
public class Paddle extends MovableObject {

    /** Cac thuoc tinh gom.
     * speed : toc do di chuyen
     * currentPower: hieu ung hien tai hien tai
     */
    private double speed;

    /** constructor cua paddle.*/
    public Paddle(double dx, double dy, double speed, int x, int y, int height, int length) {
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
        int Px = getX() ; // Px la vi tri khoi tao cua paddle
        Px -= getDx() * speed; // di chuyen sang trai
        if (Px <= 0) {
            Px = 0; // xu li khi qua gioi han man hinh
        }
        setX(Px);
    }

    public void moveRight() {
        int Px = getX();
        Px += getDx() * this.speed; // di chuyen sang phai
        if (Px >= 768 - getWidth()) {
            Px = 768 - getWidth(); // xu li khi vuot gioi han man hinh,
            // 768 la kich thuoc chieu rong cua man
        }
        setX(Px);
    }

    public void resetPaddle() {
        this.setX(324);
        this.setY(526);
        this.speed = 5;
    }

    public void applyPowerUp(PowerUp powerUp) {
        int paddleWidth = this.getWidth();
        if (powerUp.getType() == PowerUpType.EXPAND_PADDLE) {
            paddleWidth += 20;
        } else if (powerUp.getType() == PowerUpType.SHRINK_PADDLE) {
            paddleWidth -= 40;
        }
        this.setWidth(paddleWidth);
        System.out.println("Chieu dai paddle: " + this.getWidth());
    }

    public void endPowerUp(PowerUp powerUp) {
        this.setWidth(100);
        System.out.println("Do dai paddle: " + this.getWidth());
    }

}
