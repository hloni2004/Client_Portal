package za.ac.styling.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.styling.domain.Feedback;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {

    List<Feedback> findByDeliverableDeliverableId(Integer deliverableId);

    List<Feedback> findByUserUserId(Integer userId);

    List<Feedback> findByDeliverableProjectProjectId(Integer projectId);
}
