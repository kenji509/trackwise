package com.csit321g2.bainabai.trackwise.controller;

import com.csit321g2.bainabai.trackwise.dto.TopicRequest;
import com.csit321g2.bainabai.trackwise.dto.TopicResponse;
import com.csit321g2.bainabai.trackwise.service.TopicService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/topics")
public class TopicController {

    private final TopicService service;

    public TopicController(TopicService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TopicResponse create(@Valid @RequestBody TopicRequest request) {
        return service.create(request);
    }

    @GetMapping
    public List<TopicResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public TopicResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public TopicResponse update(@PathVariable Long id, @Valid @RequestBody TopicRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
