package com.bloodmanagement.service;

import org.springframework.stereotype.Service;

import com.bloodmanagement.dto.DonorDTO;
import com.bloodmanagement.model.Donor;
import com.bloodmanagement.repository.DonorRepository;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DonorService {

    private final DonorRepository donorRepository;

    @Autowired
    public DonorService(DonorRepository donorRepository) {
        this.donorRepository = donorRepository;
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

    // Delete a donor by ID
    public void deleteDonor(Integer id) {
        if (!donorRepository.existsById(id)) {
            throw new IllegalArgumentException("Donor not found with ID: " + id);
        }
        donorRepository.deleteById(id);
    }

    // Map Donor to DonorDTO
    private DonorDTO toDonorDTO(Donor donor) {
        return new DonorDTO(
                donor.getPoliticalId(),
                donor.getBloodGroup(),
                donor.getAge(),
                donor.getGender(),
                donor.getLastDonationDate() // Map User to UserDTO
        );
    }
}
