package com.bloodmanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
    @NotNull(message = "User cannot be null")
    private User user;

    @Column(unique = true, nullable = false)
    @NotNull(message = "Political ID cannot be null")
    private Integer politicalId;

    @NotBlank(message = "Blood group cannot be blank")
    private String bloodGroup;

    @NotNull(message = "Age cannot be null")
    @Min(value = 18, message = "Age must be at least 18")
    @Max(value = 65, message = "Age must not exceed 65")
    private Integer age;

    @NotBlank(message = "Gender cannot be blank")
    private String gender;

    private String lastDonationDate;
}
