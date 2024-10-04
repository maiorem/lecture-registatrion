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
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;
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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("선착순 30명인 특강에 40명 신청 시 실패 테스트")
    @Transactional
    void 신청자_30명_초과하면_실패() throws InterruptedException {
        //given
        int maxCount = 30;
        int registTestCount = 40;
        ExecutorService executorService = Executors.newFixedThreadPool(registTestCount);
        CountDownLatch latch = new CountDownLatch(registTestCount);
        AtomicInteger failCnt = new AtomicInteger(0);

        Lecture math = new Lecture(1L, "이산수학", "장영철", maxCount, maxCount);
        when(lectureService.getLecture(1L)).thenReturn(math);

        //when
        for (int i = 0; i < registTestCount; i++) {
            Long userId = i+1L;
            executorService.execute(() -> {
                try {
                    RegistRequest request = new RegistRequest(userId, math.getLectureId());
                    registrationFacade.regist(request);
                } catch(IllegalArgumentException ex) {
                    if(ex.getMessage().equals("이미 마감된 강의입니다.")){
                        failCnt.incrementAndGet();
                    }
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();

        //then
        assertEquals(10, failCnt);

    }

    @Test
    @DisplayName("동일한 유저가 같은 특강을 5번 신청했을 때 1번만 성공")
    void 중복_신청_방지(){
        //given
        int testCount = 5;
        AtomicInteger successCnt = new AtomicInteger(0);
        AtomicInteger failCnt = new AtomicInteger(0);

        User user1 = new User(1L, "홍길동");
        Lecture math = new Lecture(1L, "이산수학", "장영철", 30, 30);

        when(userService.getUser(1L)).thenReturn(user1);
        when(lectureService.getLecture(1L)).thenReturn(math);


        for (int i = 0; i < testCount; i++) {
            try {
                RegistRequest request = new RegistRequest(user1.getUserId(), math.getLectureId());
                registrationFacade.regist(request);
                successCnt.incrementAndGet();
            } catch(IllegalArgumentException ex){
                if(ex.getMessage().equals("이미 신청한 강의입니다.")){
                    failCnt.incrementAndGet();
                }
            }
        }

        assertEquals(1, successCnt);
        assertEquals(4, failCnt);

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
        // Given
        Long userId = 1L;
        User user = new User();
        List<Registration> registList = new ArrayList<>();
        List<Lecture> lectureList = new ArrayList<>();

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