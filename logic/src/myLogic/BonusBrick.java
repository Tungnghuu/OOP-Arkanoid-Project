package myLogic;

import entity.Brick;

public class BonusBrick extends Brick {

    public BonusBrick(int x, int y) {
        super(1, BrickType.BONUS, x, y);
    }
}
