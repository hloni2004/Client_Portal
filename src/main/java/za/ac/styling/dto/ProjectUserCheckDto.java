package za.ac.styling.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectUserCheckDto {

    @NotNull(message = "Project ID is required")
    private Integer projectId;

    @NotNull(message = "User ID is required")
    private Integer userId;
}
