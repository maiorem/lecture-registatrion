package com.hhplus.api.application;

import com.hhplus.api.domain.lecture.domain.LectureService;
import com.hhplus.api.domain.course.domain.CouseService;
import com.hhplus.api.domain.user.domain.UserService;
import com.hhplus.api.presentation.registration.RegistRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegistrationFacade {

    private final LectureService lectureService;
    private final UserService userService;
    private final CouseService couseService;

    @Transactional
    public RegistInfo regist(RegistRequest request) {

        return null;
    }

    public List<CourseInfo> selectableCourseList(String date, Long userId) {
        return null;
    }


    public List<CourseInfo> registCompleteList(Long userId) {
        return null;
    }
}
