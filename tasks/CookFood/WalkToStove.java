package scripts.SPXAIOCooker.tasks.CookFood;

import org.tribot.api.General;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.WebWalking;
import scripts.SPXAIOCooker.data.Vars;
import scripts.SPXAIOCooker.framework.Task;

/**
 * Created by Sphiinx on 1/2/2016.
 */
public class WalkToStove implements Task {

    @Override
    public void execute() {
        WebWalking.walkTo(Vars.get().location.getPosition(), new Condition() {
            @Override
            public boolean active() {
                General.sleep(100);
                return Vars.get().stove[0].isOnScreen();
            }
        }, General.random(50, 100));
    }

    @Override
    public String toString() {
        return "Walking to stove...";
    }

    @Override
    public boolean validate() {
        Vars.get().stove = Objects.findNearest(25, "Range");
        return !Vars.get().makeWine && Vars.get().stove.length > 0 && !Vars.get().stove[0].isOnScreen() && Inventory.getCount(Vars.get().foodId) > 0;
    }

}

