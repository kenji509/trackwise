package com.csit321g2.bainabai.trackwise.service;

import com.csit321g2.bainabai.trackwise.dto.QuestionRequest;
import com.csit321g2.bainabai.trackwise.dto.QuestionResponse;
import com.csit321g2.bainabai.trackwise.entity.Question;
import com.csit321g2.bainabai.trackwise.entity.Quiz;
import com.csit321g2.bainabai.trackwise.entity.Topic;
import com.csit321g2.bainabai.trackwise.exception.ResourceNotFoundException;
import com.csit321g2.bainabai.trackwise.repository.QuestionRepository;
import com.csit321g2.bainabai.trackwise.repository.QuizRepository;
import com.csit321g2.bainabai.trackwise.repository.TopicRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final QuizRepository quizRepository;
    private final TopicRepository topicRepository;

    public QuestionService(QuestionRepository questionRepository, QuizRepository quizRepository,
                           TopicRepository topicRepository) {
        this.questionRepository = questionRepository;
        this.quizRepository = quizRepository;
        this.topicRepository = topicRepository;
    }

    @Transactional
    public QuestionResponse create(QuestionRequest request) {
        Question question = new Question();
        apply(question, request);
        return toResponse(questionRepository.saveAndFlush(question));
    }

    @Transactional(readOnly = true)
    public List<QuestionResponse> findAll() {
        return questionRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public QuestionResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    @Transactional
    public QuestionResponse update(Long id, QuestionRequest request) {
        Question question = getOrThrow(id);
        apply(question, request);
        return toResponse(questionRepository.saveAndFlush(question));
    }

    @Transactional
    public void delete(Long id) {
        questionRepository.delete(getOrThrow(id));
        questionRepository.flush();
    }

    private void apply(Question question, QuestionRequest request) {
        Quiz quiz = quizRepository.findById(request.quizId())
                .orElseThrow(() -> new ResourceNotFoundException("Quiz", request.quizId()));
        Topic topic = topicRepository.findById(request.topicId())
                .orElseThrow(() -> new ResourceNotFoundException("Topic", request.topicId()));
        question.setQuiz(quiz);
        question.setTopic(topic);
        question.setQuestionText(request.questionText());
    }

    private Question getOrThrow(Long id) {
        return questionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Question", id));
    }

    private QuestionResponse toResponse(Question question) {
        return new QuestionResponse(question.getId(), question.getQuiz().getId(),
                question.getTopic().getId(), question.getQuestionText());
    }
}
