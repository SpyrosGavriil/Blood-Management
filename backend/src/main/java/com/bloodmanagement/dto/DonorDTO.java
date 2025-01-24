package com.bloodmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonorDTO {
    private String firstName;
    private String lastName;
    private Integer politicalId;
    private String bloodGroup;
    private Integer age;
    private String gender;
    private String lastDonationDate;
    private Long phoneNumber;
}
