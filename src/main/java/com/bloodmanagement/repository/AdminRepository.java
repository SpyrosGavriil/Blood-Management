package com.bloodmanagement.repository;

import com.bloodmanagement.model.Admin;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminRepository extends BaseUserRepository<Admin> {
    List<Admin> findByDepartment(String department);

    Optional<Admin> findByEmployeeId(String employeeId);
}