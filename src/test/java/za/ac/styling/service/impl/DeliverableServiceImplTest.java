package za.ac.styling.service.impl;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import za.ac.styling.domain.Deliverable;
import za.ac.styling.domain.Project;
import za.ac.styling.factory.DeliverableFactory;
import za.ac.styling.factory.ProjectFactory;
import za.ac.styling.service.IDeliverableService;
import za.ac.styling.service.IProjectService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DeliverableServiceImplTest {

    @Autowired
    private IDeliverableService deliverableService;

    @Autowired
    private IProjectService projectService;

    private static Project testProject;
    private static Deliverable testDeliverable1;
    private static Deliverable testDeliverable2;

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

        testDeliverable1 = DeliverableFactory.createDeliverable("report.pdf", "application/pdf",
                "http://example.com/report.pdf", testProject);
        testDeliverable2 = DeliverableFactory.createDeliverable("design.png", "image/png",
                "http://example.com/design.png", testProject);
    }

    @Test
    @Order(2)
    void save_WithValidDeliverable() {
        Deliverable saved = deliverableService.save(testDeliverable1);
        assertNotNull(saved);
        assertNotNull(saved.getDeliverableId());
        assertEquals("report.pdf", saved.getFileName());
        assertFalse(saved.getApproved());
        System.out.println("Saved Deliverable 1: " + saved);

        testDeliverable1 = saved;
    }

    @Test
    @Order(3)
    void save_WithSecondDeliverable() {
        Deliverable saved = deliverableService.save(testDeliverable2);
        assertNotNull(saved);
        assertNotNull(saved.getDeliverableId());
        assertEquals("design.png", saved.getFileName());
        System.out.println("Saved Deliverable 2: " + saved);

        testDeliverable2 = saved;
    }

    @Test
    @Order(4)
    void findById() {
        Optional<Deliverable> found = deliverableService.findById(testDeliverable1.getDeliverableId());
        assertTrue(found.isPresent());
        assertEquals(testDeliverable1.getDeliverableId(), found.get().getDeliverableId());
        assertEquals("report.pdf", found.get().getFileName());
    }

    @Test
    @Order(5)
    void findByProjectId() {
        List<Deliverable> deliverables = deliverableService.findByProjectId(testProject.getProjectId());
        assertNotNull(deliverables);
        assertTrue(deliverables.size() >= 2);
        System.out.println("Deliverables for project: " + deliverables.size());
    }

    @Test
    @Order(6)
    void findByApprovalStatus() {
        List<Deliverable> deliverables = deliverableService.findByApprovalStatus(false);
        assertNotNull(deliverables);
        assertFalse(deliverables.isEmpty());
        System.out.println("Unapproved deliverables: " + deliverables.size());
    }

    @Test
    @Order(7)
    void findByProjectIdAndApprovalStatus() {
        List<Deliverable> deliverables = deliverableService.findByProjectIdAndApprovalStatus(
                testProject.getProjectId(), false);
        assertNotNull(deliverables);
        assertTrue(deliverables.size() >= 2);
    }

    @Test
    @Order(8)
    void findByFileType() {
        List<Deliverable> deliverables = deliverableService.findByFileType("application/pdf");
        assertNotNull(deliverables);
        assertFalse(deliverables.isEmpty());
    }

    @Test
    @Order(9)
    void searchByFileName() {
        List<Deliverable> deliverables = deliverableService.searchByFileName("report");
        assertNotNull(deliverables);
        assertFalse(deliverables.isEmpty());
        assertTrue(deliverables.get(0).getFileName().contains("report"));
    }

    @Test
    @Order(10)
    void approveDeliverable() {
        deliverableService.approveDeliverable(testDeliverable1.getDeliverableId());

        Optional<Deliverable> approved = deliverableService.findById(testDeliverable1.getDeliverableId());
        assertTrue(approved.isPresent());
        assertTrue(approved.get().getApproved());
    }

    @Test
    @Order(11)
    void uploadDeliverable() {
        Deliverable uploaded = deliverableService.uploadDeliverable("document.docx", "application/msword",
                "http://example.com/document.docx", testProject.getProjectId());
        assertNotNull(uploaded);
        assertNotNull(uploaded.getDeliverableId());
        assertEquals("document.docx", uploaded.getFileName());
    }

    @Test
    @Order(12)
    void update() {
        testDeliverable2.setFileName("design_v2.png");
        Deliverable updated = deliverableService.update(testDeliverable2);
        assertNotNull(updated);
        assertEquals("design_v2.png", updated.getFileName());
    }

    @Test
    @Order(13)
    void findAll() {
        List<Deliverable> all = deliverableService.findAll();
        assertNotNull(all);
        assertTrue(all.size() >= 3);
        System.out.println("Total deliverables: " + all.size());
    }

    @Test
    @Order(14)
    void count() {
        long count = deliverableService.count();
        assertTrue(count >= 3);
        System.out.println("Deliverable count: " + count);
    }

    @Test
    @Order(15)
    void existsById() {
        boolean exists = deliverableService.existsById(testDeliverable1.getDeliverableId());
        assertTrue(exists);
    }

    @Test
    @Order(16)
    void deleteById() {
        deliverableService.deleteById(testDeliverable2.getDeliverableId());

        Optional<Deliverable> deleted = deliverableService.findById(testDeliverable2.getDeliverableId());
        assertFalse(deleted.isPresent());
    }
}
