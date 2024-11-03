package com.matzipmap.mzm_memberservice.controller;

import com.matzipmap.mzm_memberservice.data.dto.UserDto;
import com.matzipmap.mzm_memberservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    final private UserService userService;

    @GetMapping
    public ResponseEntity<Object> getUser(@AuthenticationPrincipal OAuth2User principal) {
        return ResponseEntity.ok(principal);
    }

    @GetMapping("/duplicate-name")
    public ResponseEntity<Object> duplicateName(@RequestParam(name = "name") String name) {
        Boolean isDuplicated = userService.duplicateUsername(name);
        return ResponseEntity.ok(isDuplicated);
    }

    @PatchMapping
    public ResponseEntity<Object> patchUsername(
            @AuthenticationPrincipal OAuth2User principal,
            @RequestBody UserDto.PatchDto dto) {
        boolean b = userService.patchUser(principal, dto);
        return ResponseEntity.ok(b); // TODO:
    }

    @PostMapping("/profile-image")
    public ResponseEntity<Object> patchImage(
            @AuthenticationPrincipal OAuth2User principal,
            @RequestBody MultipartFile file) {
        return ResponseEntity.ok(userService.patchImage(principal, file));
    }

    @GetMapping("/profile-image")
    public ResponseEntity<byte[]> getProfileImage(@RequestParam String path) {
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(userService.getImage(path), headers, HttpStatus.OK);
    }
    @DeleteMapping
    public ResponseEntity<Object> deleteUser(@AuthenticationPrincipal OAuth2User principal) {
        userService.deleteUser(principal);
        return ResponseEntity.ok(true);
    }
}
