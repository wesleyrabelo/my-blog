package com.wesley.blog.repository;

import com.wesley.blog.entity.ApiPost;
import com.wesley.blog.entity.ApiUser;
import com.wesley.blog.service.ApiUserDetailsService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ComponentScan("com.wesley.blog")
public class ApiPostRepositoryTest {

    @Autowired
    private ApiPostRepository apiPostRepository;
    @Autowired
    private ApiUserRepository apiUserRepository;
    @Autowired
    private ApiUserDetailsService apiUserDetailsService;
    private ApiUser apiUser;

    @BeforeEach
    public void beforeEach(){
        apiUser = new ApiUser(null, null, "teste123", "teste@email.com", "12345678aA@");
        apiUserRepository.save(apiUser);

        apiPostRepository.save(new ApiPost(null, "teste", "teste", apiUser));
        apiPostRepository.save(new ApiPost(null, "teste2", "teste2", apiUser));
    }

    @Test
    public void ApiPostRepository_FindAllApiPostByApiUser_ReturnListApiPost(){
        List<ApiPost> apiPosts = apiPostRepository.findByApiUser(apiUser);
        Assertions.assertThat(apiPosts).isNotNull();
        Assertions.assertThat(apiPosts).isNotEmpty();
    }
    @Test
    public void ApiPostRepository_FindAllApiPostByApiUser_ReturnEmptyList(){
        ApiUser fakeUser = apiUserRepository.save(new ApiUser(
                null, null, "fake1234", "fake@email.com", "12345678aA@"
        ));
        List<ApiPost> apiPosts = apiPostRepository.findByApiUser(fakeUser);
        Assertions.assertThat(apiPosts).isEmpty();
    }
}
