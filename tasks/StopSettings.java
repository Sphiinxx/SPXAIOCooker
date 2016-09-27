package scripts.spxaiocooker.tasks;

import org.tribot.api2007.Skills;
import scripts.spxaiocooker.data.Vars;
import scripts.task_framework.framework.Task;
import scripts.task_framework.framework.TaskManager;
import scripts.tribotapi.util.Logging;

/**
 * Created by Sphiinx on 9/27/16.
 */
public class StopSettings implements Task {


    @Override
    public boolean validate() {
        return (Vars.get().level_to_stop > 0 && Skills.SKILLS.COOKING.getActualLevel() >= Vars.get().level_to_stop) || (Vars.get().amount_to_cook > 0 && Vars.get().food_cooked + Vars.get().food_burned >= Vars.get().amount_to_cook);
    }

    @Override
    public void execute() {
        Logging.warning("You have reached your specified stopping point.");
        TaskManager.stopProgram(true);
    }

    @Override
    public String toString() {
        return "";
    }
}

