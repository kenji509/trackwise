package com.csit321g2.bainabai.trackwise.dto;

public record ChoiceResponse(Long choiceId, Long questionId, String choiceText, boolean isCorrect) {
}
