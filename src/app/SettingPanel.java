package app;

import java.awt.*;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;

public class SettingPanel extends JPanel {
    private int volume = 50; //Default volume
    public boolean exit = false;

    private final double widthPercent = 0.6;
    private final double heightPercent = 0.5;

    private final double[] fps = {30, 60, 120, 240};
    private final String[] speedOptions = {"0.5x", "1.0x", "1.5x", "2.0x"};
    private int selectedFpsIndex = 1;
    private boolean fpsDropdownOpen = false;

    public SettingPanel() {
        setOpaque(false);
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        int screenWidth = GamePanel.screenWidth;
        int screenHeight = GamePanel.screenHeight;

        int panelW = (int)(widthPercent * screenWidth);
        int panelH = (int)(heightPercent * screenHeight) + 100;
        int panelX = (screenWidth - panelW) / 2;
        int panelY = (screenHeight - panelH) / 2;

        // Panel background with rounded corners
        g2.setColor(new Color(0, 0, 0, 180));
        g2.fillRoundRect(panelX, panelY, panelW, panelH, 20, 20);

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
        g2.fillRoundRect(volX, volY, volW, volH, 5, 5);

        g2.setColor(Color.GREEN);
        g2.fillRoundRect(volX, volY, volW * volume / 100, volH, 5, 5);

        int knobX = volX + volW * volume / 100 - 8;
        int knobY = volY - 5;
        g2.setColor(Color.WHITE);
        g2.fillOval(knobX, knobY, 16, 30);

        g2.setFont(new Font("Arial", Font.PLAIN, panelW / 25));
        String volLabel = "Volume: " + volume;
        int volLabelWidth = g2.getFontMetrics().stringWidth(volLabel);
        g2.drawString(volLabel, panelX + (panelW - volLabelWidth)/2, volY - 10);

        int comboW = 120;
        int comboH = 25;
        int comboX = panelX + (panelW - comboW)/2;
        int comboY = volY + 50;
        g2.setColor(Color.GRAY);
        g2.fillRect(comboX, comboY, comboW, comboH);
        g2.setColor(Color.WHITE);
        g2.drawString("Speed: " + speedOptions[selectedFpsIndex], comboX + 10, comboY + 20);

        if (fpsDropdownOpen) {
            for (int i = 0; i < speedOptions.length; i++) {
                g2.setColor(Color.DARK_GRAY);
                g2.fillRect(comboX, comboY + (i+1)*comboH, comboW, comboH);
                g2.setColor(Color.WHITE);
                g2.drawString("" + speedOptions[i], comboX + 10, comboY + (i+1)*comboH + 20);
            }
        }

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
        int mouseX = e.getX();
        int mouseY = e.getY();
        int screenWidth = GamePanel.screenWidth;
        int screenHeight = GamePanel.screenHeight;

        int panelW = (int)(widthPercent * screenWidth);
        int panelH = (int)(heightPercent * screenHeight) + 100;
        int panelX = (screenWidth - panelW) / 2;
        int panelY = (screenHeight - panelH) / 2;

        int volW = (int)(panelW * 0.6);
        int volH = 20;
        int volX = panelX + (panelW - volW) / 2;
        int volY = panelY + panelH / 3;
        Rectangle volumeBar = new Rectangle(volX, volY, volW, volH);

        int btnW = (int)(panelW * 0.4);
        int btnH = 40;
        int btnX = panelX + (panelW - btnW) / 2;
        int btnY = panelY + panelH - btnH - 30;
        Rectangle saveButton = new Rectangle(btnX, btnY, btnW, btnH);

        if (volumeBar.contains(mouseX, mouseY)) {
            volume = Math.max(0, Math.min(100, (mouseX - volX) * 100 / volW));
            exit = false;
        }

        int comboW = 120;
        int comboH = 25;
        int comboX = panelX + (panelW - comboW) / 2;
        int comboY = volY + 50;

        Rectangle fpsRect = new Rectangle(comboX, comboY, comboW, comboH);

        if (fpsRect.contains(mouseX, mouseY)) {
            fpsDropdownOpen = !fpsDropdownOpen;
            exit = false;
        }

        if (fpsDropdownOpen) {
            for (int i = 0; i < speedOptions.length; i++) {
                Rectangle optRect = new Rectangle(comboX, comboY + (i + 1) * comboH, comboW, comboH);
                if (optRect.contains(mouseX, mouseY)) {
                    selectedFpsIndex = i;
                    fpsDropdownOpen = false;
                    GamePanel.getInstance().setFPS(fps[i]);
                }
            }
            return;
        }

        if (saveButton.contains(mouseX, mouseY)) {
            exit = true;
            System.out.println("Volume saved: " + volume);
        }
    }
}
