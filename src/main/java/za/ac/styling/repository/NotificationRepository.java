package za.ac.styling.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.styling.domain.Notification;
import za.ac.styling.domain.NotificationType;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {

    List<Notification> findByUserUserId(Integer userId);

    List<Notification> findByUserUserIdAndIsRead(Integer userId, Boolean isRead);

    List<Notification> findByType(NotificationType type);

    List<Notification> findByUserUserIdOrderBySentAtDesc(Integer userId);

    long countByUserUserIdAndIsRead(Integer userId, Boolean isRead);
}
