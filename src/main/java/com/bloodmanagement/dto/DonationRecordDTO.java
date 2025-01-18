package com.bloodmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonationRecordDTO {
    private Integer politicalId;
    private String bloodBankName; // Only the name of the blood bank
    private String donationDate; // Formatted as String for easier handling
}
