package com.survivingcodingbootcamp.blog.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Arrays;
import java.util.Collection;

@Entity
public class Hashtag {
    @Id
    @GeneratedValue
    private long hashtagId;
    private String hashtagContent;

    @ManyToMany
    private Collection<Post> posts;

    public Hashtag() {
    }

    public Hashtag(String hashtagContent, Post...posts) {
        this.hashtagContent = hashtagContent;
        this.posts = Arrays.asList(posts);
    }

    public void addPost(Post post) {
        posts.add(post);
    }

    public long getHashtagId() {
        return hashtagId;
    }

    public String getHashtagContent() {
        return hashtagContent;
    }

    public Collection<Post> getPosts() {
        return posts;
    }

    public boolean containsPost(Post post){
        return posts.contains(post);
    }

}
