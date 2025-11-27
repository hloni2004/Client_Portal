package za.ac.styling.factory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.ac.styling.domain.*;

import static org.junit.jupiter.api.Assertions.*;

class FeedbackFactoryTest {

    private String message;
    private Deliverable deliverable;
    private User user;

    @BeforeEach
    void setUp() {
        message = "Great work on this deliverable!";
        Project project = ProjectFactory.createProject(1, "Test Project");
        deliverable = DeliverableFactory.createDeliverable(
                "document.pdf", "application/pdf", "http://example.com/file.pdf", project
        );
        user = UserFactory.createUser("John Doe", "john@example.com", "password123", UserRole.ADMIN);
    }

    @Test
    void createFeedback_WithValidData_ShouldCreateFeedback() {
        // Act
        Feedback feedback = FeedbackFactory.createFeedback(message, deliverable, user);

        // Assert
        assertNotNull(feedback);
        assertEquals(message, feedback.getMessage());
        assertEquals(deliverable, feedback.getDeliverable());
        assertEquals(user, feedback.getUser());
    }

    @Test
    void createFeedback_WithEmptyMessage_ShouldThrowException() {
        // Arrange
        String emptyMessage = "";

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            FeedbackFactory.createFeedback(emptyMessage, deliverable, user);
        });
        assertTrue(exception.getMessage().contains("Invalid message"));
    }

    @Test
    void createFeedback_WithNullMessage_ShouldThrowException() {
        // Arrange
        String nullMessage = null;

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            FeedbackFactory.createFeedback(nullMessage, deliverable, user);
        });
        assertTrue(exception.getMessage().contains("Invalid message"));
    }

    @Test
    void createFeedback_WithNullDeliverable_ShouldThrowException() {
        // Arrange
        Deliverable nullDeliverable = null;

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            FeedbackFactory.createFeedback(message, nullDeliverable, user);
        });
        assertTrue(exception.getMessage().contains("Invalid deliverable"));
    }

    @Test
    void createFeedback_WithNullUser_ShouldThrowException() {
        // Arrange
        User nullUser = null;

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            FeedbackFactory.createFeedback(message, deliverable, nullUser);
        });
        assertTrue(exception.getMessage().contains("Invalid user"));
    }

    @Test
    void validateFeedback_WithValidFeedback_ShouldReturnTrue() {
        // Arrange
        Feedback feedback = FeedbackFactory.createFeedback(message, deliverable, user);

        // Act
        boolean isValid = FeedbackFactory.validateFeedback(feedback);

        // Assert
        assertTrue(isValid);
    }

    @Test
    void validateFeedback_WithNullFeedback_ShouldReturnFalse() {
        // Arrange
        Feedback feedback = null;

        // Act
        boolean isValid = FeedbackFactory.validateFeedback(feedback);

        // Assert
        assertFalse(isValid);
    }
}
