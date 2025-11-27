package za.ac.styling.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.ac.styling.domain.Deliverable;
import za.ac.styling.domain.Project;
import za.ac.styling.factory.DeliverableFactory;
import za.ac.styling.repository.DeliverableRepository;
import za.ac.styling.repository.ProjectRepository;
import za.ac.styling.service.IDeliverableService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class DeliverableServiceImpl implements IDeliverableService {

    private final DeliverableRepository deliverableRepository;
    private final ProjectRepository projectRepository;

    @Override
    public Deliverable save(Deliverable entity) {
        if (!DeliverableFactory.validateDeliverable(entity)) {
            throw new IllegalArgumentException("Invalid deliverable data");
        }
        return deliverableRepository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Deliverable> findById(Integer id) {
        return deliverableRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Deliverable> findAll() {
        return deliverableRepository.findAll();
    }

    @Override
    public Deliverable update(Deliverable entity) {
        if (!DeliverableFactory.validateDeliverable(entity)) {
            throw new IllegalArgumentException("Invalid deliverable data");
        }
        if (!deliverableRepository.existsById(entity.getDeliverableId())) {
            throw new IllegalArgumentException("Deliverable not found with id: " + entity.getDeliverableId());
        }
        return deliverableRepository.save(entity);
    }

    @Override
    public void deleteById(Integer id) {
        if (!deliverableRepository.existsById(id)) {
            throw new IllegalArgumentException("Deliverable not found with id: " + id);
        }
        deliverableRepository.deleteById(id);
    }

    @Override
    public void delete(Deliverable entity) {
        deliverableRepository.delete(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Integer id) {
        return deliverableRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return deliverableRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Deliverable> findByProjectId(Integer projectId) {
        return deliverableRepository.findByProjectProjectId(projectId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Deliverable> findByApprovalStatus(Boolean approved) {
        return deliverableRepository.findByApproved(approved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Deliverable> findByProjectIdAndApprovalStatus(Integer projectId, Boolean approved) {
        return deliverableRepository.findByProjectProjectIdAndApproved(projectId, approved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Deliverable> findByFileType(String fileType) {
        return deliverableRepository.findByFileType(fileType);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Deliverable> searchByFileName(String fileName) {
        return deliverableRepository.findByFileNameContainingIgnoreCase(fileName);
    }

    @Override
    public void approveDeliverable(Integer deliverableId) {
        Deliverable deliverable = deliverableRepository.findById(deliverableId)
                .orElseThrow(() -> new IllegalArgumentException("Deliverable not found with id: " + deliverableId));
        
        deliverable.approve();
        deliverableRepository.save(deliverable);
    }

    @Override
    public Deliverable uploadDeliverable(String fileName, String fileType, String fileUrl, Integer projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found with id: " + projectId));
        
        Deliverable deliverable = DeliverableFactory.createDeliverable(fileName, fileType, fileUrl, project);
        return deliverableRepository.save(deliverable);
    }
}
