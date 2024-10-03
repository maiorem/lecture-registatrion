package com.hhplus.api.domain.user.repository;

import com.hhplus.api.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Long> {

    User findUserByUserId(Long userId);
}
