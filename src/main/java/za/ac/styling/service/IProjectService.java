package za.ac.styling.service;

import za.ac.styling.domain.Project;
import za.ac.styling.domain.ProjectStatus;

import java.time.LocalDate;
import java.util.List;

public interface IProjectService extends IService<Project, Integer> {

    List<Project> findByClientId(Integer clientId);

    List<Project> findByStatus(ProjectStatus status);

    List<Project> searchByTitle(String title);

    List<Project> findOverdueProjects();

    List<Project> findProjectsDueBetween(LocalDate startDate, LocalDate endDate);

    List<Project> findByClientIdAndStatus(Integer clientId, ProjectStatus status);

    void updateProjectStatus(Integer projectId, ProjectStatus status);

    void updateProjectProgress(Integer projectId, Double progress);

    Project createProject(Integer clientId, String title, String description, LocalDate startDate, LocalDate dueDate);
}
