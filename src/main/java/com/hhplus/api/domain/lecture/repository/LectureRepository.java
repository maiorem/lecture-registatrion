package com.hhplus.api.domain.lecture.repository;

import com.hhplus.api.domain.course.domain.Course;
import com.hhplus.api.domain.lecture.domain.Lecture;
import com.hhplus.api.domain.registration.domain.Registration;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LectureRepository{

    Lecture getLectureWithLock(Long lectureId);

    Lecture getLecture(Long lectureId);

    Lecture save(Lecture lecture);

    List<Lecture> getSeletableLecturelist(List<Long> notInList, List<Long> inList);

    List<Lecture> getRegistCompleteList(List<Long> lectureIdList);
}
