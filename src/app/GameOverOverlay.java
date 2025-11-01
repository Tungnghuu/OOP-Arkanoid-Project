package app;

import java.awt.*;

public class GameOverOverlay {
    private final Font titleFont = new Font("Arial", Font.BOLD, 28);
    private final Font instructionFont = new Font("Arial", Font.PLAIN, 22);
    private final Color overlayColor = new Color(0, 0, 0, 160);
    private final Color textColor = Color.WHITE;

    public void draw(Graphics2D g2, boolean isGameOver, int screenWidth, int screenHeight) {
        g2.setColor(overlayColor);
        g2.fillRect(0, 0, screenWidth, screenHeight);

        g2.setColor(textColor);
        g2.setFont(titleFont);
        String msg = isGameOver ? "Game Over" : "All Bricks Cleared!";
        g2.drawString(msg, screenWidth / 2 - 120, screenHeight / 2 - 10);

        g2.setFont(instructionFont);
        g2.drawString("Press R to Reset", screenWidth / 2 - 110, screenHeight / 2 + 30);
    }
}
