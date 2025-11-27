package za.ac.styling.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.ac.styling.domain.Deliverable;
import za.ac.styling.domain.Feedback;
import za.ac.styling.domain.User;
import za.ac.styling.factory.FeedbackFactory;
import za.ac.styling.repository.DeliverableRepository;
import za.ac.styling.repository.FeedbackRepository;
import za.ac.styling.repository.UserRepository;
import za.ac.styling.service.IFeedbackService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class FeedbackServiceImpl implements IFeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final DeliverableRepository deliverableRepository;
    private final UserRepository userRepository;

    @Override
    public Feedback save(Feedback entity) {
        if (!FeedbackFactory.validateFeedback(entity)) {
            throw new IllegalArgumentException("Invalid feedback data");
        }
        return feedbackRepository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Feedback> findById(Integer id) {
        return feedbackRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Feedback> findAll() {
        return feedbackRepository.findAll();
    }

    @Override
    public Feedback update(Feedback entity) {
        if (!FeedbackFactory.validateFeedback(entity)) {
            throw new IllegalArgumentException("Invalid feedback data");
        }
        if (!feedbackRepository.existsById(entity.getFeedbackId())) {
            throw new IllegalArgumentException("Feedback not found with id: " + entity.getFeedbackId());
        }
        return feedbackRepository.save(entity);
    }

    @Override
    public void deleteById(Integer id) {
        if (!feedbackRepository.existsById(id)) {
            throw new IllegalArgumentException("Feedback not found with id: " + id);
        }
        feedbackRepository.deleteById(id);
    }

    @Override
    public void delete(Feedback entity) {
        feedbackRepository.delete(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Integer id) {
        return feedbackRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return feedbackRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Feedback> findByDeliverableId(Integer deliverableId) {
        return feedbackRepository.findByDeliverableDeliverableId(deliverableId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Feedback> findByUserId(Integer userId) {
        return feedbackRepository.findByUserUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Feedback> findByProjectId(Integer projectId) {
        return feedbackRepository.findByDeliverableProjectProjectId(projectId);
    }

    @Override
    public Feedback addFeedback(String message, Integer deliverableId, Integer userId) {
        Deliverable deliverable = deliverableRepository.findById(deliverableId)
                .orElseThrow(() -> new IllegalArgumentException("Deliverable not found with id: " + deliverableId));
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
        
        Feedback feedback = FeedbackFactory.createFeedback(message, deliverable, user);
        return feedbackRepository.save(feedback);
    }
}
