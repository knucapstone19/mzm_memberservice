package com.matzipmap.mzm_memberservice.data.dto;

import jakarta.security.auth.message.AuthException;
import lombok.Builder;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

@Builder
public record OAuth2UserInfo(
        String name,
        String email,
        String profile
) {
    public static OAuth2UserInfo of(String registrationId, Map<String, Object> attributes) throws AuthException {
        return switch (registrationId) {
            case "kakao" -> ofKakao((Map<String, Object>) attributes.get("properties"));
            default -> throw new AuthException();
        };
    }

    private static OAuth2UserInfo ofKakao(Map<String, Object> attributes) {
        return OAuth2UserInfo.builder()
                .name((String) attributes.get("nickname"))
                .email((String) attributes.get("email"))
                .profile((String) attributes.get("profile_image"))
                .build();

    }

}