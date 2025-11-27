package za.ac.styling.service;

import za.ac.styling.domain.Notification;
import za.ac.styling.domain.NotificationType;

import java.util.List;

public interface INotificationService extends IService<Notification, Integer> {

    List<Notification> findByUserId(Integer userId);

    List<Notification> findByUserIdAndReadStatus(Integer userId, Boolean isRead);

    List<Notification> findByType(NotificationType type);

    List<Notification> findByUserIdOrderedByDate(Integer userId);

    long countUnreadNotifications(Integer userId);

    void markAsRead(Integer notificationId);

    void markAllAsRead(Integer userId);

    Notification createNotification(String message, NotificationType type, Integer userId);
}
