package com.bloodmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonorDTO {
    private Integer id;
    private UserDTO user; // Embedded UserDTO for user details
    private Integer politicalId;
    private String bloodGroup;
    private Integer age;
    private String gender;
    private String lastDonationDate;
}
