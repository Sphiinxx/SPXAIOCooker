package scripts.SPXAIOCooker.API.Game.GroundItems;

import org.tribot.api2007.Camera;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSGroundItem;
import org.tribot.api2007.types.RSItemDefinition;
import scripts.SPXAIOCooker.API.Game.Inventory.Inventory07;


/**
 * Created by Sphiinx on 1/10/2016.
 */
public class GroundItems07 {

    /**
     * Pickups the ground item with the specified String.
     * @return True if the item was picked up; false otherwise.
     * */
    public static boolean pickUpGroundItem(String... itemName) {
        return pickUpGroundItem(itemName);
    }

    /**
     * Pickups the ground item with the specified ItemID.
     * @return True if the item was picked up; false otherwise.
     * */
    public static boolean pickUpGroundItem(final RSGroundItem[] itemIDs) {
        if (itemIDs.length > 0) {
            for (final RSGroundItem item : itemIDs) {
                final RSItemDefinition itemDefinition = item.getDefinition();
                if (itemDefinition == null) {
                    continue;
                }
                String name = itemDefinition.getName();
                if (name == null) {
                    continue;
                }
                if (Inventory.isFull() && (!itemDefinition.isStackable() && Inventory07.hasItem(item.getID()))) {
                    return false;
                } else {
                    Camera.turnToTile(item);
                    if (item.isOnScreen()) {
                        if (item.click("Take " + name)) {
                            return true;
                        }
                    } else {
                        Walking.walkTo(item);
                    }
                }
            }
        }
        return false;
    }

}
