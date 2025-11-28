package za.ac.styling.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.styling.domain.ProjectAccessRole;
import za.ac.styling.domain.ProjectUser;
import za.ac.styling.dto.*;
import za.ac.styling.service.IProjectUserService;

import java.util.List;

@RestController
@RequestMapping("/api/project-users")
@RequiredArgsConstructor
public class ProjectUserController {

    private final IProjectUserService projectUserService;

    @PostMapping
    public ResponseEntity<ProjectUser> createProjectUser(@RequestBody ProjectUser projectUser) {
        ProjectUser saved = projectUserService.save(projectUser);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PostMapping("/add")
    public ResponseEntity<ProjectUser> addUserToProject(@Valid @RequestBody ProjectUserAddDto dto) {
        ProjectUser projectUser = projectUserService.addUserToProject(dto.getProjectId(), 
                dto.getUserId(), dto.getRole());
        return new ResponseEntity<>(projectUser, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectUser> getProjectUserById(@PathVariable Integer id) {
        return projectUserService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ProjectUser>> getAllProjectUsers() {
        List<ProjectUser> projectUsers = projectUserService.findAll();
        return ResponseEntity.ok(projectUsers);
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<ProjectUser>> getUsersByProject(@PathVariable Integer projectId) {
        List<ProjectUser> projectUsers = projectUserService.findByProjectId(projectId);
        return ResponseEntity.ok(projectUsers);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ProjectUser>> getProjectsByUser(@PathVariable Integer userId) {
        List<ProjectUser> projectUsers = projectUserService.findByUserId(userId);
        return ResponseEntity.ok(projectUsers);
    }

    @GetMapping("/project/{projectId}/user/{userId}")
    public ResponseEntity<ProjectUser> getProjectUser(@PathVariable Integer projectId,
                                                       @PathVariable Integer userId) {
        return projectUserService.findByProjectIdAndUserId(projectId, userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<List<ProjectUser>> getProjectUsersByRole(@PathVariable ProjectAccessRole role) {
        List<ProjectUser> projectUsers = projectUserService.findByRole(role);
        return ResponseEntity.ok(projectUsers);
    }

    @GetMapping("/project/{projectId}/role/{role}")
    public ResponseEntity<List<ProjectUser>> getUsersByProjectAndRole(@PathVariable Integer projectId,
                                                                       @PathVariable ProjectAccessRole role) {
        List<ProjectUser> projectUsers = projectUserService.findByProjectIdAndRole(projectId, role);
        return ResponseEntity.ok(projectUsers);
    }

    @PostMapping("/exists")
    public ResponseEntity<Boolean> checkUserInProject(@Valid @RequestBody ProjectUserCheckDto dto) {
        boolean exists = projectUserService.existsByProjectIdAndUserId(dto.getProjectId(), dto.getUserId());
        return ResponseEntity.ok(exists);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectUser> updateProjectUser(@PathVariable Integer id, @RequestBody ProjectUser projectUser) {
        if (!projectUserService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        projectUser.setId(id);
        ProjectUser updated = projectUserService.update(projectUser);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/change-role")
    public ResponseEntity<Void> changeUserRole(@Valid @RequestBody ProjectUserRoleChangeDto dto) {
        projectUserService.changeUserRole(dto.getProjectId(), dto.getUserId(), dto.getNewRole());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProjectUser(@PathVariable Integer id) {
        if (!projectUserService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        projectUserService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Void> removeUserFromProject(@Valid @RequestBody ProjectUserCheckDto dto) {
        projectUserService.removeUserFromProject(dto.getProjectId(), dto.getUserId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countProjectUsers() {
        long count = projectUserService.count();
        return ResponseEntity.ok(count);
    }
}
