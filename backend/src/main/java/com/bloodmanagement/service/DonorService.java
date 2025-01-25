package com.bloodmanagement.service;

import org.springframework.stereotype.Service;

import com.bloodmanagement.dto.DonorDTO;
import com.bloodmanagement.model.Donor;
import com.bloodmanagement.repository.DonorRepository;
import com.bloodmanagement.repository.UserRepository;
import com.bloodmanagement.model.User;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DonorService {

    private final DonorRepository donorRepository;
    private final UserRepository userRepository;

    @Autowired
    public DonorService(DonorRepository donorRepository, UserRepository userRepository) {
        this.donorRepository = donorRepository;
        this.userRepository = userRepository;
    }

    // Get all donors and return as DonorDTO
    public List<DonorDTO> getAllDonors() {
        return donorRepository.findAll()
                .stream()
                .map(this::toDonorDTO) // Map each Donor to DonorDTO
                .collect(Collectors.toList());
    }

    // Get a donor by political ID and return as DonorDTO
    public DonorDTO getDonorByPoliticalId(Integer politicalId) {
        Donor donor = donorRepository.findByPoliticalId(politicalId)
                .orElseThrow(() -> new IllegalArgumentException("Donor not found with politicalId: " + politicalId));
        return toDonorDTO(donor);
    }

    // Create a new donor and return as DonorDTO
    public DonorDTO createDonor(Donor donor) {
        Donor savedDonor = donorRepository.save(donor);
        return toDonorDTO(savedDonor);
    }

    public Donor updateDonor(Donor donor) {
        // Check if the donor exists
        Donor existingDonor = donorRepository.findByPoliticalId(donor.getPoliticalId())
                .orElseThrow(() -> new RuntimeException("Donor with ID " + donor.getPoliticalId() + " not found"));

        // Update details
        existingDonor.setBloodGroup(donor.getBloodGroup());
        existingDonor.setPhoneNumber(donor.getPhoneNumber());
        existingDonor.setLastDonationDate(donor.getLastDonationDate());
        existingDonor.setAge(donor.getAge());
        existingDonor.setGender(donor.getGender());

        // Save and return the updated entity
        return donorRepository.save(existingDonor);
    }

    public void deleteDonor(Integer id) {
        if (!donorRepository.existsById(id)) {
            throw new IllegalArgumentException("Donor not found with ID: " + id);
        }
        donorRepository.deleteById(id);
    }

    // Map Donor to DonorDTO
    private DonorDTO toDonorDTO(Donor donor) {

        User user = userRepository.findByPoliticalId(donor.getPoliticalId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "User not found with politicalId: " + donor.getPoliticalId()));

        return new DonorDTO(
                user.getFirstName(),
                user.getLastName(),
                donor.getPoliticalId(),
                donor.getBloodGroup(),
                donor.getAge(),
                donor.getGender(),
                donor.getLastDonationDate(), // Map User to UserDTO
                donor.getPhoneNumber());
    }
}
