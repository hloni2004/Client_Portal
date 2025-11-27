package za.ac.styling.factory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.ac.styling.domain.Notification;
import za.ac.styling.domain.NotificationType;
import za.ac.styling.domain.User;
import za.ac.styling.domain.UserRole;

import static org.junit.jupiter.api.Assertions.*;

class NotificationFactoryTest {

    private String message;
    private NotificationType type;
    private User user;

    @BeforeEach
    void setUp() {
        message = "Your project has been updated";
        type = NotificationType.PROJECT_UPDATE;
        user = UserFactory.createUser("Jane Doe", "jane@example.com", "password123", UserRole.CLIENT);
    }

    @Test
    void createNotification_WithValidData() {
        // Act
        Notification notification = NotificationFactory.createNotification(message, type, user);

        // Assert
        assertNotNull(notification);
        assertEquals(message, notification.getMessage());
        assertEquals(type, notification.getType());
        assertEquals(user, notification.getUser());
        assertEquals(false, notification.getIsRead());
    }

    @Test
    void createNotification_WithEmptyMessage() {
        // Arrange
        String emptyMessage = "";

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            NotificationFactory.createNotification(emptyMessage, type, user);
        });
        assertTrue(exception.getMessage().contains("Invalid message"));
    }

    @Test
    void createNotification_WithNullMessage() {
        // Arrange
        String nullMessage = null;

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            NotificationFactory.createNotification(nullMessage, type, user);
        });
        assertTrue(exception.getMessage().contains("Invalid message"));
    }

    @Test
    void createNotification_WithNullType() {
        // Arrange
        NotificationType nullType = null;

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            NotificationFactory.createNotification(message, nullType, user);
        });
        assertTrue(exception.getMessage().contains("Invalid type"));
    }

    @Test
    void createNotification() {
        // Arrange
        User nullUser = null;

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            NotificationFactory.createNotification(message, type, nullUser);
        });
        assertTrue(exception.getMessage().contains("Invalid user"));
    }

    @Test
    void validateNotification_WithValidNotification() {
        // Arrange
        Notification notification = NotificationFactory.createNotification(message, type, user);

        // Act
        boolean isValid = NotificationFactory.validateNotification(notification);

        // Assert
        assertTrue(isValid);
    }

    @Test
    void validateNotification_WithNullNotification_ShouldReturnFalse() {
        // Arrange
        Notification notification = null;

        // Act
        boolean isValid = NotificationFactory.validateNotification(notification);

        // Assert
        assertFalse(isValid);
    }
}
