package scripts.SPXAIOCooker.tasks.MakeWine;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.WebWalking;
import scripts.SPXAIOCooker.data.Vars;
import scripts.SPXAIOCooker.framework.Task;
import TribotAPI.game.banking.Banking07;
import TribotAPI.game.inventory.Inventory07;

/**
 * Created by Sphiinx on 1/13/2016.
 */
public class BankHandler implements Task {

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
        if (Inventory07.getCount() > 0) {
            if (Banking07.depositAll()) {
                General.sleep(100);
                Timing.waitCondition(new Condition() {
                    @Override
                    public boolean active() {
                        return Inventory07.getCount() <= 0;
                    }
                }, General.random(750, 1000));
            }
        }
        if (Banking07.isBankItemsLoaded()) {
            if (Banking.find("Grapes").length > 0) {
                if (Banking.find("Jug of water").length > 0) {
                    if (Banking.withdraw(14, "Grapes") &&  Banking.withdraw(14, "Jug of water")) {
                        Timing.waitCondition(new Condition() {
                            @Override
                            public boolean active() {
                                General.sleep(100);
                                return Inventory.isFull();
                            }
                        }, General.random(750, 1000));
                    }
                } else {
                    General.println("We could not find any Jugs of water...");
                    General.println("Stopping Script...");
                    Vars.get().stopScript = true;
                }
            } else {
                General.println("We could not find any grapes...");
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
        return Vars.get().makeWine && (Inventory.getCount("Grapes") == 0 || Inventory.getCount("Jug of water") == 0);
    }

}

