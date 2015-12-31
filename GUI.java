package scripts.SPXAIOCooker;

import org.tribot.api.General;
import org.tribot.api2007.Player;

import javax.swing.*;

/**
 * Created by Sphiinx on 12/26/2015.
 */
public class GUI extends javax.swing.JFrame {

    private Variables variables;

    public GUI(Variables variables) {
        this.variables = variables;
        initComponents();
    }

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
        makeWine.setEnabled(false);
        start = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel1.setText("[SPX] AIO COOKER");

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

        makeWine.setText("Temporarily Disabled");
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

    private void printGuiInformation() {
        General.println("Location: " + variables.location);
        General.println("Cooking: " + variables.foodId);
    }

    private void startActionPerformed(java.awt.event.ActionEvent evt) {
        variables.levelToStop = Integer.parseInt(levelToStop.getValue().toString());
        variables.amountToStop = Integer.parseInt(amoutBeforeStopping.getValue().toString());
        variables.foodId = foodType.getText().toString();
        if (makeWine.isSelected()) {
            variables.makeWine = true;
        } else {
            variables.location = location.getSelectedItem().toString();
            switch (location.getSelectedItem().toString()) {
                case "Catherby":
                    printGuiInformation();
                    if (!Constants.CATHERBY_AREA.contains(Player.getPosition())) {
                        General.println("Please start the script at the " + variables.location + " Bank.");
                        General.println("Stopping Script...");
                        variables.stopScript = true;
                    }
                    break;
                case "Al Kharid":
                    printGuiInformation();
                    if (!Constants.ALKHARID_AREA.contains(Player.getPosition())) {
                        General.println("Please start the script at the " + variables.location + "Bank.");
                        General.println("Stopping Script...");
                        variables.stopScript = true;
                    }
                    break;
                case "Falador":
                    printGuiInformation();
                    if (!Constants.FALADOR_AREA.contains(Player.getPosition())) {
                        General.println("Please start the script at the " + variables.location + "Bank.");
                        General.println("Stopping Script...");
                        variables.stopScript = true;
                    }
                    break;
                case "Rouges Den":
                    printGuiInformation();
                    if (!Constants.ROUGES_DEN_AREA.contains(Player.getPosition())) {
                        General.println("Please start the script at the " + variables.location + "Bank.");
                        General.println("Stopping Script...");
                        variables.stopScript = true;
                    }
                    break;
            }
        }
        variables.guiComplete = true;
        setVisible(false);
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

