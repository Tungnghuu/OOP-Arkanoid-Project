package app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import java.util.List;

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
    int lives = 3;
    int score = 0;

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
            // limits CPU's usage reduced to 60 fps
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {}
        }
    }

    public void update() {
        ball.BallFollowPaddle(paddle);
        if (inputHandler.leftPressed) {
            paddle.moveLeft();
        }
        if (inputHandler.rightPressed) {
            paddle.moveRight();
        }
        if (inputHandler.spacePressed) {
            ball.startBall();
        } else {
            updateIfCollision(ball, paddle, brickList);
            ball.updateBall();
        }
        if (ball.getY() > screenHeight) {
            lives -= 1;
            ball.resetBall(paddle);
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
                        switch (brick.getType()) {
                            case NORMAL:
                                score += 10;
                                break;
                            case STRONG:
                                score += 20;
                                break;
                            case EXPLOSIVE:
                                score += 50;
                                break;
                        }
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
        DrawObject drawPaddle = new DrawObject(paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight(), Color.blue);
        DrawObject drawBall = new DrawObject(ball.getX(), ball.getY(), ball.getWidth(), ball.getHeight(),Color.orange);
        drawPaddle.drawRect(g2);
        drawBall.drawBall(g2);
        renderBrick(g2);
        g2.setColor(Color.white);
        g2.drawString("Lives: " + lives, 10, 20);
        g2.drawString("Score: " + score, screenWidth - 80, 20);
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
