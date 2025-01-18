package com.bloodmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Integer politicalId;
    private String firstName;
    private String lastName;
    private String username;
    private String role; // Exposed as a String for simplicity
}
