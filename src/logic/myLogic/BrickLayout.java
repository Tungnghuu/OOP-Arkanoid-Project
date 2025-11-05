package logic.myLogic;

import logic.entity.Brick;

import java.util.ArrayList;
import java.util.Random;

public class BrickLayout {
    // Level 1: OOP map
    public static ArrayList<ArrayList<int[]>> level1() {
        int startX = 40;
        int startY = 50;
        int w = Brick.WIDTH;
        int h = Brick.HEIGHT;
        int[][] grid = {
                {3,1,3,1,0,1,3,2,4,0,3,1,2,4},
                {3,0,0,4,0,2,0,0,1,0,2,0,0,3},
                {1,0,0,3,0,3,0,0,2,0,1,0,0,2},
                {3,0,0,2,0,4,0,0,3,0,4,0,0,1},
                {1,0,0,1,0,1,0,0,2,0,3,4,1,2},
                {4,0,0,4,0,3,0,0,1,0,2,0,0,0},
                {2,0,0,2,0,4,0,0,3,0,1,0,0,0},
                {3,0,0,3,0,1,0,0,2,0,3,0,0,0},
                {4,2,3,1,0,2,4,1,3,0,1,0,0,0},
        };
        ArrayList<ArrayList<int[]>> layout = new ArrayList<>();

        for (int i = 0; i < 9; i++) {
            ArrayList<int[]> row = new ArrayList<>();
            for (int j = 0; j < 14; j++) {
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

    public static ArrayList<ArrayList<int[]>> level2() {
        int startX = 150;
        int startY = 40;
        int w = Brick.WIDTH;
        int h = Brick.HEIGHT;
        ArrayList<ArrayList<int[]>> layout = new ArrayList<>();
        Random rand = new Random();

        for (int i = 0; i < 6; i++) {
            ArrayList<int[]> row = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                int x = startX + j * w;
                int y = startY + i * h;
                int type = (i < 2) ? 1 : (i == 5 ? 2 : 3);

                if (rand.nextDouble() < 0.15) type = 4;
                row.add(new int[]{x, y, type});
            }
            layout.add(row);
        }
        return layout;
    }

    // Level 3: pyramid shape
    public static ArrayList<ArrayList<int[]>> level3() {
        int startX = 200;
        int startY = 40;
        int w = Brick.WIDTH;
        int h = Brick.HEIGHT;
        ArrayList<ArrayList<int[]>> layout = new ArrayList<>();
        Random rand = new Random();

        for (int i = 0; i < 8; i++) {
            ArrayList<int[]> row = new ArrayList<>();
            int offset = i * (w / 2);
            for (int j = 0; j < 8 - i; j++) {
                int x = startX + offset + j * w;
                int y = startY + i * h;
                int type = (i % 2 == 0) ? 2 : 3;
                if (rand.nextDouble() < 0.2) type = 4;
                row.add(new int[]{x, y, type});
            }
            layout.add(row);
        }
        return layout;
    }

    // Level 4: compact square pattern
    public static ArrayList<ArrayList<int[]>> level4() {
        int startX = 240;
        int startY = 60;
        int w = Brick.WIDTH;
        int h = Brick.HEIGHT;
        int[][] grid = {
            {3,1,3,1,3},
            {2,4,2,4,2},
            {1,3,1,3,1},
            {2,4,2,4,2},
            {3,1,3,1,3}
        };
        ArrayList<ArrayList<int[]>> layout = new ArrayList<>();

        for (int i = 0; i < grid.length; i++) {
            ArrayList<int[]> row = new ArrayList<>();
            for (int j = 0; j < grid[i].length; j++) {
                int x = startX + j * w;
                int y = startY + i * h;
                int type = grid[i][j];
                row.add(new int[]{x, y, type});
            }
            layout.add(row);
        }
        return layout;
    }

    // Level 5: random challenge
    public static ArrayList<ArrayList<int[]>> level5() {
        int startX = 120;
        int startY = 40;
        int w = Brick.WIDTH - 3;
        int h = Brick.HEIGHT;
        ArrayList<ArrayList<int[]>> layout = new ArrayList<>();
        Random rand = new Random();

        for (int i = 0; i < 10; i++) {
            ArrayList<int[]> row = new ArrayList<>();
            for (int j = 0; j < 12; j++) {
                int x = startX + j * w;
                int y = startY + i * h;
                int type = rand.nextInt(4) + 1;
                if (rand.nextDouble() < 0.1) type = 4;
                row.add(new int[]{x, y, type});
            }
            layout.add(row);
        }
        return layout;
    }

    // Level 6: diagonal pattern
    public static ArrayList<ArrayList<int[]>> level6() {
        int startX = 120;
        int startY = 40;
        int w = Brick.WIDTH;
        int h = Brick.HEIGHT;
        ArrayList<ArrayList<int[]>> layout = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            ArrayList<int[]> row = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                if (j == i || j == 9 - i) {
                    int x = startX + j * w;
                    int y = startY + i * h;
                    row.add(new int[]{x, y, 3});
                }
            }
            layout.add(row);
        }
        return layout;
    }

