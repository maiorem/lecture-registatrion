package com.hhplus.api.application;

import com.hhplus.api.domain.course.domain.Course;
import com.hhplus.api.domain.lecture.domain.Lecture;
import com.hhplus.api.domain.lecture.domain.LectureService;
import com.hhplus.api.domain.course.domain.CouseService;
import com.hhplus.api.domain.registration.domain.Registration;
import com.hhplus.api.domain.registration.domain.RegistrationService;
import com.hhplus.api.domain.user.domain.User;
import com.hhplus.api.domain.user.domain.UserService;
import com.hhplus.api.presentation.registration.RegistRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RegistrationFacade {

    private final LectureService lectureService;
    private final UserService userService;
    private final CouseService couseService;
    private final RegistrationService registrationService;

    @Transactional
    public RegistInfo regist(RegistRequest request) {
        //회원 정보 조회
        User user = userService.getUser(request.userId());

        //강의 정보 조회
        Lecture lecture = lectureService.getLectureWithLock(request.lectureId());

        //신청 가능 여부 조회
        if (registrationService.getSelectedRegist(user, lecture)) {
            throw new IllegalArgumentException("이미 신청한 강의입니다.");
        }

        //잔여좌석여부조회
        if (lecture.getAvailableSeat() < 0) {
            throw new IllegalArgumentException("이미 마감된 강의입니다.");
        }
        //수강신청
        registrationService.regist(user, lecture);

        //잔여좌석선택
        lectureService.seatSelect(lecture);

        Course course = couseService.getCourse(lecture);
        return new RegistInfo(course.getCourseDate(), lecture.getLectureName(), user.getUserName());

    }

    public List<CourseInfo> selectableCourseList(LocalDate date, Long userId) {

        List<CourseInfo> infoList = new ArrayList<>();
        //회원 조회
        User user = userService.getUser(userId);

        //날짜별 코스 조회
        List<Course> courseList = couseService.getCouseByDate(date);

        //해당 회원이 기신청한 신청목록 조회
        List<Registration> registList = registrationService.getSelectedRegistList(user);

        //해당 코스에 맞고 / 기신청 목록에 없는 특강 조회
        List<Lecture> lectureList = lectureService.getSelectableLectureList(registList, courseList);

        for (Lecture lecture : lectureList) {
            Course course = couseService.getCourse(lecture);
            CourseInfo info = new CourseInfo(lecture, user, course);
            infoList.add(info);
        }

        return infoList;
    }


    public List<CourseInfo> registCompleteList(Long userId) {
        List<CourseInfo> infoList = new ArrayList<>();

        //회원 조회
        User user = userService.getUser(userId);

        //해당 회원이 기신청한 신청목록 조회
        List<Registration> registList = registrationService.getSelectedRegistList(user);

        //신청목록에 존재하는 특강 조회
        List<Lecture> lectureList = lectureService.registCompleteList(registList);

        for (Lecture lecture : lectureList) {
            Course course = couseService.getCourse(lecture);
            CourseInfo info = new CourseInfo(lecture, user, course);
            infoList.add(info);
        }
        return infoList;
    }
}
