package com.semtrio.TestTask;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.semtrio.TestTask.data.request.ReqAlbumData;
import com.semtrio.TestTask.data.request.ReqPostData;
import com.semtrio.TestTask.data.request.ReqUserData;
import com.semtrio.TestTask.domain.Album;
import com.semtrio.TestTask.domain.Post;
import com.semtrio.TestTask.domain.User;
import com.semtrio.TestTask.exception.ServiceException;
import com.semtrio.TestTask.service.interfaces.AlbumService;
import com.semtrio.TestTask.service.interfaces.PostService;
import com.semtrio.TestTask.service.interfaces.UserService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PostApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;

    private final String accessToken="Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTY2NjUxNjM3OSwiaWF0IjoxNjU3ODc2Mzc5fQ.2pZemiZIFJe2bsEnnd8WnDFaodnTcAU84fb7OtrdMS4";
    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @Order(1)
    public void addPost() throws Exception{

        ReqPostData postData=objectMapper.readValue(new ClassPathResource(
                "data/ReqPostData1.json").getFile(),ReqPostData.class);
        try {
            userService.getById(postData.getUserId());
        }catch (ServiceException e)
        {
            User user=userService.add(objectMapper.readValue(new ClassPathResource(
                    "data/ReqUserData1.json").getFile(), ReqUserData.class));
            postData.setUserId(user.getId());
        }
        ResultActions response = mockMvc.perform(post("/posts")
                .header("Authorization",accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postData)));
        response.andDo(print()).
                andExpect(status().isOk())
                .andExpect(jsonPath("$.userId",
                        is(postData.getUserId())))
                .andExpect(jsonPath("$.body",
                        is(postData.getBody())))
                .andExpect(jsonPath("$.title",
                        is(postData.getTitle())));
    }

    @Test
    @Order(2)
    public void editPost() throws Exception{

        ReqPostData postData=objectMapper.readValue(new ClassPathResource(
                "data/ReqPostData1.json").getFile(),ReqPostData.class);
        postData.setTitle("title test");
        ResultActions response = mockMvc.perform(put("/posts/1")
                .header("Authorization",accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postData)));
        response.andDo(print()).
                andExpect(status().isOk())
                .andExpect(jsonPath("$.userId",
                        is(postData.getUserId())))
                .andExpect(jsonPath("$.body",
                        is(postData.getBody())))
                .andExpect(jsonPath("$.title",
                        is(postData.getTitle())));
    }
    @Test
    @Order(3)
    public void getAllPost() throws Exception{

        List<Post> posts=postService.getAll(null);
        ResultActions response = mockMvc.perform(get("/posts")
                .contentType(MediaType.APPLICATION_JSON));
        response.andDo(print()).
                andExpect(status().isOk())
                .andExpect(jsonPath("$.size()",
                        is(posts.size())));
    }
    @Test
    @Order(4)
    public void getPost() throws Exception{

        Post post=postService.getById(1);
        ResultActions response = mockMvc.perform(get("/posts/1")
                .contentType(MediaType.APPLICATION_JSON));response.andDo(print()).
                andExpect(status().isOk())
                .andExpect(jsonPath("$.userId",
                        is(post.getUser().getId())))
                .andExpect(jsonPath("$.body",
                        is(post.getBody())))
                .andExpect(jsonPath("$.title",
                        is(post.getTitle())));
    }
    @Test
    @Order(5)
    public void deletePost() throws Exception{

        ResultActions response = mockMvc.perform(delete("/posts/1")
                .header("Authorization",accessToken)
                .contentType(MediaType.APPLICATION_JSON));
        response.andDo(print()).
                andExpect(status().isOk());
    }
}
