package za.ac.styling.service;

import za.ac.styling.domain.Feedback;

import java.util.List;

public interface IFeedbackService extends IService<Feedback, Integer> {

    List<Feedback> findByDeliverableId(Integer deliverableId);

    List<Feedback> findByUserId(Integer userId);

    List<Feedback> findByProjectId(Integer projectId);

    Feedback addFeedback(String message, Integer deliverableId, Integer userId);
}
