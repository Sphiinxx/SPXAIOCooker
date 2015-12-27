package scripts.SPXAIOCooker.nodes;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSInterfaceChild;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;
import scripts.SPXAIOCooker.Variables;
import scripts.SPXAIOCooker.api.Node;

/**
 * Created by Sphiinx on 12/26/2015.
 */
public class CookFood extends Node {

    private RSObject[] stove;
    private RSObject[] fire;
    private RSItem[] food;
    private String upText;
    private RSInterfaceChild cookingInterface;

    public CookFood(Variables v) {
        super(v);
    }

    @Override
    public void execute() {
        stove = Objects.findNearest(25, "Range");
        fire = Objects.findNearest(25, "Fire");
        food = Inventory.find(vars.foodId);
        upText = Game.getUptext();
        cookingInterface = Interfaces.get(307, 3);
        if (stove.length > 0) {
            if (!stove[0].isOnScreen()) {
                WebWalking.walkTo(stove[0]);
            } else {
                cookFoodOnStove();
            }
        } else if (fire.length > 0) {
            if (!fire[0].isOnScreen()) {
                WebWalking.walkTo(fire[0]);
            } else {
                cookFoodOnFire();
            }
        }
    }

    public void cookFoodOnFire() {
        if (Player.getAnimation() == vars.cookingAnimation) {
            vars.lastCookingTime = Timing.currentTimeMillis();
        } else if (Timing.timeFromMark(vars.lastCookingTime) > 3000) {
             if (upText != null && upText.contains("Use Raw") && Player.getAnimation() == -1) {
                if (fire[0].click()) {
                    Timing.waitCondition(new Condition() {
                        @Override
                        public boolean active() {
                            return cookingInterface != null && !cookingInterface.isHidden(true);
                        }
                    }, General.random(1200, 1500));
                }
            } else if (cookingInterface != null && !cookingInterface.isHidden(true)) {
                if (cookingInterface.click("Cook All")) {
                    Timing.waitCondition(new Condition() {
                        @Override
                        public boolean active() {
                            return Player.getAnimation() == vars.cookingAnimation;
                        }
                    }, General.random(750, 1000));
                }
            } else if (Player.getAnimation() == -1) {
                 if (food[0].click("Use")) {
                     Timing.waitCondition(new Condition() {
                         @Override
                         public boolean active() {
                             return upText.contains("Use Raw");
                         }
                     }, General.random(750, 1000));
                 }
             }
        }
    }

    public void cookFoodOnStove() {
        if (upText != null && upText.contains("Use Raw") && Player.getAnimation() == -1) {
            if (stove[0].click()) {
                Timing.waitCondition(new Condition() {
                    @Override
                    public boolean active() {
                        return cookingInterface != null && !cookingInterface.isHidden(true);
                    }
                }, General.random(1200, 1500));
            }
        } else if (cookingInterface != null && !cookingInterface.isHidden(true)) {
            if (cookingInterface.click("Cook All")) {
                Timing.waitCondition(new Condition() {
                    @Override
                    public boolean active() {
                        return Player.getAnimation() != -1;
                    }
                }, General.random(750, 1000));
            }
        } else if (Player.getAnimation() == -1) {
            if (food[0].click("Use")) {
                Timing.waitCondition(new Condition() {
                    @Override
                    public boolean active() {
                        return upText.contains("Use Raw");
                    }
                }, General.random(750, 1000));
            }
        }
    }

    @Override
    public boolean validate() {
        return Inventory.getCount(vars.foodId) > 0;
    }

}

