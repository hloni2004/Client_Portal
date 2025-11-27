package za.ac.styling.service;

import za.ac.styling.domain.Task;
import za.ac.styling.domain.TaskStatus;

import java.time.LocalDate;
import java.util.List;

public interface ITaskService extends IService<Task, Integer> {

    List<Task> findByProjectId(Integer projectId);

    List<Task> findByAssignedUserId(Integer userId);

    List<Task> findByStatus(TaskStatus status);

    List<Task> findOverdueTasks();

    List<Task> findByProjectIdAndStatus(Integer projectId, TaskStatus status);

    List<Task> findByAssignedUserIdAndStatus(Integer userId, TaskStatus status);

    List<Task> searchByTitle(String title);

    void updateTaskStatus(Integer taskId, TaskStatus status);

    void assignTask(Integer taskId, Integer userId);

    Task createTask(String title, String description, Integer projectId, Integer assignedToId, LocalDate dueDate);
}
