package app;

import myInterface.myInterface.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import logic.entity.*;
import logic.myLogic.*;

import javax.swing.*;

import static app.GamePanel.*;

public class RenderObject {
    static boolean isRenderPowerUp = true;
    private static ImageIcon strongBrick = LoadImage.get("/assets/Images/strongBrick.png", Brick.WIDTH, Brick.HEIGHT);
    private static ImageIcon bonusBrick = LoadImage.get("/assets/Images/bonusBrick.png", Brick.WIDTH, Brick.HEIGHT);
    private static ImageIcon explosiveBrick = LoadImage.get("/assets/Images/explosiveBrick.png", Brick.WIDTH, Brick.HEIGHT);
    private static ImageIcon normalBrick = LoadImage.get("/assets/Images/normalBrick.png", Brick.WIDTH, Brick.HEIGHT);
    private static ImageIcon expandPaddle = LoadImage.get("/assets/Images/expandPaddle.png", 30, 30);
    private static ImageIcon shrinkPaddle = LoadImage.get("/assets/Images/shrinkPaddle.png", 30, 30);
    private static ImageIcon shootingPaddle = LoadImage.get("/assets/Images/shootingPaddle.png", 30, 30);
    private static ImageIcon fastBall = LoadImage.get("/assets/Images/fastBall.png", 30, 30);
    private static ImageIcon smallBall = LoadImage.get("/assets/Images/smallBall.png", 30, 30);
    private static ImageIcon nexLevel = LoadImage.get("/assets/Images/nextLevelPowerUp.png", 30, 30);
    private static ImageIcon death = LoadImage.get("/assets/Images/death.png", 30, 30);
    private static ImageIcon extraLife = LoadImage.get("/assets/Images/extraLife.png", 30, 30);

    public static void renderBrick(Graphics g) {
        if (brickList == null || brickList.isEmpty()) return;
        Graphics2D g2 = (Graphics2D) g;
        for (int i = 0; i < brickList.size(); i++) {
            for (int j = 0; j < brickList.get(i).size(); j++) {
                Brick b = brickList.get(i).get(j);
                ImageIcon img;

                switch (b.getType()) {
                    case NORMAL:
                        img = normalBrick;
                        break;
                    case STRONG:
                        img = strongBrick;
                        break;
                    case EXPLOSIVE:
                        img = explosiveBrick;
                        break;
                    case BONUS:
                        img = bonusBrick;
                        break;
                    default:
                        img = normalBrick;                }

                DrawObject drawBrick = new DrawObject(
                        b.getX(),
                        b.getY(),
                        b.getWidth(),
                        b.getHeight(),
                        Color.orange
                );
                drawBrick.drawRect(g2, img);
                g2.drawRect(b.getX(), b.getY(), b.getWidth(), b.getHeight());
            }
        }
    }

    public static void renderPowerUp(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        for (PowerUp p : powerUpList) {
            if (!p.isRenderPowerUp()) {
                continue;
            }
            ImageIcon img;
            switch (p.getType()) {
                case EXPAND_PADDLE:
                    img = expandPaddle;
                    break;
                case SHRINK_PADDLE:
                    img = shrinkPaddle;
                    break;
                case FAST_BALL:
                    img = fastBall;
                    break;
                case FIRE_PADDLE:
                    img = shootingPaddle;
                    break;
                case SMALL_BALL:
                    img = smallBall;
                    break;
                case NEXT_LEVEL:
                    img = nexLevel;
                    break;
                case GAME_OVER:
                    img = death;
                    break;
                case EXTRA_LIFE:
                    img = extraLife;
                    break;
                default:
                    img = expandPaddle;
                    break;
            }
            DrawObject drawPowerUp = new DrawObject(
                    p.getX(),
                    p.getY(),
                    p.getWidth(),
                    p.getHeight(),
                    Color.orange
            );
            drawPowerUp.drawRect(g2, img);
            g2.drawRect(p.getX(), p.getY(), p.getWidth(), p.getHeight());
        }
    }

    public static void renderBullet(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        List<Bullet> bulletListCopy = new ArrayList<>(bulletList);
        for (Bullet b : bulletListCopy) {
            DrawObject drawBullet = new DrawObject(
                    b.getX(),
                    b.getY(),
                    b.getWidth(),
                    b.getHeight(),
                    Color.ORANGE
            );
            drawBullet.drawRect(g2);
        }
    }
}
