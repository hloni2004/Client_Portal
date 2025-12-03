package za.ac.styling.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record GoogleSheetTaskDto(
    @NotNull Integer taskId,
    @NotBlank String title,
    String description,
    @NotNull Integer projectId,
    Integer assignedToId,
    @NotBlank String dueDate,
    @NotBlank String status
) {}
