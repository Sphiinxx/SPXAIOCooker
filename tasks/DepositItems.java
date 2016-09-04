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
public class DepositItems implements Task {

    @Override
    public boolean validate() {
        return (Inventory.getCount("Grapes") <= 0 && Inventory.getCount("Jug of water") <= 0 && Inventory.getCount("Jug of wine") > 0) || (!Vars.get().is_making_wine && Inventory.getCount(Vars.get().food_names) <= 0 && Inventory.isFull());
    }

    @Override
    public void execute() {
        if (Banking07.isBankItemsLoaded()) {
            final RSItem[] inventory_cache = Inventory.getAll();
            if (Banking.depositAll() > 0) {
                Timing07.waitCondition(() -> inventory_cache.length != Inventory.getAll().length, General.random(1500, 2000));
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

