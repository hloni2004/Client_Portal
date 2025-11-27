package za.ac.styling.util;

import za.ac.styling.domain.*;

import java.time.LocalDate;
import java.util.regex.Pattern;

public class ValidationHelper {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );

    private static final Pattern PHONE_PATTERN = Pattern.compile(
            "^[+]?[(]?[0-9]{1,4}[)]?[-\\s.]?[(]?[0-9]{1,4}[)]?[-\\s.]?[0-9]{1,9}$"
    );

    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final int MAX_NAME_LENGTH = 100;
    private static final int MAX_TITLE_LENGTH = 200;

    public static boolean validateUser(User user) {
        if (user == null) {
            return false;
        }
        return validateName(user.getName()) &&
               validateEmail(user.getEmail()) &&
               validatePassword(user.getPassword()) &&
               user.getRole() != null;
    }

    public static boolean validateName(String name) {
        return name != null &&
               !name.trim().isEmpty() &&
               name.length() <= MAX_NAME_LENGTH;
    }

    public static boolean validateEmail(String email) {
        return email != null &&
               !email.trim().isEmpty() &&
               EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean validatePassword(String password) {
        return password != null &&
               password.length() >= MIN_PASSWORD_LENGTH;
    }

    public static boolean validatePhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return true; // Phone is optional
        }
        return PHONE_PATTERN.matcher(phone).matches();
    }

    public static boolean validateProject(Project project) {
        if (project == null) {
            return false;
        }
        return project.getClientId() != null &&
               project.getClientId() > 0 &&
               validateTitle(project.getTitle()) &&
               validateProjectDates(project.getStartDate(), project.getDueDate()) &&
               project.getStatus() != null &&
               validateProgress(project.getProgress());
    }

    public static boolean validateTitle(String title) {
        return title != null &&
               !title.trim().isEmpty() &&
               title.length() <= MAX_TITLE_LENGTH;
    }

    public static boolean validateProjectDates(LocalDate startDate, LocalDate dueDate) {
        if (startDate == null || dueDate == null) {
            return true; // Dates are optional
        }
        return !startDate.isAfter(dueDate);
    }

    public static boolean validateProgress(Double progress) {
        return progress != null &&
               progress >= 0.0 &&
               progress <= 100.0;
    }

    public static boolean validateTask(Task task) {
        if (task == null) {
            return false;
        }
        return validateTitle(task.getTitle()) &&
               task.getStatus() != null &&
               task.getProject() != null;
    }

    public static boolean validateTaskDueDate(LocalDate dueDate) {
        if (dueDate == null) {
            return true; // Due date is optional
        }
        return !dueDate.isBefore(LocalDate.now());
    }

    public static boolean validateDeliverable(Deliverable deliverable) {
        if (deliverable == null) {
            return false;
        }
        return validateFileName(deliverable.getFileName()) &&
               validateFileType(deliverable.getFileType()) &&
               validateFileUrl(deliverable.getFileUrl()) &&
               deliverable.getProject() != null;
    }

    public static boolean validateFileName(String fileName) {
        return fileName != null && !fileName.trim().isEmpty();
    }

    public static boolean validateFileType(String fileType) {
        return fileType != null && !fileType.trim().isEmpty();
    }

    public static boolean validateFileUrl(String fileUrl) {
        return fileUrl != null && !fileUrl.trim().isEmpty();
    }

    public static boolean validateFeedback(Feedback feedback) {
        if (feedback == null) {
            return false;
        }
        return feedback.getUser() != null &&
               feedback.getDeliverable() != null &&
               validateMessage(feedback.getMessage());
    }

    public static boolean validateNotification(Notification notification) {
        if (notification == null) {
            return false;
        }
        return notification.getUser() != null &&
               validateMessage(notification.getMessage()) &&
               notification.getType() != null;
    }

    public static boolean validateMessage(String message) {
        return message != null && !message.trim().isEmpty();
    }

    public static boolean validateId(Integer id) {
        return id != null && id > 0;
    }

    public static boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static boolean isNotNullOrEmpty(String value) {
        return !isNullOrEmpty(value);
    }
}
