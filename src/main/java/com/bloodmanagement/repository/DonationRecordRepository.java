package com.bloodmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bloodmanagement.model.BloodBank;
import com.bloodmanagement.model.DonationRecord;
import com.bloodmanagement.model.Donor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DonationRecordRepository extends JpaRepository<DonationRecord, Long> {

    List<DonationRecord> findByDonorId(Integer donorId);

    List<DonationRecord> findByBloodBankId(Long bloodBankId);

    Optional<DonationRecord> findByDonorAndBloodBankAndDonationDate(Donor donor, BloodBank bloodBank,
            LocalDate donationDate);
}
