package logic.entity;

import logic.myLogic.PowerUpType;

import java.util.ArrayList;
import java.util.List;

public class PowerUp extends GameObject {
        /** Thuoc tinh .*/
        private List<Bullet> bulletList = new ArrayList<>();
        private PowerUpType type;
        private boolean isPowerUp;
        private long startTime;
        private boolean isRenderPowerUp;
        public static  boolean isFire;
        private long duration;

        /** Construtor khoi tao.*/
        public PowerUp(int x, int y, int width, int height, PowerUpType type, long duration) {
            super(x, y, width, height);
            this.type = type;
            this.isPowerUp = false;
            this.isRenderPowerUp = true;
            this.duration = duration;
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
                        break;
                    case FIRE_PADDLE:
                        isFire = true;
                        break;
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
                    default:
                        break;
                }
                object.setSpeed(ballSpeed);
                ((Ball) object).updateBall();
            }
        }

        public void endPowerUp(MovableObject object) {
            if (object instanceof Paddle) {
                object.setWidth(80);
            }

            if (object instanceof Ball) {
                object.setSpeed(4);
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
            isFire = false;
        }

        public void createBullet(Paddle paddle) {
            Bullet b1 = new Bullet(paddle.getX(), paddle.getY(), 15, 15, 3);
            Bullet b2 = new Bullet(paddle.getX() + paddle.getWidth(), paddle.getY(), 15, 15, 3);

            bulletList.add(b1);
            bulletList.add(b2);
        }
    }
