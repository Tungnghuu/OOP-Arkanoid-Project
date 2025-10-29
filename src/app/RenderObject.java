package app;

import myInterface.myInterface.*;

import java.awt.*;
import logic.entity.*;
import logic.myLogic.*;

import javax.swing.*;

import static app.GamePanel.brickList;
import static app.GamePanel.powerUpList;

public class RenderObject {
    static boolean isRenderPowerUp = true;
    private static ImageIcon strongBrick = LoadImage.get("/asset/strongBrick.png", Brick.WIDTH, Brick.HEIGHT);
    private static ImageIcon bonusBrick = LoadImage.get("/asset/bonusBrick.png", Brick.WIDTH, Brick.HEIGHT);
    private static ImageIcon explosiveBrick = LoadImage.get("/asset/explosiveBrick.png", Brick.WIDTH, Brick.HEIGHT);
    private static ImageIcon normalBrick = LoadImage.get("/asset/normalBrick.png", Brick.WIDTH, Brick.HEIGHT);

    public static void renderBrick(Graphics g) {
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
            Color powerUpColor;
            switch (p.getType()) {
                case EXPAND_PADDLE:
                    powerUpColor = Color.YELLOW;
                    break;
                case SHRINK_PADDLE:
                    powerUpColor = Color.RED;
                    break;
                case FAST_BALL:
                    powerUpColor = Color.BLUE;
                    break;
                default:
                    powerUpColor = Color.WHITE;
            }
            DrawObject drawPowerUp = new DrawObject(
                    p.getX(),
                    p.getY(),
                    p.getWidth(),
                    p.getHeight(),
                    powerUpColor
            );
            drawPowerUp.drawRect(g2);
        }
    }
}
