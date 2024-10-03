package com.hhplus.api.application;

import com.hhplus.api.domain.course.domain.Course;
import com.hhplus.api.domain.lecture.domain.Lecture;
import com.hhplus.api.domain.user.domain.User;

import java.time.LocalDateTime;

public record CourseInfo(Long userId, Long lectureId, Long courseId, String userName, String lectureName, String tutorName, LocalDateTime courseDate) {
    public CourseInfo(Lecture lecture, User user, Course course) {
        this(user.getUserId(), lecture.getLectureId(), course.getCourseId(), user.getUserName(), lecture.getLectureName(), lecture.getTutorName(), course.getCourseDate());
    }
}
