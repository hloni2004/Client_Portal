package za.ac.styling.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.ac.styling.domain.Notification;
import za.ac.styling.domain.NotificationType;
import za.ac.styling.domain.User;
import za.ac.styling.factory.NotificationFactory;
import za.ac.styling.repository.NotificationRepository;
import za.ac.styling.repository.UserRepository;
import za.ac.styling.service.INotificationService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationServiceImpl implements INotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    @Override
    public Notification save(Notification entity) {
        if (!NotificationFactory.validateNotification(entity)) {
            throw new IllegalArgumentException("Invalid notification data");
        }
        return notificationRepository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Notification> findById(Integer id) {
        return notificationRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Notification> findAll() {
        return notificationRepository.findAll();
    }

    @Override
    public Notification update(Notification entity) {
        if (!NotificationFactory.validateNotification(entity)) {
            throw new IllegalArgumentException("Invalid notification data");
        }
        if (!notificationRepository.existsById(entity.getNotificationId())) {
            throw new IllegalArgumentException("Notification not found with id: " + entity.getNotificationId());
        }
        return notificationRepository.save(entity);
    }

    @Override
    public void deleteById(Integer id) {
        if (!notificationRepository.existsById(id)) {
            throw new IllegalArgumentException("Notification not found with id: " + id);
        }
        notificationRepository.deleteById(id);
    }

    @Override
    public void delete(Notification entity) {
        notificationRepository.delete(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Integer id) {
        return notificationRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return notificationRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Notification> findByUserId(Integer userId) {
        return notificationRepository.findByUserUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Notification> findByUserIdAndReadStatus(Integer userId, Boolean isRead) {
        return notificationRepository.findByUserUserIdAndIsRead(userId, isRead);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Notification> findByType(NotificationType type) {
        return notificationRepository.findByType(type);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Notification> findByUserIdOrderedByDate(Integer userId) {
        return notificationRepository.findByUserUserIdOrderBySentAtDesc(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public long countUnreadNotifications(Integer userId) {
        return notificationRepository.countByUserUserIdAndIsRead(userId, false);
    }

    @Override
    public void markAsRead(Integer notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new IllegalArgumentException("Notification not found with id: " + notificationId));
        
        notification.markAsRead();
        notificationRepository.save(notification);
    }

    @Override
    public void markAllAsRead(Integer userId) {
        List<Notification> unreadNotifications = notificationRepository.findByUserUserIdAndIsRead(userId, false);
        unreadNotifications.forEach(Notification::markAsRead);
        notificationRepository.saveAll(unreadNotifications);
    }

    @Override
    public Notification createNotification(String message, NotificationType type, Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
        
        Notification notification = NotificationFactory.createNotification(message, type, user);
        return notificationRepository.save(notification);
    }
}
