package com.hhplus.api.registration.application;

import com.hhplus.api.lecture.domain.LectureService;
import com.hhplus.api.timetable.domain.TimeTableService;
import com.hhplus.api.user.domain.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationFacade {

    private final LectureService lectureService;
    private final UserService userService;
    private final TimeTableService timeTableService;




}
