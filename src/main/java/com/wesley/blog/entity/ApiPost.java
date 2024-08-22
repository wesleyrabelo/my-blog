package com.wesley.blog.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wesley.blog.entity.dto.ApiPostRequestDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Date;

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
    private LocalDateTime publicationDate;

    public ApiPost() {
    }
    public ApiPost(Long id, String title, String content, ApiUser apiUser) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.apiUser = apiUser;
        this.publicationDate = LocalDateTime.now();
    }

    public ApiPost (ApiPostRequestDto apiPostRequestDto, ApiUser apiUser){
        this.id = null;
        this.title = apiPostRequestDto.getTitle();
        this.content = apiPostRequestDto.getContent();
        this.apiUser = apiUser;
        this.publicationDate = LocalDateTime.now();
    }

    public void receivingContentFromDto(ApiPostRequestDto apiPostRequestDto){
        if(apiPostRequestDto.getTitle() != null) this.title = apiPostRequestDto.getTitle();
        if(apiPostRequestDto.getContent() != null) this.content = apiPostRequestDto.getContent();
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

    public LocalDateTime getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDateTime publicationDate) {
        this.publicationDate = publicationDate;
    }
}
