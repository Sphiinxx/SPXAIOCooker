package scripts.SPXAIOCooker.nodes.CookFood;

import org.tribot.api.General;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.WebWalking;
import scripts.SPXAIOCooker.data.Variables;
import scripts.SPXAIOCooker.api.Framework.Node;

/**
 * Created by Sphiinx on 1/2/2016.
 */
public class WalkToStove extends Node {

    public WalkToStove(Variables v) {
        super(v);
    }

    @Override
    public void execute() {
        WebWalking.walkTo(vars.location.getPosition(), new Condition() {
            @Override
            public boolean active() {
                General.sleep(100);
                return vars.stove[0].isOnScreen();
            }
        }, General.random(50, 100));
    }

    @Override
    public String toString() {
        return "Walking to stove...";
    }

    @Override
    public boolean validate() {
        vars.stove = Objects.findNearest(25, "Range");
        return !vars.makeWine && vars.stove.length > 0 && !vars.stove[0].isOnScreen() && Inventory.getCount(vars.foodId) > 0;
    }

}

