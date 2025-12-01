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
public class ProjectUserRoleChangeDto {

    @NotNull(message = "Project ID is required")
    private Integer projectId;

    @NotNull(message = "User ID is required")
    private Integer userId;

    @NotNull(message = "New role is required")
    private ProjectAccessRole newRole;
}
