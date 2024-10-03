package com.hhplus.api.domain.registration.domain;


import com.hhplus.api.domain.course.domain.Course;
import com.hhplus.api.domain.lecture.domain.Lecture;
import com.hhplus.api.domain.registration.repository.RegistrationRepository;
import com.hhplus.api.domain.user.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistrationService {

    private final RegistrationRepository registrationRepository;

    public RegistrationService(RegistrationRepository registrationRepository) {
        this.registrationRepository = registrationRepository;
    }

    public boolean getSelectedRegist(User user, Lecture lecture) {
        boolean isSelected = false;
        Registration regist = registrationRepository.getSelectedRegist(user, lecture);
        if (regist != null) isSelected = true;
        return isSelected;
    }

    public Registration regist(User user, Lecture lecture) {
        Registration builder = Registration.registBuilder()
                        .lectureId(lecture.getLectureId())
                        .userId(user.getUserId())
                        .build();
        Registration regist = registrationRepository.regist(builder);
        return regist;
    }


    public List<Registration> getSelectedRegistList(User user) {
        return registrationRepository.getRegistListByUser(user);
    }
}
