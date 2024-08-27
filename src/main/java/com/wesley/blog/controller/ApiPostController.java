package com.wesley.blog.controller;

import com.wesley.blog.entity.ApiPost;
import com.wesley.blog.entity.ApiUser;
import com.wesley.blog.entity.dto.ApiPostRequestDto;
import com.wesley.blog.entity.dto.ApiPostResponseDto;
import com.wesley.blog.service.ApiPostService;
import com.wesley.blog.service.ApiUserService;
import com.wesley.blog.service.SecurityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/post")
public class ApiPostController {
    private final ApiPostService apiPostService;
    private final ApiUserService apiUserService;
    private final SecurityService securityService;

    @Autowired
    public ApiPostController(ApiPostService apiPostService, ApiUserService apiUserService, SecurityService securityService) {
        this.apiPostService = apiPostService;
        this.apiUserService = apiUserService;
        this.securityService = securityService;
    }

    @PostMapping
    public ResponseEntity<ApiPostResponseDto> saveApiPost(@RequestBody @Valid ApiPostRequestDto apiPostRequestDto){
        ApiUser apiUser = apiUserService.findApiUserByEmail(securityService.getNameFromAuthentication());
        ApiPost apiPost = new ApiPost(apiPostRequestDto, apiUser);
        ApiPost savedApiPost = apiPostService.saveApiPost(apiPost);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedApiPost.getId())
                .toUri();
        return ResponseEntity.created(location).body(new ApiPostResponseDto(savedApiPost));
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("@securityService.hasUserApiPost(#id)")
    public ResponseEntity<Void> deleteApiPostById(@PathVariable Long id){
        apiPostService.findApiPostById(id);
        apiPostService.deleteApiPostById(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiPostResponseDto> findApiPostById(@PathVariable Long id){
        return ResponseEntity.ok(new ApiPostResponseDto(apiPostService.findApiPostById(id)));
    }

    @GetMapping("/user")
    public ResponseEntity<List<ApiPostResponseDto>> findApiPostByApiUser(){
        ApiUser apiUser = apiUserService.findApiUserByEmail(securityService.getNameFromAuthentication());
        List<ApiPostResponseDto> dtoList = apiPostService
                .findApiPostByApiUser(apiUser)
                .stream()
                .map(ApiPostResponseDto::new)
                .toList();
        return ResponseEntity.ok(dtoList);
    }
    @GetMapping
    public ResponseEntity<List<ApiPostResponseDto>> findAllApiPost(){
        List<ApiPostResponseDto> dtoList = apiPostService
                .findAllApiPost()
                .stream()
                .map(ApiPostResponseDto::new)
                .toList();
        return ResponseEntity.ok(dtoList);
    }
    @PutMapping("/{id}")
    @PreAuthorize("@securityService.hasUserApiPost(#id)")
    public ResponseEntity<ApiPostResponseDto> putApiPost(@PathVariable Long id, @RequestBody @Valid ApiPostRequestDto apiPostRequestDto){
        ApiPost apiPost = apiPostService.findApiPostById(id);
        apiPost.receivingContentFromDto(apiPostRequestDto);
        return ResponseEntity.ok(new ApiPostResponseDto(apiPostService.putApiPost(apiPost)));
    }
}
