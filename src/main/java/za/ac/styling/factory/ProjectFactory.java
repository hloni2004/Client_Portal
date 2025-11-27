package za.ac.styling.factory;

import za.ac.styling.domain.Project;
import za.ac.styling.domain.ProjectStatus;
import za.ac.styling.util.ValidationHelper;

import java.time.LocalDate;

public class ProjectFactory {

    public static Project createProject(Integer clientId, String title, String description,
                                        LocalDate startDate, LocalDate dueDate) {
        if (!ValidationHelper.validateId(clientId)) {
            throw new IllegalArgumentException("Invalid clientId: must be a positive integer");
        }

        if (!ValidationHelper.validateTitle(title)) {
            throw new IllegalArgumentException("Invalid title: must not be empty and must be less than 200 characters");
        }

        if (!ValidationHelper.validateProjectDates(startDate, dueDate)) {
            throw new IllegalArgumentException("Invalid dates: start date must be before due date");
        }

        Project project = Project.builder()
                .clientId(clientId)
                .title(title)
                .description(description)
                .startDate(startDate)
                .dueDate(dueDate)
                .status(ProjectStatus.NOT_STARTED)
                .progress(0.0)
                .build();

        return project;
    }

    public static Project createProject(Integer clientId, String title) {
        if (!ValidationHelper.validateId(clientId)) {
            throw new IllegalArgumentException("Invalid clientId: must be a positive integer");
        }

        if (!ValidationHelper.validateTitle(title)) {
            throw new IllegalArgumentException("Invalid title: must not be empty and must be less than 200 characters");
        }

        Project project = Project.builder()
                .clientId(clientId)
                .title(title)
                .status(ProjectStatus.NOT_STARTED)
                .progress(0.0)
                .build();

        return project;
    }

    public static boolean validateProject(Project project) {
        return ValidationHelper.validateProject(project);
    }
}
