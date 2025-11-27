package za.ac.styling.service.impl;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import za.ac.styling.domain.*;
import za.ac.styling.factory.ProjectFactory;
import za.ac.styling.factory.UserFactory;
import za.ac.styling.service.IProjectService;
import za.ac.styling.service.IProjectUserService;
import za.ac.styling.service.IUserService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProjectUserServiceImplTest {

    @Autowired
    private IProjectUserService projectUserService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IProjectService projectService;

    private static User testUser1;
    private static User testUser2;
    private static Project testProject;
    private static ProjectUser testProjectUser1;
    private static ProjectUser testProjectUser2;

    @BeforeAll
    static void setUp() {
        testUser1 = UserFactory.createUser("User One", "user1@example.com", "password123", UserRole.CLIENT);
        testUser2 = UserFactory.createUser("User Two", "user2@example.com", "password123", UserRole.CLIENT);
        testProject = ProjectFactory.createProject(1, "Collaborative Project");
    }

    @Test
    @Order(1)
    void setupData() {
        User savedUser1 = userService.save(testUser1);
        assertNotNull(savedUser1);
        testUser1 = savedUser1;

        User savedUser2 = userService.save(testUser2);
        assertNotNull(savedUser2);
        testUser2 = savedUser2;

        Project savedProject = projectService.save(testProject);
        assertNotNull(savedProject);
        testProject = savedProject;

        System.out.println("Setup completed");
    }

    @Test
    @Order(2)
    void addUserToProject_AsOwner() {
        ProjectUser created = projectUserService.addUserToProject(testProject.getProjectId(),
                testUser1.getUserId(), ProjectAccessRole.OWNER);
        assertNotNull(created);
        assertNotNull(created.getId());
        assertEquals(ProjectAccessRole.OWNER, created.getRole());
        System.out.println("Added User 1 as OWNER: " + created);

        testProjectUser1 = created;
    }

    @Test
    @Order(3)
    void addUserToProject_AsViewer() {
        ProjectUser created = projectUserService.addUserToProject(testProject.getProjectId(),
                testUser2.getUserId(), ProjectAccessRole.VIEWER);
        assertNotNull(created);
        assertNotNull(created.getId());
        assertEquals(ProjectAccessRole.VIEWER, created.getRole());
        System.out.println("Added User 2 as VIEWER: " + created);

        testProjectUser2 = created;
    }

    @Test
    @Order(4)
    void findById() {
        Optional<ProjectUser> found = projectUserService.findById(testProjectUser1.getId());
        assertTrue(found.isPresent());
        assertEquals(testProjectUser1.getId(), found.get().getId());
        assertEquals(ProjectAccessRole.OWNER, found.get().getRole());
    }

    @Test
    @Order(5)
    void findByProjectId() {
        List<ProjectUser> projectUsers = projectUserService.findByProjectId(testProject.getProjectId());
        assertNotNull(projectUsers);
        assertTrue(projectUsers.size() >= 2);
        System.out.println("Users in project: " + projectUsers.size());
    }

    @Test
    @Order(6)
    void findByUserId() {
        List<ProjectUser> projectUsers = projectUserService.findByUserId(testUser1.getUserId());
        assertNotNull(projectUsers);
        assertFalse(projectUsers.isEmpty());
        System.out.println("Projects for user 1: " + projectUsers.size());
    }

    @Test
    @Order(7)
    void findByProjectIdAndUserId() {
        Optional<ProjectUser> found = projectUserService.findByProjectIdAndUserId(
                testProject.getProjectId(), testUser1.getUserId());
        assertTrue(found.isPresent());
        assertEquals(ProjectAccessRole.OWNER, found.get().getRole());
    }

    @Test
    @Order(8)
    void findByRole() {
        List<ProjectUser> owners = projectUserService.findByRole(ProjectAccessRole.OWNER);
        assertNotNull(owners);
        assertFalse(owners.isEmpty());
        System.out.println("Total OWNERs: " + owners.size());
    }

    @Test
    @Order(9)
    void findByProjectIdAndRole() {
        List<ProjectUser> viewers = projectUserService.findByProjectIdAndRole(
                testProject.getProjectId(), ProjectAccessRole.VIEWER);
        assertNotNull(viewers);
        assertFalse(viewers.isEmpty());
    }

    @Test
    @Order(10)
    void existsByProjectIdAndUserId() {
        boolean exists = projectUserService.existsByProjectIdAndUserId(
                testProject.getProjectId(), testUser1.getUserId());
        assertTrue(exists);
    }

    @Test
    @Order(11)
    void changeUserRole() {
        projectUserService.changeUserRole(testProject.getProjectId(), testUser2.getUserId(),
                ProjectAccessRole.EDITOR);

        Optional<ProjectUser> updated = projectUserService.findByProjectIdAndUserId(
                testProject.getProjectId(), testUser2.getUserId());
        assertTrue(updated.isPresent());
        assertEquals(ProjectAccessRole.EDITOR, updated.get().getRole());
    }

    @Test
    @Order(12)
    void update() {
        testProjectUser1.setRole(ProjectAccessRole.APPROVER);
        ProjectUser updated = projectUserService.update(testProjectUser1);
        assertNotNull(updated);
        assertEquals(ProjectAccessRole.APPROVER, updated.getRole());
    }

    @Test
    @Order(13)
    void findAll() {
        List<ProjectUser> all = projectUserService.findAll();
        assertNotNull(all);
        assertTrue(all.size() >= 2);
        System.out.println("Total project-user relationships: " + all.size());
    }

    @Test
    @Order(14)
    void count() {
        long count = projectUserService.count();
        assertTrue(count >= 2);
        System.out.println("ProjectUser count: " + count);
    }

    @Test
    @Order(15)
    void existsById() {
        boolean exists = projectUserService.existsById(testProjectUser1.getId());
        assertTrue(exists);
    }

    @Test
    @Order(16)
    void removeUserFromProject() {
        projectUserService.removeUserFromProject(testProject.getProjectId(), testUser2.getUserId());

        Optional<ProjectUser> deleted = projectUserService.findByProjectIdAndUserId(
                testProject.getProjectId(), testUser2.getUserId());
        assertFalse(deleted.isPresent());
    }

    @Test
    @Order(17)
    void addUserToProject_WithExistingUser_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class,
                () -> projectUserService.addUserToProject(testProject.getProjectId(),
                        testUser1.getUserId(), ProjectAccessRole.VIEWER));
    }
}
