package scripts.SPXAIOCooker.nodes;

import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.WebWalking;
import scripts.SPXAIOCooker.Variables;
import scripts.SPXAIOCooker.api.Node;

/**
 * Created by Sphiinx on 1/2/2016.
 */
public class WalkToFire extends Node{

    public WalkToFire(Variables v) {
        super(v);
    }

    @Override
    public void execute() {
        WebWalking.walkTo(vars.fire[0]);
    }

    @Override
    public String toString() {
        return "Walking to fire...";
    }

    @Override
    public boolean validate() {
        vars.fire = Objects.findNearest(25, "Fire");
        return vars.fire.length > 0 && !vars.fire[0].isOnScreen() && Inventory.getCount(vars.foodId) > 0;
    }

}

