package com.bloodmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bloodmanagement.dto.BloodBankDTO;
import com.bloodmanagement.model.BloodBank;
import com.bloodmanagement.repository.BloodBankRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BloodBankService {

    private final BloodBankRepository bloodBankRepository;

    @Autowired
    public BloodBankService(BloodBankRepository bloodBankRepository) {
        this.bloodBankRepository = bloodBankRepository;
    }

    // Get all blood banks and return as BloodBankDTO
    public List<BloodBankDTO> getAllBloodBanks() {
        return bloodBankRepository.findAll()
                .stream()
                .map(this::toBloodBankDTO) // Map each BloodBank to BloodBankDTO
                .collect(Collectors.toList());
    }

    // Get a blood bank by ID and return as BloodBankDTO
    public BloodBankDTO getBloodBankById(Long id) {
        BloodBank bloodBank = bloodBankRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No BloodBank found with ID: " + id));
        return toBloodBankDTO(bloodBank);
    }

    // Create or update a blood bank and return as BloodBankDTO
    public BloodBankDTO createOrUpdateBloodBank(BloodBank bloodBank) {
        BloodBank savedBloodBank = bloodBankRepository.save(bloodBank);
        return toBloodBankDTO(savedBloodBank);
    }

    // Delete a blood bank by ID
    public void deleteBloodBank(Long id) {
        if (!bloodBankRepository.existsById(id)) {
            throw new IllegalArgumentException("No BloodBank found with ID: " + id);
        }
        bloodBankRepository.deleteById(id);
    }

    // Map BloodBank to BloodBankDTO
    private BloodBankDTO toBloodBankDTO(BloodBank bloodBank) {
        return new BloodBankDTO(
                bloodBank.getId(),
                bloodBank.getName(),
                bloodBank.getLocation());
    }
}
