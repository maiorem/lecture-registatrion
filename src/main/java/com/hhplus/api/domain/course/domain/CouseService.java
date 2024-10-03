package com.hhplus.api.domain.course.domain;

import com.hhplus.api.domain.course.repository.CourseRepository;
import com.hhplus.api.domain.lecture.domain.Lecture;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CouseService {

    private final CourseRepository courseRepository;

    public CouseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course getCourse(Lecture lecture) {
        return courseRepository.getCourse(lecture);
    }

    public List<Course> getCouseByDate(LocalDate date) {
        LocalDateTime startDt = date.atStartOfDay();
        LocalDateTime endDt = date.atTime(23, 59, 59);
        return courseRepository.getCourseByDate(startDt, endDt);
    }
}
