package com.hhplus.api.domain.user.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Comment;

@Getter
@Entity
@Table(name = "TB_USER")
@Comment("수강생 테이블")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String userName;

}
