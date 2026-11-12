package com.csit321g2.bainabai.trackwise.service;

import com.csit321g2.bainabai.trackwise.dto.ChoiceRequest;
import com.csit321g2.bainabai.trackwise.dto.ChoiceResponse;
import com.csit321g2.bainabai.trackwise.entity.Choice;
import com.csit321g2.bainabai.trackwise.entity.Question;
import com.csit321g2.bainabai.trackwise.exception.ResourceNotFoundException;
import com.csit321g2.bainabai.trackwise.repository.ChoiceRepository;
import com.csit321g2.bainabai.trackwise.repository.QuestionRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChoiceService {

    private final ChoiceRepository choiceRepository;
    private final QuestionRepository questionRepository;

    public ChoiceService(ChoiceRepository choiceRepository, QuestionRepository questionRepository) {
        this.choiceRepository = choiceRepository;
        this.questionRepository = questionRepository;
    }

    @Transactional
    public ChoiceResponse create(ChoiceRequest request) {
        Choice choice = new Choice();
        apply(choice, request);
        return toResponse(choiceRepository.saveAndFlush(choice));
    }

    @Transactional(readOnly = true)
    public List<ChoiceResponse> findAll() {
        return choiceRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public ChoiceResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    @Transactional
    public ChoiceResponse update(Long id, ChoiceRequest request) {
        Choice choice = getOrThrow(id);
        apply(choice, request);
        return toResponse(choiceRepository.saveAndFlush(choice));
    }

    @Transactional
    public void delete(Long id) {
        choiceRepository.delete(getOrThrow(id));
        choiceRepository.flush();
    }

    private void apply(Choice choice, ChoiceRequest request) {
        Question question = questionRepository.findById(request.questionId())
                .orElseThrow(() -> new ResourceNotFoundException("Question", request.questionId()));
        choice.setQuestion(question);
        choice.setChoiceText(request.choiceText());
        choice.setCorrect(request.isCorrect());
    }

    private Choice getOrThrow(Long id) {
        return choiceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Choice", id));
    }

    private ChoiceResponse toResponse(Choice choice) {
        return new ChoiceResponse(choice.getId(), choice.getQuestion().getId(),
                choice.getChoiceText(), choice.isCorrect());
    }
}
