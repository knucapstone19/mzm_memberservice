package com.matzipmap.mzm_memberservice.controller;

import com.matzipmap.mzm_memberservice.service.UserService;
import com.matzipmap.mzm_memberservice.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    final private UserService userService;

    @GetMapping
    public ResponseEntity<String> test() {
        String response = "GOOD";
        return ResponseEntity.ok(response);
    }
}
