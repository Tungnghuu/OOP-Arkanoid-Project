package logic.myLogic;

import logic.entity.Brick;

public class ExplosiveBrick extends Brick {
    /** Constructor .*/
    public ExplosiveBrick(int coordinatesX, int coorrdinatesY) {
        super(1, BrickType.EXPLOSIVE,  coordinatesX, coorrdinatesY);;
    }

}
