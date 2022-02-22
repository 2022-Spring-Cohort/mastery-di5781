package com.survivingcodingbootcamp.blog.controller;

import com.survivingcodingbootcamp.blog.model.Post;
import com.survivingcodingbootcamp.blog.model.Topic;
import com.survivingcodingbootcamp.blog.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PostControllerTest {

    private PostController underTest;
    private Model model;
    private Post testPost;

    @BeforeEach
    void setUp() {
        PostRepository postRepo = mock(PostRepository.class);
        underTest = new PostController(postRepo);
        model = mock(Model.class);
        testPost = mock(Post.class);
        Optional<Post> testOptional = Optional.of(testPost);
        when(postRepo.findById(1l)).thenReturn(testOptional);

    }

    @Test
    public void displaySinglePostShouldReturnPostTemplateName(){
        String templateName = underTest.displaySinglePost(1L, model);
        assertThat(templateName).isEqualTo("single-post-template");
    }

    @Test
    public void displaySinglePostShouldAddPostToModel(){
        underTest.displaySinglePost(1L, model);
        verify(model).addAttribute("post", testPost);
    }
    @Test
    public void displaySinglePostIsMappedForTheSinglePostEndpoint() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(underTest).build();
        mockMvc.perform(get("/posts/1"))
                .andExpect(status().isOk());
    }
}
