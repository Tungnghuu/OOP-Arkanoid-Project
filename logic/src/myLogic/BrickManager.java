package myLogic;

import java.util.ArrayList;
import java.util.List;

import brickLayout.BrickLayout;
import entity.Brick;

public class BrickManager {
    private List<List<Brick>> brickList;
    private int level;

    public int getLevel() {
        return this.level;
    }

    public BrickManager() {
        this.brickList = new ArrayList<>() ;
        initBricks();
    }

    public List<List<Brick>> getBricks() {
        return brickList;
    }

    private void initBricks() {
        brickList.clear();
        ArrayList<ArrayList<int[]>> layout = BrickLayout.level3();

        for (ArrayList<int[]> rowData : layout) {
            List<Brick> row = new ArrayList<>();

            for (int[] brickData : rowData) {
                int x = brickData[0];
                int y = brickData[1];
                int type = brickData[2];

                Brick brick;
                switch (type) {
                    case 1 -> brick = new ExplosiveBrick(x, y);
                    case 2 -> brick = new StrongBrick(x, y);
                    case 3 -> brick = new NormalBrick(x, y);
                    case 4 -> brick = new BonusBrick(x, y);
                    default -> brick = new NormalBrick(x, y);
                }

                row.add(brick);
            }

            brickList.add(row);
        }

        this.level = 3;

        //TODO: fix this
    }

    // Kiểm tra đã phá hết gạch chưa
    public boolean isAllCleared() {
        for (List<Brick> row : brickList) {
            if (!row.isEmpty()) return false;
        }
        return true;
    }

    // Reset lại;
    public void reset() {
        brickList.clear();
        initBricks();
    }
}
