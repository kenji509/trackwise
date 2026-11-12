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

import com.csit321g2.bainabai.trackwise.entity.QuestionEntity;
import com.csit321g2.bainabai.trackwise.service.NameErrorResponse;
import com.csit321g2.bainabai.trackwise.service.QuestionService;

@RestController
@RequestMapping("/question/api")
@CrossOrigin(origins = "http://localhost:3000")
public class QuestionController {

    @Autowired
    QuestionService service;

    public QuestionController() {
    }

    @GetMapping("/printAuthor")
    public String printAuthor() {
        return "Kenji Uehara and Jude Ralph A. Cayomo";
    }

    @PostMapping("/postNewQuestion")
    public QuestionEntity postQuestion(@RequestBody QuestionEntity question) {
        return service.postQuestion(question);
    }

    @GetMapping("/getAllQuestions")
    public List<QuestionEntity> getAllQuestions() {
        return service.getAllQuestions();
    }

    @PutMapping("/putQuestion")
    public QuestionEntity putQuestion(@RequestParam int id, @RequestBody QuestionEntity newDetails) {
        return service.putQuestion(id, newDetails);
    }

    @DeleteMapping("/deleteQuestion/{id}")
    public String deleteQuestion(@PathVariable int id) {
        return service.deleteQuestion(id);
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
