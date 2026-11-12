package com.csit321g2.bainabai.trackwise.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "section")
public class SectionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sectionId;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    public SectionEntity() {
    }

    public SectionEntity(String name) {
        this.name = name;
    }

    public int getSectionId() {
        return sectionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
