package com.csit321g2.bainabai.trackwise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.csit321g2.bainabai.trackwise.entity.SectionEntity;

@Repository
public interface SectionRepository extends JpaRepository<SectionEntity, Integer> {

    boolean existsByNameIgnoreCase(String name);
}
