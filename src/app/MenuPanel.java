package app;

import javax.swing.*;
import java.awt.*;

public class MenuPanel {

    private Rectangle playButton = new Rectangle(300, 260, 180, 120);
    private Rectangle guideButton = new Rectangle(320, 360, 160, 40);
    private Rectangle scoreButton = new Rectangle(320, 420, 160, 40);
    private ImageIcon playImage = LoadImage.get("/asset/playGame.png", 120, 60);
    private ImageIcon bgImage = LoadImage.get("/asset/background.jpg", 768, 576);

    public Rectangle getPlayButton() {
        return playButton;
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.drawImage(bgImage.getImage(), 0, 0, 768, 576, null);
        g2.drawImage(playImage.getImage(), playButton.x, playButton.y, 120, 60, null);

        // Nút bấm
        g.setFont(new Font("Arial", Font.PLAIN, 25));
        g.setColor(Color.LIGHT_GRAY);
        g.drawString("Hướng dẫn", guideButton.x + 15, guideButton.y + 28);
        g.drawString("Điểm cao", scoreButton.x + 25, scoreButton.y + 28);

        // Viền nút
        g.setColor(Color.GRAY);
        g.drawRect(guideButton.x, guideButton.y, guideButton.width, guideButton.height);
        g.drawRect(scoreButton.x, scoreButton.y, scoreButton.width, scoreButton.height);
    }
}

