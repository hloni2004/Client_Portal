package za.ac.styling.factory;

import za.ac.styling.domain.Deliverable;
import za.ac.styling.domain.Feedback;
import za.ac.styling.domain.User;
import za.ac.styling.util.ValidationHelper;

public class FeedbackFactory {

    public static Feedback createFeedback(String message, Deliverable deliverable, User user) {
        if (!ValidationHelper.validateMessage(message)) {
            throw new IllegalArgumentException("Invalid message: must not be empty");
        }

        if (deliverable == null) {
            throw new IllegalArgumentException("Invalid deliverable: deliverable cannot be null");
        }

        if (user == null) {
            throw new IllegalArgumentException("Invalid user: user cannot be null");
        }

        Feedback feedback = Feedback.builder()
                .message(message)
                .deliverable(deliverable)
                .user(user)
                .build();

        return feedback;
    }

    public static boolean validateFeedback(Feedback feedback) {
        return ValidationHelper.validateFeedback(feedback);
    }
}
