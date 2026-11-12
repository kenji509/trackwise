package com.csit321g2.bainabai.trackwise.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csit321g2.bainabai.trackwise.entity.SectionEntity;
import com.csit321g2.bainabai.trackwise.repository.SectionRepository;

@Service
public class SectionService {

    @Autowired
    SectionRepository repo;

    public SectionService() {
    }

    public SectionEntity postSection(SectionEntity section) {
        if (section.getName() != null && repo.existsByNameIgnoreCase(section.getName())) {
            throw new IllegalArgumentException("Section " + section.getName() + " already exists!");
        }
        return repo.save(section);
    }

    public List<SectionEntity> getAllSections() {
        return repo.findAll();
    }

    public SectionEntity putSection(int id, SectionEntity newDetails) {
        SectionEntity section = repo.findById(id).orElseThrow(() -> new NoSuchElementException("Section " + id + " does not exist!"));
        if (newDetails.getName() != null && !newDetails.getName().equalsIgnoreCase(section.getName())
                && repo.existsByNameIgnoreCase(newDetails.getName())) {
            throw new IllegalArgumentException("Section " + newDetails.getName() + " already exists!");
        }
        section.setName(newDetails.getName());
        return repo.save(section);
    }

    public String deleteSection(int id) {
        if (!repo.existsById(id)) {
            throw new NoSuchElementException("Section " + id + " does not exist!");
        }
        repo.deleteById(id);
        return "Section " + id + " is successfully deleted!";
    }

}
