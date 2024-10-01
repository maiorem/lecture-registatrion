package com.hhplus.api.timetable.domain;

import com.hhplus.api.lecture.domain.Lecture;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Entity
@Comment("특강 스케쥴 테이블")
public class LectureTimeTable {

    @Id
    private Long timeTableId;

    private String dayOfTheWeek;

    private LocalDateTime lectureStart;

    private LocalDateTime lectureEnd;

    @OneToOne
    private Lecture lecture;

}