    // Level 7: cross shape
    public static ArrayList<ArrayList<int[]>> level7() {
        int startX = 100;
        int startY = 50;
        int w = Brick.WIDTH;
        int h = Brick.HEIGHT;
        ArrayList<ArrayList<int[]>> layout = new ArrayList<>();

        for (int i = 0; i < 9; i++) {
            ArrayList<int[]> row = new ArrayList<>();
            for (int j = 0; j < 11; j++) {
                if (i == 4 || j == 5) {
                    int type = (i + j) % 2 == 0 ? 2 : 3;
                    row.add(new int[]{startX + j * w, startY + i * h, type});
                }
            }
            layout.add(row);
        }
        return layout;
    }

    // Level 8: checkerboard
    public static ArrayList<ArrayList<int[]>> level8() {
        int startX = 140;
        int startY = 40;
        int w = Brick.WIDTH;
        int h = Brick.HEIGHT;
        ArrayList<ArrayList<int[]>> layout = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            ArrayList<int[]> row = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                if ((i + j) % 2 == 0)
                    row.add(new int[]{startX + j * w, startY + i * h, (i + j) % 3 + 1});
            }
            layout.add(row);
        }
        return layout;
    }

    // Level 9: border
    public static ArrayList<ArrayList<int[]>> level9() {
        int startX = 100;
        int startY = 40;
        int w = Brick.WIDTH;
        int h = Brick.HEIGHT;
        int cols = 12, rows = 8;
        ArrayList<ArrayList<int[]>> layout = new ArrayList<>();

        for (int i = 0; i < rows; i++) {
            ArrayList<int[]> row = new ArrayList<>();
            for (int j = 0; j < cols; j++) {
                if (i == 0 || i == rows - 1 || j == 0 || j == cols - 1)
                    row.add(new int[]{startX + j * w, startY + i * h, 2});
            }
            layout.add(row);
        }
        return layout;
    }

    // Level 10: diamond shape
    public static ArrayList<ArrayList<int[]>> level10() {
        int startX = 200;
        int startY = 40;
        int w = Brick.WIDTH;
        int h = Brick.HEIGHT;
        ArrayList<ArrayList<int[]>> layout = new ArrayList<>();

        int[] pattern = {1, 2, 3, 4, 3, 2, 1};
        for (int i = 0; i < pattern.length; i++) {
            ArrayList<int[]> row = new ArrayList<>();
            int bricks = pattern[i];
            int offset = (7 - bricks) * (w / 2);
            for (int j = 0; j < bricks; j++) {
                row.add(new int[]{startX + offset + j * w, startY + i * h, i % 4 + 1});
            }
            layout.add(row);
        }
        return layout;
    }

    // Level 11: wave pattern
    public static ArrayList<ArrayList<int[]>> level11() {
        int startX = 140;
        int startY = 50;
        int w = Brick.WIDTH;
        int h = Brick.HEIGHT;
        ArrayList<ArrayList<int[]>> layout = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            ArrayList<int[]> row = new ArrayList<>();
            for (int j = 0; j < 12; j++) {
                if (Math.sin((i + j) * 0.7) > 0)
                    row.add(new int[]{startX + j * w, startY + i * h, (i + j) % 4 + 1});
            }
            layout.add(row);
        }
        return layout;
    }

    // Level 12: hourglass
    public static ArrayList<ArrayList<int[]>> level12() {
        int startX = 160;
        int startY = 40;
        int w = Brick.WIDTH;
        int h = Brick.HEIGHT;
        ArrayList<ArrayList<int[]>> layout = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            ArrayList<int[]> row = new ArrayList<>();
            int bricks = (i < 4) ? i + 3 : 10 - i;
            int offset = (10 - bricks) * (w / 2);
            for (int j = 0; j < bricks; j++) {
                row.add(new int[]{startX + offset + j * w, startY + i * h, (i + j) % 3 + 1});
            }
            layout.add(row);
        }
        return layout;
    }

    // Level 13: random clusters
    public static ArrayList<ArrayList<int[]>> level13() {
        int startX = 50;
        int startY = 40;
        int w = Brick.WIDTH;
        int h = Brick.HEIGHT;
        Random rand = new Random();
        ArrayList<ArrayList<int[]>> layout = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            ArrayList<int[]> row = new ArrayList<>();
            for (int j = 0; j < 12; j++) {
                if (rand.nextDouble() < 0.4)
                    row.add(new int[]{startX + j * w, startY + i * h, rand.nextInt(4) + 1});
            }
            layout.add(row);
        }
        return layout;
    }

    // Level 14: vertical pillars
    public static ArrayList<ArrayList<int[]>> level14() {
        int startX = 130;
        int startY = 40;
        int w = Brick.WIDTH;
        int h = Brick.HEIGHT;
        ArrayList<ArrayList<int[]>> layout = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            ArrayList<int[]> row = new ArrayList<>();
            for (int j = 0; j < 12; j++) {
                if (j % 3 == 0)
                    row.add(new int[]{startX + j * w, startY + i * h, (i + j) % 4 + 1});
            }
            layout.add(row);
        }
        return layout;
    }

    // Level 15: final chaos
    public static ArrayList<ArrayList<int[]>> level15() {
        int startX = 60;
        int startY = 30;
        int w = Brick.WIDTH - 3;
        int h = Brick.HEIGHT;
        Random rand = new Random();
        ArrayList<ArrayList<int[]>> layout = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            ArrayList<int[]> row = new ArrayList<>();
            for (int j = 0; j < 14; j++) {
                if (rand.nextDouble() < 0.7)
                    row.add(new int[]{startX + j * w, startY + i * h, rand.nextInt(4) + 1});
            }
            layout.add(row);
        }
        return layout;
    }
}
