package app;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

public class GameOverOverlay {
    private final Font titleFont = new Font("SansSerif", Font.BOLD, 36);
    private final Font instructionFont = new Font("SansSerif", Font.PLAIN, 22);
    private final Color overlayColor = new Color(0, 0, 0, 160);
    private final Color textColor = Color.WHITE;

    public void draw(Graphics2D g2, boolean isGameOver, int screenWidth, int screenHeight) {
        g2.setColor(overlayColor);
        g2.fillRect(0, 0, screenWidth, screenHeight);

        g2.setColor(textColor);

        g2.setFont(titleFont);
        String msg = isGameOver ? "Game Over!" : "You Won! Congratulation!";
        FontMetrics fmTitle = g2.getFontMetrics();
        int titleWidth = fmTitle.stringWidth(msg);
        int titleX = (screenWidth - titleWidth) / 2;
        int titleY = screenHeight / 2 - 20;
        g2.drawString(msg, titleX, titleY);

        g2.setFont(instructionFont);
        String instruction = "Press R to Restart";
        FontMetrics fmInstr = g2.getFontMetrics();
        int instrWidth = fmInstr.stringWidth(instruction);
        int instrX = (screenWidth - instrWidth) / 2;
        int instrY = titleY + 50;
        g2.drawString(instruction, instrX, instrY);
    }
}
