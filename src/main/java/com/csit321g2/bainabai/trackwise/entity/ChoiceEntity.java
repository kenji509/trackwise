package com.csit321g2.bainabai.trackwise.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "choice")
public class ChoiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int choiceId;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String choiceText;

    @Column(nullable = false)
    private boolean isCorrect;

    @ManyToOne(optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "question_id", nullable = false)
    private QuestionEntity question;

    public ChoiceEntity() {
    }

    public ChoiceEntity(String choiceText, boolean isCorrect, QuestionEntity question) {
        this.choiceText = choiceText;
        this.isCorrect = isCorrect;
        this.question = question;
    }

    public int getChoiceId() {
        return choiceId;
    }

    public String getChoiceText() {
        return choiceText;
    }

    public void setChoiceText(String choiceText) {
        this.choiceText = choiceText;
    }

    public boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public QuestionEntity getQuestion() {
        return question;
    }

    public void setQuestion(QuestionEntity question) {
        this.question = question;
    }
}
