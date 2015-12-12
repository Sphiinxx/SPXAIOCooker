package scripts.SPXAIOCooker;

import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.*;
import org.tribot.api2007.types.*;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.MessageListening07;
import org.tribot.script.interfaces.Painting;

import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;
import java.net.URL;

/**
 * Created by Sphiinx on 9/27/2015.
 */
@ScriptManifest(authors = "Sphiinx", category = "Cooking", name = "SPX AIO Cooker")
public class Main extends Script implements Painting, MessageListening07 {

    // Start of our variables
    private final int RANDOM_MOUSE = General.random(95, 115);
    private final int COOKING_ANIMATION = 897;
    private final int GRAPES_ID = 1987;
    private final int JUG_OF_WATER_ID = 1937;
    private final int UNFERMENTED_WINE_ID = 1995;
    private final int WINE_ID = 1993;
    private int LEVEL_TO_STOP = 0;
    private int AMOUNT_TO_STOP = 0;
    private int COOKED_FOOD_COUNT = 0;
    private int BURNED_FOOD_COUNT = 0;
    private int startLvl = Skills.getActualLevel(Skills.SKILLS.COOKING);
    private int startXP = Skills.getXP(Skills.SKILLS.COOKING);
    private final long startTime = System.currentTimeMillis();
    private long lastCookingTime = Timing.currentTimeMillis();
    private final String SCRIPT_VERSION = "v1.0";
    private String FOOD_ID = "";
    private String STATUS = "";
    private String STOVE_OR_FIRE = "";
    private RSTile STOVE_LOCATION = new RSTile(0, 0, 0);
    private boolean GUI_COMPLETE = false;
    private boolean MAKE_WINE = false;
    private final RSArea FALADOR_AREA = new RSArea(new RSTile[]{
            new RSTile(3024, 3348, 0),
            new RSTile(3024, 3383, 0),
            new RSTile(2981, 3383, 0),
            new RSTile(2981, 3348, 0)
    });
    private final RSArea CATHERBY_AREA = new RSArea(new RSTile[]{
            new RSTile(2794, 3459, 0),
            new RSTile(2843, 3459, 0),
            new RSTile(2844, 3427, 0),
            new RSTile(2795, 3427, 0)
    });
    private final RSArea ALKHARID_AREA = new RSArea(new RSTile[]{
            new RSTile(3261, 3156, 0),
            new RSTile(3290, 3156, 0),
            new RSTile(3290, 3197, 0),
            new RSTile(3262, 3197, 0)
    });
    private final RSArea ROUGES_DEN_AREA = new RSArea(new RSTile[]{
            new RSTile(3040, 4978, 1),
            new RSTile(3040, 4964, 1),
            new RSTile(3049, 4964, 1),
            new RSTile(3049, 4978, 1)
    });
    private final RSTile CATHERBY_STOVE_POSITION = new RSTile(2817, 3443, 0);
    private final RSTile FALADOR_STOVE_POSITION = new RSTile(2989, 3366, 0);
    private final RSTile ALKHARID_STOVE_POSITION = new RSTile(3272, 3180, 0);
    private final RSTile ROUGES_DEN_FIRE_POSITION = new RSTile(3043, 4972, 1);
    private final Color color1 = new Color(0, 169, 194);
    private final Color color2 = new Color(255, 255, 255);
    private final Font font1 = new Font("Segoe Script", 0, 20);
    private final Font font2 = new Font("Arial", 0, 15);
    private final Image img1 = getImage("http://i.imgur.com/fRrLAWr.png");
    private final RenderingHints antialiasing = new RenderingHints(
            RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    // End of our variables

    // Start of our run method
    @Override
    public void run() {
        GUI GUI = new GUI();
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenW = (screensize.width) / 2;
        int screenH = (screensize.height) / 2;
        Dimension dim = GUI.getSize();
        GUI.setVisible(true);
        GUI.setLocation((screenW / 2), (screenH / 2));
        while (!GUI_COMPLETE) {
            sleep(300);
        }
        GUI.setVisible(false);
        // Set our mouse speed
        Mouse.setSpeed(RANDOM_MOUSE);
        // Start of our loop
        while (true) {
            sleep(100);
            // Check the GUI Settings
            guiStopCheck();
            if (Login.getLoginState() == Login.STATE.INGAME) {
                if (MAKE_WINE) {
                    if (Inventory.getCount(GRAPES_ID) > 1 && Inventory.getCount(JUG_OF_WATER_ID) > 1) {
                        makeWine();
                    } else if (Inventory.getCount(WINE_ID, UNFERMENTED_WINE_ID) >= 14) {
                        depositItems();
                    } else {
                        withdrawWineItems();
                    }
                } else {
                    if (Inventory.getCount(FOOD_ID) >= 0) {
                        if (STOVE_OR_FIRE.equals("Stove")) {
                            walkToStove();
                        } else {
                            walkToFire();
                        }
                    } else {
                        if (Inventory.isFull()) {
                            depositItems();
                        } else {
                            withdrawItems();
                        }
                    }
                }
            }
        }
        // End of our loop
    }
    // End of our run method

    // Start of our methods

    private void withdrawWineItems() {
        if (Banking.isInBank()) {
            if (Banking.isBankScreenOpen()) {
                if (Inventory.getAll().length > 0) {
                    Banking.depositAll();
                } else {
                    if (Banking.find(GRAPES_ID).length > 0) {
                        if (Banking.find(JUG_OF_WATER_ID).length > 0) {
                            if (Banking.withdraw(14, GRAPES_ID)) {
                                STATUS = "Withdrawing Items";
                                Timing.waitCondition(new Condition() {
                                    @Override
                                    public boolean active() {
                                        return Inventory.getCount(GRAPES_ID) > 1;
                                    }
                                }, General.random(1000, 1200));
                            }
                            if (Banking.withdraw(14, JUG_OF_WATER_ID)) {
                                STATUS = "Withdrawing Items";
                                Timing.waitCondition(new Condition() {
                                    @Override
                                    public boolean active() {
                                        return Inventory.getCount(GRAPES_ID) > 1;
                                    }
                                }, General.random(1000, 1200));
                                if (Banking.close()) {
                                    Timing.waitCondition(new Condition() {
                                        @Override
                                        public boolean active() {
                                            return !Banking.isBankScreenOpen();
                                        }
                                    }, General.random(1000, 1200));
                                }
                            }
                        } else {
                            println("We're out of Jugs of Water...");
                            println("Stopping Script...");
                            stopScript();
                        }
                    } else {
                        println("We're out of Wine...");
                        println("Stopping Script...");
                        stopScript();
                    }
                }
            } else {
                if (Banking.openBank()) {
                    STATUS = "Opening Bank";
                    Timing.waitCondition(new Condition() {
                        @Override
                        public boolean active() {
                            return Banking.isBankScreenOpen();
                        }
                    }, General.random(1000, 1200));
                }
            }
        } else {
            STATUS = "Waling to Bank";
            WebWalking.walkToBank();
        }
    }

    private void makeWine() {
        if (Clicking.click(Inventory.find(GRAPES_ID))) {
            Timing.waitCondition(new Condition() {
                @Override
                public boolean active() {
                    return Game.getUptext().contains("Grapes");
                }
            }, General.random(1000, 1200));
            if (Clicking.click(Inventory.find(JUG_OF_WATER_ID))) {
                STATUS = "Making Wine";
                RSInterfaceChild wineInterfaceScreen = Interfaces.get(162, 30);
                General.sleep(1000, 1200);
                Timing.waitCondition(new Condition() {
                    @Override
                    public boolean active() {
                        return wineInterfaceScreen != null && !wineInterfaceScreen.isHidden(true);
                    }
                }, General.random(1000, 1200));
                if (wineInterfaceScreen != null && !wineInterfaceScreen.isHidden(true)) {
                    STATUS = "Making Wine";
                    final int STARTING_AMOUNT = Inventory.getCount(JUG_OF_WATER_ID) + Inventory.getCount(GRAPES_ID);
                    final int STARTING_LEVEL = Skills.getActualLevel(Skills.SKILLS.COOKING);
                    if (wineInterfaceScreen.click("Make All")) {
                        Timing.waitCondition(wineCondition(STARTING_AMOUNT, STARTING_LEVEL), STARTING_AMOUNT * 750);
                    }
                }
            }
        }
    }

    Condition wineCondition(final int startingAmount, final int startingLvl)
    {
        return new Condition()
        {
            @Override
            public boolean active()
            {
                General.sleep(100);
                return Inventory.getAll().length <= Math.ceil((startingAmount / 2.0))
                        || Skills.getActualLevel(Skills.SKILLS.COOKING) > startingLvl;
            }

        };
    }

    private void cookFoodOnStove() {
        RSObject[] stove = Objects.findNearest(10, "Range");
        RSItem[] food = Inventory.find(FOOD_ID);
        String upText = Game.getUptext();
        RSInterfaceChild interfaceScreen = Interfaces.get(307, 3);
        if (interfaceScreen != null && !interfaceScreen.isHidden(true)) {
            interfaceScreen.click("Cook All");
            STATUS = "Cooking";
        } else if (upText != null && upText.contains("Use Raw") && Player.getAnimation() == -1) {
            if (stove[0].click()) {
                Timing.waitCondition(new Condition() {
                    @Override
                    public boolean active() {
                        return Player.getAnimation() != -1;
                    }
                }, General.random(2500, 3000));
            }
        } else if (Player.getAnimation() == -1) {
            if (food[0].click("Use")) {
                Timing.waitCondition(new Condition() {
                    @Override
                    public boolean active() {
                        return upText.contains("Use Raw");
                    }
                }, General.random(1500, 2000));
            }
        }
    }

    public void walkToStove() {
        if (Inventory.getCount(FOOD_ID) > 1) {
            RSObject[] stove = Objects.findNearest(10, "Range");
            if (stove.length > 0) {
                if (!stove[0].isOnScreen()) {
                    STATUS = "Walking to Stove";
                    WebWalking.walkTo(STOVE_LOCATION);
                } else {
                    cookFoodOnStove();
                }
            } else {
                STATUS = "Walking to Stove";
                WebWalking.walkTo(STOVE_LOCATION);
            }
        }
    }

    private void cookFoodOnFire() {
        if (Player.getAnimation() == COOKING_ANIMATION) {
            lastCookingTime = Timing.currentTimeMillis();
        } else if (Timing.timeFromMark(lastCookingTime) > 3000) {
            RSObject[] fire = Objects.findNearest(10, "Fire");
            RSItem[] food = Inventory.find(FOOD_ID);
            String upText = Game.getUptext();
            RSInterfaceChild interfaceScreen = Interfaces.get(307, 3);
            if (interfaceScreen != null && !interfaceScreen.isHidden(true)) {
                interfaceScreen.click("Cook All");
                STATUS = "Cooking";
            } else if (upText != null && upText.contains("Use Raw") && Player.getAnimation() == -1) {
                if (fire[0].click()) {
                    General.sleep(550, 650);
                    Timing.waitCondition(new Condition() {
                        @Override
                        public boolean active() {
                            return Player.getAnimation() == COOKING_ANIMATION;
                        }
                    }, General.random(1000, 1200));
                }
            } else if (Player.getAnimation() == -1) {
                if (food[0].click("Use")) {
                    General.sleep(550, 650);
                    Timing.waitCondition(new Condition() {
                        @Override
                        public boolean active() {
                            return Player.getAnimation() == COOKING_ANIMATION;
                        }
                    }, General.random(1000, 1200));
                }
            }
        }
    }

    private void walkToFire() {
        if (Inventory.getCount(FOOD_ID) > 1) {
            RSObject[] fire = Objects.findNearest(10, "Fire");
            if (fire.length > 0) {
                if (!fire[0].isOnScreen()) {
                    STATUS = "Walking to Fjre";
                    WebWalking.walkTo(STOVE_LOCATION);
                } else {
                    cookFoodOnFire();
                }
            } else {
                STATUS = "Walking to Fire";
                WebWalking.walkTo(STOVE_LOCATION);
            }
        }
    }

    private void guiStopCheck() {
        int currentLvl = Skills.getActualLevel(Skills.SKILLS.COOKING);
        if (AMOUNT_TO_STOP != 0) {
            if (COOKED_FOOD_COUNT + BURNED_FOOD_COUNT >= AMOUNT_TO_STOP) {
                println("You have reached the stopping point you requested...");
                println("Stopping Script...");
                stopScript();
                if (Login.getLoginState() == Login.STATE.INGAME) {
                    Login.logout();
                }
            }
        } else if (LEVEL_TO_STOP != 0) {
            if (currentLvl >= LEVEL_TO_STOP) {
                println("You have reached the stopping point you requested...");
                println("Stopping Script...");
                stopScript();
                if (Login.getLoginState() == Login.STATE.INGAME) {
                    Login.logout();
                }
            }
        }
    }

    private void withdrawItems() {
        if (Banking.isBankScreenOpen()) {
            if (Banking.find(FOOD_ID).length > 0) {
                STATUS = "Withdrawing Items";
                if (Banking.withdraw(28, FOOD_ID)) {
                    Timing.waitCondition(new Condition() {
                        @Override
                        public boolean active() {
                            return Inventory.getCount(FOOD_ID) == 28;
                        }
                    }, General.random(1000, 1200));
                    if (Banking.close()) {
                        Timing.waitCondition(new Condition() {
                            @Override
                            public boolean active() {
                                return !Banking.isBankScreenOpen();
                            }
                        }, General.random(1000, 1200));
                    }
                }
            } else {
                println("You do not have this item or the ID is incorrect.");
                println("Stopping Script...");
                stopScript();
            }
        } else if (Banking.openBank()) {
            Timing.waitCondition(new Condition() {
                @Override
                public boolean active() {
                    return Banking.isBankScreenOpen();
                }
            }, General.random(1000, 1200));
        }
    }

    private void depositItems() {
        if (Banking.isBankScreenOpen()) {
            STATUS = "Depositing Items";
            Banking.depositAll();
            General.sleep(650, 850);
        } else if (Banking.isInBank()) {
            if (Banking.openBank()) {
                Timing.waitCondition(new Condition() {
                    @Override
                    public boolean active() {
                        return Banking.isBankScreenOpen();
                    }
                }, General.random(2000, 2500));
            }
            STATUS = "Opening Bank";
        } else {
            WebWalking.walkToBank();
            STATUS = "Walking to Bank";
        }
    }

    @Override
    public void clanMessageReceived(String s, String s1) {
    }

    @Override
    public void personalMessageReceived(String s, String s1) {
    }

    @Override
    public void tradeRequestReceived(String s) {
    }

    @Override
    public void duelRequestReceived(String s, String s1) {
    }

    @Override
    public void playerMessageReceived(String s, String s1) {
    }

    @Override
    public void serverMessageReceived(String s) {
        if (s.contains(new String("cook"))) {
            COOKED_FOOD_COUNT++;
        }
        if (s.contains(new String("burn"))) {
            BURNED_FOOD_COUNT++;
        }
        if (s.contains(new String("You squeeze the grapes"))) {
            COOKED_FOOD_COUNT++;
        }
    }
    // End of our methods

    // Start of our paint

    private Image getImage(String url) {
        try {
            return ImageIO.read(new URL(url));
        } catch (IOException e) {
            return null;
        }
    }

    public void onPaint(Graphics g1) {
        Graphics2D g = (Graphics2D) g1;
        g.setRenderingHints(antialiasing);

        if (Login.getLoginState() == Login.STATE.INGAME) {

            int currentLvl = Skills.getActualLevel(Skills.SKILLS.COOKING);
            int gainedLvl = currentLvl - startLvl;
            int gainedXP = Skills.getXP(Skills.SKILLS.COOKING) - startXP;
            long timeRan = System.currentTimeMillis() - startTime;
            long xpPerHour = (long) (gainedXP * 3600000D / timeRan);

            g.drawImage(img1, 2, 200, null);
            g.setFont(font1);
            g.setColor(color1);
            g.drawString("- AIO Cooker", 68, 226);
            g.setFont(font2);
            g.setColor(color2);
            g.drawString("Runtime: " + Timing.msToString(timeRan), 11, 252);
            g.drawString("Exp P/H: " + xpPerHour, 11, 272);
            g.drawString("Level: " + currentLvl + " (+" + gainedLvl + ") " + gainedXP, 11, 292);
            g.drawString("Status: " + STATUS, 11, 312);
            g.drawString("Successfully Cooked: " + COOKED_FOOD_COUNT, 11, 330);
            g.drawString("Successfully Cooked: " + COOKED_FOOD_COUNT, 11, 330);
            g.drawString(SCRIPT_VERSION, 205, 330);
        }
    }

    // End of our paint

    // Start of our GUI
    public class GUI extends javax.swing.JFrame {

        public GUI() {
            initComponents();
        }

        @SuppressWarnings("unchecked")
        private void initComponents() {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            FoodPrayer = new javax.swing.ButtonGroup();
            jLabel1 = new javax.swing.JLabel();
            jSeparator1 = new javax.swing.JSeparator();
            jPanel1 = new javax.swing.JPanel();
            jLabel2 = new javax.swing.JLabel();
            jLabel3 = new javax.swing.JLabel();
            jLabel4 = new javax.swing.JLabel();
            levelToStop = new javax.swing.JSpinner();
            jLabel5 = new javax.swing.JLabel();
            amoutBeforeStopping = new javax.swing.JSpinner();
            foodType = new javax.swing.JTextField();
            location = new javax.swing.JComboBox();
            jLabel6 = new javax.swing.JLabel();
            makeWine = new javax.swing.JCheckBox();
            start = new javax.swing.JButton();

            jLabel1.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
            jLabel1.setText("SPX AIO COOKER");

            jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Options", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Ubuntu", 0, 15))); // NOI18N
            jPanel1.setPreferredSize(new java.awt.Dimension(12, 165));

