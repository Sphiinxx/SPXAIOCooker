package scripts.SPXAIOCooker.API.Game.Utility;

import org.tribot.api2007.Interfaces;
import org.tribot.api2007.types.RSInterface;

/**
 * Created by Sphiinx on 1/10/2016.
 */
public class Utility07 {

    public static final int FRIENDS_LIST_INTERFACE = 429;
    public static final int FRIENDS_LIST_INTERFACE_CHILD = 1;

    /**
     * Formats a number by replacing zeros with 'k' or 'm' dependent on the value of the number.
     * @author Encoded, modified by Sphiinx.
     * @param num The number to format.
     * @return A String of the formatted number.
     */
    public static String formatNumber(double num) {
        if (num < 1000.0) {
            return Integer.toString((int) num);
        } else if (Math.round(num) / 10000.0 < 100.0) {
            return String.format("%.1fk", Math.round(num) / 1000.0);
        } else {
            return String.format("%.1fm", Math.round(num) / 1000000.0);
        }
    }

    /**
     * Gets the current world the Player is in.
     * @return The current world the Player is in.
     * */
    public static int getCurrentWorld() {
        RSInterface friendsList = Interfaces.get(FRIENDS_LIST_INTERFACE, FRIENDS_LIST_INTERFACE_CHILD);
        if (friendsList != null) {
            return Integer.parseInt(friendsList.getText().substring(21));
        } else {
            return -1;
        }
    }

}

