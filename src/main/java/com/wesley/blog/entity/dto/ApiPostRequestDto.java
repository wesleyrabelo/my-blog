package com.wesley.blog.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wesley.blog.entity.ApiUser;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;

public class ApiPostRequestDto {
    @NotBlank(message = "Title cannot be empty")
    private String title;
    @NotBlank(message = "Content cannot be empty")
    private String content;

    public ApiPostRequestDto() {
    }
    public ApiPostRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
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
}
