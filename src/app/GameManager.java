package app;
// import java.util.ArrayList;
// import java.util.List;
import entity.*;

// import java.awt.*;

/** Lop quan ly game. */

public class GameManager {
    /**
     * cac thuoc tinh.
     */
    private Paddle paddle;
    private Ball ball;
    // private int score;
    // private int lives;

    /**
     * Constructor cua GameManager.
     */
    public GameManager() {
        paddle = new Paddle(1, 0, 5, 324, 526, 20, 120);
        ball = new Ball(5, 374, 506, 10);
        // this.score = 0;
        // this.lives = 3;
    }

    /** geter cua cac thuoc tinh.*/
    public Paddle getPaddle() {
        return new Paddle(this.paddle);
    }

    public Ball getBall() {
        return  new Ball(this.ball);
    }

    /** abcxyz
     * Thuat toan tham khao tu:
     * https://www.iostream.co/article/collision-detection-xet-va-cham-giua-hinh-tron-voi-hinh-chu-nhat-Diru1.
    */
    public boolean checkCollision(GameObject gameObject, Ball ball) {
        double centerBallX = ball.getX() + ball.getRadius();
        double centerBallY = ball.getY() + ball.getRadius();

        double closestX = centerBallX;
        double closestY = centerBallY;

        // X axis
        if (centerBallX < gameObject.getX()) {
            closestX = gameObject.getX();
        } else if (centerBallX > gameObject.getX() + gameObject.getWidth()) {
            closestX = gameObject.getX() + gameObject.getWidth();
        }

        // Y axis
        if (centerBallY < gameObject.getY()) {
            closestY = gameObject.getY();
        } else if (centerBallY > gameObject.getY() + gameObject.getHeight()) {
            closestY = gameObject.getY() + gameObject.getHeight();
        }

        double dx = centerBallX - closestX;
        double dy = centerBallY - closestY;
        double radius = ball.getRadius();

        if ((dx * dx + dy * dy) <= radius * radius) {
            if (Math.abs(dx) > Math.abs(dy)) {
                ball.bounceOff(-ball.getDx(), ball.getDy()); // hit left or right
            } else {
                ball.bounceOff(ball.getDx(), -ball.getDy()); // hit top or bottom
            }
        }

        return (dx * dx + dy * dy) <= radius * radius;
    }
}