            jLabel2.setText("Enter the exact name of the Food: ");

            jLabel3.setText("Location:");

            jLabel4.setText("Level to stop at:");

            levelToStop.setModel(new javax.swing.SpinnerNumberModel(0, 0, 99, 1));

            jLabel5.setText("Amount to make before stopping:");

            amoutBeforeStopping.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));

            location.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Catherby", "Al Kharid", "Falador", "Rogues Den"}));

            jLabel6.setText("Make Wine instead of cooking:");

            makeWine.setText("Yes");
            makeWine.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    makeWineActionPerformed(evt);
                }
            });

            javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
            jPanel1.setLayout(jPanel1Layout);
            jPanel1Layout.setHorizontalGroup(
                    jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel4)
                                            .addComponent(jLabel3)
                                            .addComponent(jLabel2)
                                            .addComponent(jLabel5)
                                            .addComponent(jLabel6))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(levelToStop, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(amoutBeforeStopping)
                                            .addComponent(foodType)
                                            .addComponent(location, 0, 1, Short.MAX_VALUE)
                                            .addComponent(makeWine, javax.swing.GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE))
                                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );
            jPanel1Layout.setVerticalGroup(
                    jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel6)
                                            .addComponent(makeWine))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 6, Short.MAX_VALUE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel2)
                                            .addComponent(foodType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel3)
                                            .addComponent(location, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel4)
                                            .addComponent(levelToStop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel5)
                                            .addComponent(amoutBeforeStopping, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(25, 25, 25))
            );

            start.setText("START");
            start.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    startActionPerformed(evt);
                }
            });

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                    .addContainerGap()
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                                                            .addComponent(jSeparator1)))
                                            .addGroup(layout.createSequentialGroup()
                                                    .addGap(104, 104, 104)
                                                    .addComponent(jLabel1)
                                                    .addGap(0, 97, Short.MAX_VALUE)))
                                    .addContainerGap())
                            .addGroup(layout.createSequentialGroup()
                                    .addGap(122, 122, 122)
                                    .addComponent(start, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );
            layout.setVerticalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addComponent(jLabel1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(start, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );

            pack();
        }

        private void startActionPerformed(java.awt.event.ActionEvent evt) {
            GUI_COMPLETE = true;
            LEVEL_TO_STOP = Integer.parseInt(levelToStop.getValue().toString());
            AMOUNT_TO_STOP = Integer.parseInt(amoutBeforeStopping.getValue().toString());
            FOOD_ID = foodType.getText().toString();
            if (makeWine.isSelected()) {
                MAKE_WINE = true;
            } else {
                switch (location.getSelectedItem().toString()) {
                    case "Catherby":
                        println("Location: Catherby");
                        println("Cooking: " + FOOD_ID);
                        STOVE_OR_FIRE = "Stove";
                        if (!CATHERBY_AREA.contains(Player.getPosition())) {
                            println("Please start the script at the Catherby Bank.");
                            println("Stopping Script...");
                            stopScript();
                        }
                        STOVE_LOCATION = CATHERBY_STOVE_POSITION;
                        break;
                    case "Al Kharid":
                        println("Location: Al Kharid");
                        println("Cooking: " + FOOD_ID);
                        STOVE_OR_FIRE = "Stove";
                        if (!ALKHARID_AREA.contains(Player.getPosition())) {
                            println("Please start the script at the AL Kharid Bank.");
                            println("Stopping Script...");
                            stopScript();
                        }
                        STOVE_LOCATION = ALKHARID_STOVE_POSITION;
                        break;
                    case "Falador":
                        println("Location: Falador");
                        println("Cooking: " + FOOD_ID);
                        STOVE_OR_FIRE = "Stove";
                        if (!FALADOR_AREA.contains(Player.getPosition())) {
                            println("Please start the script at the Falador East Bank.");
                            println("Stopping Script...");
                            stopScript();
                        }
                        STOVE_LOCATION = FALADOR_STOVE_POSITION;
                        break;
                    case "Rouges Den":
                        println("Location: Rouges Den");
                        println("Cooking: " + FOOD_ID);
                        STOVE_OR_FIRE = "Fire";
                        if (!ROUGES_DEN_AREA.contains(Player.getPosition())) {
                            println("Please start the script at the Rouges Den Bank.");
                            println("Stopping Script...");
                            stopScript();
                        }
                        STOVE_LOCATION = ROUGES_DEN_FIRE_POSITION;
                        break;
                }
            }
        }

        private void makeWineActionPerformed(java.awt.event.ActionEvent evt) {

        }

        // Variables declaration - do not modify
        private javax.swing.ButtonGroup FoodPrayer;
        private javax.swing.JSpinner amoutBeforeStopping;
        private javax.swing.JTextField foodType;
        private javax.swing.JLabel jLabel1;
        private javax.swing.JLabel jLabel2;
        private javax.swing.JLabel jLabel3;
        private javax.swing.JLabel jLabel4;
        private javax.swing.JLabel jLabel5;
        private javax.swing.JLabel jLabel6;
        private javax.swing.JPanel jPanel1;
        private javax.swing.JSeparator jSeparator1;
        private javax.swing.JSpinner levelToStop;
        private javax.swing.JComboBox location;
        private javax.swing.JCheckBox makeWine;
        private javax.swing.JButton start;
        // End of variables declaration
    }

    // End of our GUI
}
// End of our script

