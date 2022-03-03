package com.survivingcodingbootcamp.blog.controller;

import com.survivingcodingbootcamp.blog.model.Topic;
import com.survivingcodingbootcamp.blog.repository.TopicRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/topics")
public class TopicController {

    private TopicRepository topicRepo;

    public TopicController(TopicRepository topicRepo) {

        this.topicRepo = topicRepo;
    }
    @GetMapping("/{id}")
    public String displaySingleTopic(@PathVariable long id, Model model) {
        model.addAttribute("topic", topicRepo.findById(id).get());
        return "single-topic-template";
    }

    @PostMapping("/submitTopic")
    public String addTopic(@RequestParam String topicName) {
        if (topicName.isEmpty()) {
            return "errorPage-topic";
        }

        Optional<Topic> tempTopic = topicRepo.findByNameIgnoreCase(topicName);
        if (!tempTopic.isPresent()) {
            // not existed, add the topic
            Topic theTopic = new Topic(topicName);
            topicRepo.save(theTopic);
        }
        return "redirect:/";
    }
}
