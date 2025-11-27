package za.ac.styling.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.ac.styling.domain.Project;
import za.ac.styling.domain.ProjectStatus;
import za.ac.styling.factory.ProjectFactory;
import za.ac.styling.repository.ProjectRepository;
import za.ac.styling.service.IProjectService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectServiceImpl implements IProjectService {

    private final ProjectRepository projectRepository;

    @Override
    public Project save(Project entity) {
        if (!ProjectFactory.validateProject(entity)) {
            throw new IllegalArgumentException("Invalid project data");
        }
        return projectRepository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Project> findById(Integer id) {
        return projectRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @Override
    public Project update(Project entity) {
        if (!ProjectFactory.validateProject(entity)) {
            throw new IllegalArgumentException("Invalid project data");
        }
        if (!projectRepository.existsById(entity.getProjectId())) {
            throw new IllegalArgumentException("Project not found with id: " + entity.getProjectId());
        }
        return projectRepository.save(entity);
    }

    @Override
    public void deleteById(Integer id) {
        if (!projectRepository.existsById(id)) {
            throw new IllegalArgumentException("Project not found with id: " + id);
        }
        projectRepository.deleteById(id);
    }

    @Override
    public void delete(Project entity) {
        projectRepository.delete(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Integer id) {
        return projectRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return projectRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Project> findByClientId(Integer clientId) {
        return projectRepository.findByClientId(clientId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Project> findByStatus(ProjectStatus status) {
        return projectRepository.findByStatus(status);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Project> searchByTitle(String title) {
        return projectRepository.findByTitleContainingIgnoreCase(title);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Project> findOverdueProjects() {
        return projectRepository.findByDueDateBefore(LocalDate.now());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Project> findProjectsDueBetween(LocalDate startDate, LocalDate endDate) {
        return projectRepository.findByDueDateBetween(startDate, endDate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Project> findByClientIdAndStatus(Integer clientId, ProjectStatus status) {
        return projectRepository.findByClientIdAndStatus(clientId, status);
    }

    @Override
    public void updateProjectStatus(Integer projectId, ProjectStatus status) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found with id: " + projectId));
        
        project.updateStatus(status);
        projectRepository.save(project);
    }

    @Override
    public void updateProjectProgress(Integer projectId, Double progress) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found with id: " + projectId));
        
        project.setProgress(progress);
        projectRepository.save(project);
    }

    @Override
    public Project createProject(Integer clientId, String title, String description, LocalDate startDate, LocalDate dueDate) {
        Project project = ProjectFactory.createProject(clientId, title, description, startDate, dueDate);
        return projectRepository.save(project);
    }
}
