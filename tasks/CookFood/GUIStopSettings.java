package scripts.SPXAIOCooker.tasks.CookFood;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Login;
import scripts.SPXAIOCooker.data.Vars;
import scripts.SPXAIOCooker.framework.Task;


/**
 * Created by Sphiinx on 12/26/2015.
 */
public class GUIStopSettings implements Task {

    @Override
    public void execute() {
        if (Vars.get().totalCookedCount >= Vars.get().amountToStop || Vars.get().currentLvl >= Vars.get().levelToStop) {
            if (Login.getLoginState() == Login.STATE.INGAME) {
                logOut();
            } else {
                General.println("You have reached the stopping point you requested...");
                General.println("Stopping Script...");
                Vars.get().stopScript = true;
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
        return Vars.get().amountToStop != 0 || Vars.get().levelToStop != 0;
    }

}

