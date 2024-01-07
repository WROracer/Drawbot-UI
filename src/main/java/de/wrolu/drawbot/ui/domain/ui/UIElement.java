package de.wrolu.drawbot.ui.domain.ui;

import com.google.gson.*;

import javax.swing.*;
import java.lang.reflect.Type;

public abstract class UIElement {
    private final String type;

    public UIElement(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public abstract JComponent createComponent();

    public abstract boolean hasData();

    public abstract Object getData();

    public abstract String getName();

    @SuppressWarnings("SwitchStatementWithTooFewBranches")
    public static class JsonConverter implements JsonDeserializer<UIElement> {

        @Override
        public UIElement deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            if (json.isJsonObject()) {
                JsonObject jsonObject = json.getAsJsonObject();
                String type = jsonObject.get("type").getAsString();
                switch (type) {
                    case TitleElement.TYPE:
                        return context.deserialize(json, TitleElement.class);
                    case LabelElement.TYPE:
                        return context.deserialize(json, LabelElement.class);
                    case CheckboxElement.TYPE:
                        return context.deserialize(json, CheckboxElement.class);
                    case GeometryElement.TYPE:
                        return context.deserialize(json, GeometryElement.class);
                    case ButtonElement.TYPE:
                        return context.deserialize(json, ButtonElement.class);
                    case DropDownElement.TYPE:
                        return context.deserialize(json, DropDownElement.class);
                    case EntryElement.TYPE:
                        return context.deserialize(json, EntryElement.class);
                    case "none":
                        return null;
                    default:
                        throw new JsonParseException("Unknown UIElement type: " + type);
                }
            }
            return null;
        }
    }
}
