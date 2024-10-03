package com.hhplus.api.domain.user.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import org.hibernate.annotations.Comment;

@Getter
@Entity
@Comment("수강생 테이블")
public class User {

    @Id
    private Long userId;

    private String userName;

}
