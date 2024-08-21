package com.wesley.blog.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wesley.blog.entity.ApiPost;
import com.wesley.blog.entity.ApiUser;
import com.wesley.blog.entity.dto.ApiPostRequestDto;
import com.wesley.blog.exception.EntityNotFoundException;
import com.wesley.blog.repository.ApiUserRepository;
import com.wesley.blog.service.ApiPostService;
import com.wesley.blog.service.ApiUserDetailsService;
import com.wesley.blog.service.ApiUserService;
import com.wesley.blog.service.SecurityService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = ApiPostController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@ComponentScan("com.wesley.blog")
public class ApiPostControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    private ApiPostService apiPostService;
    @MockBean
    private ApiUserRepository apiUserRepository;
    @MockBean
    private ApiUserDetailsService apiUserDetailsService;
    @MockBean
    private ApiUserService apiUserService;
    @MockBean
    private SecurityService securityService;
    @Autowired
    private ObjectMapper objectMapper;
    private ApiPostRequestDto apiPostRequestDto;
    private ApiPost apiPost1;
    private ApiPost apiPost2;
    private ApiUser apiUser;
    private List<ApiPost> apiPosts;

    @BeforeEach
    public void beforeEach(){
        apiUser = new ApiUser(null, null, "teste123", "teste@email.com", "12345678aA@");
        apiPostRequestDto = new ApiPostRequestDto("title123", "content123");
        apiPost1 = new ApiPost(apiPostRequestDto, apiUser);
        apiPost2 = new ApiPost(null, "title456", "content456", apiUser);
        apiPosts = Arrays.asList(
                apiPost1,
                apiPost2
        );
    }

    @Test
    public void ApiPostController_SaveApiPost_ReturnApiPost() throws Exception{
        Mockito.when(apiUserService.findApiUserByEmail(ArgumentMatchers.any())).thenReturn(apiUser);
        Mockito.when(apiPostService.saveApiPost(ArgumentMatchers.any())).thenReturn(apiPost1);

        ResultActions response = mockMvc.perform(post("/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(apiPost1)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is("title123")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", CoreMatchers.is("content123")));
    }

    @Test
    public void ApiPostController_DeleteApiPostById_ReturnNoContent() throws Exception {
        Mockito.when(securityService.hasUserApiPost(ArgumentMatchers.any())).thenReturn(true);
        Mockito.when(apiPostService.findApiPostById(1L)).thenReturn(apiPost1);
        Mockito.doNothing().when(apiPostService).deleteApiPostById(1L);

        ResultActions response = mockMvc.perform(delete("/post/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void ApiPostController_DeleteApiPostById_ThrowEntityNotFoundException() throws Exception {
        Mockito.when(securityService.hasUserApiPost(ArgumentMatchers.any())).thenReturn(true);
        Mockito.when(apiPostService.findApiPostById(1L)).thenThrow(EntityNotFoundException.class);

        ResultActions response = mockMvc.perform(delete("/post/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void ApiPostController_DeleteApiPostById_ReturnUnauthorized() throws Exception {
        Mockito.when(securityService.hasUserApiPost(ArgumentMatchers.any())).thenReturn(false);

        ResultActions response = mockMvc.perform(delete("/post/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void ApiPostController_FindApiPostById_ReturnApiPost() throws Exception {
        Mockito.when(apiPostService.findApiPostById(1L)).thenReturn(apiPost1);

        ResultActions response = mockMvc.perform(get("/post/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is("title123")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", CoreMatchers.is("content123")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.apiUserEmail", CoreMatchers.is("teste@email.com")));
    }

    @Test
    public void ApiPostController_FindApiPostById_ThrowEntityNotFound() throws Exception {
        Mockito.when(apiPostService.findApiPostById(1L)).thenThrow(EntityNotFoundException.class);

        ResultActions response = mockMvc.perform(get("/post/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void ApiPostController_FindApiPostByEmail_ReturnListApiPost() throws Exception {
        Mockito.when(apiUserService.findApiUserByEmail(ArgumentMatchers.any())).thenReturn(apiUser);
        Mockito.when(apiPostService.findApiPostByApiUser(apiUser)).thenReturn(apiPosts);

        ResultActions response = mockMvc.perform(get("/post/user")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", CoreMatchers.is(2)));
    }

    @Test
    public void ApiPostController_FindAllApiPost_ReturnListApiPost() throws Exception {
        Mockito.when(apiPostService.findAllApiPost()).thenReturn(apiPosts);

        ResultActions response = mockMvc.perform(get("/post")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", CoreMatchers.is(2)));
    }

    @Test
    public void ApiPostController_PutApiPost_ReturnApiPost() throws Exception {
        Mockito.when(securityService.hasUserApiPost(1L)).thenReturn(true);
        Mockito.when(apiPostService.findApiPostById(1L)).thenReturn(apiPost1);
        Mockito.when(apiPostService.putApiPost(apiPost1)).thenReturn(apiPost1);

        ResultActions response = mockMvc.perform(put("/post/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(apiPostRequestDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void ApiPostController_PutApiPost_ReturnUnauthorized() throws Exception {
        Mockito.when(securityService.hasUserApiPost(1L)).thenReturn(false);

        ResultActions response = mockMvc.perform(put("/post/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(apiPostRequestDto)));

        response.andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }
}
