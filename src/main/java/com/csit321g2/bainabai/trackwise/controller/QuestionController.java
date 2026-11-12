package com.csit321g2.bainabai.trackwise.controller;

import com.csit321g2.bainabai.trackwise.dto.QuestionRequest;
import com.csit321g2.bainabai.trackwise.dto.QuestionResponse;
import com.csit321g2.bainabai.trackwise.service.QuestionService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    private final QuestionService service;

    public QuestionController(QuestionService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public QuestionResponse create(@Valid @RequestBody QuestionRequest request) {
        return service.create(request);
    }

    @GetMapping
    public List<QuestionResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public QuestionResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public QuestionResponse update(@PathVariable Long id, @Valid @RequestBody QuestionRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
