package myLogic;
/** Lop dai dien cho bong. */
public class Ball extends MovableObject {
    /** Cac thuoc tinh. */
    private int speed;
    private int radius;
    public static boolean ballStuck = true;

    /** Constructor cua Ball.*/
    public Ball(int speed, int dx, int dy, int x, int y, int radius ) {
        super(x, y, dx, dy, radius * 2, radius * 2);
        this.speed = speed;
        this.radius = radius;
    }

    /** copy Constructor cua Ball.*/
    public Ball(Ball other) {
        super(other.getX(), other.getY(), other.getDx(),
                other.getDy(), other.radius * 2, other.radius * 2);
        this.speed = other.speed;
        this.radius = other.radius;
    }

    public int getRadius() {
        return this.radius;
    }

    /** cho bong di chuyen theo paddle khi chua bat dau.*/
    public void BallFollowPaddle(Paddle paddle) {
        int ballX = this.getX();
        int ballY = this.getY();
        if (ballStuck) {
            ballX = paddle.getX() + paddle.getWidth() / 2 - this.radius;
            ballY = paddle.getY() - paddle.getHeight() ;

            this.setX(ballX);
            this.setY(ballY);
        }
    }

    /** Cho bong bat dau bay .*/
    public void startBall() {
        ballStuck = false;
    }

    public void updateBall() {
        int ballY = this.getY();
        int ballX = this.getX();
        if(!ballStuck) {
            ballY -= this.getDy() * this.speed;
            ballX += this.getDx() * this.speed/2;
            this.setX(ballX);
            this.setY(ballY);

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
        }
    }
    //public void bouceOff() {}

    public void resetBall(Paddle paddle) {
        // Phuong thức này sẽ đặt lại vị trí của bóng về vị trí ban đầu trên thanh trượt
        
    }

}
