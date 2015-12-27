package scripts.SPXAIOCooker;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Login;
import org.tribot.api2007.Skills;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.MessageListening07;
import org.tribot.script.interfaces.Painting;
import scripts.SPXAIOCooker.api.Node;
import scripts.SPXAIOCooker.nodes.*;


import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Sphiinx on 12/26/2015.
 */
@ScriptManifest(authors = "Sphiinx", category = "Cooking", name = "SPX AIO Cooker", version = 0.2)
public class Main extends Script implements Painting, MessageListening07 {

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
        g.setRenderingHints(Constants.antialiasing);

        if (Login.getLoginState() == Login.STATE.INGAME) {

            variables.currentLvl = Skills.getActualLevel(Skills.SKILLS.COOKING);
            variables.gainedLvl = variables.currentLvl - variables.startLvl;
            variables.gainedXP = Skills.getXP(Skills.SKILLS.COOKING) - variables.startXP;
            variables.timeRan = System.currentTimeMillis() - variables.startTime;
            variables.xpPerHour = (long) (variables.gainedXP * 3600000D / variables.timeRan);

            g.drawImage(Constants.img1, 2, 200, null);
            g.setFont(Constants.font1);
            g.setColor(Constants.color1);
            g.drawString("- AIO Cooker", 68, 226);
            g.setFont(Constants.font2);
            g.setColor(Constants.color2);
            g.drawString("Runtime: " + Timing.msToString(variables.timeRan), 11, 252);
            g.drawString("Exp P/H: " + variables.xpPerHour, 11, 272);
            g.drawString("Level: " + variables.currentLvl + " (+" + variables.gainedLvl + ") " + variables.gainedXP, 11, 292);
            g.drawString("Successfully Cooked: " + variables.cookedCount, 11, 312);
            g.drawString("Status: " + variables.status, 11, 330);
            g.drawString("v" + variables.version, 205, 330);
        }
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

