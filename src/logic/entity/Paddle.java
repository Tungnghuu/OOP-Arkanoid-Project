package logic.entity;

import logic.entity.PowerUp;
import logic.myLogic.PowerUpType;
import app.SoundManager;

/** Lop dai dien cho thanh truot. */
public class Paddle extends MovableObject {
    /** Cac thuoc tinh gom.

    /** constructor cua paddle.*/
    public Paddle(int x, int y, double dx, double dy, int width, int height, double speed) {
        super(x, y, dx, dy, width, height, speed);
    }

    /** Copy Constructor cua paddle.*/
    public Paddle(Paddle other) {
        super (other.getX(), other.getY(), other.getDx(), other.getDy(),
                other.getWidth(),  other.getHeight(), other.getSpeed());
    }

    /** Cac phuong thuc di chuyen trai phai. */
    public void moveLeft() {
        int Px = getX() ; // Px la vi tri khoi tao cua paddle
        Px -= getDx() * this.getSpeed(); // di chuyen sang trai
        if (Px <= 0) {
            Px = 0; // xu li khi qua gioi han man hinh
        }
        setX(Px);
    }

    public void moveRight() {
        int Px = getX();
        Px += getDx() * this.getSpeed(); // di chuyen sang phai
        if (Px >= 768 - getWidth()) {
            Px = 768 - getWidth(); // xu li khi vuot gioi han man hinh,
            // 768 la kich thuoc chieu rong cua man
        }
        setX(Px);
    }

    public void resetPaddle() {
        this.setX(324);
        this.setY(526);
        this.setWidth(80);
        this.setSpeed(5);
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

    private SoundManager soundManager;
    private boolean ballHitPaddle = false;

    public void setSoundManager(SoundManager sm) {
        this.soundManager = sm;
    }

    public void checkBallCollision(Ball ball) {
        boolean collision = this.getBounds().intersects(ball.getBounds());
        
        if (collision && !ballHitPaddle) {
            if (soundManager != null) {
                soundManager.playSFX(7);
            }
            ballHitPaddle = true;
        } else if (!collision) {
            ballHitPaddle = false;
        }
    }
}
