package scripts.SPXAIOCooker.tasks;

import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.types.RSObject;
import scripts.SPXAIOCooker.data.Vars;
import scripts.TaskFramework.framework.Task;
import scripts.TribotAPI.game.objects.Objects07;
import scripts.TribotAPI.game.timing.Timing07;

/**
 * Created by Sphiinx on 7/28/2016.
 */
public class OpenCookingRoomDoor implements Task {

    private final int DOOR_CLOSED_ID = 24050;

    @Override
    public boolean validate() {
        final RSObject door = Objects07.getObjectAt(Vars.get().location.getCookingObjectDoorTile());
        if (door == null)
            return false;

        return door.getID() == DOOR_CLOSED_ID;
    }

    @Override
    public void execute() {
        if (Banking.isBankScreenOpen())
            if (Banking.close())
                Timing07.waitCondition(() -> !Banking.isBankScreenOpen(), General.random(1500, 2000));

        final RSObject door = Objects07.getObjectAt(Vars.get().location.getCookingObjectDoorTile());
        if (door == null)
            return;

        if (door.getID() != DOOR_CLOSED_ID)
            return;

        if (door.isOnScreen()) {
            if (Clicking.click("Open", door)) {
                Timing07.waitCondition(() -> door.getID() != DOOR_CLOSED_ID, General.random(1500, 2000));
            }
        } else {
            WebWalking.walkTo(door, new Condition() {
                @Override
                public boolean active() {
                    final RSObject door = Objects07.getObjectAt(Vars.get().location.getCookingObjectDoorTile());
                    return door != null && door.isOnScreen();
                }
            }, 250);
        }
    }

    @Override
    public String toString() {
        return "Opening cooking room door";
    }
}

