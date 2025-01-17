package com.bloodmanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
// import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Entity
@Builder
@Table(name = " user")
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue
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

    // @Column(name = "blood_group", length = 5)
    // private String bloodGroup;

    // @Column(name = "is_donor", nullable = false)
    // private Boolean isDonor;

    // @Column(name = "last_donation_date")
    // private LocalDate lastDonationDate;

    // @Column(name = "age", nullable = false)
    // private Integer age;

    // @Column(name = "gender", length = 10)
    // private String gender;

    // @Column(name = "eligible_to_donate", nullable = false)
    // private Boolean eligibleToDonate;

    @Enumerated(EnumType.STRING)
    // @Column(name = "role", length = 20)
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
