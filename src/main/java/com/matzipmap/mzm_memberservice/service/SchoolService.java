package com.matzipmap.mzm_memberservice.service;

import com.matzipmap.mzm_memberservice.data.domain.School;
import com.matzipmap.mzm_memberservice.data.dto.SchoolDto;

import java.util.List;

public interface SchoolService {
    List<SchoolDto.SchoolInfo> getAllSchools();
}
