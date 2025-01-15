package com.bloodmanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseUser {
    
    @Column(name = "blood_group", length = 5)
    private String bloodGroup;
    
    @Column(name = "is_donor")
    private Boolean isDonor = false;
    
    @Column(name = "last_donation_date")
    private LocalDate lastDonationDate;
    
    @Column(name = "age")
    private Integer age;
    
    @Column(name = "gender", length = 10)
    private String gender;
    
    @Column(name = "eligible_to_donate")
    private Boolean eligibleToDonate = true;
    
}
