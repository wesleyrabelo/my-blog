package com.wesley.blog.service;

import com.wesley.blog.entity.ApiUser;
import com.wesley.blog.exception.EntityNotFoundException;
import com.wesley.blog.repository.ApiUserRepository;
import org.assertj.core.api.Assert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

@ExtendWith(MockitoExtension.class)
public class ApiUserServiceTest {
    @Mock
    private ApiUserRepository apiUserRepository;
    @InjectMocks
    private ApiUserService apiUserService;
    private ApiUser mockedApiUser;

    @BeforeEach
    public void BeforeEach(){
        mockedApiUser = Mockito.mock(ApiUser.class);
    }

    @Test
    public void ApiUserService_SaveApiUser_ReturnApiUser(){
        Mockito.when(apiUserRepository.save(mockedApiUser)).thenReturn(mockedApiUser);
        ApiUser savedApiUser = apiUserService.saveApiUser(mockedApiUser);

        Assertions.assertThat(savedApiUser).isNotNull();
        Mockito.verify(apiUserRepository).save(mockedApiUser);
    }

    @Test
    public void ApiUserService_PutApiUser_ReturnApiUser(){
        Mockito.when(apiUserRepository.save(mockedApiUser)).thenReturn(mockedApiUser);
        ApiUser savedApiUser = apiUserService.putApiUser(mockedApiUser);

        Assertions.assertThat(savedApiUser).isNotNull();
        Mockito.verify(apiUserRepository).save(mockedApiUser);
    }

    @Test
    public void ApiUserService_DeleteApiUserById_ReturnVoid(){
       apiUserService.deleteApiUserById(1L);

        Mockito.verify(apiUserRepository).deleteById(1L);
    }

    @Test
    public void ApiUserService_FindApiUserById_ReturnApiUser(){
        Mockito.when(apiUserRepository.findById(1L)).thenReturn(Optional.ofNullable(mockedApiUser));
        ApiUser foundApiUser = apiUserService.findApiUserById(1L);

        Assertions.assertThat(foundApiUser).isNotNull();
        Mockito.verify(apiUserRepository).findById(1L);
    }

    @Test
    public void ApiUserService_FindApiUserById_ThrowEntityNotFoundException(){
        Mockito.when(apiUserRepository.findById(1L)).thenThrow(EntityNotFoundException.class);

        org.junit.jupiter.api.Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> apiUserService.findApiUserById(1L)
        );
        Mockito.verify(apiUserRepository).findById(1L);
    }

    @Test
    public void ApiUserService_FindApiUserByEmail_ReturnApiUser(){
        Mockito.when(apiUserRepository.findByEmail("email@email.com")).thenReturn(Optional.ofNullable(mockedApiUser));
        ApiUser foundApiUser = apiUserService.findApiUserByEmail("email@email.com");

        Assertions.assertThat(foundApiUser).isNotNull();
        Mockito.verify(apiUserRepository).findByEmail("email@email.com");
    }

    @Test
    public void ApiUserService_FindApiUserByEmail_ThrowEntityNotFoundException(){
        Mockito.when(apiUserRepository.findByEmail("email@email.com")).thenThrow(EntityNotFoundException.class);

        org.junit.jupiter.api.Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> apiUserService.findApiUserByEmail("email@email.com")
        );
        Mockito.verify(apiUserRepository).findByEmail("email@email.com");
    }

    @Test
    public void APiUserService_FindAllApiUsers_ReturnListOfApiUsers() {
        List<ApiUser> apiUserList = Arrays.asList(
                Mockito.mock(ApiUser.class), Mockito.mock(ApiUser.class)
        );

        Mockito.when(apiUserRepository.findAll()).thenReturn(apiUserList);
        List<ApiUser> savedApiUserList = apiUserService.findAllApiUsers();

        Assertions.assertThat(savedApiUserList).isNotNull();
        Assertions.assertThat(savedApiUserList.size()).isEqualTo(2);
        Mockito.verify(apiUserRepository).findAll();
    }
}
