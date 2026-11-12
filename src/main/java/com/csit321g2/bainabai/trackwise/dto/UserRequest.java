package com.csit321g2.bainabai.trackwise.dto;

import com.csit321g2.bainabai.trackwise.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRequest(
        @NotBlank @Size(max = 150) String fullName,
        @NotBlank @Email @Size(max = 150) String email,
        @Size(min = 8, max = 100) String password,
        @NotNull Role role,
        Long sectionId) {
}
