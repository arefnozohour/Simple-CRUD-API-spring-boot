package com.semtrio.TestTask;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.semtrio.TestTask.data.request.ReqCommentData;
import com.semtrio.TestTask.data.request.ReqPostData;
import com.semtrio.TestTask.data.request.ReqUserData;
import com.semtrio.TestTask.domain.Comment;
import com.semtrio.TestTask.domain.Post;
import com.semtrio.TestTask.domain.User;
import com.semtrio.TestTask.exception.ServiceException;
import com.semtrio.TestTask.service.interfaces.CommentService;
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
public class CommentApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;

    private final String accessToken="Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTY2NjUxNjM3OSwiaWF0IjoxNjU3ODc2Mzc5fQ.2pZemiZIFJe2bsEnnd8WnDFaodnTcAU84fb7OtrdMS4";
    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @Order(1)
    public void addComment() throws Exception{

        ReqCommentData commentData=objectMapper.readValue(new ClassPathResource(
                "data/ReqCommentData1.json").getFile(),ReqCommentData.class);
        try {
            postService.getById(commentData.getPostId());
        }
        catch (ServiceException e)
        {
            ReqPostData postData=objectMapper.readValue(new ClassPathResource(
                    "data/ReqPostData1.json").getFile(),ReqPostData.class);
            try {
                userService.getById(postData.getUserId());
            }catch (ServiceException exception)
            {
                User user=userService.add(objectMapper.readValue(new ClassPathResource(
                        "data/ReqUserData1.json").getFile(), ReqUserData.class));
                postData.setUserId(user.getId());
            }
            Post post=postService.add(postData);
            commentData.setPostId(post.getId());
        }
        ResultActions response = mockMvc.perform(post("/comments")
                .header("Authorization",accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(commentData)));
        response.andDo(print()).
                andExpect(status().isOk())
                .andExpect(jsonPath("$.postId",
                        is(commentData.getPostId())))
                .andExpect(jsonPath("$.body",
                        is(commentData.getBody())))
                .andExpect(jsonPath("$.email",
                        is(commentData.getEmail())))
                .andExpect(jsonPath("$.name",
                        is(commentData.getName())));
    }

    @Test
    @Order(2)
    public void editComment() throws Exception{

        ReqCommentData commentData=objectMapper.readValue(new ClassPathResource(
                "data/ReqCommentData1.json").getFile(),ReqCommentData.class);
        commentData.setEmail("test@test.com");
        ResultActions response = mockMvc.perform(put("/comments/1")
                .header("Authorization",accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(commentData)));
        response.andDo(print()).
                andExpect(status().isOk())
                .andExpect(jsonPath("$.postId",
                        is(commentData.getPostId())))
                .andExpect(jsonPath("$.body",
                        is(commentData.getBody())))
                .andExpect(jsonPath("$.email",
                        is(commentData.getEmail())))
                .andExpect(jsonPath("$.name",
                        is(commentData.getName())));
    }
    @Test
    @Order(3)
    public void getAllComment() throws Exception{

        List<Comment> comments=commentService.getAll(null);
        ResultActions response = mockMvc.perform(get("/comments")
                .contentType(MediaType.APPLICATION_JSON));
        response.andDo(print()).
                andExpect(status().isOk())
                .andExpect(jsonPath("$.size()",
                        is(comments.size())));
    }
    @Test
    @Order(4)
    public void getComment() throws Exception{

        Comment comment=commentService.getById(1);
        ResultActions response = mockMvc.perform(get("/comments/1")
                .contentType(MediaType.APPLICATION_JSON));response.andDo(print());

        response.andDo(print()).
                andExpect(status().isOk())
                .andExpect(jsonPath("$.postId",
                        is(comment.getPost().getId())))
                .andExpect(jsonPath("$.body",
                        is(comment.getBody())))
                .andExpect(jsonPath("$.email",
                        is(comment.getEmail())))
                .andExpect(jsonPath("$.name",
                        is(comment.getName())));
    }
    @Test
    @Order(5)
    public void deleteComment() throws Exception{

        ResultActions response = mockMvc.perform(delete("/comments/1")
                .header("Authorization",accessToken)
                .contentType(MediaType.APPLICATION_JSON));
        response.andDo(print()).
                andExpect(status().isOk());
    }
}
