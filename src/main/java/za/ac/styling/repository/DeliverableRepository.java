package za.ac.styling.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.styling.domain.Deliverable;

import java.util.List;

@Repository
public interface DeliverableRepository extends JpaRepository<Deliverable, Integer> {

    List<Deliverable> findByProjectProjectId(Integer projectId);

    List<Deliverable> findByApproved(Boolean approved);

    List<Deliverable> findByProjectProjectIdAndApproved(Integer projectId, Boolean approved);

    List<Deliverable> findByFileType(String fileType);

    List<Deliverable> findByFileNameContainingIgnoreCase(String fileName);
}
