package com.hhplus.api.domain.lecture.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Comment;

@Getter
@Entity
@Table(name = "TB_LECTURE")
@Comment("특강 테이블")
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lectureId;

    private String lectureName;

    private String tutorName;

    private int maxSeat;

    private int availableSeat;

    public void selectSeat() {
        if(availableSeat > 0) this.availableSeat--;
    }

}
