package com.bloodmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bloodmanagement.dto.BloodBankDTO;
import com.bloodmanagement.dto.DonorDTO;
import com.bloodmanagement.model.BloodBank;
import com.bloodmanagement.model.Donor;
import com.bloodmanagement.service.BloodBankService;

import java.util.List;

@RestController
@RequestMapping("/api/blood-banks")
public class BloodBankController {

    private final BloodBankService bloodBankService;

    @Autowired
    public BloodBankController(BloodBankService bloodBankService) {
        this.bloodBankService = bloodBankService;
    }

    @GetMapping("/getAll")
    public List<BloodBankDTO> getAllBloodBanks() {
        return bloodBankService.getAllBloodBanks();
    }

    @GetMapping("/findByName/{name}")
    public ResponseEntity<BloodBankDTO> findByName(@PathVariable String name) {
        BloodBankDTO bloodBank = bloodBankService.findBloodBankByName(name);
        return ResponseEntity.ok(bloodBank);
    }

    @PostMapping("/create")
    public BloodBankDTO createOrUpdateBloodBank(@RequestBody BloodBank bloodBank) {
        return bloodBankService.createBloodBank(bloodBank);
    }

    @PostMapping("/update")
    public BloodBankDTO updateDonor(@RequestBody BloodBank bloodBank) {
        return bloodBankService.updateBloodBank(bloodBank);
    }

    @DeleteMapping("/delete/{name}")
    public void deleteBloodBank(@PathVariable String name) {
        bloodBankService.deleteBloodBank(name);
    }
}
