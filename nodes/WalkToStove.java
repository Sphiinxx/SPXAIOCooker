package scripts.SPXAIOCooker.nodes;

import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.WebWalking;
import scripts.SPXAIOCooker.Variables;
import scripts.SPXAIOCooker.api.Node;

/**
 * Created by Sphiinx on 1/2/2016.
 */
public class WalkToStove extends Node {

    public WalkToStove(Variables v) {
        super(v);
    }

    @Override
    public void execute() {
            WebWalking.walkTo(vars.stove[0]);
    }

    @Override
    public String toString() {
        return "Walking to stove...";
    }

    @Override
    public boolean validate() {
        vars.stove = Objects.findNearest(25, "Range");
        return vars.stove.length > 0 && !vars.stove[0].isOnScreen() && Inventory.getCount(vars.foodId) > 0;
    }

}

