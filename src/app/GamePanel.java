package app;

import java.awt.*;
import javax.swing.*;

import logic.entity.*;
import logic.myLogic.*;
import myInterface.myInterface.*;
import static app.RenderObject.*;

import java.util.List;

public class GamePanel extends JPanel implements Runnable {
    private static GamePanel instance = null; //Singleton design Pattern

    public static final int originalTileSize = 16;
    public static final int scale = 3;
    public static final int tileSize = originalTileSize * scale;
    public static int maxScreenCol = 16;
    public static int maxScreenRow = 12;
    public static final int screenWidth = tileSize * maxScreenCol;
    public static final int screenHeight = tileSize * maxScreenRow;

    private boolean inSetting = false;
    private boolean inMenu = true;
    private boolean gameOver = false;
    private boolean gameCleared = false;
    private MenuPanel menuPanel;
    private InputHandler inputHandler = new InputHandler();
    static GameManager gameManager = GameManager.getInstance();
    private SettingPanel settingPanel = new SettingPanel();
    static BrickManager brickManager = new BrickManager();
    static List<PowerUp> powerUpList = gameManager.getPowerUpList();
    private Paddle paddle = gameManager.getPaddle();
    private Ball ball = gameManager.getBall();
    private DrawObject drawPaddle = new DrawObject(paddle.getX(), paddle.getY(),
                                            paddle.getWidth(), paddle.getHeight(), Color.blue);
    private DrawObject drawBall = new DrawObject(ball.getX(), ball.getY(),
                                            ball.getWidth(), ball.getHeight(), Color.orange);
    static List<List<Brick>> brickList = brickManager.getBricks();
    private boolean scoreSaved = false;
    private Thread gameThread;

    private ImageIcon ballImage;
    private ImageIcon paddleImage;
    private ImageIcon level1To5Bg;

    private final Font hudFont = new Font("Arial", Font.PLAIN, 20);
    private final Color hudColor = Color.WHITE;

    private GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(inputHandler);
        this.addMouseListener(inputHandler);
        this.setFocusable(true);

        menuPanel = new MenuPanel();

        level1To5Bg = LoadImage.get("/assets/Images/level1To5.png", screenWidth, screenHeight);
        ballImage = LoadImage.get("/assets/Images/ball.png", 16,16);
        paddleImage = LoadImage.get("/assets/Images/paddle.png", 120,15);
    }

    public static GamePanel getInstance() {
        if (instance == null) {
            instance = new GamePanel();
        }
        return instance;
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
        double drawInterval = 1000000000.0 / 60; // 120 FPS
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
        scoreSaved = false;
        inputHandler.resetPressed = false;
    }

    public void update() {
        if (inMenu) {
            if (inputHandler.mouseClicked) {
                if (menuPanel.getPlayButton().contains(inputHandler.mouseX, inputHandler.mouseY)) {
                    startGame();
                } else if (menuPanel.getScoreButton().contains(inputHandler.mouseX, inputHandler.mouseY)) {
                    SwingUtilities.invokeLater(() -> new GameHistoryTable(GetHistory.getHistory()).setVisible(true));
                }
                inputHandler.mouseClicked = false;
            }
            return;
        }

        if (inputHandler.escapePressed) {
            inSetting = !inSetting;
            inputHandler.escapePressed = false;
        }   

        if (inSetting) {
             if (inputHandler.mouseClicked) {
                settingPanel.handleClick(inputHandler.lastMouseEvent);
                inputHandler.mouseClicked = false;
                if (settingPanel.exit) {
                    inSetting = false;
                }
            }
            return;
        }


        // Khi Game Over hoặc Clear chờ người chơi nhấn R để reset
        if (gameOver || gameCleared) {
            if (!scoreSaved) {
                gameManager.gameOver();
                scoreSaved = true;
            }
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
                p.applyPowerUp(paddle);
                p.applyPowerUp(ball);
                p.activate();
            }
            if (p.isPowerUp() && p.isExpired(10000)) {
                p.endPowerUp(paddle);
                p.endPowerUp(ball);
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

        if (brickManager.getLevel() <= 5) {
            g2.drawImage(level1To5Bg.getImage(), 0, 0, null);
        }

        if (inMenu) {
            menuPanel.draw(g2);
            return;
        }

        drawBall.setPosition(ball.getX(), ball.getY(), ball.getWidth(), ball.getHeight());
        drawPaddle.setPosition(paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight());
        drawBall.drawBall(g2, ballImage);
        drawPaddle.drawRect(g2, paddleImage);
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

        if (inSetting) {
            settingPanel.draw(g2);
        }

        this.setDoubleBuffered(true);
    }
}
