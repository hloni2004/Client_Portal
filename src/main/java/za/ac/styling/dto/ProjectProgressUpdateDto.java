package za.ac.styling.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectProgressUpdateDto {

    @NotNull(message = "Progress is required")
    @DecimalMin(value = "0.0", message = "Progress must be at least 0")
    @DecimalMax(value = "100.0", message = "Progress cannot exceed 100")
    private Double progress;
}
