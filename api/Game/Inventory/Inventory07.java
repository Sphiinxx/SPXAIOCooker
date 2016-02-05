package scripts.SPXAIOCooker.API.Game.Inventory;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Keyboard;
import org.tribot.api.input.Mouse;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.*;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSItem;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by Sphiinx on 1/10/2016.
 */
public class Inventory07 {

    /**
     * Gets the amount of items in the players inventory.
     * @return How many items are in the players inventory.
     */
    public static int getCount() {
        return Inventory.getAll().length;
    }

    /**
     * Gets the amount of free space in the players inventory.
     * @return How many free spaces is in the players inventory.
     */
    public static int getAmountOfSpace() {
        return 28 - Inventory.getAll().length;
    }

    /**
    * Checks if we have the item specified.
    * @return True if we have the item, false otherwise.
    * @param itemNames The names of the items to check.
    * */
    public static boolean hasItem(String... itemNames) {
        return Inventory.getCount(itemNames) > 0;
    }

    /**
    * Checks if we have the item specified.
    * @return True if we have the item; false otherwise.
    * @param itemIDs The IDs of the items to check.
    * */
    public static boolean hasItem(int... itemIDs) {
        return Inventory.getCount(itemIDs) > 0;
    }

    /**
     * Drops all specified items.
     * @return The amount of items dropped. Takes item stack into account.
     * @param itemNames The names of the items to drop.
     * */
    public static int drop(String... itemNames) {
        return Inventory.drop(itemNames);
    }

    /**
     * Drops all items except the items specified.
     * @return The amount of items dropped. Takes item stack into account.
     * @param itemNames The names of the items to keep.
     * */
    public static int dropAllExcept(String... itemNames) {
        return Inventory.dropAllExcept(itemNames);
    }

    /**
     * @author Encoded
     * Emulates mouse keys and drops all the inventory items except the specified ids.
     * @param ignore The ids of the items that should not be dropped.
     * @return True if all items were dropped except for those specified, false otherwise.
     */
    public static boolean mouseKeysDropAllExcept(int... ignore) {
        return mouseKeysDropAllExcept(2, ignore);
    }

    /**
     * @author Encoded
     * Emulates mouse keys and drops all the inventory items except the specified ids.
     * @param sleepMod A multiplier for the delay in between dropping items. The lower the number, the shorter the delay.
     * @param ignore The ids of the items that should not be dropped.
     * @return True if all items were dropped except for those specified, false otherwise.
     */
    public static boolean mouseKeysDropAllExcept(int sleepMod, int... ignore) {
        if (sleepMod < 0) sleepMod = 0;
        if (GameTab.getOpen() != GameTab.TABS.INVENTORY)
            GameTab.open(GameTab.TABS.INVENTORY);
        RSItem[] items = convertTo28(Inventory.find(Filters.Items.idNotEquals(ignore)));
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 7; j++) {
                if (deselectedItem()) {
                    if (j > 0) {
                        --j;
                        continue;
                    } else {
                        --i;
                        j = 6;
                        continue;
                    }
                }
                if (items[4 * j + i] == null) continue;
                Rectangle r = new RSItem(4 * j + i, 0, 0, RSItem.TYPE.INVENTORY).getArea();
                mouseKeysDropItem(r, sleepMod);
                waitForInventoryFullMessage();
            }
        }
        return Timing.waitCondition(itemsDropped(ignore), General.random(800, 1200));
    }

    private static RSItem[] convertTo28(RSItem[] items) {
        RSItem[] out = new RSItem[28];
        for (int i = 0; i < items.length; i++) {
            out[items[i].getIndex()] = items[i];
        }
        return out;
    }

    private static Condition itemsDropped(int... ignore) {
        return new Condition() {
            @Override
            public boolean active() {
                General.sleep(100, 200);
                return Inventory.find(Filters.Items.idNotEquals(ignore)).length == 0;
            }
        };
    }

    private static Condition itemNotSelected = new Condition() {
        @Override
        public boolean active() {
            General.sleep(50, 100);
            return Game.getItemSelectionState() != 1;
        }
    };

    private static boolean deselectedItem() {
        if (Game.getItemSelectionState() == 1) {
            Mouse.click(3);
            if (Timing.waitMenuOpen(General.random(450, 550)))
                if (ChooseOption.isOptionValid("Cancel"))
                    if (ChooseOption.select("Cancel"))
                        return Timing.waitCondition(itemNotSelected, General.random(700, 1000));
        }
        return false;
    }

    private static void mouseKeysDropItem(Rectangle r, int sleepMod) {
        if (!r.contains(Mouse.getPos())) {
            General.sleep(50, 100);
            Mouse.move(new Point((int) r.getCenterX() + General.random(-3, 3),
                    (int) r.getCenterY() + General.random(-3, 3)));
        }
        if (r.contains(Mouse.getPos())) {
            Mouse.click(3);
            if (sleepMod != 0) General.sleep(General.random(20, 25) * sleepMod);
            int y = getOptionMenuY();
            if (y == -1) {
                Mouse.click(1);
                General.sleep(50, 100);
            } else {
                // Holding ctrl per request by TRiLeZ
                Keyboard.sendPress(KeyEvent.CHAR_UNDEFINED, Keyboard.getKeyCode((char) KeyEvent.VK_CONTROL));
                General.sleep(30, 60);
                Mouse.hop(new Point((int) Mouse.getPos().getX(), y));
                General.sleep(30, 60);
                Keyboard.sendRelease(KeyEvent.CHAR_UNDEFINED, Keyboard.getKeyCode((char) KeyEvent.VK_CONTROL));
                Mouse.click(1);
                General.sleep(50, 100);
            }
        }
    }

    private static int getOptionMenuY() {
        String[] actions = ChooseOption.getOptions();
        for (int i = 0; i < actions.length; i++) {
            if (actions[i].toLowerCase().contains("drop")) {
                Point p = ChooseOption.getPosition();
                if (p != null) return (int) (p.getY() + 21 + 16 * i);
            }
        }
        return -1;
    }

    private static void waitForInventoryFullMessage() {
        // NPCChat methods are really slow on looking glass.
        if (!General.isLookingGlass()) {
            String message = NPCChat.getMessage();
            if (message != null && (message.contains("You don't have") || message.contains("You can't carry"))) {
                for (int i = 0; i < 10 && NPCChat.getMessage() != null; i++) General.sleep(90, 110); // TODO replace with condition
            }
        }
    }

}

