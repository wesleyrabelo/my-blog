package com.wesley.blog.entity.dto;

import com.wesley.blog.entity.ApiPost;
import com.wesley.blog.entity.ApiUser;

import java.time.LocalDateTime;
import java.util.Date;

public class ApiPostResponseDto {
    private String title;
    private String content;
    private String apiUserEmail;
    private LocalDateTime publicationDate;

    public ApiPostResponseDto() {
    }
    public ApiPostResponseDto(String title, String content, ApiUser apiUser) {
        this.title = title;
        this.content = content;
        this.apiUserEmail = apiUser.getEmail();
    }

    public ApiPostResponseDto(ApiPost apiPost){
        this.title = apiPost.getTitle();
        this.content = apiPost.getContent();
        this.apiUserEmail = apiPost.getApiUser().getEmail();
        this.publicationDate = apiPost.getPublicationDate();
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

    public LocalDateTime getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDateTime publicationDate) {
        this.publicationDate = publicationDate;
    }
}
