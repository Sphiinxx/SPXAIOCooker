package scripts.SPXAIOCooker;

import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

/**
 * Created by Sphiinx on 12/26/2015.
 */
public class Constants {
    public static final RSArea FALADOR_AREA = new RSArea(new RSTile[]{
            new RSTile(3024, 3351, 0),
            new RSTile(3026, 3383, 0),
            new RSTile(2978, 3375, 0),
            new RSTile(2978, 3351, 0)
    });
    public static final RSArea CATHERBY_AREA = new RSArea(new RSTile[]{
            new RSTile(2794, 3459, 0),
            new RSTile(2843, 3459, 0),
            new RSTile(2844, 3427, 0),
            new RSTile(2795, 3427, 0)
    });
    public static final RSArea ALKHARID_AREA = new RSArea(new RSTile[]{
            new RSTile(3261, 3156, 0),
            new RSTile(3290, 3156, 0),
            new RSTile(3290, 3197, 0),
            new RSTile(3262, 3197, 0)
    });
    public static final RSArea ROUGES_DEN_AREA = new RSArea(new RSTile[]{
            new RSTile(3040, 4978, 1),
            new RSTile(3040, 4964, 1),
            new RSTile(3049, 4964, 1),
            new RSTile(3049, 4978, 1)
    });
    public static final long START_TIME = System.currentTimeMillis();
    public static final Color RED_COLOR = new Color(214, 39, 39, 240);
    public static final Color BLACK_COLOR = new Color(0, 0, 0, 100);
    public static final Font TITLE_FONT = new Font("Arial Bold", 0, 15);
    public static final Font TEXT_FONT = new Font("Arial", 0, 12);
    public static final RenderingHints ANTIALIASING = new RenderingHints(
            RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

}

