package com.matzipmap.mzm_memberservice.service;

import com.matzipmap.mzm_memberservice.data.domain.User;
import com.matzipmap.mzm_memberservice.data.dto.UserDto;
import com.matzipmap.mzm_memberservice.data.enums.SocialType;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    User getUserById(Long userId);
    User getUserByEmail(String email);

    /**
     * 사용자 이름이 이미 사용되고있는지 검사합니다.
     * @param username 검사할 사용자 이름
     * @return isDuplicated
     */
    Boolean duplicateUsername(String username);

    boolean patchUser(OAuth2User principal, UserDto.PatchDto dto);
    String patchImage(OAuth2User principal, MultipartFile file);
    byte[] getImage(String fileName);
    boolean deleteUser(OAuth2User principal);
}
