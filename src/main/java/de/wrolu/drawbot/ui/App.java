package de.wrolu.drawbot.ui;

import com.google.gson.Gson;
import de.wrolu.drawbot.ui.domain.Config;

import javax.swing.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class App extends JFrame {

    private MainPanel mainPanel;
    private ConfigPanel configPanel;
    private Config config;

    public App() {
        super("Drawbot - Config");

        loadConfig();

        initClass();


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(configPanel);
        setSize(300, 300);
        setVisible(true);

        if (config.isSkipConfig()) {
            showMainPanel();
        }
    }


    private void loadConfig() {
        //load form config.json file
        Gson gson = new Gson();
        File configFile = new File("config.json");
        if (configFile.exists()) {
            config = gson.fromJson(readFile(configFile), Config.class);
        } else {
            config = new Config();
        }
    }

    private void initClass() {
        mainPanel = new MainPanel(this);
        configPanel = new ConfigPanel(this);
    }

    public void showMainPanel() {
        remove(configPanel);
        add(mainPanel);
        setSize(300, 600);
        setVisible(true);
        mainPanel.start();
        mainPanel.setSize(300, 600);
    }

    private String readFile(File configFile) {
        FileReader reader = null;
        try {
            reader = new FileReader(configFile);
            char[] chars = new char[(int) configFile.length()];
            reader.read(chars);
            return new String(chars);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void main(String[] args) {
        new App();
    }

    public Config getConfig() {
        return config;
    }

    public void saveConfig() {
        File configFile = new File("config.json");
        Gson gson = new Gson();
        String json = gson.toJson(config);
        writeFile(configFile, json);
    }

    private void writeFile(File configFile, String json) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(configFile);
            writer.write(json);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
