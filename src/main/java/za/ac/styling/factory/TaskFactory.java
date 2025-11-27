package za.ac.styling.factory;

import za.ac.styling.domain.Project;
import za.ac.styling.domain.Task;
import za.ac.styling.domain.TaskStatus;
import za.ac.styling.util.ValidationHelper;

import java.time.LocalDate;

public class TaskFactory {

    public static Task createTask(String title, String description, Project project,
                                  Integer assignedToId, LocalDate dueDate) {
        if (!ValidationHelper.validateTitle(title)) {
            throw new IllegalArgumentException("Invalid title: must not be empty and must be less than 200 characters");
        }

        if (project == null) {
            throw new IllegalArgumentException("Invalid project: project cannot be null");
        }

        if (assignedToId != null && !ValidationHelper.validateId(assignedToId)) {
            throw new IllegalArgumentException("Invalid assignedToId: must be a positive integer");
        }

        if (!ValidationHelper.validateTaskDueDate(dueDate)) {
            throw new IllegalArgumentException("Invalid due date: cannot be in the past");
        }

        Task task = Task.builder()
                .title(title)
                .description(description)
                .project(project)
                .assignedToId(assignedToId)
                .dueDate(dueDate)
                .status(TaskStatus.NOT_STARTED)
                .build();

        return task;
    }

    public static Task createTask(String title, Project project) {
        if (!ValidationHelper.validateTitle(title)) {
            throw new IllegalArgumentException("Invalid title: must not be empty and must be less than 200 characters");
        }

        if (project == null) {
            throw new IllegalArgumentException("Invalid project: project cannot be null");
        }

        Task task = Task.builder()
                .title(title)
                .project(project)
                .status(TaskStatus.NOT_STARTED)
                .build();

        return task;
    }

    public static boolean validateTask(Task task) {
        return ValidationHelper.validateTask(task);
    }
}
