package za.ac.styling.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.styling.domain.Project;
import za.ac.styling.domain.ProjectStatus;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {

    List<Project> findByClientId(Integer clientId);

    List<Project> findByStatus(ProjectStatus status);

    List<Project> findByTitleContainingIgnoreCase(String title);

    List<Project> findByDueDateBefore(LocalDate date);

    List<Project> findByDueDateBetween(LocalDate startDate, LocalDate endDate);

    List<Project> findByClientIdAndStatus(Integer clientId, ProjectStatus status);
}
