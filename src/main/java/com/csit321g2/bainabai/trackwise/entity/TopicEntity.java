package com.csit321g2.bainabai.trackwise.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "topic")
public class TopicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int topicId;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    public TopicEntity() {
    }

    public TopicEntity(String name) {
        this.name = name;
    }

    public int getTopicId() {
        return topicId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
