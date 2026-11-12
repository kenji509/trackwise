package com.csit321g2.bainabai.trackwise.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csit321g2.bainabai.trackwise.entity.ChoiceEntity;
import com.csit321g2.bainabai.trackwise.entity.QuestionEntity;
import com.csit321g2.bainabai.trackwise.repository.ChoiceRepository;
import com.csit321g2.bainabai.trackwise.repository.QuestionRepository;

@Service
public class ChoiceService {

    @Autowired
    ChoiceRepository repo;

    @Autowired
    QuestionRepository questionRepo;

    public ChoiceService() {
    }

    public ChoiceEntity postChoice(ChoiceEntity choice) {
        if (choice.getQuestion() == null) {
            throw new IllegalArgumentException("Question is required!");
        }
        int questionId = choice.getQuestion().getQuestionId();
        QuestionEntity question = questionRepo.findById(questionId).orElseThrow(() -> new NoSuchElementException("Question " + questionId + " does not exist!"));
        choice.setQuestion(question);
        return repo.save(choice);
    }

    public List<ChoiceEntity> getAllChoices() {
        return repo.findAll();
    }

    public ChoiceEntity getChoice(int id) {
        return repo.findById(id).orElseThrow(() -> new NoSuchElementException("Choice " + id + " does not exist!"));
    }

    public ChoiceEntity putChoice(int id, ChoiceEntity newDetails) {
        ChoiceEntity choice = repo.findById(id).orElseThrow(() -> new NoSuchElementException("Choice " + id + " does not exist!"));
        if (newDetails.getQuestion() == null) {
            throw new IllegalArgumentException("Question is required!");
        }
        int questionId = newDetails.getQuestion().getQuestionId();
        QuestionEntity question = questionRepo.findById(questionId).orElseThrow(() -> new NoSuchElementException("Question " + questionId + " does not exist!"));
        choice.setQuestion(question);
        choice.setChoiceText(newDetails.getChoiceText());
        choice.setIsCorrect(newDetails.getIsCorrect());
        return repo.save(choice);
    }

    public String deleteChoice(int id) {
        if (!repo.existsById(id)) {
            throw new NoSuchElementException("Choice " + id + " does not exist!");
        }
        repo.deleteById(id);
        return "Choice " + id + " is successfully deleted!";
    }

}
