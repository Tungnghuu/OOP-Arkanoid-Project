package myLogic;

import java.util.ArrayList;
import java.util.List;

import brickLayout.BrickLayout;
import entity.Brick;

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
        brickList.clear();
        ArrayList<ArrayList<int[]>> layout = BrickLayout.level1();

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
                    default -> brick = new NormalBrick(x, y);
                }

                row.add(brick);
            }

            brickList.add(row);
        }
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
