package com.csit321g2.bainabai.trackwise.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.csit321g2.bainabai.trackwise.entity.SectionEntity;
import com.csit321g2.bainabai.trackwise.entity.UserEntity;
import com.csit321g2.bainabai.trackwise.repository.UserRepository;
import com.csit321g2.bainabai.trackwise.repository.SectionRepository;

@Service
public class UserService {

    @Autowired
    UserRepository repo;

    @Autowired
    SectionRepository sectionRepo;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UserService() {
    }

    public UserEntity postUser(UserEntity user) {
        if (user.getEmail() != null && repo.existsByEmailIgnoreCase(user.getEmail())) {
            throw new IllegalArgumentException("Email " + user.getEmail() + " already exists!");
        }
        if (user.getPasswordHash() == null || user.getPasswordHash().isBlank()) {
            throw new IllegalArgumentException("Password is required!");
        }
        user.setPasswordHash(encoder.encode(user.getPasswordHash()));
        if (user.getSection() != null) {
            int sectionId = user.getSection().getSectionId();
            SectionEntity section = sectionRepo.findById(sectionId).orElseThrow(() -> new NoSuchElementException("Section " + sectionId + " does not exist!"));
            user.setSection(section);
        }
        return repo.save(user);
    }

    public List<UserEntity> getAllUsers() {
        return repo.findAll();
    }

    public UserEntity putUser(int id, UserEntity newDetails) {
        UserEntity user = repo.findById(id).orElseThrow(() -> new NoSuchElementException("User " + id + " does not exist!"));
        if (newDetails.getEmail() != null && !newDetails.getEmail().equalsIgnoreCase(user.getEmail())
                && repo.existsByEmailIgnoreCase(newDetails.getEmail())) {
            throw new IllegalArgumentException("Email " + newDetails.getEmail() + " already exists!");
        }
        user.setFullName(newDetails.getFullName());
        user.setEmail(newDetails.getEmail());
        user.setRole(newDetails.getRole());
        if (newDetails.getPasswordHash() != null && !newDetails.getPasswordHash().isBlank()) {
            user.setPasswordHash(encoder.encode(newDetails.getPasswordHash()));
        }
        if (newDetails.getSection() != null) {
            int sectionId = newDetails.getSection().getSectionId();
            SectionEntity section = sectionRepo.findById(sectionId).orElseThrow(() -> new NoSuchElementException("Section " + sectionId + " does not exist!"));
            user.setSection(section);
        } else {
            user.setSection(null);
        }
        return repo.save(user);
    }

    public String deleteUser(int id) {
        if (!repo.existsById(id)) {
            throw new NoSuchElementException("User " + id + " does not exist!");
        }
        repo.deleteById(id);
        return "User " + id + " is successfully deleted!";
    }

}
