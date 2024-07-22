package com.matzipmap.mzm_memberservice.data.enums;

import java.util.HashMap;
import java.util.Map;

public enum SocialType {
    KAKAO("kakao", 1L),
    GOOGLE("google", 2L),
    NAVER("naver", 3L);

    private String key;
    private Long value;

    private static final Map<String, Long> map = new HashMap<>();
    static {
        for (SocialType socialType : SocialType.values()) {
            map.put(socialType.key, socialType.value);
        }
    }

    SocialType(String key, Long value) {
        this.key = key;
        this.value = value;
    }

    public static Long getValueByKey(String key) {
        return map.get(key);
    }
}
