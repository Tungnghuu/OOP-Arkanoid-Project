package app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import java.util.List;
import java.awt.Font;

import myInterface.*;
import entity.*;
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
    BrickManager brickManager = new BrickManager();
    Paddle paddle = gameManager.getPaddle();
    Ball ball = gameManager.getBall();
    List<List<Brick>> brickList = brickManager.getBricks();
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
            int lives = gameManager.getLives();
            if (lives <= 0) {
                gameThread = null;
                break;
            }
            // limits CPU's usage reduced to 60 fps
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {}
        }
    }

    public void update() {
        if (inputHandler.leftPressed) {
            paddle.moveLeft();
        }
        if (inputHandler.rightPressed) {
            paddle.moveRight();
        }

        if (inputHandler.spacePressed && ball.isStuck()) {
            ball.startBall();
            inputHandler.spacePressed = false;
        }
    }

    public void updateIfCollision(Ball ball, Paddle paddle, List<List<Brick>> brickList) {
        if (gameManager.checkCollision(paddle, ball)) {
            ball.setDy(-ball.getDy());
        }

        for (int i = 0; i < brickList.size(); i++) {
            for (int j = 0; j < brickList.get(i).size(); j++) {
                Brick brick = brickList.get(i).get(j);
                if (gameManager.checkCollision(brick, ball)) {
                    ball.setDy(-ball.getDy());
                    brick.takeHits();
                    if (brick.isDestroy()) {
                        brickList.get(i).remove(j);
                        j--;
                    }
                }
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        DrawObject drawPaddle = new DrawObject(paddle.getX(), paddle.getY()
                , paddle.getWidth(), paddle.getHeight(), Color.blue);
        DrawObject drawBall = new DrawObject(ball.getX(), ball.getY(), ball.getWidth(),
                    ball.getHeight(), Color.orange);
        drawBall.drawBall(g2);
        drawPaddle.drawRect(g2);
        renderBrick(g2);
        g2.setColor(Color.white);
        g2.setFont(new Font("Arial", Font.PLAIN, 20));
        g2.drawString("Score: " + gameManager.getScore(), 600, 20);
        g2.drawString("Lives: " + gameManager.getLives(), 50, 20);
        g2.dispose();
    }

    public void renderBrick(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        for (int i = 0; i < brickList.size(); i++) {
            for (int j = 0; j < brickList.get(i).size(); j++) {
                Brick b = brickList.get(i).get(j);
                Color brickColor;

                switch (b.getType()) {
                    case NORMAL:
                        brickColor = Color.GREEN;
                        break;
                    case STRONG:
                        brickColor = Color.GRAY;
                        break;
                    case UNBREAKABLE:
                        brickColor = Color.RED;
                        break;
                    case EXPLOSIVE:
                        brickColor = Color.ORANGE;
                        break;
                    default:
                        brickColor = Color.WHITE;
                }

                DrawObject drawBrick = new DrawObject(
                        b.getX(),
                        b.getY(),
                        b.getWidth(),
                        b.getHeight(),
                        brickColor
                );
                drawBrick.drawRect(g2);
                g2.setColor(Color.BLACK);
                g2.drawRect(b.getX(), b.getY(), b.getWidth(), b.getHeight());
            }
        }
    }

    
}
