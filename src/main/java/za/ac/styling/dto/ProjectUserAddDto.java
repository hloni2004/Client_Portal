package za.ac.styling.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import za.ac.styling.domain.ProjectAccessRole;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectUserAddDto {

    @NotNull(message = "Project ID is required")
    private Integer projectId;

    @NotNull(message = "User ID is required")
    private Integer userId;

    @NotNull(message = "Role is required")
    private ProjectAccessRole role;
}
