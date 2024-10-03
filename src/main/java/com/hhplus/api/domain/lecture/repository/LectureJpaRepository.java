package com.hhplus.api.domain.lecture.repository;

import com.hhplus.api.domain.lecture.domain.Lecture;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LectureJpaRepository extends JpaRepository<Lecture, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT lecture FROM Lecture lecture where lecture.lectureId = :lectureId")
    Lecture findByLectureIdWithLock(@Param("lectureId") Long lectureId);
    
    Lecture findByLectureId(Long lectureId);

    @Query("SELECT lecture FROM Lecture lecture where lecture.lectureId in :courseList and lecture.lectureId not in :registList")
    List<Lecture> findSelectableLectureList(@Param("registList") List<Long> registList, @Param("courseList") List<Long> courseList);

    List<Lecture> findAllByLectureIdIn(List<Long> lectureIdList);
}
