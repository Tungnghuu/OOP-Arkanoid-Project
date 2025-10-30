package logic.brickLayout;

import logic.entity.Brick;

import java.util.ArrayList;
import java.util.Random;

public class BrickLayout {
    protected static int startX = 184;
    protected static int startY = 35;
    protected static int w = Brick.WIDTH;
    protected static int h = Brick.HEIGHT;

    public static ArrayList<ArrayList<int[]>> level1() {
        ArrayList<ArrayList<int[]>> layout = new ArrayList<>();

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

    public static ArrayList<ArrayList<int[]>> level3() {
        ArrayList<ArrayList<int[]>> layout = new ArrayList<>();
        Random rand = new Random();

        for (int i = 0; i < 8; i++) {
            ArrayList<int[]> row = new ArrayList<>();
            for (int j = 0; j < 8; j++) {
                int x = startX + j * w;
                int y = startY + i * h;

                int type = 0;


                if (j >= 7 - i) {
                    if (rand.nextDouble() < 0.1) {
                        type = 4;
                    } else {
                        int colorIndex = (i + j) % 3;
                        if (colorIndex == 0) type = 1;
                        else if (colorIndex == 1) type = 3;
                        else type = 2;
                    }
                }

                if (type != 0)
                    row.add(new int[]{x, y, type});
            }
            layout.add(row);
        }

        return layout;
    }

    public static ArrayList<ArrayList<int[]>> level4() {
       int[][] grid =   {{5,1,5,5,5,5,5,1,5},
                         {5,1,5,5,5,5,5,1,5},
                         {5,5,1,5,5,5,1,5,5},
                         {5,5,2,3,2,3,2,5,5},
                         {5,2,3,2,3,2,3,2,5},
                         {2,2,4,2,3,2,4,2,2},
                         {2,2,4,2,3,2,4,2,2},
                         {2,3,2,3,2,4,2,4,2},
                         {2,4,2,3,2,3,2,4,2},
                         {2,3,2,3,2,1,2,1,2},
                         {5,2,5,5,5,5,5,2,5},
                         {5,2,3,2,5,2,1,2,5}};

       ArrayList<ArrayList<int[]>> layout = new ArrayList<>();
       for (int i = 0; i < 12; i++) {
           ArrayList<int[]> row = new ArrayList<>();
           for (int j = 0; j < 9; j++) {
               int x = startX + j * w;
               int y = startY + i * h;
               int type = grid[i][j];
               if (type != 5)
                   row.add(new int[]{x, y, type});
           }
           layout.add(row);
       }
       return layout;
    }

    public static ArrayList<ArrayList<int[]>> level5() {
        int[][] grid = {{3,0,1,0,2,0,1,0,3},
                        {2,0,2,0,2,0,2,0,2},
                        {0,3,0,4,0,1,0,4,0},
                        {0,2,0,2,0,2,0,2,0},
                        {1,0,3,0,4,0,3,0,1},
                        {2,0,2,0,2,0,2,0,2},
                        {0,3,0,2,0,1,0,4,0},
                        {0,2,0,2,0,2,0,2,0},
                        {1,0,3,0,1,0,4,0,3},
                        {2,0,2,0,2,0,2,0,2}};
        ArrayList<ArrayList<int[]>> layout = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ArrayList<int[]> row = new ArrayList<>();
            for (int j = 0; j < 9; j++) {
                int x = startX + j * w;
                int y = startY + i * h;
                int type = grid[i][j];
                if (type != 0) {
                    row.add(new int[]{x, y, type});
                }
            }
            layout.add(row);
        }
        return layout;
    }
}
