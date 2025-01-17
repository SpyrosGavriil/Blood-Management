package com.bloodmanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Donor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @Column(unique = true, nullable = false)
    private Integer politicalId;

    private String bloodGroup;
    private Boolean eligibleToDonate;
    private Integer age;
    private String gender;
    private Boolean isActiveDonor;
    private String lastDonationDate; // Change to LocalDate if needed
}
