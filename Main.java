package scripts.spxaiocooker;

import com.allatori.annotations.DoNotRename;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.*;
import scripts.spxaiocooker.data.Vars;
import scripts.spxaiocooker.tasks.*;
import scripts.task_framework.framework.Task;
import scripts.tribotapi.AbstractScript;
import scripts.tribotapi.game.utiity.Utility07;
import scripts.tribotapi.gui.GUI;
import scripts.tribotapi.painting.paint.Calculations;
import scripts.tribotapi.painting.paint.SkillData;
import scripts.tribotapi.painting.paint.enums.DataPosition;

import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Sphiinx on 12/26/2015.
 * Re-written by Sphiinx on 7/28/2016.
 */
@ScriptManifest(authors = "Sphiinx", category = "Cooking", name = "[SPX] AIO Cooker", version = 1.6)
@DoNotRename
public class Main extends AbstractScript implements Painting, MousePainting, MouseSplinePainting, EventBlockingOverride, MessageListening07, Ending {

    @Override
    protected GUI getGUI() {
        try {
            return new GUI(new URL("http://spxscripts.com/spxaiocooker/GUI.fxml"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void run() {
        Vars.reset();
        super.run();
    }

    @Override
    public void addTasks(Task... tasks) {
        if (Vars.get().is_making_wine) {
         super.addTasks(new DepositItems(), new WithdrawItems(), new MakeWine());
        } else {
            super.addTasks(new StopSettings(), new DepositItems(), new WithdrawItems(), new OpenCookingRoomDoor(), new WalkToCookingObject(), new CookFood());
        }
    }

    @Override
    public void onPaint(Graphics g) {
        super.onPaint(g);
        paint_manager.drawGeneralData("Cooking: ", paint_manager.getSkillData(SkillData.COOKING, this.getRunningTime()), DataPosition.TWO, g);
        paint_manager.drawGeneralData("Food cooked: ", Integer.toString(Vars.get().food_cooked) + Calculations.getPerHour(Vars.get().food_cooked, this.getRunningTime()), DataPosition.THREE, g);
        paint_manager.drawGeneralData("Food burned: ", Integer.toString(Vars.get().food_burned) + Calculations.getPerHour(Vars.get().food_burned, this.getRunningTime()), DataPosition.FOUR, g);
        paint_manager.drawGeneralData("Status: ", task_manager.getStatus() + Utility07.getLoadingPeriods(), DataPosition.FIVE, g);
    }

    @Override
    public void paintMouseSpline(Graphics graphics, ArrayList<Point> arrayList) {
    }

    @Override
    public void playerMessageReceived(String s, String s1) {

    }

    @Override
    public void serverMessageReceived(String s) {
        if (s.contains("cook") || s.contains("You squeeze the grapes"))
            Vars.get().food_cooked++;
        if (s.contains("burn"))
            Vars.get().food_burned++;
    }

    @Override
    public void personalMessageReceived(String s, String s1) {

    }

    @Override
    public void clanMessageReceived(String s, String s1) {

    }

    @Override
    public void tradeRequestReceived(String s) {

    }

    @Override
    public void duelRequestReceived(String s, String s1) {

    }

    @Override
    public void onEnd() {
        SignatureData.sendSignatureData(this.getRunningTime() / 1000, Vars.get().food_cooked, SkillData.getTotalLevelsGained(), SkillData.getTotalExperienceGained());
        super.onEnd();
    }

}