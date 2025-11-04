package app;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import logic.entity.Paddle;
import logic.entity.Ball;
import logic.entity.GameObject;
import logic.entity.Brick;
import logic.entity.Bullet;
import logic.entity.PowerUp;
import logic.myLogic.PowerUpType;
import logic.myLogic.BrickType;

/** Lop quan ly game. */
public class GameManager {
    private static GameManager instance = null;

    private List<PowerUp> powerUpList = new ArrayList<>();
    private List<Bullet> bulletList = new ArrayList<>();
    private Paddle paddle;
    private Ball ball;
    private int score;
    private int lives;
    public int level;
    private double dropChance;
    private int shootCooldown;

    /**
     * Constructor cua GameManager.
     */
    private GameManager() {
        paddle = new Paddle(324, 526, 1, 0, 80, 15, 5);
        ball = new Ball(374, 506, 1, 1, 8, 4);
        this.score = 0;
        this.lives = 3;
        this.level = 1;
        this.dropChance = 0.3;
        this.shootCooldown = 0;
    }

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

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

    public List<Bullet> getBulletList() {
        return bulletList;
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

        return (dx * dx + dy * dy) <= radius * radius;
    }

    public void updateIfCollision(Ball ball, Paddle paddle, List<List<Brick>> brickList) {
        if (checkCollision(paddle, ball)) {
            handlePaddleCollision(ball, paddle);
        }

        List<Brick> hitBricks = new ArrayList<>();
        List<Brick> scoreBricks = new ArrayList<>();
        List<Bullet> hitBullets = new ArrayList<>();
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

                for (Bullet b : bulletList) {
                    if (b.getBounds().intersects(brick.getBounds())) {
                        hitBricks.add(brick);
                        hitBullets.add(b);
                        collided = true;
                        break;
                    }
                }
            }
            if (collided) {
                break;
            }
        }

        // kiem tra cac vien gach bi pha huy
        Queue<Brick> queue = new LinkedList<>(hitBricks);
        hitBricks.clear();

        while (!queue.isEmpty()) {
            Brick b = queue.poll();
            b.takeHits();
            if (b.isDestroy()) {
                scoreBricks.add(b);
                if (b.getType() == BrickType.EXPLOSIVE) {
                    int radius = 1;
                    int bx = b.getX();
                    int by = b.getY();
                    for (List<Brick> row : brickList) {
                        for (Brick other : row) {
                            if (!other.isDestroy()
                                    && Math.abs(other.getX() - bx) <= Brick.WIDTH * radius
                                    && Math.abs(other.getY() - by) <= Brick.HEIGHT * radius
                                    && other != b) {
                                scoreBricks.add(other);
                                other.remove();
                                queue.add(other);
                            }
                        }
                    }
                }
            }
        }

        // tang diem va tao powerUp
        for (Brick brick : scoreBricks) {
            addScore(brick);
            createPowerUp(brick);
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
        // xoa dan
        bulletList.removeAll(hitBullets);
        hitBullets.clear();
        // ko render powerUp nua khi cham vao paddle
        for (PowerUp p : powerUpList) {
            if (p.getBounds().intersects(paddle.getBounds())) {
                p.setRenderPowerUp(false);
            }
        }
    }

    public void createPowerUp(Brick brick) {
        if (brick.getType() == BrickType.BONUS) {
            Random rand = new Random();
            if (rand.nextDouble() < dropChance) {
                PowerUp powerUp;
                switch (rand.nextInt(7)) {
                    case 0 -> powerUp = new PowerUp(brick.getX(), brick.getY(), 30, 30, PowerUpType.EXPAND_PADDLE, 10000);
                    case 1 -> powerUp = new PowerUp(brick.getX(), brick.getY(), 30, 30, PowerUpType.SHRINK_PADDLE, 10000);
                    case 2 -> powerUp = new PowerUp(brick.getX(), brick.getY(), 30, 30, PowerUpType.FAST_BALL, 10000);
                    case 3 -> powerUp = new PowerUp(brick.getX(), brick.getY(), 30, 30, PowerUpType.FIRE_PADDLE, 10000);
                    case 4 -> powerUp = new PowerUp(brick.getX(), brick.getY(), 30, 30, PowerUpType.GAME_OVER, 10000);
                    case 5 -> powerUp = new PowerUp(brick.getX(), brick.getY(), 30, 30, PowerUpType.NEXT_LEVEL, 10000);
                    case 6 -> powerUp = new PowerUp(brick.getX(), brick.getY(), 30, 30, PowerUpType.EXTRA_LIFE, 10000);
                    default -> powerUp = new PowerUp(brick.getX(), brick.getY(), 30, 30, PowerUpType.EXPAND_PADDLE, 1000);
                }
                powerUpList.add(powerUp);
                powerUp.setRenderPowerUp(true);
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
                score += 10;
                break;
            default:
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

        //double speed = ball.getSpeed();
        double newDx = Math.sin(bounceAngle);
        double newDy = -Math.cos(bounceAngle);

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

    // Tao dan
    public void createBullet(Paddle paddle) {
        Bullet b1 = new Bullet(paddle.getX(), paddle.getY(), 10, 10, 3);
        Bullet b2 = new Bullet(paddle.getX() + paddle.getWidth(), paddle.getY(), 10, 10, 3);

        bulletList.add(b1);
        bulletList.add(b2);
    }

    // cap nhat dan
    public void updateBullet(Paddle paddle) {
       if (PowerUp.isFire) {
           if (shootCooldown <= 0) {
               createBullet(paddle);
               shootCooldown = 130;
           } else {
               shootCooldown--;
           }
       }

       for (Bullet b : bulletList) {
           b.update();
       }
    }
    // Reset game
    public void resetGame(boolean resetScore) {
        this.lives = 3;
        if (resetScore) {
            this.score = 0;
        }

        this.powerUpList.clear();
    }

    public void saveScore() {
        Score recordScore = new Score(this.score, Timestamp.valueOf(LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"))));
        RecordScore.updateScore(recordScore);
    }

    public void gameOver() {
        saveScore();
    }
}


