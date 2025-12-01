package za.ac.styling.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackAddDto {

    @NotBlank(message = "Message is required")
    private String message;

    @NotNull(message = "Deliverable ID is required")
    private Integer deliverableId;

    @NotNull(message = "User ID is required")
    private Integer userId;
}
