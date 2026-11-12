package com.csit321g2.bainabai.trackwise.dto;

import java.time.LocalDateTime;

public record QuizResponse(Long quizId, Long teacherId, String title, String description,
                           Integer timeLimitMin, LocalDateTime createdAt) {
}
