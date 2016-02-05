package scripts.SPXAIOCooker.tasks.CookFood;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.*;
import scripts.SPXAIOCooker.API.Framework.Task;
import scripts.SPXAIOCooker.data.Variables;

/**
 * Created by Sphiinx on 1/2/2016.
 */
public class CookFoodOnFire extends Task {

    public CookFoodOnFire(Variables v) {
        super(v);
    }

    @Override
    public void execute() {
        vars.food = Inventory.find(vars.foodId);
        vars.upText = Game.getUptext();
        vars.cookingInterface = Interfaces.get(307, 3);
        if (Banking.isBankScreenOpen()) {
            if (Banking.close()) {
                Timing.waitCondition(new Condition() {
                    @Override
                    public boolean active() {
                        General.sleep(100);
                        return !Banking.isBankScreenOpen();
                    }
                }, General.random(750, 1000));
            }
        } else {
            cookFoodOnFire();
        }
    }

    public void cookFoodOnFire() {
        if (Player.getAnimation() == vars.cookingAnimation) {
            vars.lastCookingTime = Timing.currentTimeMillis();
        } else if (Timing.timeFromMark(vars.lastCookingTime) > 3000) {
            if (vars.upText != null && vars.upText.contains("Use Raw") && Player.getAnimation() == -1) {
                if (vars.fire[0].click()) {
                    Timing.waitCondition(new Condition() {
                        @Override
                        public boolean active() {
                            General.sleep(100);
                            return vars.cookingInterface != null && !vars.cookingInterface.isHidden(true);
                        }
                    }, General.random(2500, 3000));
                }
            } else if (vars.cookingInterface != null && !vars.cookingInterface.isHidden(true)) {
                if (vars.cookingInterface.click("Cook All")) {
                    Timing.waitCondition(new Condition() {
                        @Override
                        public boolean active() {
                            General.sleep(100);
                            return Player.getAnimation() == vars.cookingAnimation;
                        }
                    }, General.random(750, 1000));
                }
            } else if (Player.getAnimation() == -1) {
                if (vars.food[0].click("Use")) {
                    Timing.waitCondition(new Condition() {
                        @Override
                        public boolean active() {
                            General.sleep(100);
                            return vars.upText.contains("Use Raw");
                        }
                    }, General.random(750, 1000));
                }
            }

        }
    }

    @Override
    public String toString() {
        return "Cooking food...";
    }

    @Override
    public boolean validate() {
        vars.fire = Objects.findNearest(25, "Fire");
        return !vars.makeWine && vars.fire.length > 0 && vars.fire[0].isOnScreen() && Inventory.getCount(vars.foodId) > 0;
    }

}

