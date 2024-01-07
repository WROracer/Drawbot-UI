package de.wrolu.drawbot.ui.domain.ui;

import javax.swing.*;

public class EntryElement extends UIElement {
    public static final String TYPE = "entry";
    private final String name;
    private String content;

    public EntryElement(String name, String content) {
        super(TYPE);
        this.name = name;
        this.content = content;
    }

    @Override
    public JComponent createComponent() {
        JTextField textField = new JTextField(content);
        textField.setName(name);
        textField.addActionListener(e -> {
            JTextField tf = (JTextField) e.getSource();
            content = tf.getText();
        });
        return textField;
    }

    @Override
    public boolean hasData() {
        return true;
    }

    @Override
    public Object getData() {
        return content;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

}
