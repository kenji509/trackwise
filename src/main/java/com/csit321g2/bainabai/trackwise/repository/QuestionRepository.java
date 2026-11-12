package com.csit321g2.bainabai.trackwise.repository;

import com.csit321g2.bainabai.trackwise.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
