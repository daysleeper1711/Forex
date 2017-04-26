package daysleeper.project.forex.tradelog.exceptions;

import daysleeper.project.forex.tradelog.utilies.Notification;
import daysleeper.project.forex.tradelog.utilies.NotificationLv;
import java.util.List;

public class ExceptionNotification {

    protected Notification createNotification(String message) {
        Notification notification = new Notification();
        notification.setSuccess(false);
        notification.setNotificationLv(NotificationLv.EXCEPTION);
        notification.setMessage(message);
        return notification;
    }
    
    protected Notification createNotificationValidation(List<String> messages) {
        Notification notification = new Notification();
        notification.setSuccess(false);
        notification.setNotificationLv(NotificationLv.ERROR);
        notification.setMessages(messages);
        return notification;
    }
}
