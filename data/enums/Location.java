package scripts.SPXAIOCooker.data.enums;

import org.tribot.api2007.types.RSTile;

/**
 * Created by Sphiinx on 1/20/2016.
 */
public enum Location {

    CATHERBY(new RSTile(2817, 3443, 0)),
    COOKING_GUILD(new RSTile(3146, 3452, 0)),
    WEST_FALADOR(new RSTile(2989, 3366, 0)),
    EAST_FALADOR(new RSTile(3037, 3344, 0)),
    AL_KHARID(new RSTile(3273, 3180, 0)),
    ROGUES_DEN(new RSTile(3043, 4972, 1));


    private final RSTile position;

    Location(RSTile position) {
        this.position = position;
    }

    public RSTile getPosition() {
        return position;
    }

}

