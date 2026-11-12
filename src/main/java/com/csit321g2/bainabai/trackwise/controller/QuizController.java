package com.csit321g2.bainabai.trackwise.controller;

import com.csit321g2.bainabai.trackwise.dto.QuizRequest;
import com.csit321g2.bainabai.trackwise.dto.QuizResponse;
import com.csit321g2.bainabai.trackwise.service.QuizService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    private final QuizService service;

    public QuizController(QuizService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public QuizResponse create(@Valid @RequestBody QuizRequest request) {
        return service.create(request);
    }

    @GetMapping
    public List<QuizResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public QuizResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public QuizResponse update(@PathVariable Long id, @Valid @RequestBody QuizRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
