package com.bloodmanagement.model;

import java.time.LocalDate;

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
public class DonationRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generated primary key
    private Long id; // Unique for each record

    @JoinColumn(name = "politicalId", nullable = false)
    private Integer politicalId;

    @ManyToOne
    @JoinColumn(name = "blood_bank_name", nullable = false)
    private BloodBank bloodBank;

    private LocalDate donationDate;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        DonationRecord that = (DonationRecord) o;

        // Compare each field for equality
        if (this.politicalId != that.politicalId)
            return false;
        if (!bloodBank.equals(that.bloodBank))
            return false;
        return donationDate.equals(that.donationDate);
    }

    @Override
    public int hashCode() {
        int result = politicalId.hashCode();
        result = 31 * result + bloodBank.hashCode();
        result = 31 * result + donationDate.hashCode();
        return result;
    }
}
