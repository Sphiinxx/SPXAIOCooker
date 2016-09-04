package scripts.spxaiocooker.tasks;

import org.tribot.api.General;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.types.RSItem;
import scripts.spxaiocooker.data.Vars;
import scripts.task_framework.framework.Task;
import scripts.task_framework.framework.TaskManager;
import scripts.tribotapi.game.banking.Banking07;
import scripts.tribotapi.game.timing.Timing07;
import scripts.tribotapi.util.Logging;

/**
 * Created by Sphiinx on 7/28/2016.
 */
public class WithdrawItems implements Task {

    private String item_name;

    @Override
    public boolean validate() {
        if (Inventory.isFull())
            return false;

        if (Vars.get().is_making_wine) {
            if (Inventory.getCount("Grapes") <= 0) {
                item_name = "Grapes";
                return true;
            } else if (Inventory.getCount("Jug of water") <= 0) {
                item_name = "Jug of water";
                return true;
            }
        } else {
            return Inventory.getCount(Vars.get().food_names) <= 0;
        }

        return false;
    }

    @Override
    public void execute() {
        if (Banking07.isBankItemsLoaded()) {
            if (Vars.get().is_making_wine) {
                final RSItem item_to_withdraw = Banking07.findItem(item_name);
                if (item_to_withdraw != null) {
                    final RSItem[] inventory_cache = Inventory.getAll();
                    if (Banking07.withdrawItem(14, item_to_withdraw.getID()))
                        Timing07.waitCondition(() -> inventory_cache.length != Inventory.getAll().length, General.random(1500, 2000));
                } else {
                    Logging.warning("We're out of items to make wine.");
                    TaskManager.stopProgram(true);
                }
            } else {
                final RSItem item_to_withdraw = Banking07.findItem(Vars.get().food_names);
                if (item_to_withdraw != null) {
                    final RSItem[] inventory_cache = Inventory.getAll();
                    if (Banking07.withdrawItem(0, item_to_withdraw.getID()))
                        Timing07.waitCondition(() -> inventory_cache.length != Inventory.getAll().length, General.random(1500, 2000));
                } else {
                    Logging.warning("We're out of the specified food.");
                    TaskManager.stopProgram(true);
                }
            }
        } else {
            if (!Banking07.isInBank())
                WebWalking.walkToBank();

            if (Banking.openBank())
                Timing07.waitCondition(Banking07::isBankItemsLoaded, General.random(1500, 2000));
        }
    }

    @Override
    public String toString() {
        return "Withdrawing items";
    }

}

