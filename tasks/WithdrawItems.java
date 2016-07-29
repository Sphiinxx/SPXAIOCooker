package scripts.SPXAIOCooker.tasks;

import org.tribot.api.General;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.types.RSItem;
import scripts.SPXAIOCooker.data.Vars;
import scripts.TaskFramework.framework.Task;
import scripts.TaskFramework.framework.TaskManager;
import scripts.TribotAPI.game.banking.Banking07;
import scripts.TribotAPI.game.timing.Timing07;
import scripts.TribotAPI.util.Logging;

/**
 * Created by Sphiinx on 7/28/2016.
 */
public class WithdrawItems implements Task {


    @Override
    public boolean validate() {
        return Inventory.getCount(Vars.get().food_names) <= 0 && !Inventory.isFull();
    }

    @Override
    public void execute() {
        if (Banking07.isBankItemsLoaded()) {
            final RSItem item_to_withdraw = Banking07.findItem(Vars.get().food_names);
            if (item_to_withdraw != null) {
                if (Banking07.withdrawItem(0, item_to_withdraw.getID()))
                    Timing07.waitCondition(() -> Inventory.getCount(Vars.get().food_names) > 0, General.random(1500, 2000));
            } else {
                Logging.warning("We're out of the specified food.");
                TaskManager.stopProgram(true);
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

