package com.matzipmap.mzm_memberservice.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FileNameGenerator {
    private static final String DELIMITER = "_";
    private final UuidGenerator uuidGenerator;

    public String generate(String fileName) {
        return UuidGenerator.generateUuid() + DELIMITER + fileName;
    }
}
