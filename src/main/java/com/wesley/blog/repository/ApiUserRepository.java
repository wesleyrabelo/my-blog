package com.wesley.blog.repository;

import com.wesley.blog.entity.ApiUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApiUserRepository extends JpaRepository<ApiUser, Long> {
    public Optional<ApiUser> findByEmail(String email);
    public Optional<ApiUser> findByName(String name);
}
