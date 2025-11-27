package za.ac.styling.factory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.ac.styling.domain.Project;
import za.ac.styling.domain.ProjectStatus;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ProjectFactoryTest {

    private Integer clientId;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate dueDate;

    @BeforeEach
    void setUp() {
        clientId = 1;
        title = "New Website Project";
        description = "Build a modern website";
        startDate = LocalDate.now();
        dueDate = LocalDate.now().plusDays(30);
    }

    @Test
    void createProject_WithAllFields_ShouldCreateProject() {
        // Act
        Project project = ProjectFactory.createProject(clientId, title, description, startDate, dueDate);

        // Assert
        assertNotNull(project);
        assertEquals(clientId, project.getClientId());
        assertEquals(title, project.getTitle());
        assertEquals(description, project.getDescription());
        assertEquals(startDate, project.getStartDate());
        assertEquals(dueDate, project.getDueDate());
        assertEquals(ProjectStatus.NOT_STARTED, project.getStatus());
        assertEquals(0.0, project.getProgress());
    }

    @Test
    void createProject_WithRequiredFieldsOnly_ShouldCreateProject() {
        // Act
        Project project = ProjectFactory.createProject(clientId, title);

        // Assert
        assertNotNull(project);
        assertEquals(clientId, project.getClientId());
        assertEquals(title, project.getTitle());
        assertNull(project.getDescription());
        assertNull(project.getStartDate());
        assertNull(project.getDueDate());
        assertEquals(ProjectStatus.NOT_STARTED, project.getStatus());
        assertEquals(0.0, project.getProgress());
    }

    @Test
    void createProject_WithInvalidClientId_ShouldThrowException() {
        // Arrange
        Integer invalidClientId = -1;

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            ProjectFactory.createProject(invalidClientId, title);
        });
        assertTrue(exception.getMessage().contains("Invalid clientId"));
    }

    @Test
    void createProject_WithNullClientId_ShouldThrowException() {
        // Arrange
        Integer nullClientId = null;

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            ProjectFactory.createProject(nullClientId, title);
        });
        assertTrue(exception.getMessage().contains("Invalid clientId"));
    }

    @Test
    void createProject_WithEmptyTitle_ShouldThrowException() {
        // Arrange
        String emptyTitle = "";

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            ProjectFactory.createProject(clientId, emptyTitle);
        });
        assertTrue(exception.getMessage().contains("Invalid title"));
    }

    @Test
    void createProject_WithNullTitle_ShouldThrowException() {
        // Arrange
        String nullTitle = null;

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            ProjectFactory.createProject(clientId, nullTitle);
        });
        assertTrue(exception.getMessage().contains("Invalid title"));
    }

    @Test
    void createProject_WithStartDateAfterDueDate_ShouldThrowException() {
        // Arrange
        LocalDate invalidStartDate = LocalDate.now().plusDays(30);
        LocalDate invalidDueDate = LocalDate.now();

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            ProjectFactory.createProject(clientId, title, description, invalidStartDate, invalidDueDate);
        });
        assertTrue(exception.getMessage().contains("Invalid dates"));
    }

    @Test
    void validateProject_WithValidProject_ShouldReturnTrue() {
        // Arrange
        Project project = ProjectFactory.createProject(clientId, title);

        // Act
        boolean isValid = ProjectFactory.validateProject(project);

        // Assert
        assertTrue(isValid);
    }

    @Test
    void validateProject_WithNullProject_ShouldReturnFalse() {
        // Arrange
        Project project = null;

        // Act
        boolean isValid = ProjectFactory.validateProject(project);

        // Assert
        assertFalse(isValid);
    }
}
