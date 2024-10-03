package com.hhplus.api.application;

import java.time.LocalDateTime;

public record CourseInfo(Long userId, Long lectureId, Long courseId, String userName, String lectureName, LocalDateTime courseDate) {
}
