package myLogic;
import java.util.ArrayList;
import java.util.List;

import java.awt.*;

/** Lop quan ly game. */

public class GameManager {
    /**
     * cac thuoc tinh.
     */
    private Paddle paddle;
    private Ball ball;
    private int score;
    private int lives;

    /**
     * Constructor cua GameManager.
     */
    public GameManager() {
        paddle = new Paddle(1, 0, 5, 324, 526, 20, 120);
        ball = new Ball(5, 1, 1, 374, 506, 10);
        this.score = 0;
        this.lives = 3;
    }

    /** geter cua cac thuoc tinh.*/
    public Paddle getPaddle() {
        return new Paddle(this.paddle);
    }

    public Ball getBall() {
        return  new Ball(this.ball);
    }

    public boolean checkCollision(Paddle paddle, Ball ball) {
        double centerBallX = ball.getX() + (double)ball.getWidth()/2;
        double centerBallY = ball.getY() + (double)ball.getHeight()/2;

        double Ax = centerBallX;
        double Ay = centerBallY;

        if (centerBallX <= paddle.getX()) {
            Ax = paddle.getX();
        } else if (centerBallX >= paddle.getX() + paddle.getWidth()){
            Ax = paddle.getX() + paddle.getWidth();
        }

        if (centerBallY <= paddle.getY() - paddle.getHeight() + 10) {
            Ay = paddle.getY() + paddle.getHeight();
        } else if (centerBallY >= paddle.getY()) {
            Ay = paddle.getY();
        }

        double directionX = centerBallX - Ax;
        double directionY = centerBallY - Ay;

        return (directionX * directionX + directionY * directionY)
                < (double)(ball.getHeight() * ball.getHeight())/4;
    }

    public boolean checkCollision(Brick brick, Ball ball) {

        /** Thuat toan tham khao tu https://www.iostream.co/article/collision-detection
         -xet-va-cham-giua-hinh-tron-voi-hinh-chu-nhat-Diru1.*/
        double centerBallX = ball.getX() + (double)ball.getWidth()/2;
        double centerBallY = ball.getY() + (double)ball.getHeight()/2;

        double Ax = centerBallX;
        double Ay = centerBallY;

        if (centerBallX <= brick.getX()) {
            Ax = brick.getX();
        } else if (centerBallX >= brick.getX() + brick.getWidth()){
            Ax = brick.getX() + brick.getWidth();
        }

        if (centerBallY <= brick.getY() - brick.getHeight() + 10) {
            Ay = brick.getY() + brick.getHeight();
        } else if (centerBallY >= brick.getY()) {
            Ay = brick.getY();
        }

        double directionX = centerBallX - Ax;
        double directionY = centerBallY - Ay;

        return (directionX * directionX + directionY * directionY)
                < (double)(ball.getHeight() * ball.getHeight())/4;
    }


}


