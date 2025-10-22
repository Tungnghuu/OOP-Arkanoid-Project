package app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.*;
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

    private boolean inMenu = true;
    private boolean gameOver = false;     // thêm
    private boolean gameCleared = false;  // thêm
    private MenuPanel menuPanel;
    InputHandler inputHandler = new InputHandler();
    GameManager gameManager = new GameManager();
    BrickManager brickManager = new BrickManager();
    List<PowerUp> powerUpList = gameManager.getPowerUpList();
    Paddle paddle = gameManager.getPaddle();
    Ball ball = gameManager.getBall();
    List<List<Brick>> brickList = brickManager.getBricks();
    Thread gameThread;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(inputHandler);
        this.addMouseListener(inputHandler);
        this.setFocusable(true);

        menuPanel = new MenuPanel(this);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void startGame() {
        inMenu = false;
    }

    @Override
    public void run() {
        while (gameThread != null) {
            update();
            repaint();
            try { Thread.sleep(16); } catch (InterruptedException e) {}
        }
    }

    private void doReset(boolean resetScore) {
        gameManager.resetGame(resetScore);
        brickManager.reset();
        paddle = gameManager.newDefaultPaddle();
        ball = gameManager.newDefaultBall();
        powerUpList = gameManager.getPowerUpList();
        gameOver = false;
        gameCleared = false;
        inputHandler.resetPressed = false;
    }

    public void update() {
        if (inMenu) {
            if (inputHandler.mouseClicked) {
                if (menuPanel.getPlayButton().contains(inputHandler.mouseX, inputHandler.mouseY)) {
                    startGame();
                }
                inputHandler.mouseClicked = false;
            }
            return;
        }

        // Khi Game Over hoặc Clear chờ người chơi nhấn R để reset
        if (gameOver || gameCleared) {
            if (inputHandler.resetPressed) doReset(true);
            return;
        }

        if (inputHandler.leftPressed) paddle.moveLeft();
        if (inputHandler.rightPressed) paddle.moveRight();

        if (inputHandler.spacePressed && ball.isStuck()) {
            ball.startBall();
            inputHandler.spacePressed = false;
        }

        if (!ball.isStuck()) {
            ball.updateBall(gameManager);
            gameManager.updateIfCollision(ball, paddle, brickList);
        } else {
            ball.BallFollowPaddle(paddle);
        }

        // PowerUp
        for (PowerUp p : powerUpList) {
            p.updatePowerUp();
            if (!p.isPowerUp() && paddle.getBounds().intersects(p.getBounds())) {
                paddle.applyPowerUp(p);
                p.activate();
            }
            if (p.isPowerUp() && p.isExpired(10000)) {
                paddle.endPowerUp(p);
                p.end();
            }
        }

        // Điều kiện để reset hoặc kết thúc game
        if (gameManager.getLives() <= 0) {
            gameOver = true;
        } else if (brickManager.isAllCleared()) {
            gameCleared = true;
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        ImageIcon ballImage = LoadImage.get("/asset/ball.png", 16,16);
        ImageIcon paddleImage = LoadImage.get("/asset/paddle.png", 120,15);
        if (inMenu) {
            menuPanel.draw(g);
            return;
        }
        
        DrawObject drawPaddle = new DrawObject(paddle.getX(), paddle.getY(),
                paddle.getWidth(), paddle.getHeight(), Color.blue);
        DrawObject drawBall = new DrawObject(ball.getX(), ball.getY(), ball.getWidth(),
                ball.getHeight(), Color.orange);
        drawBall.drawBall(g2, ballImage);
        drawPaddle.drawRect(g2,paddleImage);
        renderBrick(g2);
        renderPowerUp(g2);
        g2.setColor(Color.white);
        g2.setFont(new Font("Arial", Font.PLAIN, 20));
        g2.drawString("Score: " + gameManager.getScore(), 600, 20);
        g2.drawString("Lives: " + gameManager.getLives(), 50, 20);

        if (gameOver || gameCleared) {
            g2.setColor(new Color(0,0,0,160));
            g2.fillRect(0,0,getWidth(),getHeight());
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, 28));
            String msg = gameOver ? "Game Over" : "All Bricks Cleared!";
            g2.drawString(msg, getWidth()/2 - 120, getHeight()/2 - 10);
            g2.setFont(new Font("Arial", Font.PLAIN, 22));
            g2.drawString("Press R to Reset", getWidth()/2 - 110, getHeight()/2 + 30);
        }
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

    public void renderPowerUp(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        for (PowerUp p : powerUpList) {
            Color powerUpColor;
            switch (p.getType()) {
                case EXPAND_PADDLE:
                    powerUpColor = Color.YELLOW;
                    break;
                case SHRINK_PADDLE:
                    powerUpColor = Color.RED;
                    break;
                default:
                    powerUpColor = Color.WHITE;
            }
            DrawObject drawPowerUp = new DrawObject(
                    p.getX(),
                    p.getY(),
                    p.getWidth(),
                    p.getHeight(),
                    powerUpColor
            );
            drawPowerUp.drawRect(g2);
        }
    }
}
