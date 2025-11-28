package za.ac.styling.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.styling.domain.Project;
import za.ac.styling.domain.ProjectStatus;
import za.ac.styling.dto.*;
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
    public ResponseEntity<Project> createProject(@Valid @RequestBody ProjectCreateDto dto) {
        Project project = projectService.createProject(dto.getClientId(), dto.getTitle(), 
                dto.getDescription(), dto.getStartDate(), dto.getDueDate());
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

    @PostMapping("/search")
    public ResponseEntity<List<Project>> searchByTitle(@Valid @RequestBody SearchDto dto) {
        List<Project> projects = projectService.searchByTitle(dto.getQuery());
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/overdue")
    public ResponseEntity<List<Project>> getOverdueProjects() {
        List<Project> projects = projectService.findOverdueProjects();
        return ResponseEntity.ok(projects);
    }

    @PostMapping("/due-between")
    public ResponseEntity<List<Project>> getProjectsDueBetween(@Valid @RequestBody DateRangeDto dto) {
        List<Project> projects = projectService.findProjectsDueBetween(dto.getStartDate(), dto.getEndDate());
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
                                                     @Valid @RequestBody ProjectStatusUpdateDto dto) {
        projectService.updateProjectStatus(id, dto.getStatus());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/progress")
    public ResponseEntity<Void> updateProjectProgress(@PathVariable Integer id,
                                                       @Valid @RequestBody ProjectProgressUpdateDto dto) {
        projectService.updateProjectProgress(id, dto.getProgress());
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
