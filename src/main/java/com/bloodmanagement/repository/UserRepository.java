package com.bloodmanagement.repository;

import com.bloodmanagement.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends BaseUserRepository<User> {
    List<User> findByBloodGroup(String bloodGroup);

    List<User> findByIsDonorTrue();

    List<User> findByEligibleToDonateTrue();
}