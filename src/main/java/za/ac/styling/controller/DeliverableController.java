package za.ac.styling.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.styling.domain.Deliverable;
import za.ac.styling.dto.*;
import za.ac.styling.service.IDeliverableService;

import java.util.List;

@RestController
@RequestMapping("/api/deliverables")
@RequiredArgsConstructor
public class DeliverableController {

    private final IDeliverableService deliverableService;

    @PostMapping
    public ResponseEntity<Deliverable> createDeliverable(@RequestBody Deliverable deliverable) {
        Deliverable saved = deliverableService.save(deliverable);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PostMapping("/upload")
    public ResponseEntity<Deliverable> uploadDeliverable(@Valid @RequestBody DeliverableUploadDto dto) {
        Deliverable deliverable = deliverableService.uploadDeliverable(dto.getFileName(), 
                dto.getFileType(), dto.getFileUrl(), dto.getProjectId(), dto.getTaskId());
        return new ResponseEntity<>(deliverable, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Deliverable> getDeliverableById(@PathVariable Integer id) {
        return deliverableService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Deliverable>> getAllDeliverables() {
        List<Deliverable> deliverables = deliverableService.findAll();
        return ResponseEntity.ok(deliverables);
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Deliverable>> getDeliverablesByProject(@PathVariable Integer projectId) {
        List<Deliverable> deliverables = deliverableService.findByProjectId(projectId);
        return ResponseEntity.ok(deliverables);
    }

    @GetMapping("/approved/{approved}")
    public ResponseEntity<List<Deliverable>> getDeliverablesByApprovalStatus(@PathVariable Boolean approved) {
        List<Deliverable> deliverables = deliverableService.findByApprovalStatus(approved);
        return ResponseEntity.ok(deliverables);
    }

    @GetMapping("/project/{projectId}/approved/{approved}")
    public ResponseEntity<List<Deliverable>> getDeliverablesByProjectAndApprovalStatus(
            @PathVariable Integer projectId,
            @PathVariable Boolean approved) {
        List<Deliverable> deliverables = deliverableService.findByProjectIdAndApprovalStatus(projectId, approved);
        return ResponseEntity.ok(deliverables);
    }

    @GetMapping("/file-type/{fileType}")
    public ResponseEntity<List<Deliverable>> getDeliverablesByFileType(@PathVariable String fileType) {
        List<Deliverable> deliverables = deliverableService.findByFileType(fileType);
        return ResponseEntity.ok(deliverables);
    }

    @PostMapping("/search")
    public ResponseEntity<List<Deliverable>> searchByFileName(@Valid @RequestBody SearchDto dto) {
        List<Deliverable> deliverables = deliverableService.searchByFileName(dto.getQuery());
        return ResponseEntity.ok(deliverables);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Deliverable> updateDeliverable(@PathVariable Integer id, @RequestBody Deliverable deliverable) {
        if (!deliverableService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        deliverable.setDeliverableId(id);
        Deliverable updated = deliverableService.update(deliverable);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<Void> approveDeliverable(@PathVariable Integer id) {
        deliverableService.approveDeliverable(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeliverable(@PathVariable Integer id) {
        if (!deliverableService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        deliverableService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countDeliverables() {
        long count = deliverableService.count();
        return ResponseEntity.ok(count);
    }
}
