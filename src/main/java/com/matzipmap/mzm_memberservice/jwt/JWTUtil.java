package com.matzipmap.mzm_memberservice.jwt;

import com.matzipmap.mzm_memberservice.data.domain.User;
import com.matzipmap.mzm_memberservice.data.dto.PrincipalDetails;
import com.matzipmap.mzm_memberservice.service.UserService;
import com.matzipmap.mzm_memberservice.service.impl.UserServiceImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;

@Component
@Slf4j
//@Configuration
//@PropertySource(value = "classpath:application-security.yml")
public class JWTUtil implements AuthenticationProvider {
    final private SecretKey secretKey;
    final private long accessTokenTime;

    final private UserService userService;

    @Autowired
    public JWTUtil(
            @Value("${spring.jwt.secret}") String secret,
            @Value("${spring.jwt.expiration_time}") long accessTokenTime,
            UserService userService
    ) {
        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Keys.secretKeyFor(HS256).getAlgorithm());
        this.accessTokenTime = accessTokenTime;
        this.userService = userService;
    }

    /**
     * Access Token 생성
     * @param user
     * @return AccessToken
     */
    public String createAccessToken(User user) {
        return createToken(user, accessTokenTime);
    }

    /**
     * Jwt 생성
     * @param user
     * @param expireTime
     * @return JWT String
     */
    public String createToken(User user, long expireTime) {
        Claims claims = Jwts.claims();
        claims.put("userId", user.getUserId());
        claims.put("email", user.getEmail());
        claims.put("username", user.getUsername());
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime tokenValidity = now.plusSeconds(expireTime);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(now.toInstant()))
                .setExpiration(Date.from(tokenValidity.toInstant()))
                .signWith(secretKey, HS256)
                .compact();
    }

    public Long getUserId(String token) {
        return parseClaims(token).get("userId", Long.class);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        }
        return false;
    }

    public Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public Authentication getAuthentication(String token) {
        Map<String, Object> attributes =  parseClaims(token);
        Long userId = getUserId(token);
        User user = userService.getUserById(userId);
        PrincipalDetails principalDetails = new PrincipalDetails(
                user,
                attributes,
                "username"
        );
        return new UsernamePasswordAuthenticationToken(
                principalDetails.getUsername(),
                principalDetails.getPassword(),
                principalDetails.getAuthorities()
        );
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("Authentication:: authenticate called");
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        log.info("Authentication:: supports called");
        return false;
    }
}
