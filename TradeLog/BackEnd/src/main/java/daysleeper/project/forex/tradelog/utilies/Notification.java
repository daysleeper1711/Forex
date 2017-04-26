package daysleeper.project.forex.tradelog.utilies;

import java.util.ArrayList;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class Notification {

    private boolean success;
    private NotificationLv notificationLv;
    private String message;
    private List<String> messages = new ArrayList<>();

    public Notification() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public NotificationLv getNotificationLv() {
        return notificationLv;
    }

    public void setNotificationLv(NotificationLv notificationLv) {
        this.notificationLv = notificationLv;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public JsonObject toJson() {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("success", this.success)
                .add("level", notificationLv.toString());
        if (this.message != null) {
            builder.add("message", this.message);
        } 
//        else {
//            builder.add("message", "null");
//        }
        
        if (!messages.isEmpty()) {
            JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
            for (String msg : messages) {
                arrBuilder.add(msg);
            }
            builder.add("messages", arrBuilder);
        }
        return builder.build();
    }
}
