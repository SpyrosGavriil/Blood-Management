package com.bloodmanagement.service;

import org.springframework.stereotype.Service;

import com.bloodmanagement.model.Donor;
import com.bloodmanagement.repository.DonorRepository;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class DonorService {

    private final DonorRepository donorRepository;

    @Autowired
    public DonorService(DonorRepository donorRepository) {
        this.donorRepository = donorRepository;
    }

    public List<Donor> getAllDonors() {
        return donorRepository.findAll();
    }

    public Donor getDonorByPoliticalId(Integer politicalId) {
        return donorRepository.findByPoliticalId(politicalId)
                .orElseThrow(() -> new IllegalArgumentException("Donor not found with politicalId: " + politicalId));
    }

    public Donor createDonor(Donor donor) {
        return donorRepository.save(donor);
    }

    public void deleteDonor(Long id) {
        donorRepository.deleteById(id);
    }
}
