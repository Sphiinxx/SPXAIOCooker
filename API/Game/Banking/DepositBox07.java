package scripts.SPXAIOCooker.API.Game.Banking;

import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Objects;
import org.tribot.api2007.types.RSObject;


/**
 * Created by Sphiinx on 2/16/2016.
 */
public class DepositBox07 {

    /**
     * Checks if the RSPlayer is at a deposit box.
     *
     * @return True if the RSPlayer is at a deposit box; false otherwise.
     */
    public static boolean isAtDepositBox() {
        final String[] banks = new String[]{
                "Bank deposit box"
        };
        RSObject[] bank = Objects.findNearest(15, banks);
        return bank.length > 0 && bank[0].isOnScreen() && bank[0].isClickable();
    }

    /**
     * Opens the Bank deposit box if there is one on screen the deposit box and the deposit box screen isn't open.
     * If you are not in the bank it will walk to one.
     *
     * @return True if successful; false otherwise.
     */
    public static boolean openDepositBox() {
        RSObject[] bank = Objects.findNearest(10, "Bank deposit box");
        if (bank.length > 0 && bank[0].isOnScreen()) {
            if (Clicking.click("Deposit", bank)) {
                Timing.waitCondition(new Condition() {
                    @Override
                    public boolean active() {
                        General.sleep(100);
                        return Banking.isDepositBoxOpen();
                    }
                }, General.random(750, 1000));
            }
        }
        return false;
    }

    /**
     * Gets the option amount.
     *
     * @param amount The amount being deposited.
     * @return The option for depositing.
     */
    private static String getOption(int amount) {
        String op;
        if (amount == 0) {
            op = "All";
        } else if (amount == 5) {
            op = "Five";
        } else if (amount == 1) {
            op = "One";
        } else {
            op = "X";
        }
        return op;
    }

}

