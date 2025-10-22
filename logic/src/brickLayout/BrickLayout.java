package brickLayout;

import java.util.ArrayList;

import entity.Brick;

public class BrickLayout {
    public static ArrayList<ArrayList<int[]>> level1() {
        ArrayList<ArrayList<int[]>> layout = new ArrayList<>();

        int startX = 184;
        int startY = 50;
        int w = Brick.WIDTH, h = Brick.HEIGHT;

        for (int i = 0; i < 8; i++) {
            ArrayList<int[]> row = new ArrayList<>();
            for (int j = 0; j < 8; j++) {
                int x = startX + j * w;
                int y = startY + i * h;

                int type;
                if (i < 2) type = 1;
                else if (i == 7) type = 2;
                else type = 0;

                row.add(new int[]{x, y, type});
            }
            layout.add(row);
        }

        return layout;
    }

    public static ArrayList<ArrayList<int[]>> level2() {
        ArrayList<ArrayList<int[]>> layout = new ArrayList<>();
        int startX = 184;
        int startY = 50;
        int w = 40, h = 20;

        for (int i = 0; i < 8; i++) {
            ArrayList<int[]> row = new ArrayList<>();
            for (int j = 0; j < 8; j++) {
                int x = startX + j * w;
                int y = startY + i * h;

                int type = (i + j) % 3;
                row.add(new int[]{x, y, type});
            }
            layout.add(row);
        }

        return layout;
    }
}
