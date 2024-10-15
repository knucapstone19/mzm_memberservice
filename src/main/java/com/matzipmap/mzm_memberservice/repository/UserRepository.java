package com.matzipmap.mzm_memberservice.repository;

import com.matzipmap.mzm_memberservice.data.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User getUserByUserId(Long id);
    User getUserByEmail(String email); // select * from user where email="{email}"
    User getUserBySocialTypeAndSocialCode(Long socialType, String socialCode);
    Optional<User> getUserByUsername(String username);
}
