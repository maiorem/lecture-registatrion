package com.hhplus.api.domain.lecture.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import org.hibernate.annotations.Comment;

@Getter
@Entity
@Comment("특강 테이블")
public class Lecture {

    @Id
    private Long lectureId;

    private String lectureName;

    private String tutorName;

    private int maxSeat;

    private int availableSeat;

    public void selectSeat() {
        if(availableSeat > 0) this.availableSeat--;
    }

}
