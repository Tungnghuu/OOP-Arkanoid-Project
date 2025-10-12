package entity;

import app.GameManager;

/** Lop dai dien cho bong. */
public class Ball extends MovableObject {
    /** Cac thuoc tinh. */
    private double speed;
    private int radius;
    public double angleOfAttack;
    private static boolean ballStuck = true;

    /** Constructor cua Ball.*/
    public Ball(double speed, int x, int y, int radius ) {
        super(x, y, radius * 2, radius * 2);
        this.speed = speed;
        this.radius = radius;

        //Math.random gives [0,1) then * PI / 2 gives [0, PI /2) then + PI / 4 gives [PI/4, (3PI) / 4) radian;
        //which is 45 degree to 135 degree
        angleOfAttack = Math.random() * Math.PI / 2 + Math.PI / 4;
        double Dx = speed * Math.cos(angleOfAttack);
        double Dy = speed * Math.sin(angleOfAttack);

        this.setDx(Dx);
        this.setDy(Dy);
    }

    /** copy Constructor cua Ball.*/
    public Ball(Ball other) {
        super(other.getDx(), other.getDy(), other.getX(), other.getY(),
                 other.radius * 2, other.radius * 2);
        this.speed = other.speed;
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
        ballY = paddle.getY() - paddle.getHeight() - 10;

        this.setX(ballX);
        this.setY(ballY);
    }

    public boolean isStuck() {
        return ballStuck;
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
        this.speed = 4;
        this.radius = 8;
        angleOfAttack = Math.random() * Math.PI / 2 + Math.PI / 4;
        double Dx = speed * Math.cos(angleOfAttack);
        double Dy = speed * Math.sin(angleOfAttack);

        this.setDx(Dx);
        this.setDy(Dy);
    }

    public void updateBall(GameManager gm) {
        int ballY = this.getY();
        int ballX = this.getX();
        ballY += this.getDy();
        ballX += this.getDx();
        this.setY(ballY);
        this.setX(ballX);

        if (ballY <= 0) {
            this.setY(0);
            this.setDy(-this.getDy());
        } else if (ballX >= 750) {
            this.setX(750);
            this.setDx(-this.getDx());
        } else if (ballX <= 0) {
            this.setX(0);
            this.setDx(-this.getDx());
        }

        if (ballY >= 576) {
            int lives = gm.getLives();
            lives -= 1;
            gm.setLives(lives);
        //    System.out.println("Lives: " + lives);
            resetBall();
        }
    }

    public void bounceOff(double Dx, double Dy) {
        double minAngle = 0.4;
        double maxAngle = Math.PI / 2 - minAngle;
        double angle = minAngle + Math.random() * (maxAngle - minAngle);

        //direction of Dx or Dy (-1 or 1) * speed * new Angle of Attack (0 < x < 90 degree);
        //min angle slightly bigger and so max angle slightly smaller 
        double newDx = Math.signum(Dx) * speed * Math.cos(angle);
        double newDy = Math.signum(Dy) * speed * Math.sin(angle);

        this.setDx(newDx);
        this.setDy(newDy);
    }
}
