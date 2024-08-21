package com.wesley.blog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wesley.blog.entity.ApiUser;
import com.wesley.blog.entity.dto.ApiUserRequestDto;
import com.wesley.blog.exception.EntityNotFoundException;
import com.wesley.blog.repository.ApiPostRepository;
import com.wesley.blog.repository.ApiUserRepository;
import com.wesley.blog.security.jwt.JwtToken;
import com.wesley.blog.security.jwt.JwtUtils;
import com.wesley.blog.service.ApiUserDetailsService;
import com.wesley.blog.service.ApiUserService;
import com.wesley.blog.service.SecurityService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mockStatic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@WebMvcTest(controllers = ApiUserController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@ComponentScan("com.wesley.blog")
public class ApiUserControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    private ApiUserService apiUserService;
    @MockBean
    private ApiPostRepository apiPostRepository;
    @MockBean
    private ApiUserRepository apiUserRepository;
    @MockBean
    private SecurityService securityService;
    @MockBean
    private ApiUserDetailsService apiUserDetailsService;
    @MockBean
    private AuthenticationManager authenticationManager;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private JwtUtils jwtUtils;
    private ApiUserRequestDto apiUserRequestDto;
    private ApiUser apiUser;
    private JwtToken jwtToken;
    private Authentication auth;
    private UserDetails userDetails;

    @BeforeEach
    public void beforeEach() {
        apiUserRequestDto = new ApiUserRequestDto("teste123", "teste@email.com", "12345678aA@");
        apiUser = new ApiUser(apiUserRequestDto);
        userDetails = new User(apiUser.getEmail(), apiUser.getPassword(), new ArrayList<SimpleGrantedAuthority>());
        auth = Mockito.mock(Authentication.class);
        jwtToken = new JwtToken("123456");
    }

    @Test
    public void ApiUserController_Login_ReturnJwtToken() throws Exception{
        try (MockedStatic<JwtUtils> mockedJwtUtils = mockStatic(JwtUtils.class)) {
            Mockito.when(apiUserDetailsService.loadUserByUsername(ArgumentMatchers.any())).thenReturn(userDetails);
            Mockito.when(authenticationManager.authenticate(ArgumentMatchers.any())).thenReturn(auth);
            mockedJwtUtils.when(() -> JwtUtils.generateToken(auth)).thenReturn(jwtToken);

            ResultActions response = mockMvc.perform(post("/user/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(apiUserRequestDto)));

            response.andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(content().string("123456"))
                    .andDo(MockMvcResultHandlers.print());
        }
    }

    @Test
    public void ApiUserController_SaveApiUser_ReturnCreatedApiUser() throws Exception {
        given(apiUserService.saveApiUser(ArgumentMatchers.any())).willAnswer(
                invocation -> invocation.getArgument(0)
        );

        ResultActions response = mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(apiUserRequestDto)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(apiUserRequestDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(apiUserRequestDto.getEmail())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void ApiUserController_FindApiUserById_ReturnApiUser() throws Exception {
        Mockito.when(apiUserService.findApiUserById(1L)).thenReturn(apiUser);

        ResultActions response = mockMvc.perform(get("/user/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(apiUser.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(apiUser.getEmail())))
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
    public void ApiUserController_FindApiUserById_ThrowEntityNotFoundException() throws Exception {
        Mockito.when(apiUserService.findApiUserById(1L)).thenThrow(EntityNotFoundException.class);

        ResultActions response = mockMvc.perform(get("/user/1")
            .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.exception", CoreMatchers.is(EntityNotFoundException.class.getSimpleName())))
                .andDo(MockMvcResultHandlers.print());

    }
    @Test
    public void ApiUserController_GetAllApiUsers_ReturnListOfApiUsers() throws Exception {
        List<ApiUser> apiUserList = Arrays.asList(
                Mockito.mock(ApiUser.class),
                Mockito.mock(ApiUser.class),
                Mockito.mock(ApiUser.class)
        );
        Mockito.when(apiUserService.findAllApiUsers()).thenReturn(apiUserList);

        ResultActions response = mockMvc.perform(get("/user")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(apiUserList.size()));;
    }

    @Test
    public void ApiUserController_DeleteApiUserById_ReturnNoContent() throws Exception {
        Mockito.when(securityService.hasUser(1L)).thenReturn(true);
        Mockito.when(apiUserService.findApiUserById(1L)).thenReturn(
                Mockito.mock(ApiUser.class)
        );
        Mockito.doNothing().when(apiUserService).deleteApiUserById(1L);

        ResultActions response = mockMvc.perform(delete("/user/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isNoContent());
    }
    @Test
    public void ApiUserController_DeleteApiUserById_ReturnUnauthorized() throws Exception {
        Mockito.when(securityService.hasUser(1L)).thenReturn(false);

        ResultActions response = mockMvc.perform(delete("/user/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void ApiUserController_PutApiUser_ReturnApiUser() throws Exception {
        Mockito.when(securityService.hasUser(1L)).thenReturn(true);
        Mockito.when(apiUserService.findApiUserById(1L)).thenReturn(apiUser);
        Mockito.when(apiUserService.putApiUser(apiUser)).thenReturn(apiUser);

        ResultActions response = mockMvc.perform(put("/user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(apiUserRequestDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(apiUser.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(apiUser.getEmail())))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void ApiUserController_PutApiUser_ReturnUnauthorized() throws Exception{
        Mockito.when(securityService.hasUser(1L)).thenReturn(false);

        ResultActions response = mockMvc.perform(put("/user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(apiUserRequestDto)));

        response.andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }
}