package com.matzipmap.mzm_memberservice.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Slf4j
@Component
public class FileGenerator {
    public void save(MultipartFile file, Path destinationFile) {
        try(InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            log.error("file save error: {}", e.getMessage());
            throw new RuntimeException("Storage Exception");
        }
    }
}
