package za.ac.styling.service.impl;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import za.ac.styling.domain.*;
import za.ac.styling.factory.DeliverableFactory;
import za.ac.styling.factory.ProjectFactory;
import za.ac.styling.factory.UserFactory;
import za.ac.styling.service.IDeliverableService;
import za.ac.styling.service.IFeedbackService;
import za.ac.styling.service.IProjectService;
import za.ac.styling.service.IUserService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FeedbackServiceImplTest {

    @Autowired
    private IFeedbackService feedbackService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IProjectService projectService;

    @Autowired
    private IDeliverableService deliverableService;

    private static User testUser;
    private static Project testProject;
    private static Deliverable testDeliverable;
    private static Feedback testFeedback1;
    private static Feedback testFeedback2;

    @BeforeAll
    static void setUp() {
        testUser = UserFactory.createAdmin("Test User", "testuser@example.com", "password123");
        testProject = ProjectFactory.createProject(1, "Test Project");
    }

    @Test
    @Order(1)
    void setupData() {
        User savedUser = userService.save(testUser);
        assertNotNull(savedUser);
        testUser = savedUser;

        Project savedProject = projectService.save(testProject);
        assertNotNull(savedProject);
        testProject = savedProject;

        testDeliverable = DeliverableFactory.createDeliverable("file.pdf", "application/pdf",
                "http://example.com/file.pdf", testProject);
        Deliverable savedDeliverable = deliverableService.save(testDeliverable);
        assertNotNull(savedDeliverable);
        testDeliverable = savedDeliverable;

        System.out.println("Setup completed");
    }

    @Test
    @Order(2)
    void addFeedback() {
        Feedback created = feedbackService.addFeedback("Great work on this deliverable!",
                testDeliverable.getDeliverableId(), testUser.getUserId());
        assertNotNull(created);
        assertNotNull(created.getFeedbackId());
        assertEquals("Great work on this deliverable!", created.getMessage());
        System.out.println("Created Feedback 1: " + created);

        testFeedback1 = created;
    }

    @Test
    @Order(3)
    void addSecondFeedback() {
        Feedback created = feedbackService.addFeedback("Needs some improvements",
                testDeliverable.getDeliverableId(), testUser.getUserId());
        assertNotNull(created);
        assertNotNull(created.getFeedbackId());
        System.out.println("Created Feedback 2: " + created);

        testFeedback2 = created;
    }

    @Test
    @Order(4)
    void findById() {
        Optional<Feedback> found = feedbackService.findById(testFeedback1.getFeedbackId());
        assertTrue(found.isPresent());
        assertEquals(testFeedback1.getFeedbackId(), found.get().getFeedbackId());
        assertEquals("Great work on this deliverable!", found.get().getMessage());
    }

    @Test
    @Order(5)
    void findByDeliverableId() {
        List<Feedback> feedbacks = feedbackService.findByDeliverableId(testDeliverable.getDeliverableId());
        assertNotNull(feedbacks);
        assertTrue(feedbacks.size() >= 2);
        System.out.println("Feedbacks for deliverable: " + feedbacks.size());
    }

    @Test
    @Order(6)
    void findByUserId() {
        List<Feedback> feedbacks = feedbackService.findByUserId(testUser.getUserId());
        assertNotNull(feedbacks);
        assertTrue(feedbacks.size() >= 2);
        System.out.println("Feedbacks by user: " + feedbacks.size());
    }

    @Test
    @Order(7)
    void findByProjectId() {
        List<Feedback> feedbacks = feedbackService.findByProjectId(testProject.getProjectId());
        assertNotNull(feedbacks);
        assertTrue(feedbacks.size() >= 2);
        System.out.println("Feedbacks for project: " + feedbacks.size());
    }

    @Test
    @Order(8)
    void update() {
        testFeedback1.setMessage("Updated feedback message");
        Feedback updated = feedbackService.update(testFeedback1);
        assertNotNull(updated);
        assertEquals("Updated feedback message", updated.getMessage());
    }

    @Test
    @Order(9)
    void findAll() {
        List<Feedback> all = feedbackService.findAll();
        assertNotNull(all);
        assertTrue(all.size() >= 2);
        System.out.println("Total feedbacks: " + all.size());
    }

    @Test
    @Order(10)
    void count() {
        long count = feedbackService.count();
        assertTrue(count >= 2);
        System.out.println("Feedback count: " + count);
    }

    @Test
    @Order(11)
    void existsById() {
        boolean exists = feedbackService.existsById(testFeedback1.getFeedbackId());
        assertTrue(exists);
    }

    @Test
    @Order(12)
    void deleteById() {
        feedbackService.deleteById(testFeedback2.getFeedbackId());

        Optional<Feedback> deleted = feedbackService.findById(testFeedback2.getFeedbackId());
        assertFalse(deleted.isPresent());
    }
}
