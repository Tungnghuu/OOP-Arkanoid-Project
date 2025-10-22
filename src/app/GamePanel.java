package app;

import java.awt.*;
import javax.swing.*;
import java.util.List;

import myInterface.*;
import entity.*;
import myLogic.*;

public class GamePanel extends JPanel implements Runnable {
    private final int originalTileSize = 16;
    private final int scale = 3;
    private final int tileSize = originalTileSize * scale;
    private int maxScreenCol = 16;
    private int maxScreenRow = 12;
    private final int screenWidth = tileSize * maxScreenCol;
    private final int screenHeight = tileSize * maxScreenRow;

    private boolean inSetting = false;
    private boolean inMenu = true;
    private boolean gameOver = false;
    private boolean gameCleared = false;
    private MenuPanel menuPanel;
    private InputHandler inputHandler = new InputHandler();
    private GameManager gameManager = new GameManager();
    private BrickManager brickManager = new BrickManager();
    private List<PowerUp> powerUpList = gameManager.getPowerUpList();
    private Paddle paddle = gameManager.getPaddle();
    private Ball ball = gameManager.getBall();
    private DrawObject drawPaddle = new DrawObject(paddle.getX(), paddle.getY(),
                                            paddle.getWidth(), paddle.getHeight(), Color.blue);
    private DrawObject drawBall = new DrawObject(ball.getX(), ball.getY(),
                                            ball.getWidth(), ball.getHeight(), Color.orange);
    private List<List<Brick>> brickList = brickManager.getBricks();
    private Thread gameThread;

    private ImageIcon ballImage;
    private ImageIcon paddleImage;

    private final Font hudFont = new Font("Arial", Font.PLAIN, 20);
    private final Color hudColor = Color.WHITE;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(inputHandler);
        this.addMouseListener(inputHandler);
        this.setFocusable(true);

        menuPanel = new MenuPanel();

        ballImage = LoadImage.get("/asset/ball.png", 16,16);
        paddleImage = LoadImage.get("/asset/paddle.png", 120,15);
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
        double drawInterval = 1000000000.0 / 120; // 120 FPS
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
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

        if (inMenu) {
            menuPanel.draw(g);
            return;
        }

        if (inSetting) {
            
        }
        
        drawBall.setPosition(ball.getX(), ball.getY());
        drawPaddle.setPosition(paddle.getX(), paddle.getY());
        drawBall.drawBall(g2, ballImage);
        drawPaddle.drawRect(g2,paddleImage);
        renderBrick(g2);
        renderPowerUp(g2);
        g2.setColor(hudColor);
        g2.setFont(hudFont);
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
        this.setDoubleBuffered(true);
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
