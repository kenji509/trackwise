package com.csit321g2.bainabai.trackwise.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ChoiceRequest(@NotNull Long questionId, @NotBlank String choiceText, @NotNull Boolean isCorrect) {
}
