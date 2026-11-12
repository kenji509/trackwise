package com.csit321g2.bainabai.trackwise.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record QuizRequest(
        @NotNull Long teacherId,
        @NotBlank @Size(max = 200) String title,
        String description,
        @NotNull @Positive Integer timeLimitMin) {
}
