package com.zhuxi.common.utils;

import com.zhuxi.common.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.jackson.io.JacksonDeserializer;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Component
public class JwtUtils {
    private final JwtProperties jwtProperties;
    private final JwtParser adminJwtParser;
    private final JwtParser userJwtParser;

    public JwtUtils(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        adminJwtParser = Jwts.parser()
                .json(new JacksonDeserializer<>())
                .verifyWith(jwtProperties.getAdminSecretKey())
                .build();
        userJwtParser = Jwts.parser()
                .json(new JacksonDeserializer<>())
                .verifyWith(jwtProperties.getUserSecretKey())
                .build();
    }

    public String generateAdminToken(Map<String, Object> data){
        return Jwts.builder()
                .claims( data)
                .signWith(jwtProperties.getAdminSecretKey())
                .id(UUID.randomUUID().toString())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtProperties.getAdminExpire() * 1000))
                .compact();
    }

    public String generateUserToken(Map<String, Object> data){
        return Jwts.builder()
                .claims( data)
                .signWith(jwtProperties.getUserSecretKey())
                .id(UUID.randomUUID().toString())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtProperties.getUserExpire() * 1000))
                .compact();
    }

    public Claims parseAdminToken(String token){
        return adminJwtParser.parseSignedClaims( token)
                .getPayload();
    }

    public Claims parseUserToken(String token){
        return userJwtParser.parseSignedClaims( token)
                .getPayload();
    }

    public boolean validateToken(String token){
        return adminJwtParser.isSigned( token) || userJwtParser.isSigned( token);
    }
}
