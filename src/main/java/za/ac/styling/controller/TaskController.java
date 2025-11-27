package za.ac.styling.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.styling.domain.Task;
import za.ac.styling.domain.TaskStatus;
import za.ac.styling.service.ITaskService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final ITaskService taskService;

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        Task saved = taskService.save(task);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PostMapping("/create")
    public ResponseEntity<Task> createTask(@RequestParam String title,
                                           @RequestParam String description,
                                           @RequestParam Integer projectId,
                                           @RequestParam Integer assignedToId,
                                           @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dueDate) {
        Task task = taskService.createTask(title, description, projectId, assignedToId, dueDate);
        return new ResponseEntity<>(task, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Integer id) {
        return taskService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.findAll();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Task>> getTasksByProject(@PathVariable Integer projectId) {
        List<Task> tasks = taskService.findByProjectId(projectId);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Task>> getTasksByUser(@PathVariable Integer userId) {
        List<Task> tasks = taskService.findByAssignedUserId(userId);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Task>> getTasksByStatus(@PathVariable TaskStatus status) {
        List<Task> tasks = taskService.findByStatus(status);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/project/{projectId}/status/{status}")
    public ResponseEntity<List<Task>> getTasksByProjectAndStatus(@PathVariable Integer projectId,
                                                                  @PathVariable TaskStatus status) {
        List<Task> tasks = taskService.findByProjectIdAndStatus(projectId, status);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/user/{userId}/status/{status}")
    public ResponseEntity<List<Task>> getTasksByUserAndStatus(@PathVariable Integer userId,
                                                               @PathVariable TaskStatus status) {
        List<Task> tasks = taskService.findByAssignedUserIdAndStatus(userId, status);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Task>> searchByTitle(@RequestParam String title) {
        List<Task> tasks = taskService.searchByTitle(title);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/overdue")
    public ResponseEntity<List<Task>> getOverdueTasks() {
        List<Task> tasks = taskService.findOverdueTasks();
        return ResponseEntity.ok(tasks);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Integer id, @RequestBody Task task) {
        if (!taskService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        task.setTaskId(id);
        Task updated = taskService.update(task);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Void> updateTaskStatus(@PathVariable Integer id,
                                                  @RequestParam TaskStatus status) {
        taskService.updateTaskStatus(id, status);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/assign")
    public ResponseEntity<Void> assignTask(@PathVariable Integer id,
                                           @RequestParam Integer userId) {
        taskService.assignTask(id, userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Integer id) {
        if (!taskService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        taskService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countTasks() {
        long count = taskService.count();
        return ResponseEntity.ok(count);
    }
}
