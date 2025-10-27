package app;

import myInterface.myInterface.*;

import java.awt.*;
import logic.entity.*;
import logic.myLogic.*;

import static app.GamePanel.brickList;
import static app.GamePanel.powerUpList;

public class RenderObject {
    static boolean isRenderPowerUp = true;
    public static void renderBrick(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        for (int i = 0; i < brickList.size(); i++) {
            for (int j = 0; j < brickList.get(i).size(); j++) {
                Brick b = brickList.get(i).get(j);
                Color brickColor;

                switch (b.getType()) {
                    case NORMAL:
                        brickColor = Color.GREEN;
                        break;
                    case STRONG:
                        brickColor = Color.GRAY;
                        break;
                    case UNBREAKABLE:
                        brickColor = Color.RED;
                        break;
                    case EXPLOSIVE:
                        brickColor = Color.ORANGE;
                        break;
                    case BONUS:
                        brickColor = Color.BLUE;
                        break;
                    default:
                        brickColor = Color.WHITE;
                }

                DrawObject drawBrick = new DrawObject(
                        b.getX(),
                        b.getY(),
                        b.getWidth(),
                        b.getHeight(),
                        brickColor
                );
                drawBrick.drawRect(g2);
                g2.setColor(Color.BLACK);
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
