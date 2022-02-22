package com.survivingcodingbootcamp.blog.controller;

import com.survivingcodingbootcamp.blog.model.Topic;
import com.survivingcodingbootcamp.blog.repository.TopicRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class HomeControllerTest {

    private HomeController underTest;
    private Model model;
    private TopicRepository topicRepo;

    @BeforeEach
    void setUp() {
        topicRepo = mock(TopicRepository.class);
        underTest = new HomeController(topicRepo);
        model = mock(Model.class);
    }

    @Test
    public void displayHomePageReturnsHomeViewTemplate() {
        String templateName = underTest.displayHomePage(model);
        assertThat(templateName).isEqualTo("home-template");
    }

    @Test
    public void displayHomePageInteractsWithTheTopicStorageRetrievingAllTopicsAndAddingThemToTheModel() {
        Collection<Topic> retrievedTopics = Collections.EMPTY_LIST;
        when(topicRepo.findAll()).thenReturn(retrievedTopics);
        underTest.displayHomePage(model);
        verify(topicRepo).findAll();
        verify(model).addAttribute("topics", retrievedTopics);
    }

    @Test
    public void displayHomePageInteractsIsMappedForTheHomeEndpoint() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(underTest).build();
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }
}
