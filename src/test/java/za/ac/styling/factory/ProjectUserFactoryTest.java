package za.ac.styling.factory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.ac.styling.domain.*;

import static org.junit.jupiter.api.Assertions.*;

class ProjectUserFactoryTest {

    private Project project;
    private User user;
    private ProjectAccessRole role;

    @BeforeEach
    void setUp() {
        project = ProjectFactory.createProject(1, "Test Project");
        user = UserFactory.createUser("John Doe", "john@example.com", "password123", UserRole.CLIENT);
        role = ProjectAccessRole.OWNER;
    }

    @Test
    void createProjectUser_WithValidData_ShouldCreateProjectUser() {
        // Act
        ProjectUser projectUser = ProjectUserFactory.createProjectUser(project, user, role);

        // Assert
        assertNotNull(projectUser);
        assertEquals(project, projectUser.getProject());
        assertEquals(user, projectUser.getUser());
        assertEquals(role, projectUser.getRole());
    }

    @Test
    void createProjectUser_WithNullProject_ShouldThrowException() {
        // Arrange
        Project nullProject = null;

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            ProjectUserFactory.createProjectUser(nullProject, user, role);
        });
        assertTrue(exception.getMessage().contains("Invalid project"));
    }

    @Test
    void createProjectUser_WithNullUser_ShouldThrowException() {
        // Arrange
        User nullUser = null;

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            ProjectUserFactory.createProjectUser(project, nullUser, role);
        });
        assertTrue(exception.getMessage().contains("Invalid user"));
    }

    @Test
    void createProjectUser_WithNullRole_ShouldThrowException() {
        // Arrange
        ProjectAccessRole nullRole = null;

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            ProjectUserFactory.createProjectUser(project, user, nullRole);
        });
        assertTrue(exception.getMessage().contains("Invalid role"));
    }

    @Test
    void createProjectUser_WithDifferentRoles_ShouldCreateCorrectly() {
        // Act
        ProjectUser owner = ProjectUserFactory.createProjectUser(project, user, ProjectAccessRole.OWNER);
        ProjectUser viewer = ProjectUserFactory.createProjectUser(project, user, ProjectAccessRole.VIEWER);

        // Assert
        assertEquals(ProjectAccessRole.OWNER, owner.getRole());
        assertEquals(ProjectAccessRole.VIEWER, viewer.getRole());
    }
}
