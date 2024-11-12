package com.matzipmap.mzm_memberservice.data.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "user")
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @ManyToOne()
    @JoinColumn(name = "school_id")
    private School school;

    @Column(name = "social_type")
    private Long socialType;

    @Column(name = "social_code", length = 45)
    private String socialCode;

    @Column(name = "profile_url", length = 512)
    private String profileUrl;

    @Column(name = "email")
    private String email;

    @Column(name = "name", length = 45)
    private String name;

    @Column(name = "username", length = 255, unique = true)
    private String username;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
