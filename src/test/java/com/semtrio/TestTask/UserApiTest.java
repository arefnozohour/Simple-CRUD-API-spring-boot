package com.semtrio.TestTask;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.semtrio.TestTask.data.request.ReqUserData;
import com.semtrio.TestTask.domain.User;
import com.semtrio.TestTask.service.interfaces.AuthService;
import com.semtrio.TestTask.service.interfaces.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
public class UserApiTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;
    @MockBean
    private AuthService authService;

    private final String accessToken="Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTY2NjUxNjM3OSwiaWF0IjoxNjU3ODc2Mzc5fQ.2pZemiZIFJe2bsEnnd8WnDFaodnTcAU84fb7OtrdMS4";
    @Autowired
    private ObjectMapper objectMapper;



    @Test
    public void addUser() throws Exception{

        ReqUserData userData=objectMapper.readValue(new ClassPathResource(
                "data/ReqUserData1.json").getFile(),ReqUserData.class);
        User user=objectMapper.readValue(new ClassPathResource(
                "data/ReqUserData1.json").getFile(),User.class);
        user.setId(1);
        when(userService.add(userData))
                .thenReturn(user);

        ResultActions response = mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization",accessToken)
                .content(objectMapper.writeValueAsBytes(userData)));

        // then - verify the result or output using assert statements
        response.andDo(print()).
                andExpect(status().isOk())
                .andExpect(jsonPath("$.name",
                        is(userData.getName())))
                .andExpect(jsonPath("$.email",
                        is(userData.getEmail())))
                .andExpect(jsonPath("$.phone",
                        is(userData.getPhone())))
                .andExpect(jsonPath("$.username",
                        is(userData.getUsername())));
    }

    @Test
    public void editUser() throws Exception{

        ReqUserData userData=objectMapper.readValue(new ClassPathResource(
                "data/ReqUserData1.json").getFile(),ReqUserData.class);
        User user=objectMapper.readValue(new ClassPathResource(
                "data/ReqUserData1.json").getFile(),User.class);
        user.setId(1);
        when(userService.update(1,userData))
                .thenReturn(user);

        ResultActions response = mockMvc.perform(put("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization",accessToken)
                .content(objectMapper.writeValueAsBytes(userData)));

        // then - verify the result or output using assert statements
        response.andDo(print()).
                andExpect(status().isOk())
                .andExpect(jsonPath("$.name",
                        is(userData.getName())))
                .andExpect(jsonPath("$.email",
                        is(userData.getEmail())))
                .andExpect(jsonPath("$.phone",
                        is(userData.getPhone())))
                .andExpect(jsonPath("$.username",
                        is(userData.getUsername())));
    }
    @Test
    public void patchUser() throws Exception{

        ReqUserData userData=new ReqUserData();
        userData.setUsername("test");
        User user=objectMapper.readValue(new ClassPathResource(
                "data/ReqUserData1.json").getFile(),User.class);
        user.setUsername("test");
        when(userService.patch(1,userData))
                .thenReturn(user);

        ResultActions response = mockMvc.perform(patch("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization",accessToken)
                .content(objectMapper.writeValueAsBytes(userData)));

        // then - verify the result or output using assert statements
        response.andDo(print()).
                andExpect(status().isOk())
                .andExpect(jsonPath("$.username",
                        is(userData.getUsername())));
    }
    @Test
    public void deleteUser() throws Exception{



        willDoNothing().given(userService).delete(1);
        ResultActions response = mockMvc.perform(delete("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization",accessToken)
        );
        // then - verify the result or output using assert statements
        response.andDo(print()).
                andExpect(status().isOk());
    }

    @Test
    public void getAllUsers() throws Exception{
        List<User> userList = new ArrayList<>();
        userList.add(objectMapper.readValue(new ClassPathResource(
                "data/user1.json").getFile(),User.class));
        given(userService.getAll()).willReturn(userList);

        ResultActions response = mockMvc.perform(get("/users"));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(userList.size())));

    }
}
