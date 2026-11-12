package com.csit321g2.bainabai.trackwise.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SectionRequest(@NotBlank @Size(max = 100) String name) {
}
