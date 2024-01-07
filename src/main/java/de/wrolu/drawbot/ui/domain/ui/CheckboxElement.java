package de.wrolu.drawbot.ui.domain.ui;

import javax.swing.*;

public class CheckboxElement extends UIElement {
    public static final String TYPE = "checkbox";

    private final String name;
    private final String text;
    private boolean checked;

    public CheckboxElement(String name, String text, boolean checked) {
        super(TYPE);
        this.name = name;
        this.text = text;
        this.checked = checked;
    }

    public String getText() {
        return text;
    }

    public boolean isChecked() {
        return checked;
    }

    @Override
    public JComponent createComponent() {
        JCheckBox checkBox = new JCheckBox(text);
        checkBox.setName(name);
        checkBox.setSelected(checked);

        //update checked field when checkbox is clicked
        checkBox.addActionListener(e -> {
            JCheckBox cb = (JCheckBox) e.getSource();
            checked = cb.isSelected();
        });

        return checkBox;
    }

    @Override
    public boolean hasData() {
        return true;
    }

    @Override
    public Object getData() {
        return checked;
    }

    public String getName() {
        return name;
    }
}
