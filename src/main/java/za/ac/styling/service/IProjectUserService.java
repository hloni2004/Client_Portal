package za.ac.styling.service;

import za.ac.styling.domain.ProjectAccessRole;
import za.ac.styling.domain.ProjectUser;

import java.util.List;
import java.util.Optional;

public interface IProjectUserService extends IService<ProjectUser, Integer> {

    List<ProjectUser> findByProjectId(Integer projectId);

    List<ProjectUser> findByUserId(Integer userId);

    Optional<ProjectUser> findByProjectIdAndUserId(Integer projectId, Integer userId);

    List<ProjectUser> findByRole(ProjectAccessRole role);

    List<ProjectUser> findByProjectIdAndRole(Integer projectId, ProjectAccessRole role);

    boolean existsByProjectIdAndUserId(Integer projectId, Integer userId);

    void changeUserRole(Integer projectId, Integer userId, ProjectAccessRole newRole);

    ProjectUser addUserToProject(Integer projectId, Integer userId, ProjectAccessRole role);

    void removeUserFromProject(Integer projectId, Integer userId);
}
