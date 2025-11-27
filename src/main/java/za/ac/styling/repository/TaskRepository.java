package za.ac.styling.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.styling.domain.Task;
import za.ac.styling.domain.TaskStatus;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

    List<Task> findByProjectProjectId(Integer projectId);

    List<Task> findByAssignedToId(Integer userId);

    List<Task> findByStatus(TaskStatus status);

    List<Task> findByDueDateBefore(LocalDate date);

    List<Task> findByProjectProjectIdAndStatus(Integer projectId, TaskStatus status);

    List<Task> findByAssignedToIdAndStatus(Integer userId, TaskStatus status);

    List<Task> findByTitleContainingIgnoreCase(String title);
}
