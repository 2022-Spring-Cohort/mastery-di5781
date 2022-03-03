package com.survivingcodingbootcamp.blog.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Post {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    @ManyToOne
    private Topic topic;
    private String author;
    @Lob
    private String content;
    private LocalDateTime dateTime;
    @ElementCollection
    private Collection<String> postComments;

    @ManyToMany(mappedBy = "posts")
    private Collection<Hashtag> hashtags;

    protected Post() {
    }

    public Post(String title, Topic topic, String author ,String content) {
        this.title = title;
        this.topic = topic;
        this.author = author;
        this.content = content;
        this.dateTime = LocalDateTime.now();
        this.postComments = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Topic getTopic() {
        return topic;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Collection<Hashtag> getHashtags() {
        return hashtags;
    }

    public Collection<String> getPostComments() {
        return postComments;
    }

    public void addPostComments(String postComment) {
        postComments.add(postComment);
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", topic=" + topic +  '\'' +
                ", author=" + author +  '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Post post = (Post) o;

        if (id != null ? !id.equals(post.id) : post.id != null) return false;
        if (title != null ? !title.equals(post.title) : post.title != null) return false;
        if (topic != null ? !topic.equals(post.topic) : post.topic != null) return false;
        if (author != null ? !author.equals(post.author) : post.author != null) return false;
        return content != null ? content.equals(post.content) : post.content == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (topic != null ? topic.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }



}
