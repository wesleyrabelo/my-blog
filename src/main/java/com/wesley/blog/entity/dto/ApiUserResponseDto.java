package com.wesley.blog.entity.dto;

import com.wesley.blog.entity.ApiPost;
import com.wesley.blog.entity.ApiUser;

import java.util.List;

public class ApiUserResponseDto {
    private String name;
    private String email;
    private List<ApiPost> apiPosts;

    public ApiUserResponseDto() {
    }
    public ApiUserResponseDto(String name, String email, List<ApiPost> apiPosts) {
        this.name = name;
        this.email = email;
        this.apiPosts = apiPosts;
    }

    public ApiUserResponseDto (ApiUser apiUser){
        this.name = apiUser.getName();
        this.email = apiUser.getEmail();
        this.apiPosts = apiUser.getPosts();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<ApiPost> getApiPosts() {
        return apiPosts;
    }

    public void setApiPosts(List<ApiPost> apiPosts) {
        this.apiPosts = apiPosts;
    }
}
