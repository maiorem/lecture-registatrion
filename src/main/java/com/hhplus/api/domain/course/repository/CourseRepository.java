package com.hhplus.api.domain.course.repository;

import com.hhplus.api.domain.course.domain.Course;
import com.hhplus.api.domain.lecture.domain.Lecture;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CourseRepository {
    Course getCourse(Lecture lecture);

    Course save(Course course);

    List<Course> getCourseByDate(LocalDateTime startDt, LocalDateTime endDt);
}
