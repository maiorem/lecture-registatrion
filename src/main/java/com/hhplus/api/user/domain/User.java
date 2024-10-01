package com.hhplus.api.user.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.hibernate.annotations.Comment;

@Entity
@Comment("수강생 테이블")
public class User {

    @Id
    private Long userId;

    private String userName;

}
