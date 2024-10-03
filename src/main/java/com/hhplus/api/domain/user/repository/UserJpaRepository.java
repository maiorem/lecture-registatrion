package com.hhplus.api.domain.user.repository;

import com.hhplus.api.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long> {

    User findUserByUserId(Long userId);
}
