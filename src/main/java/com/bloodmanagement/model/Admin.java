package com.bloodmanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "admins")
@NoArgsConstructor
@AllArgsConstructor
public class Admin extends BaseUser {

    @Column(name = "department", nullable = false, length = 50)
    private String department;

    @Column(name = "admin_level", length = 20)
    private String adminLevel; // SUPER_ADMIN or STAFF

    @Column(name = "employee_id", unique = true, length = 20)
    private String employeeId;
}