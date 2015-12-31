package scripts.SPXAIOCooker;

import org.tribot.api.Timing;
import org.tribot.api2007.Skills;

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
    public final int startLvl = Skills.getActualLevel(Skills.SKILLS.COOKING);
    public final int startXP = Skills.getXP(Skills.SKILLS.COOKING);
    public final long startTime = System.currentTimeMillis();
    public final int cookingAnimation = 897;
    public long lastCookingTime = Timing.currentTimeMillis();
    public int currentLvl;

}

