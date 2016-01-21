package scripts.SPXAIOCooker.api.Game.Utility;

/**
 * Created by Sphiinx on 1/10/2016.
 */
public class Utility07 {

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

}

