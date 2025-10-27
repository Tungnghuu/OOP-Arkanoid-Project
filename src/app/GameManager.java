package app;

// import java.awt.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import logic.entity.*;
import logic.myLogic.*;

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
   // private double dropChance;

    /**
     * Constructor cua GameManager.
     */
    public GameManager() {
        paddle = new Paddle(324, 526, 1, 0, 80, 15, 5);
        ball = new Ball(374, 506, 1, 1, 8, 5);
        this.score = 0;
        this.lives = 3;
      //  this.dropChance = 0.4;
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

    // kiem tra va cham.
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
        boolean collided = false;
        // tim gach va cham
        for (List<Brick> row : brickList) {
            Iterator<Brick> it = row.iterator();
            while(it.hasNext()) {
                Brick brick = it.next();

                if (checkCollision(brick, ball)) {
                    handleBrickCollision(ball, brick);
                    hitBricks.add(brick);
                    collided = true;
                    break;
                }
            }
            if (collided) break;
        }

        // tao powerUp va tang diem
        for (Brick b : hitBricks) {
            b.takeHits();
            if (b.isDestroy()) {
                createPowerUp(b);
                addScore(b);
            }
        }

        // xoa gach
        for (List<Brick> row : brickList) {
            Iterator<Brick> it = row.iterator();
            while (it.hasNext()) {
                Brick b = it.next();
                if (b.isDestroy()) {
                    it.remove();
                }
            }
        }
      //  hitBricks.clear();

        for (PowerUp p : powerUpList) {
            if (p.getBounds().intersects(paddle.getBounds())) {
                p.setRenderPowerUp(false);
            }
        }
    }

    public void createPowerUp(Brick brick) {
        if (brick.getType() == BrickType.BONUS) {
            PowerUp powerUp;
            switch (new Random().nextInt(3)) {
                case 0 -> powerUp = new PowerUp(brick.getX(), brick.getY(), 15, 15, PowerUpType.EXPAND_PADDLE);
                case 1 -> powerUp = new PowerUp(brick.getX(), brick.getY(), 15, 15, PowerUpType.SHRINK_PADDLE);
                case 2 -> powerUp = new PowerUp(brick.getX(), brick.getY(), 15, 15, PowerUpType.FAST_BALL);
                default -> powerUp = new PowerUp(brick.getX(), brick.getY(), 15, 15, PowerUpType.EXPAND_PADDLE);
            }
                powerUpList.add(powerUp);
                powerUp.setRenderPowerUp(true);
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
            if (dx < 0) {
                ball.setX(brick.getX() - ball.getWidth());
            } else {
                ball.setX(brick.getX() + brick.getWidth());
            }
            ball.setDx(-ball.getDx());
        } else {
            // Va cham tren hoac duoi
            if (dy < 0) {
                ball.setY(brick.getY() - ball.getHeight());
            } else {
                ball.setY(brick.getY() + brick.getHeight());
            }
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
        return new Paddle(324, 526, 1, 0, 80, 15, 5);
    }
    public Ball newDefaultBall() {
        return new Ball(374, 506, 1, 1, 8, 5);
    }

    private void saveScore() {
        Score recordScore = new Score(this.score, Timestamp.valueOf(LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"))));
        RecordScore.insertScore(recordScore);
    }

    public void gameOver() {
        saveScore();
    }
}


