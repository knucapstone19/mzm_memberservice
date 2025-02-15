package com.matzipmap.mzm_memberservice.data.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity(name = "school")
public class School {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "school_id")
    private Long schoolId;

    @Column(name = "school_name", length = 45, unique = true)
    private String schoolName;

    @Column(name = "lng")
    private Float lng;

    @Column(name = "lat")
    private Float lat;

    @OneToMany(mappedBy = "school")
    private List<User> users;
}
