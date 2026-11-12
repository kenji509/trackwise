package com.csit321g2.bainabai.trackwise.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csit321g2.bainabai.trackwise.entity.QuestionEntity;
import com.csit321g2.bainabai.trackwise.entity.QuizEntity;
import com.csit321g2.bainabai.trackwise.entity.TopicEntity;
import com.csit321g2.bainabai.trackwise.repository.QuestionRepository;
import com.csit321g2.bainabai.trackwise.repository.QuizRepository;
import com.csit321g2.bainabai.trackwise.repository.TopicRepository;

@Service
public class QuestionService {

    @Autowired
    QuestionRepository repo;

    @Autowired
    QuizRepository quizRepo;

    @Autowired
    TopicRepository topicRepo;

    public QuestionService() {
    }

    public QuestionEntity postQuestion(QuestionEntity question) {
        if (question.getQuiz() == null) {
            throw new IllegalArgumentException("Quiz is required!");
        }
        int quizId = question.getQuiz().getQuizId();
        QuizEntity quiz = quizRepo.findById(quizId).orElseThrow(() -> new NoSuchElementException("Quiz " + quizId + " does not exist!"));
        if (question.getTopic() == null) {
            throw new IllegalArgumentException("Topic is required!");
        }
        int topicId = question.getTopic().getTopicId();
        TopicEntity topic = topicRepo.findById(topicId).orElseThrow(() -> new NoSuchElementException("Topic " + topicId + " does not exist!"));
        question.setQuiz(quiz);
        question.setTopic(topic);
        return repo.save(question);
    }

    public List<QuestionEntity> getAllQuestions() {
        return repo.findAll();
    }

    public QuestionEntity getQuestion(int id) {
        return repo.findById(id).orElseThrow(() -> new NoSuchElementException("Question " + id + " does not exist!"));
    }

    public QuestionEntity putQuestion(int id, QuestionEntity newDetails) {
        QuestionEntity question = repo.findById(id).orElseThrow(() -> new NoSuchElementException("Question " + id + " does not exist!"));
        if (newDetails.getQuiz() == null) {
            throw new IllegalArgumentException("Quiz is required!");
        }
        int quizId = newDetails.getQuiz().getQuizId();
        QuizEntity quiz = quizRepo.findById(quizId).orElseThrow(() -> new NoSuchElementException("Quiz " + quizId + " does not exist!"));
        if (newDetails.getTopic() == null) {
            throw new IllegalArgumentException("Topic is required!");
        }
        int topicId = newDetails.getTopic().getTopicId();
        TopicEntity topic = topicRepo.findById(topicId).orElseThrow(() -> new NoSuchElementException("Topic " + topicId + " does not exist!"));
        question.setQuiz(quiz);
        question.setTopic(topic);
        question.setQuestionText(newDetails.getQuestionText());
        return repo.save(question);
    }

    public String deleteQuestion(int id) {
        if (!repo.existsById(id)) {
            throw new NoSuchElementException("Question " + id + " does not exist!");
        }
        repo.deleteById(id);
        return "Question " + id + " is successfully deleted!";
    }

}
