package za.ac.styling.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import za.ac.styling.domain.TaskStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskStatusUpdateDto {

    @NotNull(message = "Status is required")
    private TaskStatus status;
}
