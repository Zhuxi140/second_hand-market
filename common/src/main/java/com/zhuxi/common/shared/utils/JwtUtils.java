package com.zhuxi.common.shared.utils;

import com.zhuxi.common.shared.constant.AuthMessage;
import com.zhuxi.common.shared.enums.Role;
import com.zhuxi.common.shared.exception.TokenException;
import com.zhuxi.common.infrastructure.properties.JwtProperties;
import com.zhuxi.common.interfaces.result.TokenResult;
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
        try {

        return adminJwtParser.parseSignedClaims( token)
                .getPayload();
        }catch(SignatureException e){
            log.warn("Invalid JWT signature");
            throw new TokenException(AuthMessage.LOGIN_INVALID);
        }catch (ExpiredJwtException e){
            log.warn("Expired JWT token");
            throw new TokenException(AuthMessage.LOGIN_EXPIRED);
        }catch (MalformedJwtException  e){
            log.warn("Malformed JWT token");
            throw new TokenException(AuthMessage.LOGIN_INVALID);
        } catch(Exception e){
            log.warn("other errors : {}",e.getMessage());
            throw new TokenException(AuthMessage.OTHER_TOKEN_ERROR);
        }
    }

    public Claims parseUserToken(String token){
        try {
        return userJwtParser.parseSignedClaims( token)
                .getPayload();

        }catch(SignatureException e){
            log.warn("Invalid JWT signature");
            throw new TokenException(AuthMessage.LOGIN_INVALID);
        }catch (ExpiredJwtException e){
            log.warn("Expired JWT token");
            throw new TokenException(AuthMessage.LOGIN_EXPIRED);
        }catch (MalformedJwtException  e){
            log.warn("Malformed JWT token");
            throw new TokenException(AuthMessage.LOGIN_INVALID);
        } catch(Exception e){
            log.warn("other errors : {}",e.getMessage());
            throw new TokenException(AuthMessage.OTHER_TOKEN_ERROR);
        }
    }

    /**
     * 验证token
     * @param token jwt-token
     * @param role  角色分类
     * @return token剩余时间 单位:毫秒
     */
    public long validateToken(String token, Role role){
        try {
            Jws<Claims> jws;
            if (role == Role.admin || role == Role.super_admin){
                jws = adminJwtParser.parseSignedClaims(token);
                Claims payload = jws.getPayload();
                Date expiration = payload.getExpiration();
                long expire = expiration.getTime() - System.currentTimeMillis();
                if (expire <= 0) {
                    log.warn("Expired JWT token");
                    throw new TokenException(AuthMessage.LOGIN_EXPIRED);
                }
                return expire;
            }else if(role == Role.user || role == Role.Merchant){
                jws = userJwtParser.parseSignedClaims(token);
                Claims payload = jws.getPayload();
                Date expiration = payload.getExpiration();
                long expire = expiration.getTime() - System.currentTimeMillis();
                if (expire <= 0) {
                    log.warn("Expired JWT token");
                    throw new TokenException(AuthMessage.LOGIN_EXPIRED);
                }
                return expire;
            }
        }catch(SignatureException e){
            log.warn("Invalid JWT signature");
            throw new TokenException(AuthMessage.LOGIN_INVALID);
        }catch (ExpiredJwtException e){
            log.warn("Expired JWT token");
            throw new TokenException(AuthMessage.LOGIN_EXPIRED);
        }catch (MalformedJwtException  e){
            log.warn("Malformed JWT token");
            throw new TokenException(AuthMessage.LOGIN_INVALID);
        } catch(Exception e){
            log.warn("other errors : {}",e.getMessage());
            throw new TokenException(AuthMessage.OTHER_TOKEN_ERROR);
        }
        return 0;
    }
}
