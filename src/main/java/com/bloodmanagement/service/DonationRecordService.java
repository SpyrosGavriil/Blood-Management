package com.bloodmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bloodmanagement.dto.*;
import com.bloodmanagement.model.DonationRecord;
import com.bloodmanagement.model.Donor;
import com.bloodmanagement.model.User;
import com.bloodmanagement.repository.DonationRecordRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DonationRecordService {

    private final DonationRecordRepository donationRecordRepository;

    @Autowired
    public DonationRecordService(DonationRecordRepository donationRecordRepository) {
        this.donationRecordRepository = donationRecordRepository;
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
        List<DonationRecord> records = donationRecordRepository.findByDonorId(donorId);

        // Map each DonationRecord to DonationRecordDTO
        return records.stream()
                .map(this::toDonationRecordDTO) // Use the existing mapping method
                .collect(Collectors.toList());
    }

    public List<DonationRecordDTO> getDonationRecordsByBloodBankId(Long bloodBankId) {
        // Fetch records by blood bank ID
        List<DonationRecord> records = donationRecordRepository.findByBloodBankId(bloodBankId);

        // Map each DonationRecord to DonationRecordDTO
        return records.stream()
                .map(this::toDonationRecordDTO) // Use the existing mapping method
                .collect(Collectors.toList());
    }

    public DonationRecordDTO createOrUpdateDonationRecord(DonationRecord donationRecord) {
        Optional<DonationRecord> existingRecordOpt = donationRecordRepository.findByDonorAndBloodBankAndDonationDate(
                donationRecord.getDonor(),
                donationRecord.getBloodBank(),
                donationRecord.getDonationDate());

        if (existingRecordOpt.isPresent()) {
            DonationRecord existingRecord = existingRecordOpt.get();

            // Check if the existing record has the same values
            if (existingRecord.equals(donationRecord)) {
                throw new IllegalArgumentException("DonationRecord already exists with the same values");
            } else {
                // Update the existing record with the new values
                existingRecord.setDonor(donationRecord.getDonor());
                existingRecord.setBloodBank(donationRecord.getBloodBank());
                existingRecord.setDonationDate(donationRecord.getDonationDate());
                DonationRecord updatedRecord = donationRecordRepository.save(existingRecord);
                return toDonationRecordDTO(updatedRecord); // Convert to DTO
            }
        } else {
            // If the record is not in the database, create a new one
            DonationRecord savedRecord = donationRecordRepository.save(donationRecord);
            return toDonationRecordDTO(savedRecord); // Convert to DTO
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
                record.getId(),
                toDonorDTO(record.getDonor()),
                record.getDonationDate().toString(), // Map Donor to DonorDTO
                record.getBloodBank().getName());
    }

    // Map Donor to DonorDTO
    private DonorDTO toDonorDTO(Donor donor) {
        return new DonorDTO(
                donor.getId(),
                toUserDTO(donor.getUser()),
                donor.getPoliticalId(),
                donor.getBloodGroup(),
                donor.getAge(),
                donor.getGender(),
                donor.getLastDonationDate());
    }

    // Map User to UserDTO
    private UserDTO toUserDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getRole().toString());
    }
}
