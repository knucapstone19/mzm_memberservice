package com.matzipmap.mzm_memberservice.service.impl;

import com.matzipmap.mzm_memberservice.data.domain.User;
import com.matzipmap.mzm_memberservice.data.dto.OAuth2UserInfo;
import com.matzipmap.mzm_memberservice.data.dto.PrincipalDetails;
import com.matzipmap.mzm_memberservice.data.enums.SocialType;
import com.matzipmap.mzm_memberservice.repository.UserRepository;
import com.matzipmap.mzm_memberservice.service.UserService;
import io.jsonwebtoken.lang.Assert;
import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl extends DefaultOAuth2UserService implements UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 유저 정보 가져오기
        Map<String, Object> oAuth2UserAttributes = super.loadUser(userRequest).getAttributes();

        // registrationId 가져오기
        String registrationId = userRequest.
                getClientRegistration().getRegistrationId();

        // userNameAttributeName 가져오기
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        OAuth2UserInfo oAuth2UserInfo;
        try {
            oAuth2UserInfo = OAuth2UserInfo.of(registrationId, oAuth2UserAttributes);
            log.info(oAuth2UserInfo.name());
        } catch (AuthException e) {
            throw new RuntimeException(e);
        }

        String socialCode = getSocialCode(registrationId, oAuth2UserAttributes, userNameAttributeName);
        Long socialType = SocialType.getValueByKey(registrationId);

        // 유저 없으면 DB에 저장
        User userFind = userRepository.getUserBySocialTypeAndSocialCode(
                socialType,
                socialCode
        );
        User retUser;
        if(userFind == null) {
            User user = User.builder()
                    .name(oAuth2UserInfo.name())
                    .profileUrl(oAuth2UserInfo.profile())
                    .email(oAuth2UserInfo.email())
                    .socialType(socialType)
                    .socialCode(socialCode)
                    .username(oAuth2UserInfo.name())
                    .build();
            User savedUser = userRepository.save(user);
            log.info("SavedUserId: {}", savedUser.getUserId());
            retUser = user;
        } else {
            retUser = userFind;
        }
        Assert.notNull(retUser);
        PrincipalDetails details =  new PrincipalDetails(
                retUser,
                oAuth2UserAttributes,
                userNameAttributeName
        );
        Assert.notNull(details);
        return details;
    }

    @Override
    public User getUserById(Long userId) {
        Optional<User> byId = userRepository.findById(userId);
        return byId.orElse(null);
    }

    @Override
    public User getUserByEmail(String email) {
        Assert.notNull(email);
        return userRepository.getUserByEmail(email);
    }

    @Override
    public User getUserBySocialTypeAndSocialCode(SocialType socialType, String socialCode) {
        User user = getUserBySocialTypeAndSocialCode(socialType, socialCode);
//        PrincipalDetails details = new PrincipalDetails(user, null, null);
        return user;
    }

    /**
     * 닉네임으로 유저 정보를 가져옵니다.
     * @param username 사용자 닉네임
     * @return
     */
    @Override
    public Boolean duplicateUsername(String username) {
        User user = userRepository.getUserByUsername(username).orElse(null);
        Boolean isDuplicated = (user != null);
        return isDuplicated;
    }

    private String getSocialCode(String type, Map<String, Object> attributes, String attributeName) {
        if(type.equals("naver")) {
            return ((Map)attributes.get(attributeName)).get("id").toString();
        }
        return attributes.get(attributeName).toString();
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        // username: "{socialType} {socialCode}"
////        String[] usernames = username.split("\\s+");
//        User user = getUserById(Long.parseLong(username));
//        PrincipalDetails details = new PrincipalDetails(
//                user,
//
//        )
//        return null;
//    }
}
