package logic.entity;

import logic.myLogic.BrickType;

public class ExplosiveBrick extends Brick {
    /** Constructor .*/
    public ExplosiveBrick(int x, int y) {
        super(1, BrickType.EXPLOSIVE, x, y);
    }

}
