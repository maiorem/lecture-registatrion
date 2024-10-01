package com.hhplus.api.registration.domain;


import com.hhplus.api.lecture.domain.Lecture;
import com.hhplus.api.user.domain.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import org.hibernate.annotations.Comment;

import java.util.List;

@Entity
@Comment("수강신청 테이블")
public class Registration {

    @Id
    private Long registId;

    @OneToMany
    private List<Lecture> lectureList;

    @OneToMany
    private List<User> userList;

}
