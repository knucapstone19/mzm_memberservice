package com.matzipmap.mzm_memberservice.repository;

import com.matzipmap.mzm_memberservice.data.domain.School;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SchoolRepository extends JpaRepository<School, Long> {
    List<School> findAllByOrderBySchoolNameAsc();
}
