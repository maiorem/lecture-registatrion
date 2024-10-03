package com.hhplus.api.domain.lecture.domain;

import com.hhplus.api.domain.course.domain.Course;
import com.hhplus.api.domain.lecture.repository.LectureRepository;
import com.hhplus.api.domain.registration.domain.Registration;
import com.hhplus.api.domain.user.domain.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LectureService {

    private final LectureRepository lectureRepository;

    public LectureService(LectureRepository lectureRepository) {
        this.lectureRepository = lectureRepository;
    }

    public Lecture getLectureWithLock(Long lectureId){
        return lectureRepository.getLectureWithLock(lectureId);
    }

    public Lecture getLecture(Long lectureId) {
        return lectureRepository.getLecture(lectureId);
    }


    public void seatSelect(Lecture lecture) {
        lecture.selectSeat();
        lectureRepository.save(lecture);
    }

    public List<Lecture> getSelectableLectureList(List<Registration> registList, List<Course> courseList) {
        //regist list에 존재하지 않으면서 courseList에 해당하는 lecture
        List<Long> notInLectureIdList = new ArrayList<>();
        List<Long> inLectureIdList = new ArrayList<>();
        for (Registration registration : registList) {
            notInLectureIdList.add(registration.getLectureId());
        }
        for (Course course : courseList) {
            inLectureIdList.add(course.getLectureId());
        }
        return lectureRepository.getSeletableLecturelist(notInLectureIdList, inLectureIdList);
    }

    public List<Lecture> registCompleteList(List<Registration> registList) {
        List<Long> lectureIdList = new ArrayList<>();
        for (Registration registration : registList) {
            lectureIdList.add(registration.getLectureId());
        }
        return lectureRepository.getRegistCompleteList(lectureIdList);
    }
}
