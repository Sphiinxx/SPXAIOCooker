package scripts.SPXAIOCooker.API.Game.Interfaces;

import org.tribot.api2007.Interfaces;
import org.tribot.api2007.types.RSInterface;

/**
 * Created by Sphiinx on 1/10/2016.
 */
public class Interfaces07 {

    /**
     * Closes all interfaces that are not an allowed Interface.
     * @param allowedInterfaces All of the allowed interfaces.
     * @return True if successful; false otherwise.
     * */
    public boolean closeAllInterfaces(RSInterface... allowedInterfaces) {
        if (Interfaces.getAll() != allowedInterfaces) {
            Interfaces.closeAll();
            return true;
        }
        return false;
    }

    /**
     * @author Encoded
     * Gets the interface for the specified component index of the specified child index of the specified parent index.
     * @param parent The parent index.
     * @param child The child index.
     * @param component The component index.
     * @return The component of the child of the parent, or null.
     */
    public static RSInterface get(int parent, int child, int component)  {
        RSInterface i = Interfaces.get(parent, child);
        if (i != null) {
            RSInterface c = i.getChild(component);
            return c;
        }
        return null;
    }

}

