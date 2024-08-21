package com.wesley.blog.security.filters;

import com.wesley.blog.security.jwt.JwtToken;
import com.wesley.blog.security.jwt.JwtUtils;
import com.wesley.blog.service.ApiUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private ApiUserDetailsService apiUserDetailsService;
    @Autowired
    private JwtUtils jwtUtils = new JwtUtils();
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if(request.getHeader("Authorization") == null || !request.getHeader("Authorization").startsWith("Bearer")){
            filterChain.doFilter(request, response);
            return;
        }

        String header = request.getHeader("Authorization");
        JwtToken jwtToken = new JwtToken(header.substring(7));

        UserDetails userDetails = apiUserDetailsService.loadUserByUsername(jwtUtils.getSubjectFromToken(jwtToken));

        if(jwtUtils.isTokenValid(jwtToken, userDetails)){
            UsernamePasswordAuthenticationToken userPassToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );

            userPassToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(userPassToken);

            filterChain.doFilter(request, response);
        } else {
            throw new AccessDeniedException("Access denied!");
        }
    }
}
