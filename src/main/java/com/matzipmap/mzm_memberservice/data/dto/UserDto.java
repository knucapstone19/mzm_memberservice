package com.matzipmap.mzm_memberservice.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class UserDto {


    @Data
    @NoArgsConstructor @AllArgsConstructor
    static public class PatchDto {
        private String username;
        private Long schoolId;
    }
}
