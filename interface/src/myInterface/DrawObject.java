package myInterface;

import java.awt.Graphics2D;
import java.awt.Color;
// import java.awt.Rectangle;

import javax.swing.JPanel;

public class DrawObject extends JPanel {
    private int x;
    private int y;
    private int width, height;
    private Color color;

    public DrawObject(int x, int y, int width, int height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void drawRect(Graphics2D g2) {
        g2.setColor(color);
        g2.fillRect(x, y, width, height);
    }

    public void drawBall(Graphics2D g2) {
        g2.setColor(color);
        g2.fillOval(x, y, width, height);
    }

}
