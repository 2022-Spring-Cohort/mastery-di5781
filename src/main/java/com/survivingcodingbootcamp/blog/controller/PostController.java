package com.survivingcodingbootcamp.blog.controller;

import com.survivingcodingbootcamp.blog.model.Post;
import com.survivingcodingbootcamp.blog.model.Topic;
import com.survivingcodingbootcamp.blog.repository.PostRepository;
import com.survivingcodingbootcamp.blog.repository.TopicRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@RequestMapping("/posts")
public class PostController {
    private PostRepository postRepo;
    private TopicRepository topicRepo;
    private Post post;
    private long myPostId;

    public PostController(PostRepository postRepo, TopicRepository topicRepo) {
        this.postRepo = postRepo;
        this.topicRepo = topicRepo;
    }

    @GetMapping("/{id}")
    public String displaySinglePost(@PathVariable long id, Model model) {

        model.addAttribute("post", postRepo.findById(id).get());
        LocalDateTime myDateTime = postRepo.findById(id).get().getDateTime();
        if(myDateTime != null) {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            String formatDateTime = myDateTime.format(format);
            model.addAttribute("postDateTime", formatDateTime);
        }

        return "single-post-template";
    }

    @PostMapping("/submitPost/{topicId}")
    public String submitPost(@RequestParam String postTitle, @RequestParam String author, @RequestParam String postContent, @RequestParam long topicId) {

        if (postTitle.isEmpty() || author.isEmpty() || postContent.isEmpty()) {
            return "errorPage-addPost";
        }

        Topic theTopic = topicRepo.findById(topicId).get();
        Post thePost = new Post(postTitle, theTopic, author, postContent);
        boolean isDup = false;
        for (Post currentPost: postRepo.findAll()) {
            if (postTitle.equalsIgnoreCase(currentPost.getTitle())) {
                if (theTopic.getName().equalsIgnoreCase(currentPost.getTopic().getName())) {
                    isDup = true;
                    break;
                }
                else {
                    // add
                    isDup = false;
                }
            } else {
                isDup = false;
            }
        }
        if (isDup == false) {
            postRepo.save(thePost);
        }
        return "redirect:/topics/" + theTopic.getId();
    }

}
