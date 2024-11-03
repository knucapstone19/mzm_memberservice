package com.matzipmap.mzm_memberservice.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class ImageConfig {

    @Value("${file.root-location}")
    private String rootLocation;

}
