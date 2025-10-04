import java.util.ArrayList;
import java.util.List;
/** Lop quan ly game. */
public class GameManager {
    /**
     * cac thuoc tinh.
     */
    private Paddle paddle;
    private Ball ball;
   // private List<Brick> brickList;
    private int score;
    private int lives;


    /**
     * Constructor cua GameManager.
     */
    public GameManager() {
        paddle = new Paddle(1, 0, 5, 324, 526, 20, 120);
        ball = new Ball(6, 1, 1, 374, 506, 10);
       // bricks = new ArrayList<>();
        this.score = 0;
        this.lives = 3;
    }

    /** geter cua cac thuoc tinh.*/
    public Paddle getPaddle() {
        return new Paddle(this.paddle);
    }

    public Ball getBall() {
        return  new Ball(this.ball);
    }

  /*  public List<Brick> getBricks() {
        return brickList;
    }*/

}


