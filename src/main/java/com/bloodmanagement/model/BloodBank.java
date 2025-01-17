package com.bloodmanagement.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BloodBank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

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
