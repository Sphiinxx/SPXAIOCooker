package scripts.SPXAIOCooker;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.Login;
import org.tribot.api2007.Skills;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.*;
import scripts.SPXAIOCooker.data.Constants;
import scripts.SPXAIOCooker.data.Vars;
import scripts.SPXAIOCooker.framework.Task;
import scripts.SPXAIOCooker.framework.TaskManager;
import scripts.SPXAIOCooker.gui.GUI;
import scripts.SPXAIOCooker.tasks.CookFood.*;
import scripts.SPXAIOCooker.tasks.MakeWine.BankHandler;
import scripts.SPXAIOCooker.tasks.MakeWine.CombineItems;


import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Sphiinx on 12/26/2015.
 */
@ScriptManifest(authors = "Sphiinx", category = "Cooking", name = "[SPX] AIO Cooker", version = 0.4)
public class Main extends Script implements MessageListening07, Painting, MousePainting, MouseSplinePainting, Ending {


    public GUI gui = new GUI();


    private TaskManager taskManager = new TaskManager();

    @Override
    public void run() {
        Vars.reset();
        getStartInformation();
        initializeGui();
        addCollection();
        Vars.get().version = getClass().getAnnotation(ScriptManifest.class).version();
        loop(20, 40);
    }

    private void addCollection() {
        taskManager.addTask(new DepositItems(), new WithdrawItems(), new GUIStopSettings(), new CookFoodOnStove(), new CookFoodOnFire(), new WalkToStove(), new WalkToFire(), new BankHandler(), new CombineItems());
    }

    private void loop(int min, int max) {
        while (!Vars.get().stopScript) {
            Task task = taskManager.getValidTask();
            if (task != null) {
                Vars.get().status = task.toString();
                task.execute();
                General.sleep(min, max);
            }
        }
    }

    public void initializeGui() {
        EventQueue.invokeLater(() -> {
            try {
                sleep(50);
                Vars.get().status = "Initializing...";
                gui.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        do
            sleep(10);
        while (!Vars.get().guiComplete);
    }

    private void getStartInformation() {
        Vars.get().startXP = Skills.getXP(Skills.SKILLS.COOKING);
        Vars.get().startLvl = Skills.getActualLevel(Skills.SKILLS.COOKING);
    }

    public void onPaint(Graphics g1) {
        Graphics2D g = (Graphics2D) g1;
        g.setRenderingHints(Constants.ANTIALIASING);

        if (Login.getLoginState() == Login.STATE.INGAME) {

            Vars.get().currentLvl = Skills.getActualLevel(Skills.SKILLS.COOKING);
            Vars.get().gainedLvl = Vars.get().currentLvl - Vars.get().startLvl;
            Vars.get().gainedXP = Skills.getXP(Skills.SKILLS.COOKING) - Vars.get().startXP;
            Vars.get().timeRan = System.currentTimeMillis() - Constants.START_TIME;
            long xpPerHour = (long) (Vars.get().gainedXP * 3600000D / Vars.get().timeRan);

            g.setColor(Constants.BLACK_COLOR);
            g.fillRoundRect(11, 220, 200, 110, 8, 8); // Paint background
            g.setColor(Constants.RED_COLOR);
            g.drawRoundRect(9, 218, 202, 112, 8, 8); // Red outline
            g.fillRoundRect(13, 223, 194, 22, 8, 8); // Title background
            g.setFont(Constants.TITLE_FONT);
            g.setColor(Color.WHITE);
            g.drawString("[SPX] AIO Cooker", 18, 239);
            g.setFont(Constants.TEXT_FONT);
            g.drawString("Runtime: " + Timing.msToString(Vars.get().timeRan), 14, 260);
            g.drawString("Exp P/H: " + xpPerHour, 14, 276);
            g.drawString("Level: " + Vars.get().currentLvl + " (+" + Vars.get().gainedLvl + ") " + Vars.get().gainedXP, 14, 293);
            g.drawString("Successfully Cooked: " + Vars.get().cookedCount, 14, 310);
            g.drawString("Status: " + Vars.get().status, 14, 326);
            g.drawString("v" + Vars.get().version, 185, 326);

        }
    }

    @Override
    public void paintMouse(Graphics g, Point point, Point point1) {
        g.setColor(Constants.BLACK_COLOR);
        g.drawRect(Mouse.getPos().x - 13, Mouse.getPos().y - 13, 27, 27); // Square rectangle Stroke
        g.drawRect(Mouse.getPos().x, Mouse.getPos().y - 512, 1, 500); // Top y axis Line Stroke
        g.drawRect(Mouse.getPos().x, Mouse.getPos().y + 13, 1, 500); // Bottom y axis Line Stroke
        g.drawRect(Mouse.getPos().x + 13, Mouse.getPos().y, 800, 1); // Right x axis line Stroke
        g.drawRect(Mouse.getPos().x - 812, Mouse.getPos().y, 800, 1); // left x axis line Stroke
        g.fillOval(Mouse.getPos().x - 3, Mouse.getPos().y - 3, 7, 7); // Center dot stroke
        g.setColor(Constants.RED_COLOR);
        g.drawRect(Mouse.getPos().x - 12, Mouse.getPos().y - 12, 25, 25); // Square rectangle
        g.drawRect(Mouse.getPos().x, Mouse.getPos().y - 512, 0, 500); // Top y axis Line
        g.drawRect(Mouse.getPos().x, Mouse.getPos().y + 13, 0, 500); // Bottom y axis Line
        g.drawRect(Mouse.getPos().x + 13, Mouse.getPos().y, 800, 0); // Right x axis line
        g.drawRect(Mouse.getPos().x - 812, Mouse.getPos().y, 800, 0); // left x axis line
        g.fillOval(Mouse.getPos().x - 2, Mouse.getPos().y - 2, 5, 5); // Center dot
    }

    @Override
    public void paintMouseSpline(Graphics graphics, ArrayList<Point> arrayList) {
    }

    @Override
    public void playerMessageReceived(String s, String s1) {

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
    public void serverMessageReceived(String s) {
        if (s.contains("cook") || s.contains("You squeeze the grapes")) {
            Vars.get().cookedCount++;
        } else if (s.contains("burn")) {
            Vars.get().burnedCount++;
        }
    }

    @Override
    public void duelRequestReceived(String s, String s1) {

    }

    @Override
    public void onEnd() {
        DynamicSignature.sendSignatureData(Vars.get().timeRan / 1000, Vars.get().cookedCount, Vars.get().gainedLvl, Vars.get().gainedXP);
    }

}

