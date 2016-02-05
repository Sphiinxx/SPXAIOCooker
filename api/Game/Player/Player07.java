package scripts.SPXAIOCooker.API.Game.Player;

import org.tribot.api2007.Game;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.types.RSInterface;


/**
 * Created by Sphiinx on 1/10/2016.
 */
public class Player07 {

    /**
     * Checks if the player is poisoned.
     * @return True if poisoned; false otherwise.
     * */
    public static boolean isPoisoned() {
        return Game.getSetting(102) > 0;
    }

    /**
     * Gets the players combat level. Note - I realised there was an API method for this after I wrote it. I'm just going to leave it here...
     * @return The players combat level.
     * */
    public static int getCombatLevel() {
        RSInterface level = Interfaces.get(593, 2);
        if (level != null) {
            String text = level.getText();
            if (text != null) {
                text = text.replace("Combat Lvl: ", "");
                int parse = Integer.parseInt(text);
                if (parse > 0) {
                    return parse;
                }
            }
        }
        return -1;
    }

}

