package com.matzipmap.mzm_memberservice.service.impl;

import com.matzipmap.mzm_memberservice.data.domain.School;
import com.matzipmap.mzm_memberservice.data.dto.SchoolDto;
import com.matzipmap.mzm_memberservice.repository.SchoolRepository;
import com.matzipmap.mzm_memberservice.service.SchoolService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SchoolServiceImpl implements SchoolService {

    final SchoolRepository schoolRepository;

    /**
     * DB에 존재하는 모든 학교 정보를 오름차순으로 가져온다.
     * @return 학교 리스트
     */
    @Override
    public List<SchoolDto.SchoolInfo> getAllSchools() {
        List<School> schools = schoolRepository.findAllByOrderBySchoolNameAsc();
        return schools.stream().map(SchoolDto.SchoolInfo::entityToDTO).toList();
    }
}
