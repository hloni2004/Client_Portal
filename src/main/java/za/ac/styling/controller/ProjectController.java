package za.ac.styling.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.styling.domain.Project;
import za.ac.styling.domain.ProjectStatus;
import za.ac.styling.service.IProjectService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final IProjectService projectService;

    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody Project project) {
        Project saved = projectService.save(project);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PostMapping("/create")
    public ResponseEntity<Project> createProject(@RequestParam Integer clientId,
                                                  @RequestParam String title,
                                                  @RequestParam String description,
                                                  @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                  @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dueDate) {
        Project project = projectService.createProject(clientId, title, description, startDate, dueDate);
        return new ResponseEntity<>(project, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Integer id) {
        return projectService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects() {
        List<Project> projects = projectService.findAll();
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<Project>> getProjectsByClient(@PathVariable Integer clientId) {
        List<Project> projects = projectService.findByClientId(clientId);
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Project>> getProjectsByStatus(@PathVariable ProjectStatus status) {
        List<Project> projects = projectService.findByStatus(status);
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/client/{clientId}/status/{status}")
    public ResponseEntity<List<Project>> getProjectsByClientAndStatus(@PathVariable Integer clientId,
                                                                       @PathVariable ProjectStatus status) {
        List<Project> projects = projectService.findByClientIdAndStatus(clientId, status);
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Project>> searchByTitle(@RequestParam String title) {
        List<Project> projects = projectService.searchByTitle(title);
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/overdue")
    public ResponseEntity<List<Project>> getOverdueProjects() {
        List<Project> projects = projectService.findOverdueProjects();
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/due-between")
    public ResponseEntity<List<Project>> getProjectsDueBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<Project> projects = projectService.findProjectsDueBetween(startDate, endDate);
        return ResponseEntity.ok(projects);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable Integer id, @RequestBody Project project) {
        if (!projectService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        project.setProjectId(id);
        Project updated = projectService.update(project);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Void> updateProjectStatus(@PathVariable Integer id,
                                                     @RequestParam ProjectStatus status) {
        projectService.updateProjectStatus(id, status);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/progress")
    public ResponseEntity<Void> updateProjectProgress(@PathVariable Integer id,
                                                       @RequestParam Double progress) {
        projectService.updateProjectProgress(id, progress);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Integer id) {
        if (!projectService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        projectService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countProjects() {
        long count = projectService.count();
        return ResponseEntity.ok(count);
    }
}
