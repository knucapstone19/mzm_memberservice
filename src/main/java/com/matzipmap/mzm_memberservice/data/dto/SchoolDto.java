package com.matzipmap.mzm_memberservice.data.dto;

import com.matzipmap.mzm_memberservice.data.domain.School;
import lombok.Builder;
import lombok.Data;

public class SchoolDto {
    @Data
    @Builder
    public static class SchoolInfo {
        private Long schoolId;
        private Float lat;
        private Float lng;
        private String schoolName;
        public static SchoolInfo entityToDTO(School school) {
            return SchoolInfo.builder()
                    .schoolId(school.getSchoolId())
                    .lat(school.getLat())
                    .lng(school.getLng())
                    .schoolName(school.getSchoolName())
                    .build();
        }
    }
}
