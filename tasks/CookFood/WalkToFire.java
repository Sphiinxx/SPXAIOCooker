package scripts.SPXAIOCooker.tasks.CookFood;

import org.tribot.api.General;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.WebWalking;
import scripts.SPXAIOCooker.data.Variables;
import scripts.SPXAIOCooker.API.Framework.Task;

/**
 * Created by Sphiinx on 1/2/2016.
 */
public class WalkToFire extends Task {

    public WalkToFire(Variables v) {
        super(v);
    }

    @Override
    public void execute() {
        WebWalking.walkTo(vars.location.getPosition(), new Condition() {
            @Override
            public boolean active() {
                General.sleep(100);
                return vars.fire[0].isOnScreen();
            }
        }, General.random(50, 100));
    }

    @Override
    public String toString() {
        return "Walking to fire...";
    }

    @Override
    public boolean validate() {
        vars.fire = Objects.findNearest(25, "Fire");
        return !vars.makeWine && vars.fire.length > 0 && !vars.fire[0].isOnScreen() && Inventory.getCount(vars.foodId) > 0;
    }

}

