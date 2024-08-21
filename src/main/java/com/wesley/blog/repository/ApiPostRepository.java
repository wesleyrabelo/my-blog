package com.wesley.blog.repository;

import com.wesley.blog.entity.ApiPost;
import com.wesley.blog.entity.ApiUser;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApiPostRepository extends JpaRepository<ApiPost, Long> {
    public List<ApiPost> findByApiUser(ApiUser apiUser);
}
