package com.bloodmanagement.repository;

import com.bloodmanagement.model.BaseUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface BaseUserRepository<T extends BaseUser> extends JpaRepository<T, Long> {
    Optional<T> findByUsername(String username);

    Boolean existsByUsername(String username);
}