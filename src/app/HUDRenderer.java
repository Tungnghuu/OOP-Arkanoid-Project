package app;

import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics2D;

public class HUDRenderer {
    private final Font hudFont = new Font("Arial", Font.PLAIN, 20);
    private final Color hudColor = Color.WHITE;

    public void draw(Graphics2D g2, int score, int lives) {
        g2.setColor(hudColor);
        g2.setFont(hudFont);
        g2.drawString("Score: " + score, 600, 20);
        g2.drawString("Lives: " + lives, 50, 20);
    }
}
