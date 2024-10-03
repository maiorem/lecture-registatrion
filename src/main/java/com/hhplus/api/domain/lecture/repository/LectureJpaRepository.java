package com.hhplus.api.domain.lecture.repository;

import com.hhplus.api.domain.lecture.domain.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureJpaRepository extends JpaRepository<Lecture, Long> {
}
