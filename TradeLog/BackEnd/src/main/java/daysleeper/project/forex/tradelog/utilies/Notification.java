package daysleeper.project.forex.tradelog.utilies;

import javax.json.Json;
import javax.json.JsonObject;

public class Notification {
    private boolean success;
    private NotificationLv notificationLv;
    private String message;

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
    
    public JsonObject toJson() {
        return Json.createObjectBuilder()
                .add("success", this.success)
                .add("level", notificationLv.toString())
                .add("message", this.message)
                .build();
    }
}
