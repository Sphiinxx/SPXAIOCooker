package scripts.spxaiocooker.tasks;

import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.types.RSObject;
import scripts.spxaiocooker.data.Vars;
import scripts.task_framework.framework.Task;
import scripts.tribotapi.game.objects.Objects07;
import scripts.tribotapi.game.timing.Timing07;
import scripts.tribotapi.util.Logging;

/**
 * Created by Sphiinx on 7/28/2016.
 */
public class OpenCookingRoomDoor implements Task {

    private final int DOOR_CLOSED_ID_VARROCK = 11780;
    private final int DOOR_CLOSED_ID_FALADOR = 24050;

    @Override
    public boolean validate() {
        final RSObject door = Objects07.getObjectAt(Vars.get().location.getCookingObjectDoorTile());
        if (door == null)
            return false;

        return door.getID() == DOOR_CLOSED_ID_VARROCK || door.getID() == DOOR_CLOSED_ID_FALADOR;
    }

    @Override
    public void execute() {
        if (Banking.isBankScreenOpen())
            if (Banking.close())
                Timing07.waitCondition(() -> !Banking.isBankScreenOpen(), General.random(1500, 2000));

        final RSObject door = Objects07.getObjectAt(Vars.get().location.getCookingObjectDoorTile());
        if (door == null)
            return;

        if (door.getID() != DOOR_CLOSED_ID_VARROCK && door.getID() != DOOR_CLOSED_ID_FALADOR)
            return;

        if (door.isOnScreen()) {
            if (Clicking.click("Open", door))
                Timing07.waitCondition(() -> door.getID() != DOOR_CLOSED_ID_VARROCK || door.getID() != DOOR_CLOSED_ID_FALADOR, General.random(1500, 2000));
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

