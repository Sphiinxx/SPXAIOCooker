package scripts.spxaiocooker.data.enums;

import org.tribot.api2007.types.RSTile;

/**
 * Created by Sphiinx on 1/20/2016.
 */
public enum Location {

    CATHERBY(new RSTile(2817, 3444, 0), new RSTile(-1, -1, -1)),
    COOKING_GUILD(new RSTile(3145, 3453, 0), new RSTile(-1, -1, -1)),
    VARROCK_EAST_1(new RSTile(3237, 3409, 0), new RSTile(3242, 3412, 0)),
    VARROCK_EAST_2(new RSTile(3237, 3403, 0), new RSTile(3241, 3406, 0)),
    WEST_FALADOR(new RSTile(2988, 3365, 0), new RSTile(2989, 3368, 0)),
    EAST_FALADOR_1(new RSTile(3039, 3367, 0), new RSTile(3038, 3361, 0)),
    EAST_FALADOR_2(new RSTile(3036, 3342, 0), new RSTile(3037, 3347, 0)),
    EDGEVILLE(new RSTile(3036, 3342, 0), new RSTile(3037, 3347, 0)),
    AL_KHARID(new RSTile(3271, 3180, 0), new RSTile(3276, 3180, 0)),
    ROGUES_DEN(new RSTile(3043, 4973, 1), new RSTile(-1, -1, -1));


    private final RSTile COOKING_OBJECT_TILE;
    private final RSTile COOKING_OBJECT_DOOR_TILE;

    Location(RSTile cooking_object_tile, RSTile cooking_object_door_tile) {
        this.COOKING_OBJECT_TILE = cooking_object_tile;
        this.COOKING_OBJECT_DOOR_TILE = cooking_object_door_tile;
    }

    public RSTile getCookingObjectTile() {
        return COOKING_OBJECT_TILE;
    }

    public RSTile getCookingObjectDoorTile() {
        return COOKING_OBJECT_DOOR_TILE;
    }

}

