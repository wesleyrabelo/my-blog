package com.wesley.blog.service;

import com.wesley.blog.entity.ApiPost;
import com.wesley.blog.entity.ApiUser;
import com.wesley.blog.exception.EntityNotFoundException;
import com.wesley.blog.repository.ApiPostRepository;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ApiPostServiceTest {
    @Mock
    private ApiPostRepository apiPostRepository;
    @InjectMocks
    private ApiPostService apiPostService;
    private ApiPost mockedApiPost;

    @BeforeEach
    public void beforeEach() {
        mockedApiPost = Mockito.mock(ApiPost.class);
    }

    @Test
    public void ApiPostService_SaveApiPost_ReturnApiPost() {
        Mockito.when(apiPostRepository.save(mockedApiPost)).thenReturn(mockedApiPost);

        ApiPost savedApiPost = apiPostService.saveApiPost(mockedApiPost);

        Assertions.assertThat(savedApiPost).isNotNull();
        Mockito.verify(apiPostRepository).save(mockedApiPost);
    }

    @Test
    public void ApiPostService_PutApiPost_ReturnApiPost() {
        Mockito.when(apiPostRepository.save(mockedApiPost)).thenReturn(mockedApiPost);

        ApiPost savedApiPost = apiPostService.putApiPost(mockedApiPost);

        Assertions.assertThat(savedApiPost).isNotNull();
        Mockito.verify(apiPostRepository).save(mockedApiPost);
    }

    @Test
    public void ApiPostService_DeleteApiPostById_ReturnVoid(){
        apiPostService.deleteApiPostById(1L);

        Mockito.verify(apiPostRepository).deleteById(1L);
    }
    @Test
    public void ApiPostService_FindApiPostById_ReturnApiPost(){
        Mockito.when(apiPostRepository.findById(1L)).thenReturn(Optional.ofNullable(mockedApiPost));
        ApiPost foundApiPost = apiPostService.findApiPostById(1L);

        Assertions.assertThat(foundApiPost).isNotNull();
        Mockito.verify(apiPostRepository).findById(1L);
    }
    @Test
    public void ApiPostService_FindApiPostById_ThrowEntityNotFoundException(){
        Mockito.when(apiPostRepository.findById(1L)).thenThrow(EntityNotFoundException.class);

        org.junit.jupiter.api.Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> apiPostService.findApiPostById(1L)
        );
        Mockito.verify(apiPostRepository).findById(1L);
    }

    @Test
    public void ApiPostService_FindApiPostByApiUser_ReturnListOfApiPost(){
        ApiUser mockedApiUser = Mockito.mock(ApiUser.class);
        List<ApiPost> apiPostList = java.util.Arrays.asList(
                Mockito.mock(ApiPost.class), Mockito.mock(ApiPost.class)
        );

        Mockito.when(apiPostRepository.findByApiUser(mockedApiUser)).thenReturn(apiPostList);
        List<ApiPost> foundApiPosts = apiPostService.findApiPostByApiUser(mockedApiUser);

        Assertions.assertThat(foundApiPosts).isNotNull();
        Assertions.assertThat(foundApiPosts.size()).isEqualTo(2);
        Mockito.verify(apiPostRepository).findByApiUser(mockedApiUser);
    }

    @Test
    public void ApiPostService_FindAllApiPost_ReturnListOfApiPost(){
        List<ApiPost> apiPostList = java.util.Arrays.asList(
                Mockito.mock(ApiPost.class), Mockito.mock(ApiPost.class)
        );

        Mockito.when(apiPostRepository.findAll()).thenReturn(apiPostList);
        List<ApiPost> foundApiPosts = apiPostService.findAllApiPost();

        Assertions.assertThat(foundApiPosts).isNotNull();
        Assertions.assertThat(foundApiPosts.size()).isEqualTo(2);
        Mockito.verify(apiPostRepository).findAll();
    }
}
