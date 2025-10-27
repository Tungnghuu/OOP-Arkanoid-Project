package app;

import myInterface.*;
import javax.swing.*;
import java.awt.*;

public class MenuPanel implements DrawBackground {
    private final int buttonWidth = 120;
    private final int buttonHeight = 60;
    private int centerX = GamePanel.screenWidth / 2 - buttonWidth / 2;
    private Rectangle playButton = new Rectangle(centerX, 260, buttonWidth, buttonHeight);
    private Rectangle guideButton = new Rectangle(centerX, 340, buttonWidth, buttonHeight);
    private Rectangle scoreButton = new Rectangle(centerX, 420, buttonWidth, buttonHeight);

    private ImageIcon playImage = LoadImage.get("/asset/playGame.png", buttonWidth, buttonHeight);
    private ImageIcon bgImage   = LoadImage.get("/asset/background.png", GamePanel.screenWidth, GamePanel.screenHeight);
    private ImageIcon highScoreImg = LoadImage.get("/asset/score.jpg", buttonWidth, buttonHeight);

    public Rectangle getPlayButton() {
        return playButton;
    }

    public Rectangle getScoreButton() {
        return scoreButton;
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(bgImage.getImage(), 0, 0, null);

        g2.drawImage(playImage.getImage(), playButton.x, playButton.y, null);
        g2.drawImage(highScoreImg.getImage(), scoreButton.x, scoreButton.y, null);

        g.setFont(new Font("Arial", Font.PLAIN, 25));
        g.setColor(Color.LIGHT_GRAY);

        String text = "Tutorial";
        FontMetrics fm = g.getFontMetrics();
        int textX = guideButton.x + (guideButton.width - fm.stringWidth(text)) / 2;
        int textY = guideButton.y + (guideButton.height + fm.getAscent()) / 2 - 5;

        g.drawString(text, textX, textY);
    }
}
