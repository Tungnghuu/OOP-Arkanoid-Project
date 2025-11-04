package logic.entity;

import logic.myLogic.BrickType;

public class NormalBrick extends Brick {
     /** Constructor cua NormalBrick.*/
     public NormalBrick(int x, int y) {
        super(1, BrickType.NORMAL, x, y);
     }
}
