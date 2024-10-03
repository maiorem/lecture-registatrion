package com.hhplus.api.domain.course.repository;

import com.hhplus.api.domain.course.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseJpaRepository extends JpaRepository<Course, Long> {
}
