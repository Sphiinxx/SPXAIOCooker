package scripts.SPXAIOCooker.tasks;

import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.*;
import org.tribot.api2007.types.*;
import scripts.SPXAIOCooker.data.Vars;
import scripts.TaskFramework.framework.Task;
import scripts.TribotAPI.game.inventory.Inventory07;
import scripts.TribotAPI.game.objects.Objects07;
import scripts.TribotAPI.game.timing.Timing07;

/**
 * Created by Sphiinx on 7/28/2016.
 */
public class CookFood implements Task {

    private final int COOKING_INTERFACE = 307;
    private final int COOKING_INTERFACE_CHILD = 3;
    private long last_cook_time;

    @Override
    public boolean validate() {
        final RSObject cooking_object = Objects07.getObjectAt(Vars.get().location.getCookingObjectTile());
        return Inventory.getCount(Vars.get().food_names) > 0 && cooking_object != null && cooking_object.isOnScreen();
    }

    @Override
    public void execute() {
        if (Banking.isBankScreenOpen())
            if (Banking.close())
                Timing07.waitCondition(() -> !Banking.isBankScreenOpen(), General.random(1500, 2000));

        final RSObject cooking_object = Objects07.getObjectAt(Vars.get().location.getCookingObjectTile());
        if (cooking_object == null)
            return;

        cookFood(cooking_object);
    }

    private void cookFood(RSObject cooking_object) {
        if (Player.getAnimation() != -1) {
            last_cook_time = Timing.currentTimeMillis();
        } else if (Timing.timeFromMark(last_cook_time) > 3000) {
            if (Player.isMoving())
                return;

            final RSItem item_to_use = Inventory07.getItem(Vars.get().food_names);
            if (item_to_use == null)
                return;

            final RSInterface cooking_interface = Interfaces.get(COOKING_INTERFACE, COOKING_INTERFACE_CHILD);
            if (cooking_interface != null) {
                if (Clicking.click("Cook All", cooking_interface))
                    Timing07.waitCondition(() -> Player.getAnimation() != -1, General.random(2000, 2500));
            } else if (Game.getUptext() != null && Game.getUptext().contains("Use")) {
                useFoodOnCookingObject(item_to_use, cooking_object);
            } else {
                if (Clicking.click("Use", item_to_use))
                    Timing07.waitCondition(() -> Game.getUptext() != null && Game.getUptext().contains("Use"), General.random(2000, 2500));
            }
        }
    }

    private void useFoodOnCookingObject(RSItem item_to_use, RSObject cooking_object) {
        final RSItemDefinition item_definition = item_to_use.getDefinition();
        final RSObjectDefinition cooking_object_definition = cooking_object.getDefinition();

        if (item_definition != null && cooking_object_definition != null) {
            final String item_definition_name = item_definition.getName();
            final String cooking_object_definition_name = cooking_object_definition.getName();

            if (item_definition_name != null && cooking_object_definition_name != null) {
                if (Clicking.click("Use " + item_definition_name + " -> " + cooking_object_definition_name, cooking_object))
                    Timing07.waitCondition(() -> Interfaces.get(COOKING_INTERFACE, COOKING_INTERFACE_CHILD) != null, General.random(3500, 4000));
            }
        }
    }

    @Override
    public String toString() {
        return "Cooking food";
    }
}

