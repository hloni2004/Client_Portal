package za.ac.styling.factory;

import za.ac.styling.domain.Deliverable;
import za.ac.styling.domain.Project;
import za.ac.styling.util.ValidationHelper;

public class DeliverableFactory {

    public static Deliverable createDeliverable(String fileName, String fileType, String fileUrl, Project project) {
        if (!ValidationHelper.validateFileName(fileName)) {
            throw new IllegalArgumentException("Invalid fileName: must not be empty");
        }

        if (!ValidationHelper.validateFileType(fileType)) {
            throw new IllegalArgumentException("Invalid fileType: must not be empty");
        }

        if (!ValidationHelper.validateFileUrl(fileUrl)) {
            throw new IllegalArgumentException("Invalid fileUrl: must not be empty");
        }

        if (project == null) {
            throw new IllegalArgumentException("Invalid project: project cannot be null");
        }

        Deliverable deliverable = Deliverable.builder()
                .fileName(fileName)
                .fileType(fileType)
                .fileUrl(fileUrl)
                .project(project)
                .approved(false)
                .build();

        return deliverable;
    }

    public static boolean validateDeliverable(Deliverable deliverable) {
        return ValidationHelper.validateDeliverable(deliverable);
    }
}
