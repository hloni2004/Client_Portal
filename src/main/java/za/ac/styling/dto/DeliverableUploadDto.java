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
public class DeliverableUploadDto {

    @NotBlank(message = "File name is required")
    private String fileName;

    @NotBlank(message = "File type is required")
    private String fileType;

    @NotBlank(message = "File URL is required")
    private String fileUrl;

    @NotNull(message = "Project ID is required")
    private Integer projectId;
}
