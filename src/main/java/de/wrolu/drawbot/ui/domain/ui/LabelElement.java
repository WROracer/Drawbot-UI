package de.wrolu.drawbot.ui.domain.ui;

import javax.swing.*;

public class LabelElement extends UIElement {
    public static final String TYPE = "label";
    private final String text;

    public LabelElement(String text) {
        super(TYPE);
        this.text = text;
    }

    @Override
    public JComponent createComponent() {
        return new JLabel(text);
    }

    @Override
    public boolean hasData() {
        return false;
    }

    @Override
    public Object getData() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    public String getText() {
        return text;
    }
}
