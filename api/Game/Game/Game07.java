package scripts.SPXAIOCooker.api.Game.Game;


import org.tribot.api2007.Game;
import org.tribot.api2007.Login;

/**
 * Created by Sphiinx on 1/10/2016.
 */
public class Game07 {

    /**
     * Checks if the player is in game.
     * @return True if in game; false otherwise.
     * */
    public static boolean isInGame() {
        return Login.getLoginState() == Login.STATE.INGAME && Game.getGameState() == 30;
    }

    /**
     * Checks if the player is at the Welcome Screen.
     * @return True if at Welcome Screen; false otherwise.
     * */
    public static boolean isAtWelcomeScreen() {
        return Login.getLoginState() == Login.STATE.WELCOMESCREEN;
    }

    /**
     * Checks if the player is at the Login Screen.
     * @return True if at Login Screen; false otherwise.
     * */
    public static boolean isAtLoginScreen() {
        return Login.getLoginState() == Login.STATE.LOGINSCREEN && Game.getGameState() == 10;
    }

}

