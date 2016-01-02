package scripts.SPXAIOCooker.nodes;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.*;
import scripts.SPXAIOCooker.Variables;
import scripts.SPXAIOCooker.api.Node;

/**
 * Created by Sphiinx on 1/2/2016.
 */
public class CookFoodOnStove extends Node {

    public CookFoodOnStove(Variables v) {
        super(v);
    }

    @Override
    public void execute() {
        vars.food = Inventory.find(vars.foodId);
        vars.upText = Game.getUptext();
        vars.cookingInterface = Interfaces.get(307, 3);
        cookFoodOnStove();
    }

    public void cookFoodOnStove() {
        if (vars.upText != null && vars.upText.contains("Use Raw") && Player.getAnimation() == -1) {
            if (vars.stove[0].click()) {
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
                        return Player.getAnimation() != -1;
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

    @Override
    public String toString() {
        return "Cooking food...";
    }

    @Override
    public boolean validate() {
        vars.stove = Objects.findNearest(25, "Range");
        return vars.stove.length > 0 && vars.stove[0].isOnScreen() && Inventory.getCount(vars.foodId) > 0;
    }

}

