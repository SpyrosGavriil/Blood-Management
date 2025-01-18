package com.bloodmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bloodmanagement.model.DonationRecord;

import java.util.List;

@Repository
public interface DonationRecordRepository extends JpaRepository<DonationRecord, Long> {

    List<DonationRecord> findByBloodBankName(String name);

    List<DonationRecord> findByPoliticalId(Integer politicalId);

}
