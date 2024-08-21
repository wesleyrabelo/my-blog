package com.wesley.blog.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtils {

    private static final String JWT_SECRET = "1234567890123456789012345678901234567890123456789012345678901234";
    public static JwtToken generateToken(Authentication authentication){
        String token = JWT
                .create()
                .withSubject(authentication.getName())
                .withClaim("ROLE", authentication.getAuthorities().toString())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(new Date().getTime() + 1200000))
                .sign(Algorithm.HMAC512(JWT_SECRET));

        return new JwtToken(token);
    }

    public Boolean isTokenValid(JwtToken jwtToken, UserDetails userDetails){
        return !isTokenExpired(jwtToken) && getSubjectFromToken(jwtToken).equals(userDetails.getUsername());
    }
    public Boolean isTokenExpired(JwtToken jwtToken) {
        Date expDate = getExpirationFromToken(jwtToken);
       return new Date().after(expDate);
    }

    public String getSubjectFromToken(JwtToken jwtToken){
        return createJwtVerifier().verify(jwtToken.getToken()).getSubject();
    }
    public Date getExpirationFromToken(JwtToken jwtToken){
         return createJwtVerifier()
                .verify(jwtToken.getToken())
                .getExpiresAt();
    }
    private JWTVerifier createJwtVerifier(){
        return JWT.require(Algorithm.HMAC512(JWT_SECRET)).build();
    }
}
