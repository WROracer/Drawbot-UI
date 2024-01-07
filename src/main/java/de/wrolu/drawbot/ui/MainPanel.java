package de.wrolu.drawbot.ui;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.dispatcher.SwingDispatchService;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.wrolu.drawbot.ui.domain.ButtonPressedMessage;
import de.wrolu.drawbot.ui.domain.UpdateUIMessage;
import de.wrolu.drawbot.ui.domain.WebsocketMessage;
import de.wrolu.drawbot.ui.domain.ui.ButtonElement;
import de.wrolu.drawbot.ui.domain.ui.GeometryElement;
import de.wrolu.drawbot.ui.domain.ui.TitleElement;
import de.wrolu.drawbot.ui.domain.ui.UIElement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainPanel extends JPanel implements NativeKeyListener, WebsocketClientEndpoint.MessageHandler, ActionListener {

    private final boolean nativeEnabled = true;
    private final Gson gson;
    private final App app;
    private final List<UIElement> uiElements;
    private WebsocketClientEndpoint wsc;
    private HashMap<JButton, ButtonElement> buttons = new HashMap<>();

    public MainPanel(App app) {
        super();
        this.app = app;

        buttons = new HashMap<>();
        uiElements = new ArrayList<>();

        //Init Gson
        gson = new GsonBuilder()
                .registerTypeAdapter(WebsocketMessage.class, new WebsocketMessage.JsonConverter())
                .registerTypeAdapter(UIElement.class, new UIElement.JsonConverter())
                .setPrettyPrinting()
                .create();
    }

    public void start() {
        //Register Native Hook
        try {
            if (nativeEnabled) {
                GlobalScreen.registerNativeHook();

                GlobalScreen.setEventDispatcher(new SwingDispatchService());
                GlobalScreen.addNativeKeyListener(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            wsc = new WebsocketClientEndpoint(new URI(app.getConfig().getHost() + ":" + app.getConfig().getPort()));
            wsc.addMessageHandler(this);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeEvent) {
        //if key equals "q" or esc then send "stop" to the websocket
        if (nativeEvent.getKeyChar() == 'q' || nativeEvent.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
            wsc.sendMessage("stop");
            System.out.println("stoping Drawing");
        }
    }

    @Override
    public void handleMessage(String message) {
        System.out.println(message);

        //If the message contains "ping" then send "pong" back (Health Check)
        if (message.contains("ping")) {
            wsc.sendMessage("pong");
        } else {
            WebsocketMessage websocketMessage = gson.fromJson(message, WebsocketMessage.class);
            System.out.println(websocketMessage);
            if (websocketMessage instanceof UpdateUIMessage) {
                changeUI((UpdateUIMessage) websocketMessage);
            }
        }
    }

    private void changeUI(UpdateUIMessage uiMessage) {
        removeAll();
        buttons.clear();
        uiElements.clear();
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        JPanel linePanel = createLinePanel();
        for (List<UIElement> line : uiMessage.getData()) {
            for (UIElement uiElement : line) {
                if (uiElement instanceof TitleElement titleElement) {
                    app.setTitle(titleElement.getText());
                    //linePanel = null;
                } else if (uiElement instanceof GeometryElement geometryElement) {
                    //linePanel = null;
                    if (geometryElement.getX() != 0 && geometryElement.getY() != 0) {
                        setBounds(geometryElement.getX(), geometryElement.getY(), geometryElement.getWidth(), geometryElement.getHeight());
                    } else {
                        setSize(geometryElement.getWidth(), geometryElement.getHeight());
                    }
                } else if (uiElement != null) {

                    JComponent component = uiElement.createComponent();

                    linePanel.add(component);
                    linePanel.add(Box.createRigidArea(new Dimension(5, 5)));

                    if (component instanceof JButton button) {
                        ((JButton) component).addActionListener(this);
                        buttons.put(button, (ButtonElement) uiElement);
                    } else {
                        uiElements.add(uiElement);
                    }
                }
            }
            if (linePanel != null) {
                add(linePanel);
                add(Box.createRigidArea(new Dimension(5, 5)));
            }
            linePanel = createLinePanel();
        }
        revalidate();
        repaint();
    }

    private JPanel createLinePanel() {
        JPanel linePanel = new JPanel();
        linePanel.setLayout(new BoxLayout(linePanel, BoxLayout.LINE_AXIS));
        //add a Border of 5px on the top and bottom in the color black
        //linePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        //add a spacer of 5px on the top
        return linePanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        ButtonElement buttonElement = buttons.get(button);
        HashMap<String, Object> data = collectData();
        ButtonPressedMessage buttonPressedMessage = new ButtonPressedMessage(buttonElement, data);
        String json = gson.toJson(buttonPressedMessage);
        wsc.sendMessage(json);
    }

    private HashMap<String, Object> collectData() {
        HashMap<String, Object> data = new HashMap<>();
        for (UIElement uiElement : uiElements) {
            if (uiElement.hasData()) {
                data.put(uiElement.getName(), uiElement.getData());
            }
        }
        return data;
    }
}
