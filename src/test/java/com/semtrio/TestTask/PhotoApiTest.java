package com.semtrio.TestTask;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.semtrio.TestTask.data.request.*;
import com.semtrio.TestTask.domain.*;
import com.semtrio.TestTask.exception.ServiceException;
import com.semtrio.TestTask.service.interfaces.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PhotoApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;
    @Autowired
    private AlbumService albumService;
    @Autowired
    private PhotoService photoService;

    private final String accessToken="Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTY2NjUxNjM3OSwiaWF0IjoxNjU3ODc2Mzc5fQ.2pZemiZIFJe2bsEnnd8WnDFaodnTcAU84fb7OtrdMS4";
    @Autowired
    private ObjectMapper objectMapper;

    private Integer photoId;

    @BeforeEach
    public void setUp() throws IOException {

        ReqPhotoData photoData=objectMapper.readValue(new ClassPathResource(
                "data/ReqPhotoData1.json").getFile(),ReqPhotoData.class);
        try {
            albumService.getById(photoData.getAlbumId());
        }
        catch (ServiceException e)
        {
            ReqAlbumData albumData=objectMapper.readValue(new ClassPathResource(
                    "data/ReqAlbumData1.json").getFile(),ReqAlbumData.class);
            try {
                userService.getById(albumData.getUserId());
            }catch (ServiceException exception)
            {
                User user=userService.add(objectMapper.readValue(new ClassPathResource(
                        "data/ReqUserData1.json").getFile(), ReqUserData.class));
                albumData.setUserId(user.getId());
            }
            Album album=albumService.add(albumData);
            photoData.setAlbumId(album.getId());
        }
        Photo photo=photoService.add(photoData);
        photoId=photo.getId();
    }


    @Test
    @Order(1)
    public void addPhoto() throws Exception{

        ReqPhotoData photoData=objectMapper.readValue(new ClassPathResource(
                "data/ReqPhotoData1.json").getFile(),ReqPhotoData.class);
        try {
            albumService.getById(photoData.getAlbumId());
        }
        catch (ServiceException e)
        {
            ReqAlbumData albumData=objectMapper.readValue(new ClassPathResource(
                    "data/ReqAlbumData1.json").getFile(),ReqAlbumData.class);
            try {
                userService.getById(albumData.getUserId());
            }catch (ServiceException exception)
            {
                User user=userService.add(objectMapper.readValue(new ClassPathResource(
                        "data/ReqUserData1.json").getFile(), ReqUserData.class));
                albumData.setUserId(user.getId());
            }
            Album album=albumService.add(albumData);
            photoData.setAlbumId(album.getId());
        }
        ResultActions response = mockMvc.perform(post("/photos")
                .header("Authorization",accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(photoData)));
        response.andDo(print()).
                andExpect(status().isOk())
                .andExpect(jsonPath("$.albumId",
                        is(photoData.getAlbumId())))
                .andExpect(jsonPath("$.title",
                        is(photoData.getTitle())))
                .andExpect(jsonPath("$.url",
                        is(photoData.getUrl())))
                .andExpect(jsonPath("$.thumbnailUrl",
                        is(photoData.getThumbnailUrl())));
    }

    @Test
    @Order(2)
    public void editPhoto() throws Exception{

        ReqPhotoData photoData=objectMapper.readValue(new ClassPathResource(
                "data/ReqPhotoData1.json").getFile(),ReqPhotoData.class);
        photoData.setTitle("test title");
        ResultActions response = mockMvc.perform(put("/photos/"+photoId)
                .header("Authorization",accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(photoData)));
        response.andDo(print()).
                andExpect(status().isOk())
                .andExpect(jsonPath("$.albumId",
                        is(photoData.getAlbumId())))
                .andExpect(jsonPath("$.title",
                        is(photoData.getTitle())))
                .andExpect(jsonPath("$.url",
                        is(photoData.getUrl())))
                .andExpect(jsonPath("$.thumbnailUrl",
                        is(photoData.getThumbnailUrl())));
    }
    @Test
    @Order(3)
    public void getAllPhoto() throws Exception{

        List<Photo> photos=photoService.getAll(null);
        ResultActions response = mockMvc.perform(get("/photos")
                .contentType(MediaType.APPLICATION_JSON));
        response.andDo(print()).
                andExpect(status().isOk())
                .andExpect(jsonPath("$.size()",
                        is(photos.size())));
    }
    @Test
    @Order(4)
    public void getPhoto() throws Exception{

        Photo photo=photoService.getById(photoId);
        ResultActions response = mockMvc.perform(get("/photos/"+photoId)
                .contentType(MediaType.APPLICATION_JSON));response.andDo(print());

        response.andDo(print()).
                andExpect(status().isOk())
                .andExpect(jsonPath("$.albumId",
                        is(photo.getAlbum().getId())))
                .andExpect(jsonPath("$.title",
                        is(photo.getTitle())))
                .andExpect(jsonPath("$.url",
                        is(photo.getUrl())))
                .andExpect(jsonPath("$.thumbnailUrl",
                        is(photo.getThumbnailUrl())));
    }
    @Test
    @Order(5)
    public void deletePhoto() throws Exception{

        ResultActions response = mockMvc.perform(delete("/photos/"+photoId)
                .header("Authorization",accessToken)
                .contentType(MediaType.APPLICATION_JSON));
        response.andDo(print()).
                andExpect(status().isOk());
    }
}
