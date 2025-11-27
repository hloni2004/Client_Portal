package za.ac.styling.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.styling.domain.Notification;
import za.ac.styling.domain.NotificationType;
import za.ac.styling.service.INotificationService;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final INotificationService notificationService;

    @PostMapping
    public ResponseEntity<Notification> createNotification(@RequestBody Notification notification) {
        Notification saved = notificationService.save(notification);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PostMapping("/create")
    public ResponseEntity<Notification> createNotification(@RequestParam String message,
                                                            @RequestParam NotificationType type,
                                                            @RequestParam Integer userId) {
        Notification notification = notificationService.createNotification(message, type, userId);
        return new ResponseEntity<>(notification, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notification> getNotificationById(@PathVariable Integer id) {
        return notificationService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Notification>> getAllNotifications() {
        List<Notification> notifications = notificationService.findAll();
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Notification>> getNotificationsByUser(@PathVariable Integer userId) {
        List<Notification> notifications = notificationService.findByUserId(userId);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/user/{userId}/ordered")
    public ResponseEntity<List<Notification>> getNotificationsByUserOrdered(@PathVariable Integer userId) {
        List<Notification> notifications = notificationService.findByUserIdOrderedByDate(userId);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/user/{userId}/read/{isRead}")
    public ResponseEntity<List<Notification>> getNotificationsByUserAndReadStatus(@PathVariable Integer userId,
                                                                                   @PathVariable Boolean isRead) {
        List<Notification> notifications = notificationService.findByUserIdAndReadStatus(userId, isRead);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<Notification>> getNotificationsByType(@PathVariable NotificationType type) {
        List<Notification> notifications = notificationService.findByType(type);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/user/{userId}/unread-count")
    public ResponseEntity<Long> countUnreadNotifications(@PathVariable Integer userId) {
        long count = notificationService.countUnreadNotifications(userId);
        return ResponseEntity.ok(count);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Notification> updateNotification(@PathVariable Integer id, @RequestBody Notification notification) {
        if (!notificationService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        notification.setNotificationId(id);
        Notification updated = notificationService.update(notification);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/{id}/mark-read")
    public ResponseEntity<Void> markAsRead(@PathVariable Integer id) {
        notificationService.markAsRead(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/user/{userId}/mark-all-read")
    public ResponseEntity<Void> markAllAsRead(@PathVariable Integer userId) {
        notificationService.markAllAsRead(userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Integer id) {
        if (!notificationService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        notificationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countNotifications() {
        long count = notificationService.count();
        return ResponseEntity.ok(count);
    }
}
