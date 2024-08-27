package com.wesley.blog.entity.dto;

import com.wesley.blog.entity.ApiPost;

import java.time.LocalDateTime;

public class ApiPostResponseDto {
    private String title;
    private String content;
    private String apiUserEmail;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public ApiPostResponseDto() {
    }

    public ApiPostResponseDto(ApiPost apiPost){
        this.title = apiPost.getTitle();
        this.content = apiPost.getContent();
        this.apiUserEmail = apiPost.getApiUser().getEmail();
        this.createdAt = apiPost.getCreatedAt();
        this.modifiedAt = apiPost.getModifiedAt();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getApiUserEmail() {
        return apiUserEmail;
    }

    public void setApiUserEmail(String apiUserEmail) {
        this.apiUserEmail = apiUserEmail;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }
}
