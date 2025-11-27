package za.ac.styling.factory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.ac.styling.domain.Project;
import za.ac.styling.domain.Task;
import za.ac.styling.domain.TaskStatus;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TaskFactoryTest {

    private String title;
    private String description;
    private Project project;
    private Integer assignedToId;
    private LocalDate dueDate;

    @BeforeEach
    void setUp() {
        title = "Implement login feature";
        description = "Create user authentication";
        project = ProjectFactory.createProject(1, "Test Project");
        assignedToId = 5;
        dueDate = LocalDate.now().plusDays(7);
    }

    @Test
    void createTask_WithAllFields_ShouldCreateTask() {
        // Act
        Task task = TaskFactory.createTask(title, description, project, assignedToId, dueDate);

        // Assert
        assertNotNull(task);
        assertEquals(title, task.getTitle());
        assertEquals(description, task.getDescription());
        assertEquals(project, task.getProject());
        assertEquals(assignedToId, task.getAssignedToId());
        assertEquals(dueDate, task.getDueDate());
        assertEquals(TaskStatus.NOT_STARTED, task.getStatus());
    }

    @Test
    void createTask_WithRequiredFieldsOnly_ShouldCreateTask() {
        // Act
        Task task = TaskFactory.createTask(title, project);

        // Assert
        assertNotNull(task);
        assertEquals(title, task.getTitle());
        assertEquals(project, task.getProject());
        assertNull(task.getDescription());
        assertNull(task.getAssignedToId());
        assertNull(task.getDueDate());
        assertEquals(TaskStatus.NOT_STARTED, task.getStatus());
    }

    @Test
    void createTask_WithEmptyTitle_ShouldThrowException() {
        // Arrange
        String emptyTitle = "";

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            TaskFactory.createTask(emptyTitle, project);
        });
        assertTrue(exception.getMessage().contains("Invalid title"));
    }

    @Test
    void createTask_WithNullTitle_ShouldThrowException() {
        // Arrange
        String nullTitle = null;

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            TaskFactory.createTask(nullTitle, project);
        });
        assertTrue(exception.getMessage().contains("Invalid title"));
    }

    @Test
    void createTask_WithNullProject_ShouldThrowException() {
        // Arrange
        Project nullProject = null;

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            TaskFactory.createTask(title, nullProject);
        });
        assertTrue(exception.getMessage().contains("Invalid project"));
    }

    @Test
    void createTask_WithInvalidAssignedToId_ShouldThrowException() {
        // Arrange
        Integer invalidAssignedToId = -1;

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            TaskFactory.createTask(title, description, project, invalidAssignedToId, dueDate);
        });
        assertTrue(exception.getMessage().contains("Invalid assignedToId"));
    }

    @Test
    void createTask_WithPastDueDate_ShouldThrowException() {
        // Arrange
        LocalDate pastDate = LocalDate.now().minusDays(1);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            TaskFactory.createTask(title, description, project, assignedToId, pastDate);
        });
        assertTrue(exception.getMessage().contains("Invalid due date"));
    }

    @Test
    void validateTask_WithValidTask_ShouldReturnTrue() {
        // Arrange
        Task task = TaskFactory.createTask(title, project);

        // Act
        boolean isValid = TaskFactory.validateTask(task);

        // Assert
        assertTrue(isValid);
    }

    @Test
    void validateTask_WithNullTask_ShouldReturnFalse() {
        // Arrange
        Task task = null;

        // Act
        boolean isValid = TaskFactory.validateTask(task);

        // Assert
        assertFalse(isValid);
    }
}
