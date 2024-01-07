package de.wrolu.drawbot.ui.domain.ui;

import javax.swing.*;

public class TitleElement extends UIElement {
    public static final String TYPE = "title";
    private final String text;

    public TitleElement(String text) {
        super(TYPE);
        this.text = text;
    }

    @Override
    public JComponent createComponent() {
        //this is for the Window title so we don't need a component
        return null;
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
