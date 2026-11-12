package com.csit321g2.bainabai.trackwise.service;

import com.csit321g2.bainabai.trackwise.dto.TopicRequest;
import com.csit321g2.bainabai.trackwise.dto.TopicResponse;
import com.csit321g2.bainabai.trackwise.entity.Topic;
import com.csit321g2.bainabai.trackwise.exception.ResourceNotFoundException;
import com.csit321g2.bainabai.trackwise.repository.TopicRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TopicService {

    private final TopicRepository repository;

    public TopicService(TopicRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public TopicResponse create(TopicRequest request) {
        Topic topic = new Topic();
        topic.setName(request.name());
        return toResponse(repository.saveAndFlush(topic));
    }

    @Transactional(readOnly = true)
    public List<TopicResponse> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public TopicResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    @Transactional
    public TopicResponse update(Long id, TopicRequest request) {
        Topic topic = getOrThrow(id);
        topic.setName(request.name());
        return toResponse(repository.saveAndFlush(topic));
    }

    @Transactional
    public void delete(Long id) {
        repository.delete(getOrThrow(id));
        repository.flush();
    }

    private Topic getOrThrow(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Topic", id));
    }

    private TopicResponse toResponse(Topic topic) {
        return new TopicResponse(topic.getId(), topic.getName());
    }
}
