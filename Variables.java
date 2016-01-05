package scripts.SPXAIOCooker;

import org.tribot.api.Timing;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSInterfaceChild;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;

/**
 * Created by Sphiinx on 12/26/2015.
 */
public class Variables {

    public boolean guiComplete;
    public boolean stopScript;
    public boolean makeWine;
    public String status;
    public String foodId;
    public String location;
    public double version;
    public int amountToStop;
    public int levelToStop;
    public int cookedCount;
    public int burnedCount;
    public int totalCookedCount = cookedCount + burnedCount;
    public int startLvl;
    public int startXP;
    public final int cookingAnimation = 897;
    public long lastCookingTime = Timing.currentTimeMillis();
    public int currentLvl;
    public RSObject[] stove;
    public RSObject[] fire;
    public RSItem[] food;
    public String upText;
    public RSInterfaceChild cookingInterface;

}

