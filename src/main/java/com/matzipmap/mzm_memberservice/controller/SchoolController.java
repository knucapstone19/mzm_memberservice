package com.matzipmap.mzm_memberservice.controller;

import com.matzipmap.mzm_memberservice.data.domain.School;
import com.matzipmap.mzm_memberservice.data.dto.SchoolDto;
import com.matzipmap.mzm_memberservice.service.SchoolService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/school")
public class SchoolController {

    final SchoolService schoolService;

    /**
     * 학교이름과 아이디 리스트를 리턴합니다. 학교 리스트는 한글 오름차순으로 정렬되어 넘겨줍니다.
     * @return
     */
    @GetMapping()
    public ResponseEntity<Object> getSchoolList() {
        List<SchoolDto.SchoolInfo> allSchools = schoolService.getAllSchools();
        return ResponseEntity.ok(allSchools);
    }
}
