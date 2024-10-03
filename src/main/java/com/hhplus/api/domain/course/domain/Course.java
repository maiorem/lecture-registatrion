package com.hhplus.api.domain.course.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "TB_COURSE")
@Comment("특강 스케쥴 테이블")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseId;

    private LocalDateTime courseDate;

    private Long lectureId;

}
