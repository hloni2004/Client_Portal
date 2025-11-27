package za.ac.styling.service.impl;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import za.ac.styling.domain.Project;
import za.ac.styling.domain.Task;
import za.ac.styling.domain.TaskStatus;
import za.ac.styling.factory.ProjectFactory;
import za.ac.styling.factory.TaskFactory;
import za.ac.styling.service.IProjectService;
import za.ac.styling.service.ITaskService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TaskServiceImplTest {

    @Autowired
    private ITaskService taskService;

    @Autowired
    private IProjectService projectService;

    private static Project testProject;
    private static Task testTask1;
    private static Task testTask2;

    @BeforeAll
    static void setUp() {
        testProject = ProjectFactory.createProject(1, "Test Project");
    }

    @Test
    @Order(1)
    void setupProject() {
        Project saved = projectService.save(testProject);
        assertNotNull(saved);
        assertNotNull(saved.getProjectId());
        System.out.println("Saved Project: " + saved);

        testProject = saved;

        testTask1 = TaskFactory.createTask("Setup Environment", "Configure development environment",
                testProject, 1, LocalDate.now().plusDays(5));
        testTask2 = TaskFactory.createTask("Write Tests", testProject);
    }

    @Test
    @Order(2)
    void save_WithValidTask() {
        Task saved = taskService.save(testTask1);
        assertNotNull(saved);
        assertNotNull(saved.getTaskId());
        assertEquals("Setup Environment", saved.getTitle());
        assertEquals(TaskStatus.NOT_STARTED, saved.getStatus());
        System.out.println("Saved Task 1: " + saved);

        testTask1 = saved;
    }

    @Test
    @Order(3)
    void save_WithSecondTask() {
        Task saved = taskService.save(testTask2);
        assertNotNull(saved);
        assertNotNull(saved.getTaskId());
        assertEquals("Write Tests", saved.getTitle());
        System.out.println("Saved Task 2: " + saved);

        testTask2 = saved;
    }

    @Test
    @Order(4)
    void findById() {
        Optional<Task> found = taskService.findById(testTask1.getTaskId());
        assertTrue(found.isPresent());
        assertEquals(testTask1.getTaskId(), found.get().getTaskId());
        assertEquals("Setup Environment", found.get().getTitle());
    }

    @Test
    @Order(5)
    void findByProjectId() {
        List<Task> tasks = taskService.findByProjectId(testProject.getProjectId());
        assertNotNull(tasks);
        assertTrue(tasks.size() >= 2);
        System.out.println("Tasks for project: " + tasks.size());
    }

    @Test
    @Order(6)
    void findByAssignedUserId() {
        List<Task> tasks = taskService.findByAssignedUserId(1);
        assertNotNull(tasks);
        assertFalse(tasks.isEmpty());
        System.out.println("Tasks for user 1: " + tasks.size());
    }

    @Test
    @Order(7)
    void findByStatus() {
        List<Task> tasks = taskService.findByStatus(TaskStatus.NOT_STARTED);
        assertNotNull(tasks);
        assertFalse(tasks.isEmpty());
        System.out.println("NOT_STARTED tasks: " + tasks.size());
    }

    @Test
    @Order(8)
    void searchByTitle() {
        List<Task> tasks = taskService.searchByTitle("Setup");
        assertNotNull(tasks);
        assertFalse(tasks.isEmpty());
        assertTrue(tasks.get(0).getTitle().contains("Setup"));
    }

    @Test
    @Order(9)
    void findByProjectIdAndStatus() {
        List<Task> tasks = taskService.findByProjectIdAndStatus(testProject.getProjectId(), TaskStatus.NOT_STARTED);
        assertNotNull(tasks);
        assertFalse(tasks.isEmpty());
    }

    @Test
    @Order(10)
    void findByAssignedUserIdAndStatus() {
        List<Task> tasks = taskService.findByAssignedUserIdAndStatus(1, TaskStatus.NOT_STARTED);
        assertNotNull(tasks);
        assertFalse(tasks.isEmpty());
    }

    @Test
    @Order(11)
    void updateTaskStatus() {
        taskService.updateTaskStatus(testTask1.getTaskId(), TaskStatus.IN_PROGRESS);

        Optional<Task> updated = taskService.findById(testTask1.getTaskId());
        assertTrue(updated.isPresent());
        assertEquals(TaskStatus.IN_PROGRESS, updated.get().getStatus());
    }

    @Test
    @Order(12)
    void assignTask() {
        taskService.assignTask(testTask2.getTaskId(), 2);

        Optional<Task> updated = taskService.findById(testTask2.getTaskId());
        assertTrue(updated.isPresent());
        assertEquals(2, updated.get().getAssignedToId());
    }

    @Test
    @Order(13)
    void createTask() {
        Task created = taskService.createTask("New Task", "Task description",
                testProject.getProjectId(), 3, LocalDate.now().plusDays(7));
        assertNotNull(created);
        assertNotNull(created.getTaskId());
        assertEquals("New Task", created.getTitle());
        assertEquals(3, created.getAssignedToId());
    }

    @Test
    @Order(14)
    void update() {
        testTask1.setDescription("Updated description");
        Task updated = taskService.update(testTask1);
        assertNotNull(updated);
        assertEquals("Updated description", updated.getDescription());
    }

    @Test
    @Order(15)
    void findAll() {
        List<Task> all = taskService.findAll();
        assertNotNull(all);
        assertTrue(all.size() >= 3);
        System.out.println("Total tasks: " + all.size());
    }

    @Test
    @Order(16)
    void count() {
        long count = taskService.count();
        assertTrue(count >= 3);
        System.out.println("Task count: " + count);
    }

    @Test
    @Order(17)
    void existsById() {
        boolean exists = taskService.existsById(testTask1.getTaskId());
        assertTrue(exists);
    }

    @Test
    @Order(18)
    void deleteById() {
        taskService.deleteById(testTask2.getTaskId());

        Optional<Task> deleted = taskService.findById(testTask2.getTaskId());
        assertFalse(deleted.isPresent());
    }
}
