package scripts.SPXAIOCooker.tasks.MakeWine;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSItem;
import scripts.SPXAIOCooker.data.Vars;
import scripts.SPXAIOCooker.framework.Task;

/**
 * Created by Sphiinx on 1/13/2016.
 */
public class CombineItems implements Task {

    @Override
    public void execute() {
        if (Banking.isBankScreenOpen()) {
            closeBank();
        } else {
            combineItems();
        }
    }

    public void closeBank() {
        if (Banking.close()) {
            Timing.waitCondition(new Condition() {
                @Override
                public boolean active() {
                    General.sleep(100);
                    return !Banking.isBankScreenOpen();
                }
            }, General.random(750, 1000));
        }
    }

    public void combineItems() {
        RSItem[] grapes = Inventory.find("Grapes");
        RSItem[] jugs = Inventory.find("Jug of water");
        Vars.get().upText = Game.getUptext();
        Vars.get().cookingInterface = Interfaces.get(309, 3);

        if (Vars.get().upText != null && Vars.get().upText.contains("Use")) {
            if (jugs[0].click("Use Grapes -> Jug of water")) {
                Timing.waitCondition(new Condition() {
                    @Override
                    public boolean active() {
                        General.sleep(100);
                        return Vars.get().cookingInterface != null && !Vars.get().cookingInterface.isHidden(true);
                    }
                }, General.random(750, 1000));
            }
        } else if (Vars.get().cookingInterface != null && !Vars.get().cookingInterface.isHidden(true)) {
            final int CURRENT_LEVEL = Skills.getCurrentLevel(Skills.SKILLS.COOKING);
            if (Vars.get().cookingInterface.click("Make All")) {
                Timing.waitCondition(new Condition() {
                    @Override
                    public boolean active() {
                        General.sleep(100);
                        return Inventory.getCount("Grapes") == 0 || Inventory.getCount("Jug of water") == 0 || Skills.getActualLevel(Skills.SKILLS.COOKING) != CURRENT_LEVEL;
                    }
                }, General.random(17500, 18500));
            }
        } else if (Player.getAnimation() == -1) {
            if (grapes[0].click("Use")) {
                Timing.waitCondition(new Condition() {
                    @Override
                    public boolean active() {
                        General.sleep(100);
                        return Vars.get().upText.contains("Use");
                    }
                }, General.random(750, 1000));
            }
        }
    }

    /*if (Vars.get().upText != null && Vars.get().upText.contains("Use")) {
        if (Vars.get().stove[0].click()) {
            Timing.waitCondition(new Condition() {
                @Override
                public boolean active() {
                    General.sleep(100);
                    return Vars.get().cookingInterface != null && !Vars.get().cookingInterface.isHidden(true);
                }
            }, General.random(2500, 3000));
        }
    } else if (Vars.get().cookingInterface != null && !Vars.get().cookingInterface.isHidden(true)) {
        if (Vars.get().cookingInterface.click("Make All")) {
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
    }*/

    @Override
    public String toString() {
        return "Making wine...";
    }

    @Override
    public boolean validate() {
        return Vars.get().makeWine && Inventory.getCount("Grapes") > 0 && Inventory.getCount("Jug of water") > 0;
    }

}

