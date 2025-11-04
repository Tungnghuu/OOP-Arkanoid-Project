package logic.entity;

import logic.myLogic.BrickType;

public class BonusBrick extends Brick {

    public BonusBrick(int x, int y) {
        super(1, BrickType.BONUS, x, y);
    }
}
