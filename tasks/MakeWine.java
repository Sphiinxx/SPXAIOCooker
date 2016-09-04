package scripts.spxaiocooker.tasks;

import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.*;
import org.tribot.api2007.types.*;
import scripts.task_framework.framework.Task;
import scripts.tribotapi.game.inventory.Inventory07;
import scripts.tribotapi.game.timing.Timing07;

/**
 * Created by Sphiinx on 7/29/2016.
 */
public class MakeWine implements Task {

    private final int COOKING_INTERFACE = 309;
    private final int COOKING_INTERFACE_CHILD = 3;

    private long last_inventory_check;
    private boolean should_make_wine;
    public RSItem[] inventory_cache;

    @Override
    public boolean validate() {
        return Inventory.getCount("Grapes") > 0 && Inventory.getCount("Jug of water") > 0;
    }

    @Override
    public void execute() {
        if (Banking.isBankScreenOpen())
            if (Banking.close())
                Timing07.waitCondition(() -> !Banking.isBankScreenOpen(), General.random(1500, 2000));

        makeWine();
    }

    private void makeWine() {
        if (last_inventory_check <= 0) {
            last_inventory_check = Timing.currentTimeMillis();
            General.println("Set true");
            should_make_wine = true;
        }

        if (Timing.timeFromMark(last_inventory_check) > 3000 && !should_make_wine) {
            inventory_cache = Inventory.getAll();
            last_inventory_check = Timing.currentTimeMillis();
            if (inventory_cache != null) {
                if (inventory_cache.length != Inventory.getAll().length) {
                    General.println("Set true");
                    should_make_wine = true;
                }
            }

        }


        if (should_make_wine) {
            final RSItem item_to_use = Inventory07.getItem("Grapes");
            if (item_to_use == null)
                return;

            final RSInterface cooking_interface = Interfaces.get(COOKING_INTERFACE, COOKING_INTERFACE_CHILD);
            if (cooking_interface != null) {
                final RSItem[] inventory_check = Inventory.getAll();
                if (Clicking.click("Make All", cooking_interface))
                    if (Timing07.waitCondition(() -> inventory_check.length != Inventory.getAll().length, General.random(2500, 3000))) {
                        General.sleep(500);
                        should_make_wine = false;
                    }
            } else if (Game.getUptext() != null && Game.getUptext().contains("Use")) {
                useGrapeOnCookingJug(item_to_use);
            } else {
                if (Clicking.click("Use", item_to_use))
                    Timing07.waitCondition(() -> Game.getUptext() != null && Game.getUptext().contains("Use"), General.random(2000, 2500));
            }

        }
    }

    private void useGrapeOnCookingJug(RSItem item_to_use) {
        final RSItem jug_of_water = Inventory07.getItem("Jug of water");

        final RSItemDefinition item_definition = item_to_use.getDefinition();
        final RSItemDefinition cooking_object_definition = jug_of_water.getDefinition();

        if (item_definition != null && cooking_object_definition != null) {
            final String item_definition_name = item_definition.getName();
            final String cooking_object_definition_name = cooking_object_definition.getName();

            if (item_definition_name != null && cooking_object_definition_name != null) {
                if (Clicking.click("Use " + item_definition_name + " -> " + cooking_object_definition_name, jug_of_water))
                    Timing07.waitCondition(() -> Interfaces.get(COOKING_INTERFACE, COOKING_INTERFACE_CHILD) != null, General.random(3500, 4000));
            }
        }
    }

    @Override
    public String toString() {
        return "Making wine";
    }
}

