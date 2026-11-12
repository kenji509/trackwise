package com.csit321g2.bainabai.trackwise.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.csit321g2.bainabai.trackwise.entity.SectionEntity;
import com.csit321g2.bainabai.trackwise.service.NameErrorResponse;
import com.csit321g2.bainabai.trackwise.service.SectionService;

@RestController
@RequestMapping("/section/api")
@CrossOrigin(origins = "http://localhost:3000")
public class SectionController {

    @Autowired
    SectionService service;

    public SectionController() {
    }

    @GetMapping("/printAuthor")
    public String printAuthor() {
        return "Kenji Uehara and Jude Ralph A. Cayomo";
    }

    @PostMapping("/postNewSection")
    public SectionEntity postSection(@RequestBody SectionEntity section) {
        return service.postSection(section);
    }

    @GetMapping("/getAllSections")
    public List<SectionEntity> getAllSections() {
        return service.getAllSections();
    }

    @GetMapping("/getSection/{id}")
    public SectionEntity getSection(@PathVariable int id) {
        return service.getSection(id);
    }

    @PutMapping("/putSection")
    public SectionEntity putSection(@RequestParam int id, @RequestBody SectionEntity newDetails) {
        return service.putSection(id, newDetails);
    }

    @DeleteMapping("/deleteSection/{id}")
    public String deleteSection(@PathVariable int id) {
        return service.deleteSection(id);
    }

    @ExceptionHandler
    public ResponseEntity<NameErrorResponse> handleException(NoSuchElementException ex) {
        NameErrorResponse error = new NameErrorResponse();
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(ex.getMessage());
        error.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<NameErrorResponse> handleException(Exception ex) {
        NameErrorResponse error = new NameErrorResponse();
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(ex.getMessage());
        error.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}
