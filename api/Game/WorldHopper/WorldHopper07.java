package scripts.SPXAIOCooker.API.Game.WorldHopper;

import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSInterfaceChild;
import org.tribot.api2007.types.RSInterfaceComponent;

import java.awt.*;

/**
 * Created by Sphiinx on 1/10/2016.
 */
public class WorldHopper07 {

    public static final int[] MEMBER_WORLDS = new int[] {
            2, 3, 4, 5, 6, 9, 10, 11, 12, 13, 14, 17, 18, 19, 20, 22, 27, 28,
            29, 30, 33, 34, 36, 38, 41, 42, 43, 44, 46, 50, 51, 53, 54, 58,
            59, 60, 61, 62, 65, 66, 67, 68, 69, 70, 73, 74, 75, 76, 77, 78, 86
    };

    public static final int[] FREE_WORLDS = {
            1, 8, 16, 26, 35, 81, 82, 83, 84, 85, 93, 94
    };

    public static final int[] PVP_WORLDS = {
            25, 37
    };

    public static final int[] DEADMAN_WORLDS = {
            45, 52, 57
    };

    /**
     * Gets a random world with the world list specified.
     * @return A random world with the world list specified.
     * */
    public static int getRandomWorld(int... worldList) {
        int world;
        world = worldList[General.random(0, worldList.length - 1)];
        return world;
    }

    /**
     * Switches worldType with the specified world.
     * @return Player has switched worldType.
     * */
    public static boolean switchWorld(int world){
        int realWorld = world % 100;
        if(WorldHopper.getWorld() == world){
            return true;
        }
        if(!GameTab.open(GameTab.TABS.LOGOUT) || !openWorldSwitchInterface() || !moveMouseInsideWorldSwitchInterface()){
            return false;
        }
        RSInterfaceComponent desiredWorld = getWorldInterfaceChild(realWorld);
        return desiredWorld != null && scrollToWorldInterface(desiredWorld) && clickWorldSwitchAndClickYes(desiredWorld) && waitUntilWorld(world);
    }

    /**
     * Opens the world switching interface.
     * @return World switching interface is open.
     * */
    private static boolean openWorldSwitchInterface(){
        if(Interfaces.isInterfaceValid(182)){
            RSInterfaceChild child = Interfaces.get(182, 5);
            if(child == null || !child.click() || !Timing.waitCondition(new Condition() {
                @Override
                public boolean active() {
                    General.sleep(50, 100);
                    return Interfaces.isInterfaceValid(69);
                }
            }, General.random(1200, 2200))){
                return false;
            }
        }
        return true;
    }

    /**
     * Moves your mouse inside the world switching interface.
     * @return True if successful; false otherwise.
     * */
    private static boolean moveMouseInsideWorldSwitchInterface(){
        RSInterfaceChild worldSelectBox = Interfaces.get(69, 4);
        if(worldSelectBox != null){
            Mouse.moveBox(worldSelectBox.getAbsoluteBounds());
            return true;
        }
        return false;
    }

    /**
     * Gets the world interface child.
     * @return The world interface child.
     * */
    private static RSInterfaceComponent getWorldInterfaceChild(int world){
        if(Interfaces.isInterfaceValid(69)) {
            RSInterfaceChild worldInterface = Interfaces.get(69, 7);
            if(worldInterface != null && worldInterface.getChildren()!= null){
                for(RSInterfaceComponent component : worldInterface.getChildren()) {
                    if(component != null && component.getText().equals(String.valueOf(world))) {
                        return worldInterface.getChildren()[component.getIndex() - 2];
                    }
                }
            }
        }
        return null;
    }

    /**
     * Scrolls to the world within the interface.
     * @return True if successful; false otherwise.
     * */
    private static boolean scrollToWorldInterface(RSInterfaceComponent desiredWorld){
        final long timeout = System.currentTimeMillis() + 7000;
        Rectangle worldClickBox;
        while ((worldClickBox = desiredWorld.getAbsoluteBounds()) != null && (worldClickBox.y < 229 || worldClickBox.y > 417)){
            Mouse.scroll(worldClickBox.y < 229);
            General.sleep(50, 100);
            if(System.currentTimeMillis() > timeout){
                return false;
            }
        }
        return worldClickBox != null && worldClickBox.y > 229 && worldClickBox.y < 417;
    }

    /**
     * Clicks the world within the interface.
     * @return True if successful; false otherwise.
     * */
    private static boolean clickWorldSwitchAndClickYes(RSInterfaceComponent desiredWorld){
        return Clicking.click(desiredWorld) && Timing.waitCondition(new Condition() {
            @Override
            public boolean active() {
                General.sleep(100, 300);
                return NPCChat.getOptions() != null;
            }
        }, General.random(2000, 3000)) && NPCChat.selectOption("Yes.", true);
    }

    /**
     * Timeout to wait for the world.
     * @return True if the specified world is equal to the WorldHopper world; otherwise false.
     * */
    private static boolean waitUntilWorld(int world){
        return Timing.waitCondition(new Condition() {
            @Override
            public boolean active() {
                General.sleep(100, 300);
                return WorldHopper.getWorld() == world;
            }
        }, General.random(4000, 6000));
    }

}

