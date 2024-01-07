package de.wrolu.drawbot.ui.domain.ui;

import javax.swing.*;

public class ButtonElement extends UIElement {
    public static final String TYPE = "button";
    private final String text;
    private final String name;
    private boolean checked;

    public ButtonElement(String name, String text, boolean checked) {
        super(TYPE);
        this.name = name;
        this.text = text;
        this.checked = checked;
    }

    public boolean isChecked() {
        return checked;
    }

    @Override
    public JComponent createComponent() {
        JButton button = new JButton(text);
        button.setName(name);
        button.setSelected(checked);

        //update checked field when checkbox is clicked
        button.addActionListener(e -> {
            JButton cb = (JButton) e.getSource();
            checked = cb.isSelected();
        });
        return button;
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
        return name;
    }

    public String getText() {
        return text;
    }
}
