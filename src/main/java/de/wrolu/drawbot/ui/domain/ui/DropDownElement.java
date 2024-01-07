package de.wrolu.drawbot.ui.domain.ui;

import javax.swing.*;
import java.util.List;

public class DropDownElement extends UIElement {
    public static final String TYPE = "dropdown";

    private final String name;
    private final List<String> values;
    private String selected;

    public DropDownElement(String name, List<String> values, String selected) {
        super(TYPE);
        this.name = name;
        this.values = values;
        this.selected = selected;
    }

    @Override
    public JComponent createComponent() {
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.setName(name);
        values.forEach(comboBox::addItem);
        comboBox.setSelectedItem(selected);
        comboBox.addActionListener(e -> {
            JComboBox<String> cb = (JComboBox<String>) e.getSource();
            selected = (String) cb.getSelectedItem();
        });
        return comboBox;
    }

    @Override
    public boolean hasData() {
        return true;
    }

    @Override
    public Object getData() {
        return selected;
    }

    @Override
    public String getName() {
        return name;
    }

    public List<String> getValues() {
        return values;
    }

    public String getSelected() {
        return selected;
    }
}
