package myLogic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BrickManager {
    private List<List<Brick>> brickList;

    public BrickManager() {
        this.brickList = new ArrayList<>() ;
        initBricks();
    }
    public List<List<Brick>> getBricks() {
        return brickList;
    }

    private void initBricks() {
        int startX = 184;
        int startY = 50;

        for (int i = 0; i < 8; i++) {
            List<Brick> row = new ArrayList<>();
            for (int j = 0; j < 8; j++) {
                int x = startX + j * Brick.WIDTH ;
                int y = startY + i * Brick.HEIGHT;

                // Sinh loại ngẫu nhiên:
                Brick brick;
                switch (new Random().nextInt(5)) {
                    case 0 -> brick = new NormalBrick(x, y);
                    case 1 -> brick = new StrongBrick(x, y);
                    case 3 -> brick = new ExplosiveBrick(x, y);
                    default -> brick = new NormalBrick(x, y);
                }
                row.add(brick);
            }
            brickList.add(row);
        }
    }

}
