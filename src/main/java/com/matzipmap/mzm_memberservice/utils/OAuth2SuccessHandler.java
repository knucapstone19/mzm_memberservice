package com.matzipmap.mzm_memberservice.utils;

import com.matzipmap.mzm_memberservice.config.MzmConfigProperties;
import com.matzipmap.mzm_memberservice.data.domain.User;
import com.matzipmap.mzm_memberservice.data.dto.PrincipalDetails;
import com.matzipmap.mzm_memberservice.data.enums.SocialType;
import com.matzipmap.mzm_memberservice.jwt.JWTUtil;
import com.matzipmap.mzm_memberservice.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@RequiredArgsConstructor
@Component
@Slf4j
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final JWTUtil jwtUtil;
    private final UserRepository userRepository;
    private final OAuth2AuthorizedClientService authorizedClientService;
    private final MzmConfigProperties configProperties;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        OAuth2AuthenticationToken auth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
                auth2AuthenticationToken.getAuthorizedClientRegistrationId(), auth2AuthenticationToken.getName());

//        OAuth2AccessToken atoken = client.getAccessToken();
        PrincipalDetails principal = (PrincipalDetails) auth2AuthenticationToken.getPrincipal();
        String social_code = principal.getSocialCode();
        String social_type = auth2AuthenticationToken.getAuthorizedClientRegistrationId();
        User user = userRepository.getUserBySocialTypeAndSocialCode(SocialType.getValueByKey(social_type), social_code);
        String accessToken = jwtUtil.createAccessToken(user);
        // 헤더에 담아서 보낸다.
        log.info("OnAuthenticationSuccess: {}", user.getName());
        response.addHeader("Authorization", accessToken);

        final String URL = configProperties.getFrontUrl()+"/auth";
        String redirectUrl = UriComponentsBuilder.fromHttpUrl(URL)
                .queryParam("access-token", accessToken)
                .build().toUriString();
        response.sendRedirect(redirectUrl);
    }
}
