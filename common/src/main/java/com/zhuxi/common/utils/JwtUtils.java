package com.zhuxi.common.utils;

import com.zhuxi.common.constant.TokenMessage;
import com.zhuxi.common.enums.Role;
import com.zhuxi.common.exception.TokenException;
import com.zhuxi.common.properties.JwtProperties;
import com.zhuxi.common.result.TokenResult;
import io.jsonwebtoken.*;
import io.jsonwebtoken.jackson.io.JacksonDeserializer;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * @author zhuxi
 */
@Slf4j
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

    public TokenResult generateAdminToken(Map<String, Object> data){
        long expire = System.currentTimeMillis() + jwtProperties.getAdminExpire() * 1000;
        Date date = new Date(expire);
        String token = Jwts.builder()
                .claims(data)
                .signWith(jwtProperties.getAdminSecretKey())
                .id(UUID.randomUUID().toString())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(date)
                .compact();
        return new TokenResult(token, expire);
    }

    public TokenResult generateUserToken(Map<String, Object> data){
        long expire = System.currentTimeMillis() + jwtProperties.getUserExpire() * 1000;
        Date date = new Date(expire);
        String token = Jwts.builder()
                .claims(data)
                .signWith(jwtProperties.getUserSecretKey())
                .id(UUID.randomUUID().toString())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(date)
                .compact();
        return new TokenResult(token, expire);
    }

    public Claims parseAdminToken(String token){
        return adminJwtParser.parseSignedClaims( token)
                .getPayload();
    }

    public Claims parseUserToken(String token){
        return userJwtParser.parseSignedClaims( token)
                .getPayload();
    }

    public void validateToken(String token, Role role){
        try {
            Jws<Claims> jws;
            if (role == Role.admin || role == Role.super_admin){
                jws = adminJwtParser.parseSignedClaims(token);
            }else if(role == Role.user || role == Role.Merchant){
                jws = userJwtParser.parseSignedClaims(token);
            }
            /*Claims payload = jws.getPayload();*/
        }catch(SignatureException e){
            log.warn("Invalid JWT signature");
            throw new TokenException(TokenMessage.LOGIN_INVALID);
        }catch (ExpiredJwtException e){
            log.warn("Expired JWT token");
            throw new TokenException(TokenMessage.LOGIN_EXPIRED);
        }catch (MalformedJwtException  e){
            log.warn("Malformed JWT token");
            throw new TokenException(TokenMessage.LOGIN_INVALID);
        } catch(Exception e){
            log.warn("other errors : {}",e.getMessage());
            throw new TokenException(TokenMessage.OTHER_TOKEN_ERROR);
        }
    }
}
