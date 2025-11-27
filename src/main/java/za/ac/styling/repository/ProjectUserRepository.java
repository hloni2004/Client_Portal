package za.ac.styling.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.styling.domain.ProjectAccessRole;
import za.ac.styling.domain.ProjectUser;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectUserRepository extends JpaRepository<ProjectUser, Integer> {

    List<ProjectUser> findByProjectProjectId(Integer projectId);

    List<ProjectUser> findByUserUserId(Integer userId);

    Optional<ProjectUser> findByProjectProjectIdAndUserUserId(Integer projectId, Integer userId);

    List<ProjectUser> findByRole(ProjectAccessRole role);

    List<ProjectUser> findByProjectProjectIdAndRole(Integer projectId, ProjectAccessRole role);

    boolean existsByProjectProjectIdAndUserUserId(Integer projectId, Integer userId);
}
