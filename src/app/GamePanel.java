package app;

import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
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
    static List<Bullet> bulletList = gameManager.getBulletList();
    private Paddle paddle = gameManager.getPaddle();
    private Ball ball = gameManager.getBall();
    private DrawObject drawPaddle = new DrawObject(paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight(), Color.orange);
    private DrawObject drawBall = new DrawObject(ball.getX(), ball.getY(), ball.getWidth(), ball.getHeight(), Color.GREEN);
    static List<List<Brick>> brickList = brickManager.getBricks();
    private boolean scoreSaved = false;
    private Thread gameThread;

    private boolean winSoundPlayed = false;
    private boolean loseSoundPlayed = false;
    private int currentBgmIndex = -1;

    private ImageIcon ballImage;
    private ImageIcon paddleImage;

    private ImageIcon level1To3Bg;
    private ImageIcon level4To6Bg;
    private ImageIcon level7To9Bg;
    private ImageIcon level10To12Bg;
    private ImageIcon level13To15Bg;

    private HUDRenderer hudRenderer = new HUDRenderer();
    private GameOverOverlay gameOverOverlay = new GameOverOverlay();

    private double drawInterval = 1000000000.0 / 60; // default 60 FPS

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

        ball.setSoundManager(soundManager);
        paddle.setSoundManager(soundManager);
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
        ensureLevelBgm();
    }

    public void setFPS(double fps) {
        this.drawInterval = 1000000000.0 / fps;
    } 

    @Override
    public void run() {
        drawInterval = 1000000000.0 / 60;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                delta--;
            }
            repaint();
        }
    }

    private void doReset(boolean resetScore) {
        gameManager.level = 1;
        gameManager.resetGame(resetScore);
        brickManager.reset();
        paddle.resetPaddle();
        ball.resetBall();
        powerUpList.clear();
        gameOver = false;
        gameCleared = false;
        scoreSaved = false;
        winSoundPlayed = false;
        loseSoundPlayed = false;
        inputHandler.resetPressed = false;
        ensureLevelBgm();
    }

    private void doReset() {
        brickManager.reset();
        paddle.resetPaddle();
        ball.resetBall();
        powerUpList = gameManager.getPowerUpList();
        ball.setBallStuck(true);
        gameCleared = false;
        winSoundPlayed = false;
        ensureLevelBgm();
    }

    // Chọn nhạc nền theo level
    private int chooseBgmForLevel(int level) {
        if (level >= 1 && level <= 3) {
            return 0; // earth theme
        }

        if (level >= 4 && level <= 6) { 
            return 4; // underwater theme
        }

        if (level >= 7 && level <= 9) {
            return 5; // volcano theme
        }

        if (level >= 10 && level <= 12) {
            return 3; // space theme
        }

        if (level >= 13 && level <= 15) {
            return 2; // neon theme
        }

        return 0;
    }

    // Đảm bảo nhạc nền được chọn đúng với level hiện tại
    private void ensureLevelBgm() {
        int level = brickManager.getLevel();
        int targetIndex = chooseBgmForLevel(level);
        if (targetIndex != currentBgmIndex) {
            if (currentBgmIndex != -1) {
                stopBGM();
            }

            soundManager.playBGM(targetIndex);
            currentBgmIndex = targetIndex;
        }
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
                    SwingUtilities.invokeLater(() -> new LeaderTable(GetLeaderBoard.GetLeaderboard()).setVisible(true));
                } else if (menuPanel.getHistoryButton().contains(inputHandler.mouseX, inputHandler.mouseY)) {
                    SwingUtilities.invokeLater(() -> new HistoryTable(GetHistory.getHistory()).setVisible(true));
                }
                inputHandler.mouseClicked = false;
            }
            return;
        }

        soundManager.setVolume(settingPanel.volume / 100f);

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

                if (settingPanel.backToMenu) {
                    doReset(true);
                    inMenu = true;
                    soundManager.stopBGM();
                    settingPanel.backToMenu = false;
                }
            }
            return;
        }

        // When Game is Over wait user to press R to reset
        if (gameOver) {
            if (!loseSoundPlayed) {
                stopBGM();
                currentBgmIndex = -1;
                playSFX(1); // lose
                loseSoundPlayed = true;
            }
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
                this.doReset();
            } else {
                gameCleared = false;
                inMenu = true;
                stopBGM();
                currentBgmIndex = -1;
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
            int bricksBeforeCollision = brickManager.getTotalBricksRemaining();

            ball.updateBall(gameManager);
            gameManager.updateIfCollision(ball, paddle, brickList);
            paddle.checkBallCollision(ball);

            int bricksAfterCollision = brickManager.getTotalBricksRemaining();

            if (bricksAfterCollision < bricksBeforeCollision) {
                playSFX(8); // brick_hit
            }

            if (gameManager.checkCollision(paddle, ball)) {
                playSFX(7);
            }

            ball.updateLives(gameManager);
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
        gameManager.updateBullet(paddle);

        // Condition to reset or end game
        if (gameManager.getLives() <= 0) {
            gameOver = true;
        } else if (brickManager.isAllCleared()) {
            gameCleared = true;
            if (!winSoundPlayed) {
                playSFX(6); // win
                winSoundPlayed = true;
            }
        }

        // nextLevel or gameOver with powerUp
        List<PowerUp> toRemove = new ArrayList<>();
        for (PowerUp p : powerUpList) {
            if (paddle.getBounds().intersects(p.getBounds())) {
                int lives = gameManager.getLives();
                switch (p.getType()) {
                    case GAME_OVER:
                        gameOver = true;
                        break;
                    case NEXT_LEVEL:
                        gameCleared = true;
                        break;
                    case EXTRA_LIFE:
                        if (lives <= 3) {
                            lives++;
                            gameManager.setLives(lives);
                        }
                        break;
                    default:
                        break;
                }
                toRemove.add(p);
            }
        }
        powerUpList.removeAll(toRemove);
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
        renderBullet(g2);

        hudRenderer.draw(g2, gameManager.getScore(), gameManager.getLives());

        if (gameOver || gameCleared) {
            gameOverOverlay.draw(g2, gameOver, getWidth(), getHeight());
        }

        if (inSetting) {
            settingPanel.draw(g2);
        }

        this.setDoubleBuffered(true);
    }

    public void stopBGM() {
        soundManager.stopBGM();
    }

    public void playSFX(int i) {
        soundManager.playSFX(i);
    }
}
