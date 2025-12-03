package za.ac.styling.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record GoogleSheetProjectDto(
    @NotNull Integer projectId,
    @NotNull Integer clientId,
    @NotBlank String title,
    String description,
    @NotBlank String startDate,
    @NotBlank String dueDate,
    @NotBlank String status,
    @NotNull Double progress
) {}
