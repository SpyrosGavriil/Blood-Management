package com.bloodmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.bloodmanagement.dto.*;
import com.bloodmanagement.model.BloodBank;
import com.bloodmanagement.model.DonationRecord;
import com.bloodmanagement.model.Donor;
import com.bloodmanagement.repository.BloodBankRepository;
import com.bloodmanagement.repository.DonationRecordRepository;
import com.bloodmanagement.repository.DonorRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DonationRecordService {

    private final DonationRecordRepository donationRecordRepository;
    private final DonorRepository donorRepository;
    private final BloodBankRepository bloodBankRepository;

    @Autowired
    public DonationRecordService(DonationRecordRepository donationRecordRepository, DonorRepository donorRepository,
            BloodBankRepository bloodBankRepository) {
        this.donationRecordRepository = donationRecordRepository;
        this.donorRepository = donorRepository;
        this.bloodBankRepository = bloodBankRepository;
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

    @PostMapping("/create")
    public DonationRecordDTO createDonationRecord(@RequestBody DonationRecord donationRecord) {
        // Fetch blood bank details by name
        String bloodBankName = donationRecord.getBloodBank().getName();
        BloodBank bloodBank = bloodBankRepository.findByName(bloodBankName)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Blood Bank with name " + bloodBankName + " does not exist"));

        // Set the complete BloodBank object in the donation record
        donationRecord.setBloodBank(bloodBank);
        // bloodBank.addDonationRecord(donationRecord); // Add the donation record to the blood bank

        // Fetch donor details by political ID
        Optional<Donor> donorOpt = donorRepository.findByPoliticalId(donationRecord.getPoliticalId());
        if (donorOpt.isPresent()) {
            Donor donor = donorOpt.get();

            // Parse the last donation date and the incoming donation date
            LocalDate incomingDonationDate = donationRecord.getDonationDate();
            LocalDate lastDonationDate = donor.getLastDonationDate() != null
                    ? LocalDate.parse(donor.getLastDonationDate())
                    : null;

            // Check if the incoming date is after the last donation date
            if (lastDonationDate == null || incomingDonationDate.isAfter(lastDonationDate)) {
                donor.setLastDonationDate(incomingDonationDate.toString());
                donorRepository.save(donor); // Persist the updated donor
            }
        }

        // Save the donation record
        DonationRecord savedRecord = donationRecordRepository.save(donationRecord);

        // Return the saved donation record as a DTO
        return toDonationRecordDTO(savedRecord);
    }

    public DonationRecordDTO updateDonationRecord(DonationRecord donationRecord) {
        // Check if the donation record exists
        DonationRecord existingRecord = donationRecordRepository.findById(donationRecord.getId())
                .orElseThrow(() -> new RuntimeException("Donation record with ID " + donationRecord.getId() + " not found"));

        // Fetch and validate the associated blood bank
        BloodBank bloodBank = bloodBankRepository.findByName(donationRecord.getBloodBank().getName())
                .orElseThrow(() -> new RuntimeException("Blood bank with name " + donationRecord.getBloodBank().getName() + " not found"));

        // Update details
        existingRecord.setBloodBank(bloodBank);
        existingRecord.setDonationDate(donationRecord.getDonationDate());

        // Save and return the updated entity
        return toDonationRecordDTO(donationRecordRepository.save(existingRecord));
    }

    public void deleteDonationRecord(Long id) {
        // Check if the record exists
        if (!donationRecordRepository.existsById(id)) {
            throw new IllegalArgumentException("DonationRecord not found with ID: " + id);
        }
    
        // Fetch the record to be deleted
        DonationRecord deletedRecord = donationRecordRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("DonationRecord not found with ID: " + id));
        Integer politicalId = deletedRecord.getPoliticalId();
    
        // Delete the record
        donationRecordRepository.deleteById(id);
    
        // Fetch all remaining records for the donor
        List<DonationRecord> remainingRecords = donationRecordRepository.findByPoliticalId(politicalId);
    
        // Find the latest donation date before the deleted one
        LocalDate latestDonationDate = remainingRecords.stream()
                .map(DonationRecord::getDonationDate)
                .max(LocalDate::compareTo)
                .orElse(null);
    
        // Update the donor's last donation date
        Donor donor = donorRepository.findByPoliticalId(politicalId)
                .orElseThrow(() -> new IllegalArgumentException("Donor not found with political ID: " + politicalId));
        donor.setLastDonationDate(latestDonationDate != null ? latestDonationDate.toString() : null);
        donorRepository.save(donor);
    }

    // Map DonationRecord to DonationRecordDTO
    private DonationRecordDTO toDonationRecordDTO(DonationRecord record) {
        return new DonationRecordDTO(
                record.getId(),
                record.getPoliticalId(),
                record.getBloodBank().getName(),
                record.getDonationDate().toString()); // Map Donor to DonorDTO
    }
}