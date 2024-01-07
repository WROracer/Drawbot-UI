package de.wrolu.drawbot.ui.domain.ui;

import javax.swing.*;

public class GeometryElement extends UIElement {
    public static final String TYPE = "geometry";
    private final String name;
    private final int width;
    private final int height;
    private final int x;
    private final int y;

    public GeometryElement(String name, int width, int height, int x, int y) {
        super(TYPE);
        this.name = name;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }

    @Override
    public JComponent createComponent() {
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

    public String getName() {
        return name;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
