package app;

import java.awt.*;
import javax.swing.*;
import java.util.List;

import logic.entity.*;
import logic.myLogic.*;
import myInterface.myInterface.*;
import static app.RenderObject.*;


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
    SoundManager soundManager = new SoundManager();
    static GameManager gameManager = GameManager.getInstance();
    private SettingPanel settingPanel = new SettingPanel();
    static BrickManager brickManager = new BrickManager();
    static List<PowerUp> powerUpList = gameManager.getPowerUpList();
    private Paddle paddle = gameManager.getPaddle();
    private Ball ball = gameManager.getBall();
    private DrawObject drawPaddle = new DrawObject(paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight(), Color.orange);
    private DrawObject drawBall = new DrawObject(ball.getX(), ball.getY(), ball.getWidth(), ball.getHeight(), Color.GREEN);
    static List<List<Brick>> brickList = brickManager.getBricks();
    private boolean scoreSaved = false;
    private Thread gameThread;

    private ImageIcon ballImage;
    private ImageIcon paddleImage;

    private ImageIcon level1To3Bg;
    private ImageIcon level4To6Bg;
    private ImageIcon level7To9Bg;
    private ImageIcon level10To12Bg;
    private ImageIcon level13To15Bg;


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

        level1To3Bg = LoadImage.get("/assets/Images/level1-3.png", screenWidth, screenHeight);
        level4To6Bg = LoadImage.get("/assets/Images/level4-6.png", screenWidth, screenHeight);
        level7To9Bg = LoadImage.get("/assets/Images/level7-9.png", screenWidth, screenHeight);
        level10To12Bg = LoadImage.get("/assets/Images/level10-12.png", screenWidth, screenHeight);
        level13To15Bg = LoadImage.get("/assets/Images/level13-15.png", screenWidth, screenHeight);

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
        gameManager.level = 1;
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

    private void doReset() {
        brickManager.reset();
        paddle = gameManager.newDefaultPaddle();
        ball = gameManager.newDefaultBall();
        powerUpList = gameManager.getPowerUpList();
        ball.setBallStuck(true);
        gameCleared = false;
    }
    public void update() {
        if (inputHandler.dPressed) {
            brickManager.clearAllBricks();
            inputHandler.dPressed = false;
        }

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

        // When Game is Over wait user to press R to reset
        if (gameOver) {
            if (!scoreSaved) {
                gameManager.gameOver();
                scoreSaved = true;
            }
            if (inputHandler.resetPressed)
                doReset(true);
            return;
        }

        // Next Level
        if (gameCleared) {
            if (gameManager.level < 15) {
                gameManager.level++;
                doReset();
            } else {
                gameCleared = false;
                inMenu = true;
            }
        }

        if (inputHandler.leftPressed)
            paddle.moveLeft();
        if (inputHandler.rightPressed)
            paddle.moveRight();

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
        }

        for (PowerUp p: powerUpList) {
            if (p.isPowerUp() && p.isExpired(10000)) {
                p.endPowerUp(paddle);
                p.endPowerUp(ball);
                p.end();
            }
        }

        // Condition to reset or end game
        if (gameManager.getLives() <= 0) {
            gameOver = true;
        } else if (brickManager.isAllCleared()) {
            gameCleared = true;
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        int level = brickManager.getLevel();

        if (level <= 3) {
            g2.drawImage(level1To3Bg.getImage(), 0, 0, null);
        } else if (level >= 4 && level <= 6) {
            g2.drawImage(level4To6Bg.getImage(), 0, 0, null);
        } else if (level >= 7 && level <= 9) {
            g2.drawImage(level7To9Bg.getImage(), 0, 0, null);
        } else if (level >= 10 && level <= 12) {
            g2.drawImage(level10To12Bg.getImage(), 0, 0, null);
        } else if (level >= 13 && level <= 15) {
            g2.drawImage(level13To15Bg.getImage(), 0, 0, null);
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

    public void playMusic(int i) {
        soundManager.setFile(i);
        soundManager.play();
        soundManager.loop();
    }

    public void stopMusic() {
        soundManager.stop();
    }

    public void playSFX(int i) {
        soundManager.setFile(i);
        soundManager.play();
    }
}
