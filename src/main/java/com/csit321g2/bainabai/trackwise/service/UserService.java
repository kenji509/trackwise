package com.csit321g2.bainabai.trackwise.service;

import com.csit321g2.bainabai.trackwise.dto.UserRequest;
import com.csit321g2.bainabai.trackwise.dto.UserResponse;
import com.csit321g2.bainabai.trackwise.entity.Section;
import com.csit321g2.bainabai.trackwise.entity.User;
import com.csit321g2.bainabai.trackwise.exception.BadRequestException;
import com.csit321g2.bainabai.trackwise.exception.DuplicateResourceException;
import com.csit321g2.bainabai.trackwise.exception.ResourceNotFoundException;
import com.csit321g2.bainabai.trackwise.repository.SectionRepository;
import com.csit321g2.bainabai.trackwise.repository.UserRepository;
import java.util.List;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final SectionRepository sectionRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository, SectionRepository sectionRepository) {
        this.userRepository = userRepository;
        this.sectionRepository = sectionRepository;
    }

    @Transactional
    public UserResponse create(UserRequest request) {
        if (request.password() == null || request.password().isBlank()) {
            throw new BadRequestException("Password is required");
        }
        if (userRepository.existsByEmail(request.email())) {
            throw new DuplicateResourceException("Email already in use");
        }
        User user = new User();
        apply(user, request);
        user.setPasswordHash(passwordEncoder.encode(request.password()));
        return toResponse(userRepository.saveAndFlush(user));
    }

    @Transactional(readOnly = true)
    public List<UserResponse> findAll() {
        return userRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public UserResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    @Transactional
    public UserResponse update(Long id, UserRequest request) {
        User user = getOrThrow(id);
        if (userRepository.existsByEmailAndIdNot(request.email(), id)) {
            throw new DuplicateResourceException("Email already in use");
        }
        apply(user, request);
        if (request.password() != null && !request.password().isBlank()) {
            user.setPasswordHash(passwordEncoder.encode(request.password()));
        }
        return toResponse(userRepository.saveAndFlush(user));
    }

    @Transactional
    public void delete(Long id) {
        userRepository.delete(getOrThrow(id));
        userRepository.flush();
    }

    private void apply(User user, UserRequest request) {
        user.setFullName(request.fullName());
        user.setEmail(request.email());
        user.setRole(request.role());
        Section section = null;
        if (request.sectionId() != null) {
            section = sectionRepository.findById(request.sectionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Section", request.sectionId()));
        }
        user.setSection(section);
    }

    private User getOrThrow(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", id));
    }

    private UserResponse toResponse(User user) {
        Long sectionId = user.getSection() == null ? null : user.getSection().getId();
        return new UserResponse(user.getId(), user.getFullName(), user.getEmail(), user.getRole(), sectionId);
    }
}
