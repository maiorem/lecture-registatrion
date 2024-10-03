package com.hhplus.api.domain.registration.repository;

import com.hhplus.api.domain.registration.domain.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegistrationJpaRepository extends JpaRepository<Registration, Long> {

    List<Registration> findAllByUserId(Long userId);
}
