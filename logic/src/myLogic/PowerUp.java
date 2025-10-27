package myLogic;

import entity.Ball;
import entity.GameObject;
import entity.MovableObject;
import entity.Paddle;
// import entity.Paddle;

public class PowerUp extends GameObject {
    /** Thuoc tinh .*/
    private PowerUpType type;
    private boolean isPowerUp;
    private int duration;
    private long startTime;
    private  boolean isRenderPowerUp;

    /** Construtor khoi tao.*/
    public PowerUp(int x, int y, int width, int height, PowerUpType type) {
        super(x, y, height, width);
        this.type = type;
        // this.duration = duration;
        this.isPowerUp = false;
        this.isRenderPowerUp = true;
    }

    public PowerUpType getType() {
        return this.type;
    }

    public boolean isPowerUp() {
        return isPowerUp;
    }

    public boolean isRenderPowerUp() {
        return isRenderPowerUp;
    }

    public void setRenderPowerUp(boolean renderPowerUp) {
        isRenderPowerUp = renderPowerUp;
    }

    public void setPowerUp(boolean isPowerUp) {
        this.isPowerUp = isPowerUp;
    }

    public long getDuration() {
        return this.duration;
    }

    public void updatePowerUp() {
        int powerUpY = this.getY();
        powerUpY += 5;
        this.setY(powerUpY);
    }

    public void applyPowerUp(MovableObject object) {
        if (object instanceof Paddle) {
            int paddleWidth = object.getWidth();
            switch (this.getType()) {
                case EXPAND_PADDLE:
                    paddleWidth += 40;
                    break;
                case SHRINK_PADDLE:
                    paddleWidth -= 40;
                default:
                    break;
            }
            object.setWidth(paddleWidth);
        }

        if (object instanceof Ball) {
            double ballSpeed = ((Ball) object).getSpeed();
            switch (this.getType()) {
                case FAST_BALL:
                    ballSpeed = 7;
                    break;
                case SLOW_BALL:
                    ballSpeed = 2;
                default:
                    break;
            }
            object.setSpeed(ballSpeed);
        }
    }

    public void endPowerUp(MovableObject object) {
        if (object instanceof Paddle) {
            object.setWidth(80);
        }

        if (object instanceof Ball) {
            object.setSpeed(5);
        }
    }

    public void activate() {
        startTime = System.currentTimeMillis();
        isPowerUp = true;
    }

    public boolean isExpired(long duration) {
        return isPowerUp && System.currentTimeMillis() - startTime > duration;
    }

    public void end() {
        isPowerUp = false;
    }
}
