package com.survivingcodingbootcamp.blog.integration;

import com.survivingcodingbootcamp.blog.controller.HomeController;
import com.survivingcodingbootcamp.blog.controller.TopicController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class BlogApplicationSmokeTests {
    @Autowired
    private HomeController homeController;
    @Autowired
    private TopicController topicController;

    @Test
    void contextLoads() {
    }

    @Test
    void beanCreation() {
        assertNotNull(homeController);
        assertNotNull(topicController);

    }
}
