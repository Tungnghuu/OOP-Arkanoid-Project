package myLogic;

import entity.Brick;

public class StrongBrick extends Brick {
    /** constructor cua strongBrick.*/
    public StrongBrick(int x, int y) {
        super(3, BrickType.STRONG, x, y);
    }
}
