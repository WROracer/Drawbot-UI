package de.wrolu.drawbot.ui.domain;

import com.google.gson.*;

import java.lang.reflect.Type;

public abstract class WebsocketMessage {
    private final String type;

    public WebsocketMessage(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @SuppressWarnings("SwitchStatementWithTooFewBranches")
    public static class JsonConverter implements JsonDeserializer<WebsocketMessage> {
        @Override
        public WebsocketMessage deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            if (json.isJsonObject()) {
                JsonObject jsonObject = json.getAsJsonObject();
                String type = jsonObject.get("type").getAsString();
                switch (type) {
                    case UpdateUIMessage.TYPE:
                        return context.deserialize(json, UpdateUIMessage.class);
                    case ButtonPressedMessage.TYPE:
                        return context.deserialize(json, ButtonPressedMessage.class);
                    default:
                        throw new JsonParseException("Unknown message type: " + type);
                }
            }
            return null;
        }
    }
}
