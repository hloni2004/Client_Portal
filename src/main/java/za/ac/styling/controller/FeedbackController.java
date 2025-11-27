package za.ac.styling.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.styling.domain.Feedback;
import za.ac.styling.service.IFeedbackService;

import java.util.List;

@RestController
@RequestMapping("/api/feedback")
@RequiredArgsConstructor
public class FeedbackController {

    private final IFeedbackService feedbackService;

    @PostMapping
    public ResponseEntity<Feedback> createFeedback(@RequestBody Feedback feedback) {
        Feedback saved = feedbackService.save(feedback);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PostMapping("/add")
    public ResponseEntity<Feedback> addFeedback(@RequestParam String message,
                                                 @RequestParam Integer deliverableId,
                                                 @RequestParam Integer userId) {
        Feedback feedback = feedbackService.addFeedback(message, deliverableId, userId);
        return new ResponseEntity<>(feedback, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Feedback> getFeedbackById(@PathVariable Integer id) {
        return feedbackService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Feedback>> getAllFeedback() {
        List<Feedback> feedbacks = feedbackService.findAll();
        return ResponseEntity.ok(feedbacks);
    }

    @GetMapping("/deliverable/{deliverableId}")
    public ResponseEntity<List<Feedback>> getFeedbackByDeliverable(@PathVariable Integer deliverableId) {
        List<Feedback> feedbacks = feedbackService.findByDeliverableId(deliverableId);
        return ResponseEntity.ok(feedbacks);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Feedback>> getFeedbackByUser(@PathVariable Integer userId) {
        List<Feedback> feedbacks = feedbackService.findByUserId(userId);
        return ResponseEntity.ok(feedbacks);
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Feedback>> getFeedbackByProject(@PathVariable Integer projectId) {
        List<Feedback> feedbacks = feedbackService.findByProjectId(projectId);
        return ResponseEntity.ok(feedbacks);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Feedback> updateFeedback(@PathVariable Integer id, @RequestBody Feedback feedback) {
        if (!feedbackService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        feedback.setFeedbackId(id);
        Feedback updated = feedbackService.update(feedback);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeedback(@PathVariable Integer id) {
        if (!feedbackService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        feedbackService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countFeedback() {
        long count = feedbackService.count();
        return ResponseEntity.ok(count);
    }
}
