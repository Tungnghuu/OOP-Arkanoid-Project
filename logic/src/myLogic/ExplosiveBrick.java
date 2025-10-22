package myLogic;

import entity.Brick;

public class ExplosiveBrick extends Brick {
    /** Toa do cua vien gach Ẽplosive.*/

    /** Constructor .*/
    public ExplosiveBrick(int coordinatesX, int coorrdinatesY) {
        super(1, BrickType.EXPLOSIVE,  coordinatesX, coorrdinatesY);;
    }

}
