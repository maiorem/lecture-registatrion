package com.hhplus.api.application;

import com.hhplus.api.domain.course.domain.Course;
import com.hhplus.api.domain.course.domain.CouseService;
import com.hhplus.api.domain.lecture.domain.Lecture;
import com.hhplus.api.domain.lecture.domain.LectureService;
import com.hhplus.api.domain.registration.domain.Registration;
import com.hhplus.api.domain.registration.domain.RegistrationService;
import com.hhplus.api.domain.user.domain.User;
import com.hhplus.api.domain.user.domain.UserService;
import com.hhplus.api.presentation.registration.RegistRequest;
import jakarta.transaction.Transactional;
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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegistrationFacadeTest {

    @Mock
    private LectureService lectureService;
    @Mock
    private UserService userService;
    @Mock
    private RegistrationService registrationService;
    @Mock
    private CouseService couseService;

    @InjectMocks
    private RegistrationFacade registrationFacade;

    private Lecture lecture;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("선착순 30명인 특강에 40명 신청 시 실패 테스트")
    @Transactional
    void testConcurrentRegistration() throws InterruptedException {
        // Given
        int totalStudents = 40;
        Long lectureId = 1L;
        lecture = new Lecture(1L, "이산수학", "장영철", 30, 30);
        when(lectureService.getLectureWithLock(anyLong())).thenReturn(lecture);

        // 모든 학생들이 동시에 수강신청을 시도
        ExecutorService executor = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(totalStudents);
        AtomicInteger successCnt = new AtomicInteger(0);
        AtomicInteger failCnt = new AtomicInteger(0);

        // Lecture 잔여 좌석이 0 이하가 되면 예외를 던지도록 설정
        doAnswer(invocation -> {
            synchronized (lecture) {
                int availableSeats = lecture.getAvailableSeat();
                if (availableSeats <= 0) {
                    throw new IllegalArgumentException("이미 마감된 강의입니다.");
                }
                when(lecture.getAvailableSeat()).thenReturn(availableSeats - 1);
                return null;
            }
        }).when(lectureService).seatSelect(any(Lecture.class));

        // 유저마다 고유의 ID를 가지도록 설정
        for (int i = 0; i < totalStudents; i++) {
            final int studentId = i;
            executor.submit(() -> {
                try {
                    User user = mock(User.class);
                    when(userService.getUser((long) studentId)).thenReturn(user);

                    RegistRequest request = new RegistRequest((long) studentId, lectureId);
                    try {
                        registrationFacade.regist(request);
                        successCnt.incrementAndGet();
                    } catch (IllegalArgumentException e) {
                        // 예상된 실패: 정원이 초과되면 예외가 발생한다.
                        failCnt.incrementAndGet();
                    }
                } finally {
                    latch.countDown();
                }
            });
        }

        // 모든 스레드가 완료될 때까지 대기
        latch.await(10, TimeUnit.SECONDS);

        executor.shutdown();

        // Then
        // 총 30명만 성공적으로 수강신청했는지 확인
        assertEquals(30, successCnt);
        verify(registrationService, times(30)).regist(any(User.class), eq(lecture));
        // 나머지 10명은 실패했는지 확인
        assertEquals(10, failCnt);
        verify(lectureService, times(30)).seatSelect(lecture);
    }

    @Test
    @DisplayName("동일한 유저가 같은 특강을 5번 신청했을 때 1번만 성공")
    void testMultipleRegistrationsBySameUser() {

        // Given
        Long userId = 1L;
        Long lectureId = 1L;
        RegistRequest request = new RegistRequest(userId, lectureId);

        user = new User(1L, "홍길동");
        lecture = new Lecture(1L, "이산수학", "장영철", 30, 1);

        when(userService.getUser(anyLong())).thenReturn(user);
        when(lectureService.getLectureWithLock(anyLong())).thenReturn(lecture);

        // 첫 번째 신청 시에는 getSelectedRegist가 false를 반환하여 성공하도록 설정
        when(registrationService.getSelectedRegist(user, lecture)).thenReturn(false)
                .thenReturn(true)  // 그 이후의 요청은 이미 신청한 강의로 인식되도록 true 반환
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(true);

        // When
        // 동일한 유저가 5번 신청을 시도
        registrationFacade.regist(request);  // 첫 번째는 성공
        assertThrows(IllegalArgumentException.class, () -> registrationFacade.regist(request)); // 두 번째는 실패
        assertThrows(IllegalArgumentException.class, () -> registrationFacade.regist(request)); // 세 번째는 실패
        assertThrows(IllegalArgumentException.class, () -> registrationFacade.regist(request)); // 네 번째는 실패
        assertThrows(IllegalArgumentException.class, () -> registrationFacade.regist(request)); // 다섯 번째는 실패

        // Then
        // 첫 번째 신청은 성공, 1번만 registrationService.regist 호출
        verify(registrationService, times(1)).regist(user, lecture);
        // 그 이후로는 실패, getSelectedRegist는 총 5번 호출됨 (매 요청마다 확인)
        verify(registrationService, times(5)).getSelectedRegist(user, lecture);
    }



    @Test
    @DisplayName("특강 신청 테스트")
    void testRegistSuccess() {
        // Given
        Long userId = 1L;
        Long lectureId = 1L;
        RegistRequest request = new RegistRequest(userId, lectureId);

        User user = new User();
        Lecture lecture = new Lecture();
        Course course = new Course();

        when(userService.getUser(userId)).thenReturn(user);
        when(lectureService.getLectureWithLock(lectureId)).thenReturn(lecture);
        when(registrationService.getSelectedRegist(user, lecture)).thenReturn(false);
        when(lecture.getAvailableSeat()).thenReturn(10);
        when(couseService.getCourse(lecture)).thenReturn(course);

        // When
        RegistInfo registInfo = registrationFacade.regist(request);

        // Then
        assertNotNull(registInfo);
        verify(registrationService).regist(user, lecture);
        verify(lectureService).seatSelect(lecture);
    }

    @Test
    @DisplayName("이미 신청한 경우 테스트")
    void testRegistAlreadyRegistered() {
        // Given
        Long userId = 1L;
        Long lectureId = 1L;
        RegistRequest request = new RegistRequest(userId, lectureId);

        User user = new User();
        Lecture lecture = new Lecture();

        when(userService.getUser(userId)).thenReturn(user);
        when(lectureService.getLectureWithLock(lectureId)).thenReturn(lecture);
        when(registrationService.getSelectedRegist(user, lecture)).thenReturn(true);

        // When / Then
        assertThrows(IllegalArgumentException.class, () -> registrationFacade.regist(request));
    }


    @Test
    @DisplayName("특강 선택 가능 목록 조회 테스트")
    void testSelectableCourseList() {
        // Given
        Long userId = 1L;
        LocalDate date = LocalDate.now();
        User user = new User();
        List<Course> courseList = new ArrayList<>();
        List<Registration> registList = new ArrayList<>();
        List<Lecture> lectureList = new ArrayList<>();

        when(userService.getUser(userId)).thenReturn(user);
        when(couseService.getCouseByDate(date)).thenReturn(courseList);
        when(registrationService.getSelectedRegistList(user)).thenReturn(registList);
        when(lectureService.getSelectableLectureList(registList, courseList)).thenReturn(lectureList);

        // When
        List<CourseInfo> result = registrationFacade.selectableCourseList(date, userId);

        // Then
        assertNotNull(result);
        verify(userService).getUser(userId);
        verify(couseService).getCouseByDate(date);
        verify(lectureService).getSelectableLectureList(registList, courseList);
    }

    @Test
    @DisplayName("특강 신청 완료 목록 조회 테스트")
    void testRegistCompleteList() {
        //given
        Long userId = 1L;
        user = new User(userId, "홍길동");
        Lecture math = new Lecture(1L, "이산수학", "장영철", 30, 30);
        Lecture english = new Lecture(2L, "영어회화", "김수희", 30, 5);
        Lecture science = new Lecture(3L, "과학", "오은영", 30, 10);
        Lecture culture = new Lecture(4L, "문화의이해", "유은수", 30, 20);

        Course mathCourse = new Course(1L, LocalDateTime.of(2024, Month.SEPTEMBER, 10, 15, 30), 1L);
        Course englishCourse = new Course(2L, LocalDateTime.of(2024, Month.OCTOBER, 12, 11, 00), 2L);
        Course scienceCourse = new Course(3L, LocalDateTime.of(2024, Month.OCTOBER, 12, 16, 00), 3L);
        Course cultureCourse = new Course(4L, LocalDateTime.of(2024, Month.OCTOBER, 14, 12, 30), 4L);

        List<Registration> registList = new ArrayList<>();
        List<Lecture> lectureList = new ArrayList<>();

        when(lectureService.getLecture(1L)).thenReturn(math);
        when(lectureService.getLecture(2L)).thenReturn(english);
        when(lectureService.getLecture(3L)).thenReturn(science);
        when(lectureService.getLecture(4L)).thenReturn(culture);

        when(couseService.getCourse(math)).thenReturn(mathCourse);
        when(couseService.getCourse(english)).thenReturn(englishCourse);
        when(couseService.getCourse(science)).thenReturn(scienceCourse);
        when(couseService.getCourse(culture)).thenReturn(cultureCourse);

        when(userService.getUser(userId)).thenReturn(user);
        when(registrationService.getSelectedRegistList(user)).thenReturn(registList);
        when(lectureService.registCompleteList(registList)).thenReturn(lectureList);

        // When
        List<CourseInfo> result = registrationFacade.registCompleteList(userId);

        // Then
        assertNotNull(result);
        verify(userService).getUser(userId);
        verify(registrationService).getSelectedRegistList(user);
        verify(lectureService).registCompleteList(registList);
    }
}