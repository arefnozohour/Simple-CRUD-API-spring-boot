package com.semtrio.TestTask;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.semtrio.TestTask.data.request.ReqPostData;
import com.semtrio.TestTask.data.request.ReqTodoData;
import com.semtrio.TestTask.data.request.ReqUserData;
import com.semtrio.TestTask.domain.Post;
import com.semtrio.TestTask.domain.Todo;
import com.semtrio.TestTask.domain.User;
import com.semtrio.TestTask.exception.ServiceException;
import com.semtrio.TestTask.service.interfaces.PostService;
import com.semtrio.TestTask.service.interfaces.TodoService;
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
public class TodoApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;
    @Autowired
    private TodoService todoService;

    private final String accessToken="Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTY2NjUxNjM3OSwiaWF0IjoxNjU3ODc2Mzc5fQ.2pZemiZIFJe2bsEnnd8WnDFaodnTcAU84fb7OtrdMS4";
    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @Order(1)
    public void addTodo() throws Exception{

        ReqTodoData todoData=objectMapper.readValue(new ClassPathResource(
                "data/ReqTodoData1.json").getFile(), ReqTodoData.class);
        try {
            userService.getById(todoData.getUserId());
        }catch (ServiceException e)
        {
            User user=userService.add(objectMapper.readValue(new ClassPathResource(
                    "data/ReqUserData1.json").getFile(), ReqUserData.class));
            todoData.setUserId(user.getId());
        }
        ResultActions response = mockMvc.perform(post("/todos")
                .header("Authorization",accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(todoData)));
        response.andDo(print()).
                andExpect(status().isOk())
                .andExpect(jsonPath("$.userId",
                        is(todoData.getUserId())))
                .andExpect(jsonPath("$.completed",
                        is(todoData.getCompleted())))
                .andExpect(jsonPath("$.title",
                        is(todoData.getTitle())));
    }

    @Test
    @Order(2)
    public void editTodo() throws Exception{

        ReqTodoData todoData=objectMapper.readValue(new ClassPathResource(
                "data/ReqTodoData1.json").getFile(),ReqTodoData.class);
        todoData.setTitle("title test");
        ResultActions response = mockMvc.perform(put("/todos/1")
                .header("Authorization",accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(todoData)));
        response.andDo(print()).
                andExpect(status().isOk())
                .andExpect(jsonPath("$.userId",
                        is(todoData.getUserId())))
                .andExpect(jsonPath("$.completed",
                        is(todoData.getCompleted())))
                .andExpect(jsonPath("$.title",
                        is(todoData.getTitle())));
    }
    @Test
    @Order(3)
    public void getAllTodo() throws Exception{

        List<Todo> todos=todoService.getAll(null);
        ResultActions response = mockMvc.perform(get("/todos")
                .contentType(MediaType.APPLICATION_JSON));
        response.andDo(print()).
                andExpect(status().isOk())
                .andExpect(jsonPath("$.size()",
                        is(todos.size())));
    }
    @Test
    @Order(4)
    public void getTodo() throws Exception{

        Todo todo=todoService.getById(1);
        ResultActions response = mockMvc.perform(get("/todos/1")
                .contentType(MediaType.APPLICATION_JSON));response.andDo(print()).
                andExpect(status().isOk())
                .andExpect(jsonPath("$.userId",
                        is(todo.getUser().getId())))
                .andExpect(jsonPath("$.completed",
                        is(todo.getCompleted())))
                .andExpect(jsonPath("$.title",
                        is(todo.getTitle())));
    }
    @Test
    @Order(5)
    public void deleteTodo() throws Exception{

        ResultActions response = mockMvc.perform(delete("/todos/1")
                .header("Authorization",accessToken)
                .contentType(MediaType.APPLICATION_JSON));
        response.andDo(print()).
                andExpect(status().isOk());
    }
}
