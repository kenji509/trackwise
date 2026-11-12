package com.csit321g2.bainabai.trackwise.dto;

import com.csit321g2.bainabai.trackwise.entity.Role;

public record UserResponse(Long userId, String fullName, String email, Role role, Long sectionId) {
}
