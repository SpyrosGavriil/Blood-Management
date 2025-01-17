package com.bloodmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bloodmanagement.model.BloodBank;
import com.bloodmanagement.repository.BloodBankRepository;

import java.util.List;

@Service
public class BloodBankService {

    private final BloodBankRepository bloodBankRepository;

    @Autowired
    public BloodBankService(BloodBankRepository bloodBankRepository) {
        this.bloodBankRepository = bloodBankRepository;
    }

    public List<BloodBank> getAllBloodBanks() {
        return bloodBankRepository.findAll();
    }

    public BloodBank getBloodBankById(Long id) {
        return bloodBankRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No BloodBank found with ID: " + id));
    }

    public BloodBank createOrUpdateBloodBank(BloodBank bloodBank) {
        return bloodBankRepository.save(bloodBank);
    }

    public void deleteBloodBank(Long id) {
        bloodBankRepository.deleteById(id);
    }
}
