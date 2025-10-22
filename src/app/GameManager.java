package app;

// import java.awt.*;
import java.util.ArrayList;
import java.util.List; 
import java.util.Random;

import entity.*;
import myLogic.BrickType;
import myLogic.PowerUp;
import myLogic.PowerUpType;

/** Lop quan ly game. */
public class GameManager {
    /**
     * cac thuoc tinh.
     */
    private List<PowerUp> powerUpList = new ArrayList<>();
    private Paddle paddle;
    private Ball ball;
    private int score;
    private int lives;
    private double dropChance;

    /**
     * Constructor cua GameManager.
     */
    public GameManager() {
        paddle = new Paddle(1, 0, 5, 324, 526, 15, 100);
        ball = new Ball(4, 374, 506, 1, 1, 8);
        this.score = 0;
        this.lives = 3;
        this.dropChance = 0.4;
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

        /*
        if ((dx * dx + dy * dy) <= radius * radius) {
            if (Math.abs(dx) > Math.abs(dy)) {
                ball.bounceOff(-ball.getDx(), ball.getDy()); // hit left or right
            } else {
                ball.bounceOff(ball.getDx(), -ball.getDy()); // hit top or bottom
            }
        }
        */

        return (dx * dx + dy * dy) <= radius * radius;
    }

    public void updateIfCollision(Ball ball, Paddle paddle, List<List<Brick>> brickList) {
        if (checkCollision(paddle, ball)) {
            handlePaddleCollision(ball, paddle);
        }
        List<Brick> hitBricks = new ArrayList<>();
        outer:
        for (int i = 0; i < brickList.size(); i++) {
            List<Brick> row = brickList.get(i);
            for (int j = 0; j < brickList.get(i).size(); j++) {
                Brick brick = row.get(j);
                if (checkCollision(brick, ball)) {
                    hitBricks.add(brick);
                    handleBrickCollision(ball, brick);
                    brick.takeHits();
                    break outer; // Thoát vòng lặp
                }
            }
        }

        if (!hitBricks.isEmpty()) {
            for (Brick b : hitBricks) {
                b.takeHits();
                if (b.isDestroy()) {
                    createPowerUp(b);
                    addScore(b);
                }
            }

            for (List <Brick> row : brickList) {
                row.removeIf(Brick :: isDestroy);
            }
        }
    }

    public void createPowerUp(Brick brick) {
        if (brick.getType() == BrickType.EXPLOSIVE) {
            PowerUp powerUp;
            if (Math.random() < this.dropChance) {
                switch (new Random().nextInt(2)) {
                    case 0 -> powerUp = new PowerUp(brick.getX(), brick.getY(), 15, 15, PowerUpType.EXPAND_PADDLE);
                    case 1 -> powerUp = new PowerUp(brick.getX(), brick.getY(), 15, 15, PowerUpType.SHRINK_PADDLE);
                    default -> powerUp = new PowerUp(brick.getX(), brick.getY(), 15, 15, PowerUpType.EXPAND_PADDLE);
                }
                powerUpList.add(powerUp);
            }
        }
    }

    public void addScore(Brick brick) {
        switch (brick.getType()) {
            case NORMAL:
                score += 10;
                break;
            case STRONG:
                score += 20;
                break;
            case EXPLOSIVE:
                score += 50;
                break;
            case BONUS:
                //TODO: Handle BONUS brick type if needed
                break;
            case UNBREAKABLE:
                //TODO: Handle UNBREAKABLE brick type if needed
                break;
        }
    }

    /** Xu li va cham paddle va ball.*/
    public void handlePaddleCollision(Ball ball, Paddle paddle) {
        double ballCenterX = ball.getX() + ball.getRadius();
        double paddleCenterX = paddle.getX() + paddle.getWidth() / 2.0;

        double distance = ballCenterX - paddleCenterX;
        double normalizeDistance = distance / (paddle.getWidth() / 2.0);

        double maxAngle = Math.toRadians(60);
        double bounceAngle = maxAngle * normalizeDistance;

        double speed = ball.getSpeed();
        double newDx =  speed * Math.sin(bounceAngle);
        double newDy = -speed * Math.cos(bounceAngle);

        ball.setDx(newDx);
        ball.setDy(newDy);

    }

    /** Xu li va cham bong va gach.*/
    public void handleBrickCollision(Ball ball, Brick brick) {
        double ballCenterX = ball.getX() + ball.getRadius();
        double ballCenterY = ball.getY() + ball.getRadius();
        double brickCenterX = brick.getX() + brick.getWidth() / 2.0;
        double brickCenterY = brick.getY() + brick.getHeight() / 2.0;

        double dx = ballCenterX - brickCenterX;
        double dy = ballCenterY - brickCenterY;

        double overlapX = (brick.getWidth() / 2.0 + ball.getRadius()) - Math.abs(dx);
        double overlapY = (brick.getHeight() / 2.0 + ball.getRadius()) - Math.abs(dy);

        if (overlapX < overlapY) {
            // Va cham trai hoac phai
            ball.setDx(-ball.getDx());
        } else {
            // Va cham tren hoac duoi
            ball.setDy(-ball.getDy());
        }
    }

    // Reset game
    public void resetGame(boolean resetScore) {
        this.lives = 3;
        if (resetScore) this.score = 0;
        this.powerUpList.clear();
    }

    // Khởi tạo lại thanh đỡ và bóng
    public Paddle newDefaultPaddle() {
        return new Paddle(1, 0, 5, 324, 526, 15, 100);
    }
    public Ball newDefaultBall() {
        return new Ball(4, 374, 506, 1, 1, 8);
    }
}


