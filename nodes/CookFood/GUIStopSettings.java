package scripts.SPXAIOCooker.nodes.CookFood;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Login;
import scripts.SPXAIOCooker.data.Variables;
import scripts.SPXAIOCooker.api.Framework.Node;

/**
 * Created by Sphiinx on 12/26/2015.
 */
public class GUIStopSettings extends Node {

    public GUIStopSettings(Variables v) {
        super(v);
    }

    @Override
    public void execute() {
        if (vars.totalCookedCount >= vars.amountToStop || vars.currentLvl >= vars.levelToStop) {
            if (Login.getLoginState() == Login.STATE.INGAME) {
                logOut();
            } else {
                General.println("You have reached the stopping point you requested...");
                General.println("Stopping Script...");
                vars.stopScript = true;
            }
        }
    }

    public void logOut() {
        if (Login.logout()) {
            Timing.waitCondition(new Condition() {
                @Override
                public boolean active() {
                    General.sleep(100);
                    return Login.getLoginState() == Login.STATE.LOGINSCREEN;
                }
            }, General.random(1000, 1500));
        }
    }

    @Override
    public boolean validate() {
        return vars.amountToStop != 0 || vars.levelToStop != 0;
    }

}
