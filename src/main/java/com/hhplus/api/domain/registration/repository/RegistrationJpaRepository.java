package com.hhplus.api.domain.registration.repository;

import com.hhplus.api.domain.registration.domain.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationJpaRepository extends JpaRepository<Registration, Long> {
}
