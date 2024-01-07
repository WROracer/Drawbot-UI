package de.wrolu.drawbot.ui.domain;

import de.wrolu.drawbot.ui.domain.ui.UIElement;

import java.util.List;

public class UpdateUIMessage extends WebsocketMessage {

    public static final String TYPE = "updateUI";
    private List<List<UIElement>> data;

    public UpdateUIMessage() {
        super(TYPE);
    }

    public List<List<UIElement>> getData() {
        return data;
    }
}
