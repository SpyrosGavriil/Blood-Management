package com.bloodmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.bloodmanagement.model.BloodBank;
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
    public List<BloodBank> getAllBloodBanks() {
        return bloodBankService.getAllBloodBanks();
    }

    @GetMapping("/{id}")
    public BloodBank getBloodBankById(@PathVariable Long id) {
        return bloodBankService.getBloodBankById(id);
    }

    @PostMapping("/update")
    public BloodBank createOrUpdateBloodBank(@RequestBody BloodBank bloodBank) {
        return bloodBankService.createOrUpdateBloodBank(bloodBank);
    }

    @DeleteMapping("delete/{id}")
    public void deleteBloodBank(@PathVariable Long id) {
        bloodBankService.deleteBloodBank(id);
    }
}
