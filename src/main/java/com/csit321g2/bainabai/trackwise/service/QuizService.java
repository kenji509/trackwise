package com.csit321g2.bainabai.trackwise.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csit321g2.bainabai.trackwise.entity.QuizEntity;
import com.csit321g2.bainabai.trackwise.entity.Role;
import com.csit321g2.bainabai.trackwise.entity.UserEntity;
import com.csit321g2.bainabai.trackwise.repository.QuizRepository;
import com.csit321g2.bainabai.trackwise.repository.UserRepository;

@Service
public class QuizService {

    @Autowired
    QuizRepository repo;

    @Autowired
    UserRepository userRepo;

    public QuizService() {
    }

    public QuizEntity postQuiz(QuizEntity quiz) {
        if (quiz.getTeacher() == null) {
            throw new IllegalArgumentException("Teacher is required!");
        }
        int teacherId = quiz.getTeacher().getUserId();
        UserEntity teacher = userRepo.findById(teacherId).orElseThrow(() -> new NoSuchElementException("User " + teacherId + " does not exist!"));
        if (teacher.getRole() != Role.TEACHER) {
            throw new IllegalArgumentException("User " + teacherId + " is not a teacher!");
        }
        quiz.setTeacher(teacher);
        return repo.save(quiz);
    }

    public List<QuizEntity> getAllQuizzes() {
        return repo.findAll();
    }

    public QuizEntity putQuiz(int id, QuizEntity newDetails) {
        QuizEntity quiz = repo.findById(id).orElseThrow(() -> new NoSuchElementException("Quiz " + id + " does not exist!"));
        if (newDetails.getTeacher() == null) {
            throw new IllegalArgumentException("Teacher is required!");
        }
        int teacherId = newDetails.getTeacher().getUserId();
        UserEntity teacher = userRepo.findById(teacherId).orElseThrow(() -> new NoSuchElementException("User " + teacherId + " does not exist!"));
        if (teacher.getRole() != Role.TEACHER) {
            throw new IllegalArgumentException("User " + teacherId + " is not a teacher!");
        }
        quiz.setTeacher(teacher);
        quiz.setTitle(newDetails.getTitle());
        quiz.setDescription(newDetails.getDescription());
        quiz.setTimeLimitMin(newDetails.getTimeLimitMin());
        return repo.save(quiz);
    }

    public String deleteQuiz(int id) {
        if (!repo.existsById(id)) {
            throw new NoSuchElementException("Quiz " + id + " does not exist!");
        }
        repo.deleteById(id);
        return "Quiz " + id + " is successfully deleted!";
    }

}
