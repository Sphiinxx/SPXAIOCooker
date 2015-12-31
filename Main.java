package scripts.SPXAIOCooker;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.Login;
import org.tribot.api2007.Skills;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.MessageListening07;
import org.tribot.script.interfaces.MousePainting;
import org.tribot.script.interfaces.MouseSplinePainting;
import org.tribot.script.interfaces.Painting;
import scripts.SPXAIOCooker.api.Node;
import scripts.SPXAIOCooker.nodes.*;


import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Sphiinx on 12/26/2015.
 */
@ScriptManifest(authors = "Sphiinx", category = "Cooking", name = "[SPX] AIO Cooker", version = 0.2)
public class Main extends Script implements Painting, MessageListening07, MousePainting, MouseSplinePainting {

    private Variables variables = new Variables();
    private ArrayList<Node> nodes = new ArrayList<>();
    public GUI gui = new GUI(variables);

    @Override
    public void run() {
        initializeGui();
        Collections.addAll(nodes, new DepositItems(variables), new WithdrawItems(variables), new GUIStopSettings(variables), new CookFood(variables));
        variables.version = getClass().getAnnotation(ScriptManifest.class).version();
        loop(20, 40);
    }

    private void loop(int min, int max) {
        while (!variables.stopScript) {
            for (final Node node : nodes) {
                if (node.validate()) {
                    node.execute();
                    General.sleep(min, max);
                }
            }
        }
    }

    public void initializeGui() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    sleep(50);
                    variables.status = "Initializing...";
                    gui.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        do
            sleep(10);
        while (!variables.guiComplete);
    }

    public void onPaint(Graphics g1) {
        Graphics2D g = (Graphics2D) g1;
        g.setRenderingHints(Constants.ANTIALIASING);

        if (Login.getLoginState() == Login.STATE.INGAME) {

            variables.currentLvl = Skills.getActualLevel(Skills.SKILLS.COOKING);
            int gainedLvl = variables.currentLvl - variables.startLvl;
            int gainedXP = Skills.getXP(Skills.SKILLS.COOKING) - variables.startXP;
            long timeRan = System.currentTimeMillis() - variables.startTime;
            long xpPerHour = (long) (gainedXP * 3600000D / timeRan);

            g.setColor(Constants.BLACK_COLOR);
            g.fillRoundRect(11, 220, 200, 110, 8, 8); // Paint background
            g.setColor(Constants.RED_COLOR);
            g.drawRoundRect(9, 218, 202, 112, 8, 8); // Red outline
            g.fillRoundRect(13, 223, 194, 22, 8, 8); // Title background
            g.setFont(Constants.TITLE_FONT);
            g.setColor(Color.WHITE);
            g.drawString("[SPX] AIO Cooker", 18, 239);
            g.setFont(Constants.TEXT_FONT);
            g.drawString("Runtime: " + Timing.msToString(timeRan), 14, 260);
            g.drawString("Exp P/H: " + xpPerHour, 14, 276);
            g.drawString("Level: " + variables.currentLvl + " (+" + gainedLvl + ") " + gainedXP, 14, 293);
            g.drawString("Successfully Cooked: " + variables.cookedCount, 14, 310);
            g.drawString("Status: " + variables.status, 14, 326);
            g.drawString("v" + variables.version, 185, 326);

        }
    }

    @Override
    public void paintMouse(Graphics graphics, Point point, Point point1) {
        graphics.setColor(Color.BLACK);
        graphics.drawRect(Mouse.getPos().x - 13, Mouse.getPos().y - 13, 27, 27); // Square rectangle Stroke
        graphics.drawRect(Mouse.getPos().x, Mouse.getPos().y - 512, 1, 500); // Top y axis Line Stroke
        graphics.drawRect(Mouse.getPos().x, Mouse.getPos().y + 13, 1, 500); // Bottom y axis Line Stroke
        graphics.drawRect(Mouse.getPos().x + 13, Mouse.getPos().y, 800, 1); // Right x axis line Stroke
        graphics.drawRect(Mouse.getPos().x - 812, Mouse.getPos().y, 800, 1); // left x axis line Stroke
        graphics.fillOval(Mouse.getPos().x - 3, Mouse.getPos().y - 3, 7, 7); // Center dot stroke
        graphics.setColor(Constants.RED_COLOR);
        graphics.drawRect(Mouse.getPos().x - 12, Mouse.getPos().y - 12, 25, 25); // Square rectangle
        graphics.drawRect(Mouse.getPos().x, Mouse.getPos().y - 512, 0, 500); // Top y axis Line
        graphics.drawRect(Mouse.getPos().x, Mouse.getPos().y + 13, 0, 500); // Bottom y axis Line
        graphics.drawRect(Mouse.getPos().x + 13, Mouse.getPos().y, 800, 0); // Right x axis line
        graphics.drawRect(Mouse.getPos().x - 812, Mouse.getPos().y, 800, 0); // left x axis line
        graphics.fillOval(Mouse.getPos().x - 2, Mouse.getPos().y - 2, 5, 5); // Center dot
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
        if (s.contains(new String("cook")) || s.contains(new String("You squeeze the grapes"))) {
            variables.cookedCount++;
        } else if (s.contains(new String("burn"))) {
            variables.burnedCount++;
        }
    }

    @Override
    public void duelRequestReceived(String s, String s1) {

    }
}

