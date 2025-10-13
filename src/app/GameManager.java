package app;
// import java.awt.*;
import java.util.ArrayList;
// import java.util.List;
import entity.*;
import myLogic.BrickType;
import myLogic.PowerUp;
import myLogic.PowerUpType;

// import java.awt.*;

import java.util.List;
import java.util.Random;

/** Lop quan ly game. */

public class GameManager {
    /**
     * cac thuoc tinh.
     */
    private List<PowerUp> powerUpList = new ArrayList<>();
    private Paddle paddle;
    private Ball ball;
    private int score = 0;
    private int lives = 3;

    /**
     * Constructor cua GameManager.
     */
    public GameManager() {
        paddle = new Paddle(1, 0, 5, 324, 526, 15, 120);
        ball = new Ball(4, 374, 506, 8);
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

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getScore() {
        return score;
    }

    public List<PowerUp> getPowerUpList() {
        return powerUpList;
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

    public void updateIfCollision(Ball ball, Paddle paddle, List<List<Brick>> brickList) {

        checkCollision(paddle, ball);

        List<Brick> hitBricks = new ArrayList<>();

        for (int i = 0; i < brickList.size(); i++) {
            for (int j = 0; j < brickList.get(i).size(); j++) {
                Brick brick = brickList.get(i).get(j);
                if (checkCollision(brick, ball)) {
                    hitBricks.add(brick);
                }
            }
        }

        if (!hitBricks.isEmpty()) {
            for (Brick b : hitBricks) {
                b.takeHits();
                //System.out.print("Hitpoints: " + b.getHitPoints());
                if (b.isDestroy()) {
                    if (b.getType() == BrickType.EXPLOSIVE) {
                       PowerUp powerUp;
                       switch (new Random().nextInt(2)) {
                           case 0 -> powerUp = new PowerUp(b.getX(), b.getY(), 15, 15, PowerUpType.EXPAND_PADDLE);
                           case 1 -> powerUp = new PowerUp(b.getX(), b.getY(), 15, 15, PowerUpType.SHRINK_PADDLE);
                           default -> powerUp = new PowerUp(b.getX(), b.getY(), 15, 15, PowerUpType.EXPAND_PADDLE);
                       }
                        powerUpList.add(powerUp);
                    }
                    switch (b.getType()) {
                        case NORMAL:
                            score += 10;
                            break;
                        case STRONG:
                            score += 20;
                            break;
                        case EXPLOSIVE:
                            score += 50;
                            break;
                    }
                    //System.out.println("Score: " + this.score);
                }
            }

            for (List<Brick> row : brickList) {
                row.removeIf(Brick::isDestroy);
            }
        }
    }
}


