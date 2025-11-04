package logic.entity;

import app.GameManager;
import app.SoundManager;

/** Lop dai dien cho bong. */
public class Ball extends MovableObject {
    /** Cac thuoc tinh. */
    private int radius;
    public double angleOfAttack;
    private boolean ballStuck = true;
    private SoundManager soundManager;

    /** Constructor cua Ball.*/
    public Ball(int x, int y, double dx, double dy, int radius, double speed) {
        super(x, y, dx, dy, radius * 2, radius * 2, speed);
        this.radius = radius;

        int rand = (int) (Math.random() * 2); // rand is either 0 or 1
        if (rand == 0) {
            this.setDx(0.1);
        } else {
            this.setDx(-0.1);
        }
        this.setDy(speed);
    }

    /** copy Constructor cua Ball.*/
    public Ball(Ball other) {
        super(other.getX(), other.getY(), other.getDx(), other.getDy(),
                other.radius * 2, other.radius * 2, other.getSpeed());
        this.radius = other.radius;
    }

    public int getRadius() {
        return this.radius;
    }


    /**
     * cho bong di chuyen theo paddle khi chua bat dau.
     */
    public void BallFollowPaddle(Paddle paddle) {
        int ballX = this.getX();
        int ballY = this.getY();

        ballX = paddle.getX() + paddle.getWidth() / 2 - this.radius;
        ballY = paddle.getY() - 2 * this.radius;

        this.setX(ballX);
        this.setY(ballY);
    }

    public boolean isStuck() {
        return ballStuck;
    }
    public void setBallStuck (boolean isStuck) {
        this.ballStuck = isStuck;
    }

    /**
     * Cho bong bat dau bay .
     */
    public void startBall() {
        ballStuck = false;
    }

    public void resetBall() {
        ballStuck = true;
        this.setX(374);
        this.setY(506);
        this.setSpeed(4);
        this.radius = 8;

        int rand = (int) (Math.random() * 2); // rand is either 0 or 1
        if (rand == 0) {
            this.setDx(0.1);
        } else {
            this.setDx(-0.1);
        }
        this.setDy(this.getSpeed());
    }

    public void setSoundManager(SoundManager sm) {
        this.soundManager = sm;
    }

    public void updateBall(GameManager gm) {
        double ballY = this.getY();
        double ballX = this.getX();
        ballY += this.getDy() * this.getSpeed();
        ballX += this.getDx() * this.getSpeed();
        this.setY((int)ballY);
        this.setX((int)ballX);

        if (ballY <= 0) {
            this.setY(0);
            this.setDy(-this.getDy());
            if (soundManager != null) {
                soundManager.playSFX(9);
            }
        } else if (ballX >= 750) {
            this.setX(750);
            this.setDx(-this.getDx());
            if (soundManager != null) {
                soundManager.playSFX(9);
            }
        } else if (ballX <= 0) {
            this.setX(0);
            this.setDx(-this.getDx());
            if (soundManager != null) {
                soundManager.playSFX(9);
            }
        }
    }


    public void updateLives(GameManager gm) {
        double ballY = this.getY();
        if (ballY >= 576) {
            int lives = gm.getLives();
            lives -= 1;
            gm.setLives(lives);
            resetBall();
        }
    }

}
