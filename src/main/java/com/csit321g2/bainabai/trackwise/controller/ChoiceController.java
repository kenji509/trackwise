package com.csit321g2.bainabai.trackwise.controller;

import com.csit321g2.bainabai.trackwise.dto.ChoiceRequest;
import com.csit321g2.bainabai.trackwise.dto.ChoiceResponse;
import com.csit321g2.bainabai.trackwise.service.ChoiceService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/choices")
public class ChoiceController {

    private final ChoiceService service;

    public ChoiceController(ChoiceService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ChoiceResponse create(@Valid @RequestBody ChoiceRequest request) {
        return service.create(request);
    }

    @GetMapping
    public List<ChoiceResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ChoiceResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public ChoiceResponse update(@PathVariable Long id, @Valid @RequestBody ChoiceRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
