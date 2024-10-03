package com.hhplus.api.domain.registration.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.hibernate.annotations.Comment;

@Entity
@Comment("수강신청 테이블")
public class Registration {

    @Id
    private Long registId;

    private Long lectureId;

    private Long userId1;

}
