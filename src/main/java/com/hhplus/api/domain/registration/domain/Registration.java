package com.hhplus.api.domain.registration.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Comment("수강신청 테이블")
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long registId;

    private Long lectureId;

    private Long userId;

    @Builder(builderMethodName = "registBuilder")
    public Registration(Long lectureId, Long userId){
        this.lectureId = lectureId;
        this.userId = userId;

    }

}
