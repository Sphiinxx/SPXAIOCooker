package scripts.SPXAIOCooker.nodes.CookFood;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.WebWalking;
import scripts.SPXAIOCooker.data.Variables;
import scripts.SPXAIOCooker.api.Framework.Node;

/**
 * Created by Sphiinx on 12/26/2015.
 */
public class DepositItems extends Node {

    public DepositItems(Variables v) {
        super(v);
    }

    @Override
    public void execute() {
        vars.status = "Banking...";
        if (Banking.isInBank()) {
            openBank();
        } else {
            walkToBank();
        }
    }

    private void openBank() {
        if (Banking.isBankScreenOpen()) {
            if (Banking.depositAll() > 0) {
                Timing.waitCondition(new Condition() {
                    @Override
                    public boolean active() {
                        General.sleep(100);
                        return !Inventory.isFull();
                    }
                }, General.random(750, 1000));
            }
        } else {
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
        return "Depositing items...";
    }

    @Override
    public boolean validate() {
            return !vars.makeWine && Inventory.isFull() && Inventory.getCount(vars.foodId) <= 0;
    }

}

