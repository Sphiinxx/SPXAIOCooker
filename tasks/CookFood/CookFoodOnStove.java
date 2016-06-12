package scripts.SPXAIOCooker.tasks.CookFood;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.*;
import scripts.SPXAIOCooker.data.Vars;
import scripts.SPXAIOCooker.framework.Task;

/**
 * Created by Sphiinx on 1/2/2016.
 */
public class CookFoodOnStove implements Task {

    public void execute() {
        Vars.get().food = Inventory.find(Vars.get().foodId);
        Vars.get().upText = Game.getUptext();
        Vars.get().cookingInterface = Interfaces.get(307, 3);
        cookFoodOnStove();
    }

    public void cookFoodOnStove() {
        if (Vars.get().upText != null && Vars.get().upText.contains("Use Raw") && Player.getAnimation() == -1) {
            if (!Player.isMoving()) {
                if (Vars.get().stove[0].click()) {
                    Timing.waitCondition(new Condition() {
                        @Override
                        public boolean active() {
                            General.sleep(100);
                            General.println("Clicked");
                            return Vars.get().cookingInterface != null && !Vars.get().cookingInterface.isHidden(true);
                        }
                    }, General.random(2800, 3000));
                }
            }


        } else if (Vars.get().cookingInterface != null && !Vars.get().cookingInterface.isHidden(true)) {
            if (Vars.get().cookingInterface.click("Cook All")) {
                Timing.waitCondition(new Condition() {
                    @Override
                    public boolean active() {
                        General.sleep(100);
                        return Player.getAnimation() != -1;
                    }
                }, General.random(750, 1000));
            }
        } else if (Player.getAnimation() == -1) {
            if (Vars.get().food[0].click("Use")) {
                Timing.waitCondition(new Condition() {
                    @Override
                    public boolean active() {
                        General.sleep(100);
                        return Vars.get().upText.contains("Use Raw");
                    }
                }, General.random(750, 1000));
            }
        }
    }

    public String toString() {
        return "Cooking food...";
    }

    public boolean validate() {
        Vars.get().stove = Objects.findNearest(25, "Range");
        return !Vars.get().makeWine && Vars.get().stove.length > 0 && Vars.get().stove[0].isOnScreen() && Inventory.getCount(Vars.get().foodId) > 0;
    }

}

