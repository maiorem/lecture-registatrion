package com.hhplus.api.domain.registration.repository;

import com.hhplus.api.domain.lecture.domain.Lecture;
import com.hhplus.api.domain.registration.domain.Registration;
import com.hhplus.api.domain.user.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegistrationRepository {
    Registration getSelectedRegist(User user, Lecture lecture);

    Registration regist(Registration builder);

    List<Registration> getRegistListByUser(User user);
}
