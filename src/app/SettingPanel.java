package app;

import java.awt.*;
import java.awt.event.MouseEvent;

public class SettingPanel {
    private int volume = 50; //Default volume
    // private boolean draggingVolume = false;
    public boolean exit = false;

    private final double widthPercent = 0.6;
    private final double heightPercent = 0.5;

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        int screenWidth = GamePanel.screenWidth;
        int screenHeight = GamePanel.screenHeight;

        int panelW = (int)(widthPercent * screenWidth);
        int panelH = (int)(heightPercent * screenHeight);
        int panelX = (screenWidth - panelW) / 2;
        int panelY = (screenHeight - panelH) / 2;

        // Smooth edges and anti-aliasing
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Panel background with rounded corners
        g2.setColor(new Color(0, 0, 0, 180));
        g2.fillRoundRect(panelX, panelY, panelW, panelH, 30, 30);

        g2.setFont(new Font("Arial", Font.BOLD, panelW / 12));
        g2.setColor(Color.WHITE);
        String title = "Settings";
        int titleWidth = g2.getFontMetrics().stringWidth(title);
        g2.drawString(title, panelX + (panelW - titleWidth)/2, panelY + panelH / 8 + 10);

        int volW = (int)(panelW * 0.6);
        int volH = 20;
        int volX = panelX + (panelW - volW) / 2;
        int volY = panelY + panelH / 3;

        g2.setColor(Color.DARK_GRAY);
        g2.fillRoundRect(volX, volY, volW, volH, 10, 10);

        g2.setColor(Color.GREEN);
        g2.fillRoundRect(volX, volY, volW * volume / 100, volH, 10, 10);

        int knobX = volX + volW * volume / 100 - 8;
        int knobY = volY - 5;
        g2.setColor(Color.WHITE);
        g2.fillOval(knobX, knobY, 16, 30);

        g2.setFont(new Font("Arial", Font.PLAIN, panelW / 25));
        String volLabel = "Volume: " + volume;
        int volLabelWidth = g2.getFontMetrics().stringWidth(volLabel);
        g2.drawString(volLabel, panelX + (panelW - volLabelWidth)/2, volY - 10);

        int btnW = (int)(panelW * 0.4);
        int btnH = 40;
        int btnX = panelX + (panelW - btnW)/2;
        int btnY = panelY + panelH - btnH - 30;

        g2.setColor(new Color(0, 120, 255));
        g2.fillRoundRect(btnX, btnY, btnW, btnH, 15, 15);
        g2.setColor(Color.WHITE);
        String btnText = "Save & Exit";
        int btnTextWidth = g2.getFontMetrics().stringWidth(btnText);
        g2.drawString(btnText, btnX + (btnW - btnTextWidth)/2, btnY + btnH/2 + 7);
    }

    public void handleClick(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();
        int screenWidth = GamePanel.screenWidth;
        int screenHeight = GamePanel.screenHeight;

        int panelW = (int)(widthPercent * screenWidth);
        int panelH = (int)(heightPercent * screenHeight);
        int panelX = (screenWidth - panelW) / 2;
        int panelY = (screenHeight - panelH) / 2;

        int volW = (int)(panelW * 0.6);
        int volH = 20;
        int volX = panelX + (panelW - volW) / 2;
        int volY = panelY + panelH / 3;
        Rectangle volumeBar = new Rectangle(volX, volY, volW, volH);

        int btnW = (int)(panelW * 0.4);
        int btnH = 40;
        int btnX = panelX + (panelW - btnW)/2;
        int btnY = panelY + panelH - btnH - 30;
        Rectangle saveButton = new Rectangle(btnX, btnY, btnW, btnH);

        if (volumeBar.contains(mx, my)) {
            volume = Math.max(0, Math.min(100, (mx - volX) * 100 / volW));
        }

        if (saveButton.contains(mx, my)) {
            exit = true;
            System.out.println("Volume saved: " + volume);
        }
    }
}
