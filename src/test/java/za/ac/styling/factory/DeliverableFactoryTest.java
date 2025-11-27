package za.ac.styling.factory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.ac.styling.domain.Deliverable;
import za.ac.styling.domain.Project;

import static org.junit.jupiter.api.Assertions.*;

class DeliverableFactoryTest {

    private String fileName;
    private String fileType;
    private String fileUrl;
    private Project project;

    @BeforeEach
    void setUp() {
        fileName = "report.pdf";
        fileType = "application/pdf";
        fileUrl = "http://example.com/files/report.pdf";
        project = ProjectFactory.createProject(1, "Test Project");
    }

    @Test
    void createDeliverable_WithValidData_ShouldCreateDeliverable() {

        Deliverable deliverable = DeliverableFactory.createDeliverable(fileName, fileType, fileUrl, project);

        // Assert
        assertNotNull(deliverable);
        assertEquals(fileName, deliverable.getFileName());
        assertEquals(fileType, deliverable.getFileType());
        assertEquals(fileUrl, deliverable.getFileUrl());
        assertEquals(project, deliverable.getProject());
        assertEquals(false, deliverable.getApproved());
        System.out.print(deliverable.toString());
    }

    @Test
    void createDeliverable_WithEmptyFileName_ShouldThrowException() {
        // Arrange
        String emptyFileName = "";

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            DeliverableFactory.createDeliverable(emptyFileName, fileType, fileUrl, project);
        });
        assertTrue(exception.getMessage().contains("Invalid fileName"));
    }

    @Test
    void createDeliverable_WithNullFileName_ShouldThrowException() {
        // Arrange
        String nullFileName = null;

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            DeliverableFactory.createDeliverable(nullFileName, fileType, fileUrl, project);
        });
        assertTrue(exception.getMessage().contains("Invalid fileName"));
    }

    @Test
    void createDeliverable_WithEmptyFileType_ShouldThrowException() {
        // Arrange
        String emptyFileType = "";

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            DeliverableFactory.createDeliverable(fileName, emptyFileType, fileUrl, project);
        });
        assertTrue(exception.getMessage().contains("Invalid fileType"));
    }

    @Test
    void createDeliverable_WithNullFileType_ShouldThrowException() {
        // Arrange
        String nullFileType = null;

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            DeliverableFactory.createDeliverable(fileName, nullFileType, fileUrl, project);
        });
        assertTrue(exception.getMessage().contains("Invalid fileType"));
    }

    @Test
    void createDeliverable_WithEmptyFileUrl_ShouldThrowException() {
        // Arrange
        String emptyFileUrl = "";

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            DeliverableFactory.createDeliverable(fileName, fileType, emptyFileUrl, project);
        });
        assertTrue(exception.getMessage().contains("Invalid fileUrl"));
    }

    @Test
    void createDeliverable_WithNullFileUrl_ShouldThrowException() {
        // Arrange
        String nullFileUrl = null;

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            DeliverableFactory.createDeliverable(fileName, fileType, nullFileUrl, project);
        });
        assertTrue(exception.getMessage().contains("Invalid fileUrl"));
    }

    @Test
    void createDeliverable_WithNullProject_ShouldThrowException() {
        // Arrange
        Project nullProject = null;

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            DeliverableFactory.createDeliverable(fileName, fileType, fileUrl, nullProject);
        });
        assertTrue(exception.getMessage().contains("Invalid project"));
    }

    @Test
    void validateDeliverable_WithValidDeliverable_ShouldReturnTrue() {
        // Arrange
        Deliverable deliverable = DeliverableFactory.createDeliverable(fileName, fileType, fileUrl, project);

        // Act
        boolean isValid = DeliverableFactory.validateDeliverable(deliverable);

        // Assert
        assertTrue(isValid);
    }

    @Test
    void validateDeliverable_WithNullDeliverable_ShouldReturnFalse() {
        // Arrange
        Deliverable deliverable = null;

        // Act
        boolean isValid = DeliverableFactory.validateDeliverable(deliverable);

        // Assert
        assertFalse(isValid);
    }
}