package com.csit321g2.bainabai.trackwise.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record QuestionRequest(@NotNull Long quizId, @NotNull Long topicId, @NotBlank String questionText) {
}
