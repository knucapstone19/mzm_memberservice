package com.matzipmap.mzm_memberservice.utils;

import com.matzipmap.mzm_memberservice.config.ImageConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Component
public class FileGeneratorUtil {

    private final String URL_FORMAT = "%s%s";
    private static final String STORE_FILE_OUTSIDE_CURRENT_DIRECTORY_MESSAGE = "유효하지 않은 저장 경로입니다.";
    private final FileNameGenerator fileNameGenerator;
    private final Path rootPath;

    public FileGeneratorUtil(FileNameGenerator fileNameGenerator, ImageConfig imageConfig) {
        this.fileNameGenerator = fileNameGenerator;
        this.rootPath = Paths.get(imageConfig.getRootLocation());

    }

    public String generateFileName(String fileName) {
        return fileNameGenerator.generate(fileName);
    }

    public String generateFileUrl(String baseUrl, String fileName) {
        return String.format(URL_FORMAT, baseUrl, fileName);
    }

    public Path generateAbsolutePath(String fileName) {
        Path absoulutePath = rootPath.resolve(Paths.get(fileName))
                .normalize()
                .toAbsolutePath();
        if(!absoulutePath.getParent().equals(rootPath.toAbsolutePath())) {
            throw new IllegalArgumentException(STORE_FILE_OUTSIDE_CURRENT_DIRECTORY_MESSAGE);
        }
        return absoulutePath;
    }
}
