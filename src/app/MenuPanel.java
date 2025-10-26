package app;

import javax.swing.*;
import java.awt.*;

public class MenuPanel {
    private Rectangle playButton = new Rectangle(300, 260, 120, 60);
    private Rectangle guideButton = new Rectangle(300, 360, 120, 60);
    private Rectangle scoreButton = new Rectangle(300, 420, 120, 60);
    private ImageIcon playImage = LoadImage.get("/asset/playGame.png", 120, 60);
    private ImageIcon bgImage = LoadImage.get("/asset/background.jpg", 768, 576);
    private ImageIcon highScoreImg = LoadImage.get("/asset/score.jpg", 120, 60);

    public Rectangle getPlayButton() {
        return playButton;
    }
    public Rectangle getScoreButton() {
        return scoreButton;
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.drawImage(bgImage.getImage(), 0, 0, 768, 576, null);
        g2.drawImage(playImage.getImage(), playButton.x, playButton.y, 120, 60, null);
        g2.drawImage(highScoreImg.getImage(), scoreButton.x, scoreButton.y, 120, 60, null);
        // Nút bấm
        g.setFont(new Font("Arial", Font.PLAIN, 25));
        g.setColor(Color.LIGHT_GRAY);
        g.drawString("Hướng dẫn", guideButton.x, guideButton.y);

    }
}

