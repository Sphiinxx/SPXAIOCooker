package scripts.SPXAIOCooker.tasks;

import org.tribot.api.General;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.types.RSObject;
import scripts.SPXAIOCooker.data.Vars;
import scripts.TaskFramework.framework.Task;
import scripts.TribotAPI.game.objects.Objects07;

/**
 * Created by Sphiinx on 7/28/2016.
 */
public class WalkToCookingObject implements Task {


    @Override
    public boolean validate() {
        final RSObject cooking_object = Objects07.getObjectAt(Vars.get().location.getCookingObjectTile());
        return cooking_object != null && !cooking_object.isOnScreen() || cooking_object == null;
    }

    @Override
    public void execute() {
            WebWalking.walkTo(Vars.get().location.getCookingObjectTile(), new Condition() {
                @Override
                public boolean active() {
                    General.sleep(100);
                    final RSObject cooking_object = Objects07.getObjectAt(Vars.get().location.getCookingObjectTile());
                    return cooking_object != null && cooking_object.isOnScreen();
                }
            }, 250);
    }

    @Override
    public String toString() {
        return "Walking to stove/fire";
    }
}

