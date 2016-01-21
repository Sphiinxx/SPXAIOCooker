package scripts.SPXAIOCooker.api.Game.Combat;

import org.tribot.api2007.Combat;
import org.tribot.api2007.Game;
import org.tribot.api2007.Player;

/**
 * Created by Sphiinx on 1/10/2016.
 */
public class Combat07 {

    /**
     * Checks if the Special Attack is selected.
     * @return True if Special Attack is selected; false otherwise.
     * */
    public static boolean isSpecialSelected() {
        return Game.getSetting(301) == 1;
    }

    /**
     * Checks the players Special Attack percent.
     * @return The players Special Attack percent.
     * */
    public static int getSpecialPercent() {
        return Game.getSetting(300) / 10;
    }

    /**
     * Checks if the player is in combat.
     * @return True if in combat; false otherwise.
     * */
    public static boolean isInCombat() {
        return Combat.getAttackingEntities().length > 0 || Player.getRSPlayer().isInCombat() || Combat.isUnderAttack();
    }

}

