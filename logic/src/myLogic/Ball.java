package myLogic;
/** Lop dai dien cho bong. */
public class Ball extends MovableObject {
    /**
     * Cac thuoc tinh.
     */
    private int speed;
    private int radius;
    private static boolean ballStuck = true;


    /**
     * Constructor cua Ball.
     */
    public Ball(int speed, int dx, int dy, int x, int y, int radius) {
        super(x, y, dx, dy, radius * 2, radius * 2);
        this.speed = speed;
        this.radius = radius;
    }

    /**
     * copy Constructor cua Ball.
     */
    public Ball(Ball other) {
        super(other.getX(), other.getY(), other.getDx(),
                other.getDy(), other.radius * 2, other.radius * 2);
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
        ballY = paddle.getY() - paddle.getHeight() + this.radius;

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
        this.setDx(1);
        this.setDy(1);
        this.speed = 5;
        this.radius = 8;
    }

    public void updateBall(GameManager gm) {
        int ballY = this.getY();
        int ballX = this.getX();
            ballY -= this.getDy() * this.speed;
            ballX += this.getDx() * this.speed/2;
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
                lives --;
                gm.setLives(lives);
            //    System.out.println("Lives: " + lives);
                resetBall();
            }
        }
    }
