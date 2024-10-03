package com.hhplus.api.domain.registration.repository;

import com.hhplus.api.domain.lecture.domain.Lecture;
import com.hhplus.api.domain.registration.domain.Registration;
import com.hhplus.api.domain.user.domain.User;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class RegistrationRepositoryImpl implements RegistrationRepository {

    private final RegistrationJpaRepository jpaRepository;

    @Override
    public Registration getSelectedRegist(User user, Lecture lecture) {
        return null;
    }

    @Override
    public Registration regist(Registration builder) {
        return jpaRepository.save(builder);
    }

    @Override
    public List<Registration> getRegistListByUser(User user) {
        return jpaRepository.findAllByUserId(user.getUserId());
    }
}
