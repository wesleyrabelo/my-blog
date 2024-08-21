package com.wesley.blog.entity;

import com.wesley.blog.entity.dto.ApiUserRequestDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;

import java.util.Date;
import java.util.List;

@Entity
public class ApiUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "apiUser")
    private List<ApiPost> apiPosts;
    @NotBlank(message = "Name cannot be empty")
    @Size(min = 6, max = 32)
    private String name;
    @Email(regexp = "^[\\w!#$%&amp;'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&amp;'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$", message = "Invalid email")
    @NotBlank(message = "Email cannot be empty")
    @Column(unique = true)
    private String email;
    @NotBlank(message = "Password cannot be empty")
    @Size(min = 8, message = "Password length must be 8 characters at least!")
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$", message = "Invalid password format")
    private String password;
    @NotNull
    private Date creationDate;
    @Enumerated(EnumType.STRING)
    @NotNull
    private Role role;

    private enum Role{
        ADMIN,
        USER
    }

    public ApiUser() {
    }
    public ApiUser(Long id, List<ApiPost> apiPosts, String name, String email, String password) {
        this.id = id;
        this.apiPosts = apiPosts;
        this.name = name;
        this.email = email;
        this.password = password;
        this.creationDate = new Date();
        this.role = Role.USER;
    }

    public ApiUser(ApiUserRequestDto apiUserRequestDto){
        this.id = null;
        this.apiPosts = null;
        this.name = apiUserRequestDto.getName();
        this.email = apiUserRequestDto.getEmail();
        this.password = apiUserRequestDto.getPassword();
        this.creationDate = new Date();
        this.role = Role.USER;
    }

    public void receivingInfoFromDto(ApiUserRequestDto apiUserRequestDto){
        if(apiUserRequestDto.getName() != null) this.name = apiUserRequestDto.getName();
        if(apiUserRequestDto.getEmail() != null) this.email = apiUserRequestDto.getEmail();
        if(apiUserRequestDto.getPassword() != null) this.password = apiUserRequestDto.getPassword();
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ApiPost> getPosts() {
        return apiPosts;
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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Role getRole() {
        return role;
    }
}
