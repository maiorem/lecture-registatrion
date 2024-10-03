package com.hhplus.api.domain.lecture.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.hibernate.annotations.Comment;

@Entity
@Comment("특강 테이블")
public class Lecture {

    @Id
    private Long lectureId;

    private String lectureName;

    private String tutorName;

}
