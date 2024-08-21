package com.wesley.blog.service;

import com.wesley.blog.entity.ApiUser;
import com.wesley.blog.exception.EntityNotFoundException;
import com.wesley.blog.repository.ApiUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component("apiUserDetailsService")
public class ApiUserDetailsService implements UserDetailsService {
    @Autowired
    private ApiUserRepository apiUserRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        ApiUser apiUser = apiUserRepository.findByEmail(email).orElseThrow(
                EntityNotFoundException::new
        );

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + apiUser.getRole()));

        return new User(apiUser.getEmail(), apiUser.getPassword(), authorities);
    }
}
