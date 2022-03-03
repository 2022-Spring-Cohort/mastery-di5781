package com.survivingcodingbootcamp.blog.controller;

import com.survivingcodingbootcamp.blog.model.Hashtag;
import com.survivingcodingbootcamp.blog.model.Post;
import com.survivingcodingbootcamp.blog.repository.HashtagRepository;
import com.survivingcodingbootcamp.blog.repository.PostRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.Optional;

@Controller
public class HashtagController {
    private HashtagRepository hashtagRepo;
    private PostRepository postRepo;

    public HashtagController(HashtagRepository hashtagRepo, PostRepository postRepo) {
        this.hashtagRepo = hashtagRepo;
        this.postRepo = postRepo;
    }

    @RequestMapping("/hashtags")
    public String showAllHashtags(Model theModel) {

        theModel.addAttribute("hashtags", hashtagRepo.findAll());

        return "all-hashtags-template";
    }

    @RequestMapping("/postwithinahastag/{hashtagId}")
    public String showAllPostsWithInTheHashtag(Model model, @PathVariable long hashtagId) {

        Optional<Hashtag> tempHashtag = hashtagRepo.findById(hashtagId);
        if (tempHashtag.isPresent()) {
            model.addAttribute("postsInHashtag", tempHashtag.get());
            model.addAttribute("theHashtag", tempHashtag.get().getHashtagContent());
        }
        return "single-hashtag-template";
    }

    @PostMapping("/submitHashtag")
    public String submitHashtag(@RequestParam String hashtagContent, @RequestParam long postId) {

        if (hashtagContent.isEmpty()) {
            return "errorPage-hashtag";     // no empty hashtagContent
        }
        else {
            if (!hashtagContent.contains("#")) {
                hashtagContent = "#" + hashtagContent;
            }
        }
        Post thePost = postRepo.findById(postId).get();

        Optional<Hashtag> tempHashtag = hashtagRepo.findByHashtagContentIgnoreCase(hashtagContent);
        if (tempHashtag.isPresent()) {
            if(!tempHashtag.get().containsPost(thePost)) {
                tempHashtag.get().addPost(thePost);
                hashtagRepo.save(tempHashtag.get());
            }
        }
        else {
            Hashtag theHashtag = new Hashtag(hashtagContent, thePost);
            hashtagRepo.save(theHashtag);
        }
        return "redirect:/hashtags";
    }

    @PostMapping("/submitComment")
    public String addComment(@RequestParam String postComment, @RequestParam long postId) {

        if (postComment.isEmpty()) {
            return "errorPage-comment";
        }

        Post tempPost = postRepo.findById(postId).get();

        if (tempPost.getPostComments().contains(postComment.toLowerCase())) {
        }
        else {
            tempPost.addPostComments(postComment.toLowerCase());
            postRepo.save(tempPost);
        }
        return "redirect:/posts/" + postId ;
    }

}
