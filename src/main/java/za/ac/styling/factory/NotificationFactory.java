package za.ac.styling.factory;

import za.ac.styling.domain.Notification;
import za.ac.styling.domain.NotificationType;
import za.ac.styling.domain.User;
import za.ac.styling.util.ValidationHelper;

public class NotificationFactory {

    public static Notification createNotification(String message, NotificationType type, User user) {
        if (!ValidationHelper.validateMessage(message)) {
            throw new IllegalArgumentException("Invalid message: must not be empty");
        }

        if (type == null) {
            throw new IllegalArgumentException("Invalid type: type cannot be null");
        }

        if (user == null) {
            throw new IllegalArgumentException("Invalid user: user cannot be null");
        }

        Notification notification = Notification.builder()
                .message(message)
                .type(type)
                .user(user)
                .isRead(false)
                .build();

        return notification;
    }

    public static boolean validateNotification(Notification notification) {
        return ValidationHelper.validateNotification(notification);
    }
}
