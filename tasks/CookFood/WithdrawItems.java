package scripts.SPXAIOCooker.tasks.CookFood;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.WebWalking;
import scripts.SPXAIOCooker.data.Vars;
import scripts.SPXAIOCooker.framework.Task;
import TribotAPI.game.banking.Banking07;

/**
 * Created by Sphiinx on 12/26/2015.
 */
public class WithdrawItems implements Task {

    @Override
    public void execute() {
        if (Banking.isInBank()) {
            if (Banking.isBankScreenOpen()) {
                withdrawItems();
            } else {
                openBank();
            }
        } else {
            walkToBank();
        }
    }

    public void withdrawItems() {
        if (Banking07.isBankItemsLoaded()) {
            if (Banking.find(Vars.get().foodId).length > 0) {
                if (Banking.withdraw(0, Vars.get().foodId)) {
                    Timing.waitCondition(new Condition() {
                        @Override
                        public boolean active() {
                            General.sleep(100);
                            return Inventory.getCount(Vars.get().foodId) == 28;
                        }
                    }, General.random(750, 1000));
                }
            } else {
                General.println("We could not find the food requested...");
                General.println("Stopping Script...");
                Vars.get().stopScript = true;
            }
        }
    }

    public void openBank() {
        if (Banking.openBank()) {
            Timing.waitCondition(new Condition() {
                @Override
                public boolean active() {
                    General.sleep(100);
                    return Banking.isBankScreenOpen();
                }
            }, General.random(750, 1000));
        }
    }

    private void walkToBank() {
        if (WebWalking.walkToBank()) {
            Timing.waitCondition(new Condition() {
                @Override
                public boolean active() {
                    General.sleep(100);
                    return Banking.isInBank();
                }
            }, General.random(750, 1000));
        }
    }

    @Override
    public String toString(){
        return "Withdrawing items...";
    }

    @Override
    public boolean validate() {
        return !Vars.get().makeWine && Inventory.getCount(Vars.get().foodId) <= 0;
    }

}

