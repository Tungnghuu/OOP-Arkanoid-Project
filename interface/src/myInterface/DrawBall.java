package myInterface;

import java.awt.Graphics2D;
import java.awt.Color;

public class DrawBall extends DrawObject {
    public DrawBall(int x, int y, int width, int height, Color color) {
        super(x, y, width, height, color);
    }

    public void draw(Graphics2D g2) {
        g2.setColor(this.getColor());
        g2.fillOval(this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }
}
