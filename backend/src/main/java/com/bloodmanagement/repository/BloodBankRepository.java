package com.bloodmanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bloodmanagement.model.BloodBank;

@Repository
public interface BloodBankRepository extends JpaRepository<BloodBank, Long> {

    // Method to find by name
    Optional<BloodBank> findByName(String name);
}
