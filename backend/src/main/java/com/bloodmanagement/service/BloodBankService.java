package com.bloodmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bloodmanagement.dto.BloodBankDTO;
import com.bloodmanagement.model.BloodBank;
import com.bloodmanagement.repository.BloodBankRepository;

import java.util.List;
import java.util.Optional;
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

    // Find a blood bank by name and return as BloodBankDTO
    public BloodBankDTO findBloodBankByName(String name) {
        BloodBank bloodBank = bloodBankRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Blood bank with name " + name + " not found"));
        return toBloodBankDTO(bloodBank);
    }

    // Create or update a blood bank and return as BloodBankDTO
    public BloodBankDTO createBloodBank(BloodBank bloodBank) {
        BloodBank savedBloodBank = bloodBankRepository.save(bloodBank);
        return toBloodBankDTO(savedBloodBank);
    }

    public BloodBankDTO updateBloodBank(BloodBank bloodBank) {
        // Check if the blood bank exists
        BloodBank existingBloodBank = bloodBankRepository.findByName(bloodBank.getName())
                .orElseThrow(() -> new RuntimeException("Blood bank with name " + bloodBank.getName() + " not found"));

        // Update details
        existingBloodBank.setLocation(bloodBank.getLocation());
        existingBloodBank.setContact(bloodBank.getContact());

        // Save and return the updated entity
        return toBloodBankDTO(bloodBankRepository.save(existingBloodBank));
    }

    // Delete a blood bank by name
    public void deleteBloodBank(String name) {
        Optional<BloodBank> bloodBankOptional = bloodBankRepository.findByName(name);
        if (bloodBankOptional.isEmpty()) { // Use isEmpty() to check if the Optional is not present
            throw new IllegalArgumentException("No BloodBank found with name: " + name);
        }
        bloodBankRepository.delete(bloodBankOptional.get());
    }

    // Map BloodBank to BloodBankDTO
    private BloodBankDTO toBloodBankDTO(BloodBank bloodBank) {
        return new BloodBankDTO(
                bloodBank.getName(),
                bloodBank.getLocation(),
                bloodBank.getContact() // Added mapping
        );
    }
}
