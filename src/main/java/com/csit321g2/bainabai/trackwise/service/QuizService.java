package com.csit321g2.bainabai.trackwise.service;

import com.csit321g2.bainabai.trackwise.dto.QuizRequest;
import com.csit321g2.bainabai.trackwise.dto.QuizResponse;
import com.csit321g2.bainabai.trackwise.entity.Quiz;
import com.csit321g2.bainabai.trackwise.entity.Role;
import com.csit321g2.bainabai.trackwise.entity.User;
import com.csit321g2.bainabai.trackwise.exception.BadRequestException;
import com.csit321g2.bainabai.trackwise.exception.ResourceNotFoundException;
import com.csit321g2.bainabai.trackwise.repository.QuizRepository;
import com.csit321g2.bainabai.trackwise.repository.UserRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class QuizService {

    private final QuizRepository quizRepository;
    private final UserRepository userRepository;

    public QuizService(QuizRepository quizRepository, UserRepository userRepository) {
        this.quizRepository = quizRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public QuizResponse create(QuizRequest request) {
        Quiz quiz = new Quiz();
        apply(quiz, request);
        return toResponse(quizRepository.saveAndFlush(quiz));
    }

    @Transactional(readOnly = true)
    public List<QuizResponse> findAll() {
        return quizRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public QuizResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    @Transactional
    public QuizResponse update(Long id, QuizRequest request) {
        Quiz quiz = getOrThrow(id);
        apply(quiz, request);
        return toResponse(quizRepository.saveAndFlush(quiz));
    }

    @Transactional
    public void delete(Long id) {
        quizRepository.delete(getOrThrow(id));
        quizRepository.flush();
    }

    private void apply(Quiz quiz, QuizRequest request) {
        User teacher = userRepository.findById(request.teacherId())
                .orElseThrow(() -> new ResourceNotFoundException("User", request.teacherId()));
        if (teacher.getRole() != Role.TEACHER) {
            throw new BadRequestException("User " + teacher.getId() + " is not a TEACHER");
        }
        quiz.setTeacher(teacher);
        quiz.setTitle(request.title());
        quiz.setDescription(request.description());
        quiz.setTimeLimitMin(request.timeLimitMin());
    }

    private Quiz getOrThrow(Long id) {
        return quizRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Quiz", id));
    }

    private QuizResponse toResponse(Quiz quiz) {
        return new QuizResponse(quiz.getId(), quiz.getTeacher().getId(), quiz.getTitle(),
                quiz.getDescription(), quiz.getTimeLimitMin(), quiz.getCreatedAt());
    }
}
