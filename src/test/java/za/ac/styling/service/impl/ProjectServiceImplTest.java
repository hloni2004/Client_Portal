package za.ac.styling.service.impl;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import za.ac.styling.domain.Project;
import za.ac.styling.domain.ProjectStatus;
import za.ac.styling.factory.ProjectFactory;
import za.ac.styling.service.IProjectService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProjectServiceImplTest {

    @Autowired
    private IProjectService projectService;

    private static Project testProject1;
    private static Project testProject2;

    @BeforeAll
    static void setUp() {
        testProject1 = ProjectFactory.createProject(1, "Website Development", "Build a modern website",
                LocalDate.now(), LocalDate.now().plusDays(30));
        testProject2 = ProjectFactory.createProject(1, "Mobile App", "Create mobile application",
                LocalDate.now(), LocalDate.now().plusDays(60));
    }

    @Test
    @Order(1)
    void save_WithValidProject() {
        Project saved = projectService.save(testProject1);
        assertNotNull(saved);
        assertNotNull(saved.getProjectId());
        assertEquals("Website Development", saved.getTitle());
        assertEquals(ProjectStatus.NOT_STARTED, saved.getStatus());
        System.out.println("Saved Project 1: " + saved);

        testProject1 = saved;
    }

    @Test
    @Order(2)
    void save_WithSecondProject() {
        Project saved = projectService.save(testProject2);
        assertNotNull(saved);
        assertNotNull(saved.getProjectId());
        assertEquals("Mobile App", saved.getTitle());
        System.out.println("Saved Project 2: " + saved);

        testProject2 = saved;
    }

    @Test
    @Order(3)
    void findById_WhenProjectExists() {
        Optional<Project> found = projectService.findById(testProject1.getProjectId());
        assertTrue(found.isPresent());
        assertEquals(testProject1.getProjectId(), found.get().getProjectId());
        assertEquals("Website Development", found.get().getTitle());
    }

    @Test
    @Order(4)
    void findByClientId() {
        List<Project> projects = projectService.findByClientId(1);
        assertNotNull(projects);
        assertFalse(projects.isEmpty());
        assertTrue(projects.size() >= 2);
        System.out.println("Projects for client 1: " + projects.size());
    }

    @Test
    @Order(5)
    void findByStatus() {
        List<Project> projects = projectService.findByStatus(ProjectStatus.NOT_STARTED);
        assertNotNull(projects);
        assertFalse(projects.isEmpty());
        System.out.println("NOT_STARTED projects: " + projects.size());
    }

    @Test
    @Order(6)
    void searchByTitle() {
        List<Project> projects = projectService.searchByTitle("Website");
        assertNotNull(projects);
        assertFalse(projects.isEmpty());
        assertTrue(projects.get(0).getTitle().contains("Website"));
    }

    @Test
    @Order(7)
    void findProjectsDueBetween() {
        List<Project> projects = projectService.findProjectsDueBetween(
                LocalDate.now(), LocalDate.now().plusDays(90));
        assertNotNull(projects);
        assertFalse(projects.isEmpty());
        System.out.println("Projects due in next 90 days: " + projects.size());
    }

    @Test
    @Order(8)
    void findByClientIdAndStatus() {
        List<Project> projects = projectService.findByClientIdAndStatus(1, ProjectStatus.NOT_STARTED);
        assertNotNull(projects);
        assertFalse(projects.isEmpty());
    }

    @Test
    @Order(9)
    void updateProjectStatus() {
        projectService.updateProjectStatus(testProject1.getProjectId(), ProjectStatus.IN_PROGRESS);

        Optional<Project> updated = projectService.findById(testProject1.getProjectId());
        assertTrue(updated.isPresent());
        assertEquals(ProjectStatus.IN_PROGRESS, updated.get().getStatus());
    }

    @Test
    @Order(10)
    void updateProjectProgress() {
        projectService.updateProjectProgress(testProject1.getProjectId(), 50.0);

        Optional<Project> updated = projectService.findById(testProject1.getProjectId());
        assertTrue(updated.isPresent());
        assertEquals(50.0, updated.get().getProgress());
    }

    @Test
    @Order(11)
    void createProject() {
        Project created = projectService.createProject(2, "E-commerce Site", "Online store",
                LocalDate.now(), LocalDate.now().plusDays(45));
        assertNotNull(created);
        assertNotNull(created.getProjectId());
        assertEquals("E-commerce Site", created.getTitle());
        assertEquals(2, created.getClientId());
    }

    @Test
    @Order(12)
    void update_WithValidProject() {
        testProject2.setDescription("Updated mobile app description");
        Project updated = projectService.update(testProject2);
        assertNotNull(updated);
        assertEquals("Updated mobile app description", updated.getDescription());
    }

    @Test
    @Order(13)
    void findAll() {
        List<Project> all = projectService.findAll();
        assertNotNull(all);
        assertTrue(all.size() >= 3);
        System.out.println("Total projects: " + all.size());
    }

    @Test
    @Order(14)
    void count() {
        long count = projectService.count();
        assertTrue(count >= 3);
        System.out.println("Project count: " + count);
    }

    @Test
    @Order(15)
    void existsById() {
        boolean exists = projectService.existsById(testProject1.getProjectId());
        assertTrue(exists);
    }

    @Test
    @Order(16)
    void deleteById() {
        projectService.deleteById(testProject2.getProjectId());

        Optional<Project> deleted = projectService.findById(testProject2.getProjectId());
        assertFalse(deleted.isPresent());
    }
}
