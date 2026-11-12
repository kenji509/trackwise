package com.csit321g2.bainabai.trackwise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.csit321g2.bainabai.trackwise.entity.ChoiceEntity;

@Repository
public interface ChoiceRepository extends JpaRepository<ChoiceEntity, Integer> {
}
