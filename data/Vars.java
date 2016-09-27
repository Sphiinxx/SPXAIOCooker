package scripts.spxaiocooker.data;

import scripts.spxaiocooker.data.enums.Location;

/**
 * Created by Sphiinx on 12/26/2015.
 */
public class Vars {

    public static Vars vars;

    public static Vars get() {
        return vars == null ? vars = new Vars() : vars;
    }

    public static void reset() {
        vars = null;
    }

    public boolean is_making_wine;
    
    public String[] food_names;

    public double amount_to_cook;
    public double level_to_stop;

    public int food_cooked;
    public int food_burned;

    public Location location;

}

