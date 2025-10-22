package myLogic;

import entity.GameObject;
// import entity.Paddle;

public class PowerUp extends GameObject {
    /** Thuoc tinh .*/
    private PowerUpType type;
    private boolean isPowerUp;
    private int duration;
    private long startTime;

    /** Construtor khoi tao.*/
    public PowerUp(int x, int y, int width, int height, PowerUpType type) {
        super(x, y, height, width);
        this.type = type;
        // this.duration = duration;
        this.isPowerUp = false;
    }

    public PowerUpType getType() {
        return this.type;
    }

    public boolean isPowerUp() {
        return isPowerUp;
    }

    public long getDuration() {
        return this.duration;
    }

    public void updatePowerUp() {
        int powerUpY = this.getY();
        powerUpY += 5;
        this.setY(powerUpY);
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
