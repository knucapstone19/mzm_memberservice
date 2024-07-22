package com.matzipmap.mzm_memberservice.service;

import com.matzipmap.mzm_memberservice.data.domain.User;
import com.matzipmap.mzm_memberservice.data.enums.SocialType;

public interface UserService {
    User getUserById(Long userId);
    User getUserByEmail(String email);
    User getUserBySocialTypeAndSocialCode(SocialType socialType, String socialCode);
}
