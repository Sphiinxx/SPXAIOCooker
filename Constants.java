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
            new RSTile(3024, 3348, 0),
            new RSTile(3024, 3383, 0),
            new RSTile(2981, 3383, 0),
            new RSTile(2981, 3348, 0)
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
    public static final Color color1 = new Color(0, 169, 194);
    public static final Color color2 = new Color(255, 255, 255);
    public static final Font font1 = new Font("Segoe Script", 0, 20);
    public static final Font font2 = new Font("Arial", 0, 15);
    public static final Image img1 = getImage("http://i.imgur.com/fRrLAWr.png");
    public static final RenderingHints antialiasing = new RenderingHints(
            RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    public static Image getImage(String url) {
        try {
            return ImageIO.read(new URL(url));
        } catch (IOException e) {
            return null;
        }
    }

}

