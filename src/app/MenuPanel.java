package app;

import myInterface.myInterface.*;
import javax.swing.*;
import java.awt.*;

public class MenuPanel implements DrawBackground {
    private final int buttonWidth = 175;
    private final int buttonHeight = 50;
    private int centerX = GamePanel.screenWidth / 2 - buttonWidth / 2;
    private Rectangle playButton = new Rectangle(centerX, 260, buttonWidth, buttonHeight);
    private Rectangle historyButton = new Rectangle(centerX, 340, buttonWidth, buttonHeight);
    private Rectangle scoreButton = new Rectangle(centerX, 420, buttonWidth, buttonHeight);

    private ImageIcon playImage = LoadImage.get("/assets/Images/playGame.png", buttonWidth, buttonHeight);
    private ImageIcon bgImage   = LoadImage.get("/assets/Images/background.png", GamePanel.screenWidth, GamePanel.screenHeight);
    private ImageIcon highScoreImg = LoadImage.get("/assets/Images/leaderBoard.png", buttonWidth, buttonHeight);
    private ImageIcon scoreImg = LoadImage.get("/assets/Images/highScore.png", buttonWidth, buttonHeight);

    public Rectangle getPlayButton() {
        return playButton;
    }

    public Rectangle getScoreButton() {
        return scoreButton;
    }

    public Rectangle getHistoryButton() {
        return historyButton;
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(bgImage.getImage(), 0, 0, null);

        g2.drawImage(playImage.getImage(), playButton.x, playButton.y, null);
        g2.drawImage(highScoreImg.getImage(), scoreButton.x, scoreButton.y, null);
        g2.drawImage(scoreImg.getImage(), historyButton.x, historyButton.y, null);

        g.setFont(new Font("Arial", Font.PLAIN, 25));
        g.setColor(Color.LIGHT_GRAY);
    }
}
