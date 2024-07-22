package com.matzipmap.mzm_memberservice.config;

import com.matzipmap.mzm_memberservice.filters.JwtFilter;
import com.matzipmap.mzm_memberservice.jwt.JWTUtil;
import com.matzipmap.mzm_memberservice.utils.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SpringConfig {

    final private OAuth2SuccessHandler oAuth2SuccessHandler;

    private JwtFilter jwtFilter(JWTUtil jwtUtil) {
        return new JwtFilter(jwtUtil);
    }
    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http,
            JWTUtil jwtUtil,
            OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService
    ) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // csrf 비활성화 -> cookie 사용 X면 꺼도 됨. cookie를 사용할 경우 httpOnly(XSS 방어), sameSite(CSRF 방어)로 방어해야함.
                .cors(AbstractHttpConfigurer::disable) // 프론트와 연결시 설정 필요
                .httpBasic(AbstractHttpConfigurer::disable) // 기본 인증 로그인 비활성화
                .formLogin(AbstractHttpConfigurer::disable) // 기본 login form 비활성화
                .logout(AbstractHttpConfigurer::disable) // 기본 logout 비활성화
                .sessionManagement(c->c.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 사용 안함
                .addFilterBefore(jwtFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(authorizeRequest ->
                    authorizeRequest
                            .requestMatchers(
                                    AntPathRequestMatcher.antMatcher("/user/**")
                            )
                            .authenticated()
                            .requestMatchers(
                                    AntPathRequestMatcher.antMatcher("/**")
                            ).permitAll()

                )
                .oauth2Login(oauth->
                        oauth.userInfoEndpoint(c->c.userService(oauth2UserService))
                                .successHandler(oAuth2SuccessHandler)
                )
                .headers(
                        headersConfigurer ->
                            headersConfigurer
                                    .frameOptions(
                                            HeadersConfigurer.FrameOptionsConfig::sameOrigin
                                    )
                );
        return http.build();
    }
}
