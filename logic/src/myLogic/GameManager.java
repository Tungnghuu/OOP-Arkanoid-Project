package myLogic;
import java.awt.*;
import java.util.ArrayList;
// import java.util.List;

// import java.awt.*;

import java.util.List;

/** Lop quan ly game. */

public class GameManager {
    /**
     * cac thuoc tinh.
     */
    private Paddle paddle;
    private Ball ball;
    private int score;
    private int lives = 3;

    /**
     * Constructor cua GameManager.
     */
    public GameManager() {
        paddle = new Paddle(1, 0, 5, 324, 526, 20, 120);
        ball = new Ball(5, 1, 1, 374, 506, 8);
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

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getScore() {
        return score;
    }

    // public boolean checkCollision(Paddle paddle, Ball ball) {
    //     double centerBallX = ball.getX() + (double)ball.getWidth() / 2;
    //     double centerBallY = ball.getY() + (double)ball.getHeight() / 2;

    //     double Ax = centerBallX;
    //     double Ay = centerBallY;

    //     if (centerBallX <= paddle.getX()) {
    //         Ax = paddle.getX();
    //     } else if (centerBallX >= paddle.getX() + paddle.getWidth()){
    //         Ax = paddle.getX() + paddle.getWidth();
    //     }

    //     if (centerBallY <= paddle.getY() - paddle.getHeight() + 10) {
    //         Ay = paddle.getY() + paddle.getHeight();
    //     } else if (centerBallY >= paddle.getY()) {
    //         Ay = paddle.getY();
    //     }

    //     double directionX = centerBallX - Ax;
    //     double directionY = centerBallY - Ay;

    //     return (directionX * directionX + directionY * directionY)
    //             < (double)(ball.getHeight() * ball.getHeight()) / 4;
    // }

    /** abcxyz
     * Thuat toan tham khao tu:
     * https://www.iostream.co/article/collision-detection-xet-va-cham-giua-hinh-tron-voi-hinh-chu-nhat-Diru1.
    */
    public boolean checkCollision(GameObject gameObject, Ball ball) {
        double centerBallX = ball.getX() + ball.getWidth() / 2.0;
        double centerBallY = ball.getY() + ball.getHeight() / 2.0;

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

        return (dx * dx + dy * dy) <= radius * radius;
    }

    public void updateIfCollision(Ball ball, Paddle paddle, List<List<Brick>> brickList) {
        if (checkCollision(paddle, ball)) {
            ball.setDy(-ball.getDy());
        }

        List<Brick> hitBricks = new ArrayList<>();

        for (int i = 0; i < brickList.size(); i++) {
            for (int j = 0; j < brickList.get(i).size(); j++) {
                Brick brick = brickList.get(i).get(j);
                if (checkCollision(brick, ball)) {
                    hitBricks.add(brick);
                    if (hitBricks.size() >= 2) break;
                }
            }
            if (hitBricks.size() >= 2) break;
        }

        if (!hitBricks.isEmpty()) {
            ball.setDy(-ball.getDy());
            for (Brick b : hitBricks) {
                b.takeHits();
                System.out.print("Hitpoints: " + b.getHitPoints());
                if (b.isDestroy()) {
                    this.score += 10;
              //      System.out.println("Score: " + this.score);
                }
            }

            for (List<Brick> row : brickList) {
                row.removeIf(Brick::isDestroy);
            }
        }
    }

}


