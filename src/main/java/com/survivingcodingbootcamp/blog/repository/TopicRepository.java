package com.survivingcodingbootcamp.blog.repository;

import com.survivingcodingbootcamp.blog.model.Topic;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TopicRepository extends CrudRepository<Topic, Long> {
    public Optional<Topic> findByNameIgnoreCase(String topicName);
}
