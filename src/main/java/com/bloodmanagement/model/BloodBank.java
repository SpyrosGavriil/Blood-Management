package com.bloodmanagement.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BloodBank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Name cannot be null")
    @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
    private String name;

    @NotNull(message = "Location cannot be null")
    @Size(min = 5, max = 200, message = "Location must be between 5 and 200 characters")
    private String location;

    @Builder.Default
    @OneToMany(mappedBy = "bloodBank", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DonationRecord> donationRecords = new ArrayList<>();

    public void addDonationRecord(DonationRecord record) {
        donationRecords.add(record);
    }

    public void removeDonationRecord(DonationRecord record) {
        donationRecords.remove(record);
    }
}
