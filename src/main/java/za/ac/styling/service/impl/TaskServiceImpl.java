package za.ac.styling.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.ac.styling.domain.Project;
import za.ac.styling.domain.Task;
import za.ac.styling.domain.TaskStatus;
import za.ac.styling.factory.TaskFactory;
import za.ac.styling.repository.ProjectRepository;
import za.ac.styling.repository.TaskRepository;
import za.ac.styling.service.ITaskService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskServiceImpl implements ITaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    @Override
    public Task save(Task entity) {
        if (!TaskFactory.validateTask(entity)) {
            throw new IllegalArgumentException("Invalid task data");
        }
        return taskRepository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Task> findById(Integer id) {
        return taskRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public Task update(Task entity) {
        if (!TaskFactory.validateTask(entity)) {
            throw new IllegalArgumentException("Invalid task data");
        }
        if (!taskRepository.existsById(entity.getTaskId())) {
            throw new IllegalArgumentException("Task not found with id: " + entity.getTaskId());
        }
        return taskRepository.save(entity);
    }

    @Override
    public void deleteById(Integer id) {
        if (!taskRepository.existsById(id)) {
            throw new IllegalArgumentException("Task not found with id: " + id);
        }
        taskRepository.deleteById(id);
    }

    @Override
    public void delete(Task entity) {
        taskRepository.delete(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Integer id) {
        return taskRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return taskRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> findByProjectId(Integer projectId) {
        return taskRepository.findByProjectProjectId(projectId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> findByAssignedUserId(Integer userId) {
        return taskRepository.findByAssignedToId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> findByStatus(TaskStatus status) {
        return taskRepository.findByStatus(status);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> findOverdueTasks() {
        return taskRepository.findByDueDateBefore(LocalDate.now());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> findByProjectIdAndStatus(Integer projectId, TaskStatus status) {
        return taskRepository.findByProjectProjectIdAndStatus(projectId, status);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> findByAssignedUserIdAndStatus(Integer userId, TaskStatus status) {
        return taskRepository.findByAssignedToIdAndStatus(userId, status);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> searchByTitle(String title) {
        return taskRepository.findByTitleContainingIgnoreCase(title);
    }

    @Override
    public void updateTaskStatus(Integer taskId, TaskStatus status) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found with id: " + taskId));
        
        task.updateStatus(status);
        taskRepository.save(task);
    }

    @Override
    public void assignTask(Integer taskId, Integer userId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found with id: " + taskId));
        
        task.setAssignedToId(userId);
        taskRepository.save(task);
    }

    @Override
    public Task createTask(String title, String description, Integer projectId, Integer assignedToId, LocalDate dueDate) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found with id: " + projectId));
        
        Task task = TaskFactory.createTask(title, description, project, assignedToId, dueDate);
        return taskRepository.save(task);
    }
}
