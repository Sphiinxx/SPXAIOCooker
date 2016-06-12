package scripts.SPXAIOCooker.data;

import org.tribot.api.Timing;
import org.tribot.api2007.types.RSInterfaceChild;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;
import scripts.SPXAIOCooker.data.enums.Location;

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

    public boolean guiComplete;
    public boolean stopScript;
    public boolean makeWine;
    public String status;
    public String foodId;
    public double version;
    public int amountToStop;
    public int levelToStop;
    public int cookedCount;
    public int burnedCount;
    public int totalCookedCount = cookedCount + burnedCount;
    public int startLvl;
    public int startXP;
    public int gainedXP;
    public int gainedLvl;
    public final int cookingAnimation = 897;
    public long lastCookingTime = Timing.currentTimeMillis();
    public long timeRan;
    public int currentLvl;
    public RSObject[] stove;
    public RSObject[] fire;
    public RSItem[] food;
    public String upText;
    public RSInterfaceChild cookingInterface;
    public Location location;

}
