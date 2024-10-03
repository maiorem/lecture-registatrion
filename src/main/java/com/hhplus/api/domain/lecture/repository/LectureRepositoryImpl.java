package com.hhplus.api.domain.lecture.repository;

import com.hhplus.api.domain.course.domain.Course;
import com.hhplus.api.domain.lecture.domain.Lecture;
import com.hhplus.api.domain.registration.domain.Registration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class LectureRepositoryImpl implements LectureRepository {

    private final LectureJpaRepository lectureJpaRepository;

    @Override
    public Lecture getLectureWithLock(Long lectureId) {
        return lectureJpaRepository.findByLectureIdWithLock(lectureId);
    }

    @Override
    public Lecture getLecture(Long lectureId) {
        return lectureJpaRepository.findByLectureId(lectureId);
    }

    @Override
    public Lecture save(Lecture lecture) {
        return lectureJpaRepository.save(lecture);
    }

    @Override
    public List<Lecture> getSeletableLecturelist(List<Long> registList, List<Long> courseList) {
        return lectureJpaRepository.findSelectableLectureList(registList, courseList);
    }

    @Override
    public List<Lecture> getRegistCompleteList(List<Long> lectureIdList) {
        return lectureJpaRepository.findAllByLectureIdIn(lectureIdList);
    }
}
