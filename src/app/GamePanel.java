package app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

import myInterface.*;
import myLogic.*;

public class GamePanel extends JPanel implements Runnable {
    final int originalTileSize = 16;
    final int scale = 3;
    final int tileSize = originalTileSize * scale;
    int maxScreenCol = 16;
    int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol;
    final int screenHeight = tileSize * maxScreenRow;

    InputHandler inputHandler = new InputHandler();
    GameManager gameManager = new GameManager();
    Paddle paddle = gameManager.getPaddle();
    Ball ball = gameManager.getBall();
    Thread gameThread;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(inputHandler);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        while (gameThread != null) {
            update();
            repaint();
            // limits CPU's usage reduced to 60 fps
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {}
        }
    }

    public void update() {
        if (inputHandler.leftPressed) {
            paddle.moveLeft();
        } else if (inputHandler.rightPressed) {
            paddle.moveRight();
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        DrawObject drawBall = new DrawBall(ball.getX(), ball.getY(), ball.getWidth(), ball.getHeight(), Color.white);
        DrawObject drawPaddle = new DrawObject(paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight(), Color.blue);
        drawPaddle.draw(g2);
        drawBall.draw(g2);
    }
}