package com.hhplus.api.domain.user.repository;

import com.hhplus.api.domain.user.domain.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {

    User getUser(Long userId);

}
