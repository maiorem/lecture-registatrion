package com.hhplus.api.application;

import com.hhplus.api.domain.course.domain.Course;
import com.hhplus.api.domain.course.domain.CouseService;
import com.hhplus.api.domain.lecture.domain.Lecture;
import com.hhplus.api.domain.lecture.domain.LectureService;
import com.hhplus.api.domain.lecture.repository.LectureRepository;
import com.hhplus.api.domain.registration.domain.RegistrationService;
import com.hhplus.api.domain.user.domain.User;
import com.hhplus.api.domain.user.domain.UserService;
import com.hhplus.api.domain.user.repository.UserRepository;
import com.hhplus.api.domain.user.repository.UserRepositoryImpl;
import com.hhplus.api.presentation.registration.RegistRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegistrationFacadeTest {

    @InjectMocks
    private RegistrationFacade registrationFacade;

    @Mock
    private UserService userService;
    @Mock
    private LectureService lectureService;
    @Mock
    private RegistrationService registrationService;
    @Mock
    private CouseService couseService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private LectureRepository lectureRepository;

    @Test
    @DisplayName("특강 신청 테스트")
    void regist() {
        //given
        User user1 = new User(1L, "홍길동");
        Lecture math = new Lecture(1L, "이산수학", "장영철", 30, 30);
        Course mathCourse = new Course(1L, LocalDateTime.of(2024, Month.SEPTEMBER, 10, 15, 30), 1L);

        when(userService.getUser(1L)).thenReturn(user1);
        when(lectureService.getLecture(1L)).thenReturn(math);
        when(couseService.getCourse(math)).thenReturn(mathCourse);

        //when
        RegistRequest request = new RegistRequest(1L, 1L);
        registrationFacade.regist(request);

        //then
        assertEquals(29, math.getAvailableSeat());

    }

    @Test
    @DisplayName("특강 선택 가능 목록 조회 테스트")
    void selectableCourseList() {
        //given
        User user1 = new User(1L, "홍길동");
        Lecture math = new Lecture(1L, "이산수학", "장영철", 30, 30);
        Lecture english = new Lecture(2L, "영어회화", "김수희", 30, 5);
        Lecture science = new Lecture(3L, "과학", "오은영", 30, 10);
        Lecture culture = new Lecture(4L, "문화의이해", "유은수", 30, 20);

        Course mathCourse = new Course(1L, LocalDateTime.of(2024, Month.SEPTEMBER, 10, 15, 30), 1L);
        Course englishCourse = new Course(2L, LocalDateTime.of(2024, Month.OCTOBER, 12, 11, 00), 2L);
        Course scienceCourse = new Course(3L, LocalDateTime.of(2024, Month.OCTOBER, 12, 16, 00), 3L);
        Course cultureCourse = new Course(4L, LocalDateTime.of(2024, Month.OCTOBER, 14, 12, 30), 4L);

        when(userService.getUser(1L)).thenReturn(user1);

        when(lectureService.getLecture(1L)).thenReturn(math);
        when(lectureService.getLecture(2L)).thenReturn(english);
        when(lectureService.getLecture(3L)).thenReturn(science);
        when(lectureService.getLecture(4L)).thenReturn(culture);

        when(couseService.getCourse(math)).thenReturn(mathCourse);
        when(couseService.getCourse(english)).thenReturn(englishCourse);
        when(couseService.getCourse(science)).thenReturn(scienceCourse);
        when(couseService.getCourse(culture)).thenReturn(cultureCourse);

        //when
        List<CourseInfo> result = registrationFacade.selectableCourseList(LocalDate.of(2024, Month.OCTOBER, 12), 1L);

        //then
        assertEquals(2, result.size());

    }

    @Test
    @DisplayName("특강 신청 완료 목록 조회 테스트")
    void registCompleteList() {
        //given
        User user1 = new User(1L, "홍길동");
        Lecture math = new Lecture(1L, "이산수학", "장영철", 30, 30);
        Lecture english = new Lecture(2L, "영어회화", "김수희", 30, 5);
        Lecture science = new Lecture(3L, "과학", "오은영", 30, 10);
        Lecture culture = new Lecture(4L, "문화의이해", "유은수", 30, 20);

        Course mathCourse = new Course(1L, LocalDateTime.of(2024, Month.SEPTEMBER, 10, 15, 30), 1L);
        Course englishCourse = new Course(2L, LocalDateTime.of(2024, Month.OCTOBER, 12, 11, 00), 2L);
        Course scienceCourse = new Course(3L, LocalDateTime.of(2024, Month.OCTOBER, 12, 16, 00), 3L);
        Course cultureCourse = new Course(4L, LocalDateTime.of(2024, Month.OCTOBER, 14, 12, 30), 4L);

        when(userService.getUser(1L)).thenReturn(user1);

        when(lectureService.getLecture(1L)).thenReturn(math);
        when(lectureService.getLecture(2L)).thenReturn(english);
        when(lectureService.getLecture(3L)).thenReturn(science);
        when(lectureService.getLecture(4L)).thenReturn(culture);

        when(couseService.getCourse(math)).thenReturn(mathCourse);
        when(couseService.getCourse(english)).thenReturn(englishCourse);
        when(couseService.getCourse(science)).thenReturn(scienceCourse);
        when(couseService.getCourse(culture)).thenReturn(cultureCourse);

        RegistRequest request = new RegistRequest(1L, 1L);
        registrationFacade.regist(request);
        RegistRequest request2 = new RegistRequest(1L, 3L);
        registrationFacade.regist(request2);
        RegistRequest request3 = new RegistRequest(1L, 4L);
        registrationFacade.regist(request2);

        //when
        List<CourseInfo> result = registrationFacade.registCompleteList(1L);

        //then
        assertEquals(3, result.size());
    }
}