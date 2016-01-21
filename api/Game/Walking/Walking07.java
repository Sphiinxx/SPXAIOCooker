package scripts.SPXAIOCooker.api.Game.Walking;

import org.tribot.api.General;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;

/**
 * Created by Sphiinx on 1/10/2016.
 */
public class Walking07 {

    /**
     * Checks if a position can be randomized by an offset.
     * @author xCode, modified by Sphiinx.
     * @param area The area in which to check if the tile exists.
     * @param pos The position of the object to walk to.
     * @param offset The offset in which to randomise by.
     * @return RandomTile if successful; null otherwise.
     * */
    public static RSTile randomPosition(RSArea area, RSTile pos, int offset) {
        for (int i = 0; i < 10; i++) {
            RSTile randomTile = new RSTile(General.random(pos.getX() - offset , pos.getX() + offset), General.random(pos.getY() - offset, pos.getY() + offset));
            if (area.contains(randomTile)) {
                return randomTile;
            }
        }
        return null;
    }

}

