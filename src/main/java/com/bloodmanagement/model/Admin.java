package com.bloodmanagement.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@Table(name = "admins")
@NoArgsConstructor
@AllArgsConstructor
public class Admin implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    // @Column(name = "political_id", nullable = false)
    // private Integer politicalId;

    // @Column(name = "first_name", length = 50, nullable = false)
    // private String firstName;

    // @Column(name = "last_name", length = 50, nullable = false)
    // private String lastName;

    // @Column(name = "email", length = 100, nullable = false, unique = true)
    // private String email;

    // @Column(name = "username", length = 50, nullable = false, unique = true)
    // private String username;

    // @Column(name = "password", length = 255, nullable = false)
    // private String password;

    // @Column(name = "age", nullable = false)
    // private Integer age;

    // @Column(name = "gender", length = 10)
    // private String gender;

    // @Column(name = "department", nullable = false, length = 50)
    // private String department;

    // @Column(name = "employee_id", unique = true, length = 20)
    // private String employeeId;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 20)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getUsername() {
        return this.username;
    }

}