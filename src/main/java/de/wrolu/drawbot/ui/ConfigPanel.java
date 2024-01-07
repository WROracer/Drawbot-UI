package de.wrolu.drawbot.ui;

import javax.swing.*;
import java.awt.*;

public class ConfigPanel extends JPanel {

    private final App app;

    private final JTextField hostField;
    private final JTextField portField;

    private final JCheckBox dontShowAgainCheckBox;
    private final JButton okButton;

    public ConfigPanel(App app) {
        this.app = app;

        hostField = new JTextField();
        portField = new JTextField();
        dontShowAgainCheckBox = new JCheckBox();
        okButton = new JButton("OK");

        //init ui like this BotAddress: [hostField]:[portField] (nextLine) Open this Config on Startup [dontShowAgainCheckBox]  (nextLine) [okButton]
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JPanel hostPanel = new JPanel();
        hostPanel.setLayout(new BoxLayout(hostPanel, BoxLayout.X_AXIS));
        hostPanel.add(new JLabel("BotAddress: "));
        hostPanel.add(hostField);
        hostPanel.add(new JLabel(":"));
        hostPanel.add(portField);
        add(hostPanel);
        JPanel dontShowAgainPanel = new JPanel();
        dontShowAgainPanel.setLayout(new BoxLayout(dontShowAgainPanel, BoxLayout.X_AXIS));
        dontShowAgainPanel.add(new JLabel("Open this Config on Startup "));
        dontShowAgainPanel.add(dontShowAgainCheckBox);
        add(dontShowAgainPanel);
        add(okButton);


        hostField.setMaximumSize(new Dimension(100, 20));
        portField.setMaximumSize(new Dimension(50, 20));

        hostField.setText(app.getConfig().getHost());
        portField.setText(app.getConfig().getPort());
        dontShowAgainCheckBox.setSelected(app.getConfig().isSkipConfig());

        /*JColorChooser tcc = new JColorChooser(Color.WHITE);

        add(tcc);*/

        //change size of the text fields

        //add action listener to ok button
        okButton.addActionListener(e -> {
            app.getConfig().setHost(hostField.getText());
            app.getConfig().setPort(portField.getText());
            app.getConfig().setSkipConfig(dontShowAgainCheckBox.isSelected());
            app.saveConfig();
            app.showMainPanel();
        });
    }
}
