package com.wesley.blog.repository;

import com.wesley.blog.entity.ApiUser;
import jakarta.validation.ConstraintViolationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Date;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ApiUserRepositoryTest {
    @Autowired
    private ApiUserRepository apiUserRepository;

    @BeforeEach
    public void beforeEach(){
        apiUserRepository.save(new ApiUser(null, null, "teste123", "teste@email.com", "12345678aA@"));
    }
    @Test
    public void ApiUserRepository_FindApiUserByEmail_ReturnApiUser(){
        ApiUser apiUser = apiUserRepository.findByEmail("teste@email.com").orElse(null);
        Assertions.assertThat(apiUser).isNotNull();
        Assertions.assertThat(apiUser.getId()).isGreaterThan(0);
        Assertions.assertThat(apiUser.getEmail()).isEqualTo("teste@email.com");
        Assertions.assertThat(apiUser.getCreationDate()).isBefore(LocalDateTime.now());

    }

    @Test
    public void ApiUserRepository_SaveApiUserWithInvalidName_ReturnException(){
        org.junit.jupiter.api.Assertions.assertThrows(ConstraintViolationException.class,
                () -> apiUserRepository.save(new ApiUser(null, null, "teste", "teste@email.com", "12345678aA@"))
        );
        org.junit.jupiter.api.Assertions.assertThrows(ConstraintViolationException.class,
                () -> apiUserRepository.save(new ApiUser(null, null, "1234567890123456789012345678901234567", "teste@email.com", "12345678aA@"))
        );
        org.junit.jupiter.api.Assertions.assertThrows(ConstraintViolationException.class,
                () -> apiUserRepository.save(new ApiUser(null, null, "", "teste@email.com", "12345678aA@"))
        );
    }

    @Test
    public void ApiUserRepository_SaveApiUserWithInvalidEmail_ReturnException(){
        org.junit.jupiter.api.Assertions.assertThrows(ConstraintViolationException.class,
                () -> apiUserRepository.save(
                        new ApiUser(null, null, "teste123", "teste@email", "12345678aA@")
                )
        );
        org.junit.jupiter.api.Assertions.assertThrows(ConstraintViolationException.class,
                () -> apiUserRepository.save(
                        new ApiUser(null, null, "teste123", "teste@.com", "12345678aA@")
                )
        );
        org.junit.jupiter.api.Assertions.assertThrows(ConstraintViolationException.class,
                () -> apiUserRepository.save(
                        new ApiUser(null, null, "teste123", "@email.com", "12345678aA@")
                )
        );
        org.junit.jupiter.api.Assertions.assertThrows(ConstraintViolationException.class,
                () -> apiUserRepository.save(
                        new ApiUser(null, null, "teste123", ".teste@email.com", "12345678aA@")
                )
        );
        org.junit.jupiter.api.Assertions.assertThrows(ConstraintViolationException.class,
                () -> apiUserRepository.save(
                        new ApiUser(null, null, "teste123", "teste@email..com", "12345678aA@")
                )
        );
        org.junit.jupiter.api.Assertions.assertThrows(ConstraintViolationException.class,
                () -> apiUserRepository.save(
                        new ApiUser(null, null, "teste123", "teste@email.com.", "12345678aA@")
                )
        );
    }
    @Test
    public void ApiUserRepository_SaveApiUserWithInvalidPassword_ReturnException(){
        org.junit.jupiter.api.Assertions.assertThrows(ConstraintViolationException.class,
                () -> apiUserRepository.save(new ApiUser(null, null, "teste123", "teste@email.com", "123456"))
        );
        org.junit.jupiter.api.Assertions.assertThrows(ConstraintViolationException.class,
                () -> apiUserRepository.save(new ApiUser(null, null, "teste123", "teste@email.com", "12345678"))
        );
        org.junit.jupiter.api.Assertions.assertThrows(ConstraintViolationException.class,
                () -> apiUserRepository.save(new ApiUser(null, null, "teste123", "teste@email.com", "12345678a"))
        );
        org.junit.jupiter.api.Assertions.assertThrows(ConstraintViolationException.class,
                () -> apiUserRepository.save(new ApiUser(null, null, "teste123", "teste@email.com", "12345678aA"))
        );
        org.junit.jupiter.api.Assertions.assertThrows(ConstraintViolationException.class,
                () -> apiUserRepository.save(new ApiUser(null, null, "teste123", "teste@email.com", "12345678a@"))
        );
        org.junit.jupiter.api.Assertions.assertThrows(ConstraintViolationException.class,
                () -> apiUserRepository.save(new ApiUser(null, null, "teste123", "teste@email.com", "12345678A"))
        );
        org.junit.jupiter.api.Assertions.assertThrows(ConstraintViolationException.class,
                () -> apiUserRepository.save(new ApiUser(null, null, "teste123", "teste@email.com", "12345678A@"))
        );
        org.junit.jupiter.api.Assertions.assertThrows(ConstraintViolationException.class,
                () -> apiUserRepository.save(new ApiUser(null, null, "teste123", "teste@email.com", "12345678@"))
        );
        org.junit.jupiter.api.Assertions.assertThrows(ConstraintViolationException.class,
                () -> apiUserRepository.save(new ApiUser(null, null, "teste123", "teste@email.com", "abcdefghA"))
        );
        org.junit.jupiter.api.Assertions.assertThrows(ConstraintViolationException.class,
                () -> apiUserRepository.save(new ApiUser(null, null, "teste123", "teste@email.com", "abcdefghA@"))
        );
    }
}
