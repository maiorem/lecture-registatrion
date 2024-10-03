package com.hhplus.api.domain.user.repository;

import com.hhplus.api.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public User getUser(Long userId) {
        return userJpaRepository.findUserByUserId(userId);
    }
}
