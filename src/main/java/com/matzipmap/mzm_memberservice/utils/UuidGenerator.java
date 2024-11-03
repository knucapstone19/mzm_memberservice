package com.matzipmap.mzm_memberservice.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class UuidGenerator {
    public static String generateUuid() {
        return UUID.randomUUID().toString();
    }
}
