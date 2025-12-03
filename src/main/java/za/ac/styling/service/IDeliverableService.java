package za.ac.styling.service;

import za.ac.styling.domain.Deliverable;

import java.util.List;

public interface IDeliverableService extends IService<Deliverable, Integer> {

    List<Deliverable> findByProjectId(Integer projectId);

    List<Deliverable> findByApprovalStatus(Boolean approved);

    List<Deliverable> findByProjectIdAndApprovalStatus(Integer projectId, Boolean approved);

    List<Deliverable> findByFileType(String fileType);

    List<Deliverable> searchByFileName(String fileName);

    void approveDeliverable(Integer deliverableId);

    Deliverable uploadDeliverable(String fileName, String fileType, String fileUrl, Integer projectId, Integer taskId);
}
