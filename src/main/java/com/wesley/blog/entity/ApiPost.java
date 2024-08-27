package com.wesley.blog.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wesley.blog.entity.dto.ApiPostRequestDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
public class ApiPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Title cannot be empty")
    private String title;
    @NotBlank(message = "Content cannot be empty")
    private String content;
    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private ApiUser apiUser;
    @NotNull
    private LocalDateTime createdAt;
    @NotNull
    private LocalDateTime modifiedAt;

    public ApiPost() {
    }
    public ApiPost(Long id, String title, String content, ApiUser apiUser) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.apiUser = apiUser;
        this.createdAt = LocalDateTime.now();
        this.modifiedAt = LocalDateTime.now();
    }

    public ApiPost (ApiPostRequestDto apiPostRequestDto, ApiUser apiUser){
        this.id = null;
        this.title = apiPostRequestDto.getTitle();
        this.content = apiPostRequestDto.getContent();
        this.apiUser = apiUser;
        this.createdAt = LocalDateTime.now();
        this.modifiedAt = LocalDateTime.now();
    }

    public void receivingContentFromDto(ApiPostRequestDto apiPostRequestDto){
        if(apiPostRequestDto.getTitle() != null) this.title = apiPostRequestDto.getTitle();
        if(apiPostRequestDto.getContent() != null) this.content = apiPostRequestDto.getContent();
        this.modifiedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public ApiUser getApiUser() {
        return apiUser;
    }

    public void setApiUser(ApiUser apiUser) {
        this.apiUser = apiUser;
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
