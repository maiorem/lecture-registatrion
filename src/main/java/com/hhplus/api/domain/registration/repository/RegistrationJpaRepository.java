package com.hhplus.api.domain.registration.repository;

import com.hhplus.api.domain.registration.domain.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegistrationJpaRepository extends JpaRepository<Registration, Long> {

    List<Registration> findAllByUserId(Long userId);
}
