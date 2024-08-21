package com.wesley.blog.entity.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

public class ApiUserRequestDto {
    @NotBlank(message = "Name cannot be empty")
    @Size(min = 6, max = 32, message = "Name must have at least 6 and a maximum of 32 characters")
    private String name;
    @Email(regexp = "^[\\w!#$%&amp;'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&amp;'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$", message = "Invalid email")
    @NotBlank(message = "Email cannot be empty dto")
    private String email;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 8, message = "Password size must be 8 characters at least!")
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$", message = "Invalid password format")
    private String password;

    public ApiUserRequestDto() {
    }
    public ApiUserRequestDto(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
