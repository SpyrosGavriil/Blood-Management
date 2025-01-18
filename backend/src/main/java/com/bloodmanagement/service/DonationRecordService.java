package com.bloodmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bloodmanagement.dto.*;
import com.bloodmanagement.model.DonationRecord;
import com.bloodmanagement.model.Donor;
import com.bloodmanagement.repository.DonationRecordRepository;
import com.bloodmanagement.repository.DonorRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DonationRecordService {

    private final DonationRecordRepository donationRecordRepository;
    private final DonorRepository donorRepository;

    @Autowired
    public DonationRecordService(DonationRecordRepository donationRecordRepository, DonorRepository donorRepository) {
        this.donationRecordRepository = donationRecordRepository;
        this.donorRepository = donorRepository;
    }

    public List<DonationRecordDTO> getAllDonationRecords() {
        return donationRecordRepository.findAll()
                .stream()
                .map(this::toDonationRecordDTO)
                .collect(Collectors.toList());
    }

    public DonationRecordDTO getDonationRecordById(Long id) {
        return donationRecordRepository.findById(id)
                .map(this::toDonationRecordDTO)
                .orElseThrow(() -> new IllegalArgumentException("DonationRecord not found with ID: " + id));
    }

    // Get Donation Records by Donor ID as DTOs
    public List<DonationRecordDTO> getDonationRecordsByDonorId(Integer donorId) {
        // Fetch records by donor ID
        List<DonationRecord> records = donationRecordRepository.findByPoliticalId(donorId);

        // Map each DonationRecord to DonationRecordDTO
        return records.stream()
                .map(this::toDonationRecordDTO) // Use the existing mapping method
                .collect(Collectors.toList());
    }

    public List<DonationRecordDTO> getDonationRecordsByBloodBankId(String name) {
        // Fetch records by blood bank ID
        List<DonationRecord> records = donationRecordRepository.findByBloodBankName(name);

        if (records == null) {
            throw new IllegalArgumentException("Blood bank with name '" + name + "' not found.");
        }

        // Map each DonationRecord to DonationRecordDTO
        return records.stream()
                .map(this::toDonationRecordDTO) // Use the existing mapping method
                .collect(Collectors.toList());
    }

    public DonationRecordDTO createDonationRecord(DonationRecord donationRecord) {
        // Search for a donor by political ID
        Optional<Donor> donorOpt = donorRepository.findByPoliticalId(donationRecord.getPoliticalId());

        if (donorOpt.isPresent()) {
            // Save the new donation record
            DonationRecord savedRecord = donationRecordRepository.save(donationRecord);
            donorOpt.get().setLastDonationDate(donationRecord.getDonationDate().toString());

            // Convert the saved donation record to a DTO and return
            return toDonationRecordDTO(savedRecord);
        } else {
            throw new IllegalArgumentException("Donor with the specified political ID does not exist");
        }
    }

    public void deleteDonationRecord(Long id) {
        if (!donationRecordRepository.existsById(id)) {
            throw new IllegalArgumentException("DonationRecord not found with ID: " + id);
        }
        donationRecordRepository.deleteById(id);
    }

    // Map DonationRecord to DonationRecordDTO
    private DonationRecordDTO toDonationRecordDTO(DonationRecord record) {
        return new DonationRecordDTO(
                record.getPoliticalId(),
                record.getDonationDate().toString(), // Map Donor to DonorDTO
                record.getBloodBank().getName());
    }
}