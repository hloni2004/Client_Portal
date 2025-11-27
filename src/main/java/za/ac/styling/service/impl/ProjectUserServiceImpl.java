package za.ac.styling.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.ac.styling.domain.Project;
import za.ac.styling.domain.ProjectAccessRole;
import za.ac.styling.domain.ProjectUser;
import za.ac.styling.domain.User;
import za.ac.styling.factory.ProjectUserFactory;
import za.ac.styling.repository.ProjectRepository;
import za.ac.styling.repository.ProjectUserRepository;
import za.ac.styling.repository.UserRepository;
import za.ac.styling.service.IProjectUserService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectUserServiceImpl implements IProjectUserService {

    private final ProjectUserRepository projectUserRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Override
    public ProjectUser save(ProjectUser entity) {
        if (entity == null || entity.getProject() == null || entity.getUser() == null || entity.getRole() == null) {
            throw new IllegalArgumentException("Invalid project user data");
        }
        return projectUserRepository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProjectUser> findById(Integer id) {
        return projectUserRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectUser> findAll() {
        return projectUserRepository.findAll();
    }

    @Override
    public ProjectUser update(ProjectUser entity) {
        if (entity == null || entity.getProject() == null || entity.getUser() == null || entity.getRole() == null) {
            throw new IllegalArgumentException("Invalid project user data");
        }
        if (!projectUserRepository.existsById(entity.getId())) {
            throw new IllegalArgumentException("ProjectUser not found with id: " + entity.getId());
        }
        return projectUserRepository.save(entity);
    }

    @Override
    public void deleteById(Integer id) {
        if (!projectUserRepository.existsById(id)) {
            throw new IllegalArgumentException("ProjectUser not found with id: " + id);
        }
        projectUserRepository.deleteById(id);
    }

    @Override
    public void delete(ProjectUser entity) {
        projectUserRepository.delete(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Integer id) {
        return projectUserRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return projectUserRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectUser> findByProjectId(Integer projectId) {
        return projectUserRepository.findByProjectProjectId(projectId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectUser> findByUserId(Integer userId) {
        return projectUserRepository.findByUserUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProjectUser> findByProjectIdAndUserId(Integer projectId, Integer userId) {
        return projectUserRepository.findByProjectProjectIdAndUserUserId(projectId, userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectUser> findByRole(ProjectAccessRole role) {
        return projectUserRepository.findByRole(role);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectUser> findByProjectIdAndRole(Integer projectId, ProjectAccessRole role) {
        return projectUserRepository.findByProjectProjectIdAndRole(projectId, role);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByProjectIdAndUserId(Integer projectId, Integer userId) {
        return projectUserRepository.existsByProjectProjectIdAndUserUserId(projectId, userId);
    }

    @Override
    public void changeUserRole(Integer projectId, Integer userId, ProjectAccessRole newRole) {
        ProjectUser projectUser = projectUserRepository.findByProjectProjectIdAndUserUserId(projectId, userId)
                .orElseThrow(() -> new IllegalArgumentException("ProjectUser not found for project: " + projectId + " and user: " + userId));
        
        projectUser.changeAccessRole(newRole);
        projectUserRepository.save(projectUser);
    }

    @Override
    public ProjectUser addUserToProject(Integer projectId, Integer userId, ProjectAccessRole role) {
        if (projectUserRepository.existsByProjectProjectIdAndUserUserId(projectId, userId)) {
            throw new IllegalArgumentException("User already assigned to this project");
        }
        
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found with id: " + projectId));
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
        
        ProjectUser projectUser = ProjectUserFactory.createProjectUser(project, user, role);
        return projectUserRepository.save(projectUser);
    }

    @Override
    public void removeUserFromProject(Integer projectId, Integer userId) {
        ProjectUser projectUser = projectUserRepository.findByProjectProjectIdAndUserUserId(projectId, userId)
                .orElseThrow(() -> new IllegalArgumentException("ProjectUser not found for project: " + projectId + " and user: " + userId));
        
        projectUserRepository.delete(projectUser);
    }
}
