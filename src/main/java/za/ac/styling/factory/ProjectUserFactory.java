package za.ac.styling.factory;

import za.ac.styling.domain.Project;
import za.ac.styling.domain.ProjectAccessRole;
import za.ac.styling.domain.ProjectUser;
import za.ac.styling.domain.User;

public class ProjectUserFactory {

    public static ProjectUser createProjectUser(Project project, User user, ProjectAccessRole role) {
        if (project == null) {
            throw new IllegalArgumentException("Invalid project: project cannot be null");
        }

        if (user == null) {
            throw new IllegalArgumentException("Invalid user: user cannot be null");
        }

        if (role == null) {
            throw new IllegalArgumentException("Invalid role: role cannot be null");
        }

        ProjectUser projectUser = ProjectUser.builder()
                .project(project)
                .user(user)
                .role(role)
                .build();

        return projectUser;
    }
}
