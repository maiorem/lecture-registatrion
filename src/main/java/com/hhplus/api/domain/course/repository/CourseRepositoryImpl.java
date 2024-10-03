package com.hhplus.api.domain.course.repository;

import com.hhplus.api.domain.course.domain.Course;
import com.hhplus.api.domain.lecture.domain.Lecture;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CourseRepositoryImpl implements CourseRepository{

    private final CourseJpaRepository courseJpaRepository;

    @Override
    public Course getCourse(Lecture lecture) {
        return courseJpaRepository.findByLectureId(lecture.getLectureId());
    }

    @Override
    public Course save(Course course) {
        return courseJpaRepository.save(course);
    }

    @Override
    public List<Course> getCourseByDate(LocalDateTime startDt, LocalDateTime endDt) {
        return courseJpaRepository.findByDate(startDt, endDt);
    }
}
