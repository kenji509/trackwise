package com.csit321g2.bainabai.trackwise.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csit321g2.bainabai.trackwise.entity.TopicEntity;
import com.csit321g2.bainabai.trackwise.repository.TopicRepository;

@Service
public class TopicService {

    @Autowired
    TopicRepository repo;

    public TopicService() {
    }

    public TopicEntity postTopic(TopicEntity topic) {
        if (topic.getName() != null && repo.existsByNameIgnoreCase(topic.getName())) {
            throw new IllegalArgumentException("Topic " + topic.getName() + " already exists!");
        }
        return repo.save(topic);
    }

    public List<TopicEntity> getAllTopics() {
        return repo.findAll();
    }

    public TopicEntity getTopic(int id) {
        return repo.findById(id).orElseThrow(() -> new NoSuchElementException("Topic " + id + " does not exist!"));
    }

    public TopicEntity putTopic(int id, TopicEntity newDetails) {
        TopicEntity topic = repo.findById(id).orElseThrow(() -> new NoSuchElementException("Topic " + id + " does not exist!"));
        if (newDetails.getName() != null && !newDetails.getName().equalsIgnoreCase(topic.getName())
                && repo.existsByNameIgnoreCase(newDetails.getName())) {
            throw new IllegalArgumentException("Topic " + newDetails.getName() + " already exists!");
        }
        topic.setName(newDetails.getName());
        return repo.save(topic);
    }

    public String deleteTopic(int id) {
        if (!repo.existsById(id)) {
            throw new NoSuchElementException("Topic " + id + " does not exist!");
        }
        repo.deleteById(id);
        return "Topic " + id + " is successfully deleted!";
    }

}
