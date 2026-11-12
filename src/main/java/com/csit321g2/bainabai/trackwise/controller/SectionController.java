package com.csit321g2.bainabai.trackwise.controller;

import com.csit321g2.bainabai.trackwise.dto.SectionRequest;
import com.csit321g2.bainabai.trackwise.dto.SectionResponse;
import com.csit321g2.bainabai.trackwise.service.SectionService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sections")
public class SectionController {

    private final SectionService service;

    public SectionController(SectionService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SectionResponse create(@Valid @RequestBody SectionRequest request) {
        return service.create(request);
    }

    @GetMapping
    public List<SectionResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public SectionResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public SectionResponse update(@PathVariable Long id, @Valid @RequestBody SectionRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
