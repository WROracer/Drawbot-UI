package de.wrolu.drawbot.ui.domain;

import de.wrolu.drawbot.ui.domain.ui.ButtonElement;

import java.util.HashMap;

public class ButtonPressedMessage extends WebsocketMessage {
    public static final String TYPE = "buttonPressed";
    private final HashMap<String, Object> data;

    private final String button;

    public ButtonPressedMessage(ButtonElement buttonElement, HashMap<String, Object> data) {
        super(TYPE);
        this.button = buttonElement.getName();
        this.data = data;
    }

    public String getButton() {
        return button;
    }

    public HashMap<String, Object> getData() {
        return data;
    }
}
