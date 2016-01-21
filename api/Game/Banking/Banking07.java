package scripts.SPXAIOCooker.api.Game.Banking;

import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Camera;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.types.RSNPC;

/**
 * Created by Sphiinx on 1/10/2016.
 */
public class Banking07 {

    /**
     * Deposits all items in your inventory using the 'Deposit inventory' button.
     * @return The amount of items deposited. Takes item stack into account.
     * */
    public static boolean depositAll() {
        return Banking.depositAll() > 0;
    }

    /**
     * Deposits all of the item specified using the 'Deposit-All' button.
     * @return True if successful; false otherwise.
     * */
    public static boolean depositAllItem(String... itemName) {
        return Banking.deposit(0, itemName);
    }

    /**
     * Deposits all of the itemID specified using the 'Deposit-All' button.
     * @return True if successful; false otherwise.
     * */
    public static boolean depositAllItem(int... itemID) {
        return Banking.deposit(0, itemID);
    }

    /**
     * Opens the bank in the Grand Exchange.
     * @return True if successful; false otherwise.
     * */
    public static boolean openGrandExchangeBank() {
        RSNPC[] banker = NPCs.findNearest("Banker");
        if (banker.length > 0) {
            if (banker[0].isOnScreen()) {
                return Clicking.click("Bank Banker", banker[0]);
            }
        } else {
            Camera.turnToTile(banker[0]);
            Timing.waitCondition(new Condition() {
                @Override
                public boolean active() {
                    return banker[0].isOnScreen();
                }
            }, General.random(1000, 1200));
        }
        return false;
    }

}

