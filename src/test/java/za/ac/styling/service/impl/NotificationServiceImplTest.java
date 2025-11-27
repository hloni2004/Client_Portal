package za.ac.styling.service.impl;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import za.ac.styling.domain.Notification;
import za.ac.styling.domain.NotificationType;
import za.ac.styling.domain.User;
import za.ac.styling.domain.UserRole;
import za.ac.styling.factory.UserFactory;
import za.ac.styling.service.INotificationService;
import za.ac.styling.service.IUserService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class NotificationServiceImplTest {

    @Autowired
    private INotificationService notificationService;

    @Autowired
    private IUserService userService;

    private static User testUser;
    private static Notification testNotification1;
    private static Notification testNotification2;

    @BeforeAll
    static void setUp() {
        testUser = UserFactory.createUser("Test User", "notifyuser@example.com", "password123", UserRole.CLIENT);
    }

    @Test
    @Order(1)
    void setupUser() {
        User saved = userService.save(testUser);
        assertNotNull(saved);
        assertNotNull(saved.getUserId());
        System.out.println("Saved User: " + saved);

        testUser = saved;
    }

    @Test
    @Order(2)
    void createNotification() {
        Notification created = notificationService.createNotification("Your project has been updated",
                NotificationType.PROJECT_UPDATE, testUser.getUserId());
        assertNotNull(created);
        assertNotNull(created.getNotificationId());
        assertEquals("Your project has been updated", created.getMessage());
        assertFalse(created.getIsRead());
        System.out.println("Created Notification 1: " + created);

        testNotification1 = created;
    }

    @Test
    @Order(3)
    void createSecondNotification() {
        Notification created = notificationService.createNotification("New task assigned to you",
                NotificationType.TASK_UPDATE, testUser.getUserId());
        assertNotNull(created);
        assertNotNull(created.getNotificationId());
        System.out.println("Created Notification 2: " + created);

        testNotification2 = created;
    }

    @Test
    @Order(4)
    void findById() {
        Optional<Notification> found = notificationService.findById(testNotification1.getNotificationId());
        assertTrue(found.isPresent());
        assertEquals(testNotification1.getNotificationId(), found.get().getNotificationId());
        assertEquals("Your project has been updated", found.get().getMessage());
    }

    @Test
    @Order(5)
    void findByUserId() {
        List<Notification> notifications = notificationService.findByUserId(testUser.getUserId());
        assertNotNull(notifications);
        assertTrue(notifications.size() >= 2);
        System.out.println("Notifications for user: " + notifications.size());
    }

    @Test
    @Order(6)
    void findByUserIdAndReadStatus() {
        List<Notification> unread = notificationService.findByUserIdAndReadStatus(testUser.getUserId(), false);
        assertNotNull(unread);
        assertTrue(unread.size() >= 2);
        System.out.println("Unread notifications: " + unread.size());
    }

    @Test
    @Order(7)
    void findByType() {
        List<Notification> notifications = notificationService.findByType(NotificationType.PROJECT_UPDATE);
        assertNotNull(notifications);
        assertFalse(notifications.isEmpty());
    }

    @Test
    @Order(8)
    void findByUserIdOrderedByDate() {
        List<Notification> notifications = notificationService.findByUserIdOrderedByDate(testUser.getUserId());
        assertNotNull(notifications);
        assertTrue(notifications.size() >= 2);
    }

    @Test
    @Order(9)
    void countUnreadNotifications() {
        long count = notificationService.countUnreadNotifications(testUser.getUserId());
        assertTrue(count >= 2);
        System.out.println("Unread notification count: " + count);
    }

    @Test
    @Order(10)
    void markAsRead() {
        notificationService.markAsRead(testNotification1.getNotificationId());

        Optional<Notification> updated = notificationService.findById(testNotification1.getNotificationId());
        assertTrue(updated.isPresent());
        assertTrue(updated.get().getIsRead());
    }

    @Test
    @Order(11)
    void markAllAsRead() {
        notificationService.markAllAsRead(testUser.getUserId());

        List<Notification> unread = notificationService.findByUserIdAndReadStatus(testUser.getUserId(), false);
        assertTrue(unread.isEmpty());
    }

    @Test
    @Order(12)
    void update() {
        testNotification2.setMessage("Updated notification message");
        Notification updated = notificationService.update(testNotification2);
        assertNotNull(updated);
        assertEquals("Updated notification message", updated.getMessage());
    }

    @Test
    @Order(13)
    void findAll() {
        List<Notification> all = notificationService.findAll();
        assertNotNull(all);
        assertTrue(all.size() >= 2);
        System.out.println("Total notifications: " + all.size());
    }

    @Test
    @Order(14)
    void count() {
        long count = notificationService.count();
        assertTrue(count >= 2);
        System.out.println("Notification count: " + count);
    }

    @Test
    @Order(15)
    void existsById() {
        boolean exists = notificationService.existsById(testNotification1.getNotificationId());
        assertTrue(exists);
    }

    @Test
    @Order(16)
    void deleteById() {
        notificationService.deleteById(testNotification2.getNotificationId());

        Optional<Notification> deleted = notificationService.findById(testNotification2.getNotificationId());
        assertFalse(deleted.isPresent());
    }
}
