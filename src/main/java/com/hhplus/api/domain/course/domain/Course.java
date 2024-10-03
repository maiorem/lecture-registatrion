package com.hhplus.api.domain.course.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Entity
@Comment("특강 스케쥴 테이블")
public class Course {

    @Id
    private Long courseId;

    private LocalDateTime courseDate;

    private Long lectureId;

}
