package com.hhplus.api.domain.course.repository;

import com.hhplus.api.domain.course.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CourseJpaRepository extends JpaRepository<Course, Long> {

    Course findByLectureId(Long lectureId);

    @Query("SELECT course FROM Course course WHERE course.courseDate BETWEEN :startDt AND :endDt")
    List<Course> findByDate(@Param("startDt") LocalDateTime startDt, @Param("endDt") LocalDateTime endDt);
}
