package com.wesley.blog.controller;

import com.wesley.blog.entity.ApiUser;
import com.wesley.blog.entity.dto.ApiUserRequestDto;
import com.wesley.blog.entity.dto.ApiUserResponseDto;
import com.wesley.blog.security.jwt.JwtToken;
import com.wesley.blog.security.jwt.JwtUtils;
import com.wesley.blog.service.ApiUserDetailsService;
import com.wesley.blog.service.ApiUserService;
import com.wesley.blog.service.SecurityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/user")
public class ApiUserController {
    @Autowired
    private ApiUserService apiUserService;
    @Autowired
    private ApiUserDetailsService apiUserDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private SecurityService securityService;
    @Autowired
    JwtUtils jwtUtils = new JwtUtils();

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid ApiUserRequestDto apiUserRequestDto){
        UserDetails userDetails = apiUserDetailsService.loadUserByUsername(apiUserRequestDto.getEmail());
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                 userDetails.getUsername(),
                 apiUserRequestDto.getPassword(),
                 userDetails.getAuthorities()
        );
        Authentication auth = authenticationManager.authenticate(token);
        JwtToken jwt = JwtUtils.generateToken(auth);

        return ResponseEntity.ok(jwt.getToken());
    }

    @PostMapping
    public ResponseEntity<ApiUserResponseDto> saveApiUser(@RequestBody @Valid ApiUserRequestDto apiUserRequestDto){
        apiUserRequestDto.setPassword(passwordEncoder.encode(apiUserRequestDto.getPassword()));
        ApiUser apiUser = new ApiUser(apiUserRequestDto);
        ApiUser savedApiUser = apiUserService.saveApiUser(apiUser);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedApiUser.getId())
                .toUri();
        return ResponseEntity.created(location).body(new ApiUserResponseDto(savedApiUser));
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("@securityService.hasUser(#id) || hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteApiUserById(@PathVariable Long id){
        apiUserService.findApiUserById(id);
        apiUserService.deleteApiUserById(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiUserResponseDto> findApiUserById(@PathVariable Long id){
        ApiUser apiUser = apiUserService.findApiUserById(id);
        return ResponseEntity.ok(new ApiUserResponseDto(apiUser));
    }
    @GetMapping
    public ResponseEntity<List<ApiUserResponseDto>> findAllApiUsers(){
        List<ApiUserResponseDto> dtoList = apiUserService
                .findAllApiUsers()
                .stream()
                .map(ApiUserResponseDto::new)
                .toList();

        return ResponseEntity.ok(dtoList);
    }
    @PutMapping("/{id}")
    @PreAuthorize("@securityService.hasUser(#id)")
    public ResponseEntity<ApiUserResponseDto> putApiUser(@PathVariable Long id, @RequestBody @Valid ApiUserRequestDto apiUserRequestDto){
        ApiUser apiUser = apiUserService.findApiUserById(id);
        apiUser.receivingInfoFromDto(apiUserRequestDto);
        return ResponseEntity.ok(new ApiUserResponseDto(apiUserService.putApiUser(apiUser)));
    }
}
