package com.semtrio.TestTask;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.semtrio.TestTask.data.request.ReqAlbumData;
import com.semtrio.TestTask.data.request.ReqLoginData;
import com.semtrio.TestTask.data.request.ReqUserData;
import com.semtrio.TestTask.data.response.ResAlbumData;
import com.semtrio.TestTask.data.response.ResLoginData;
import com.semtrio.TestTask.domain.Album;
import com.semtrio.TestTask.domain.User;
import com.semtrio.TestTask.exception.ServiceException;
import com.semtrio.TestTask.service.interfaces.AlbumService;
import com.semtrio.TestTask.service.interfaces.AuthService;
import com.semtrio.TestTask.service.interfaces.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.xml.ws.Service;
import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AlbumApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;
    @Autowired
    private AlbumService albumService;

    private final String accessToken="Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTY2NjUxNjM3OSwiaWF0IjoxNjU3ODc2Mzc5fQ.2pZemiZIFJe2bsEnnd8WnDFaodnTcAU84fb7OtrdMS4";
    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @Order(1)
    public void addAlbum() throws Exception{

        ReqAlbumData albumData=objectMapper.readValue(new ClassPathResource(
                "data/ReqAlbumData1.json").getFile(),ReqAlbumData.class);
        try {
            userService.getById(albumData.getUserId());
        }catch (ServiceException e)
        {
            User user=userService.add(objectMapper.readValue(new ClassPathResource(
                    "data/ReqUserData1.json").getFile(),ReqUserData.class));
            albumData.setUserId(user.getId());
        }
        ResultActions response = mockMvc.perform(post("/albums")
                .header("Authorization",accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(albumData)));
        response.andDo(print()).
                andExpect(status().isOk())
                .andExpect(jsonPath("$.userId",
                        is(albumData.getUserId())))
                .andExpect(jsonPath("$.title",
                        is(albumData.getTitle())));
    }

    @Test
    @Order(2)
    public void editAlbum() throws Exception{

        ReqAlbumData albumData=objectMapper.readValue(new ClassPathResource(
                "data/ReqAlbumData1.json").getFile(),ReqAlbumData.class);
        albumData.setTitle("title test");
        ResultActions response = mockMvc.perform(put("/albums/1")
                .header("Authorization",accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(albumData)));
        response.andDo(print()).
                andExpect(status().isOk())
                .andExpect(jsonPath("$.userId",
                        is(albumData.getUserId())))
                .andExpect(jsonPath("$.title",
                        is(albumData.getTitle())));
    }
    @Test
    @Order(3)
    public void getAllAlbum() throws Exception{

        List<Album> albums=albumService.getAll(null);
        ResultActions response = mockMvc.perform(get("/albums")
                .contentType(MediaType.APPLICATION_JSON));
        response.andDo(print()).
                andExpect(status().isOk())
                .andExpect(jsonPath("$.size()",
                        is(albums.size())));
    }
    @Test
    @Order(4)
    public void getAlbum() throws Exception{

        Album album=albumService.getById(1);
        ResultActions response = mockMvc.perform(get("/albums/1")
                .contentType(MediaType.APPLICATION_JSON));response.andDo(print()).
                andExpect(status().isOk())
                .andExpect(jsonPath("$.userId",
                        is(album.getUser().getId())))
                .andExpect(jsonPath("$.title",
                        is(album.getTitle())));
    }
    @Test
    @Order(5)
    public void deleteAlbum() throws Exception{

        ResultActions response = mockMvc.perform(delete("/albums/1")
                .header("Authorization",accessToken)
                .contentType(MediaType.APPLICATION_JSON));
        response.andDo(print()).
                andExpect(status().isOk());
    }
}
