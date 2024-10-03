package com.hhplus.api.domain.lecture.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

@Getter
@Entity
@Builder
@Table(name = "TB_LECTURE")
@Comment("특강 테이블")
@NoArgsConstructor
@AllArgsConstructor
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lectureId;

    private String lectureName;

    private String tutorName;

    @ColumnDefault("30")
    private int maxSeat;

    @ColumnDefault("30")
    private int availableSeat;

    public void selectSeat() {
        if(this.availableSeat > 0) this.availableSeat--;
    }

}
