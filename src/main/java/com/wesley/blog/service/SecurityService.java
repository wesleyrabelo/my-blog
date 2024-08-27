package com.wesley.blog.service;

import com.wesley.blog.entity.ApiPost;
import com.wesley.blog.entity.ApiUser;
import com.wesley.blog.entity.dto.ApiPostRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("securityService")
public class SecurityService {
    private final ApiUserService userService;
    private final ApiPostService postService;

    @Autowired
    public SecurityService(ApiUserService userService, ApiPostService postService){
        this.userService = userService;
        this.postService = postService;
    }

    public Authentication getAuthentication(){
        return SecurityContextHolder
                    .getContext()
                    .getAuthentication();
    }
    public String getNameFromAuthentication(){
        return getAuthentication()
                .getName();
    }
    public boolean hasUser(Long id){
        ApiUser user = userService.findApiUserById(id);
        return getNameFromAuthentication().equals(user.getEmail());
    }
    public boolean hasUserApiPost(Long id){
        ApiPost apiPost = postService.findApiPostById(id);
        ApiUser apiUser = userService.findApiUserById(
                        apiPost
                        .getApiUser()
                        .getId()
        );
        return getNameFromAuthentication().equals(apiUser.getEmail());
    }
}
