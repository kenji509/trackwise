package com.csit321g2.bainabai.trackwise.service;

import com.csit321g2.bainabai.trackwise.dto.SectionRequest;
import com.csit321g2.bainabai.trackwise.dto.SectionResponse;
import com.csit321g2.bainabai.trackwise.entity.Section;
import com.csit321g2.bainabai.trackwise.exception.ResourceNotFoundException;
import com.csit321g2.bainabai.trackwise.repository.SectionRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SectionService {

    private final SectionRepository repository;

    public SectionService(SectionRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public SectionResponse create(SectionRequest request) {
        Section section = new Section();
        section.setName(request.name());
        return toResponse(repository.saveAndFlush(section));
    }

    @Transactional(readOnly = true)
    public List<SectionResponse> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public SectionResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    @Transactional
    public SectionResponse update(Long id, SectionRequest request) {
        Section section = getOrThrow(id);
        section.setName(request.name());
        return toResponse(repository.saveAndFlush(section));
    }

    @Transactional
    public void delete(Long id) {
        repository.delete(getOrThrow(id));
        repository.flush();
    }

    private Section getOrThrow(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Section", id));
    }

    private SectionResponse toResponse(Section section) {
        return new SectionResponse(section.getId(), section.getName());
    }
}
