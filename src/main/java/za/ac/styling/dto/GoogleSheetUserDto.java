package za.ac.styling.dto;

import jakarta.validation.constraints.NotNull;

public record GoogleSheetUserDto(
    @NotNull Integer userId,
    String name,
    String email,
    String role,
    String companyName,
    String createdAt
){}
