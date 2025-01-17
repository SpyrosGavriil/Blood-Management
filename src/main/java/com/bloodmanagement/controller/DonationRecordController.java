package com.bloodmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.bloodmanagement.dto.DonationRecordDTO;
import com.bloodmanagement.model.DonationRecord;
import com.bloodmanagement.service.DonationRecordService;

import java.util.List;

@RestController
@RequestMapping("/api/donation-records")
public class DonationRecordController {

    private final DonationRecordService donationRecordService;

    @Autowired
    public DonationRecordController(DonationRecordService donationRecordService) {
        this.donationRecordService = donationRecordService;
    }

    @GetMapping("/getAll")
    public List<DonationRecordDTO> getAllDonationRecords() {
        return donationRecordService.getAllDonationRecords();
    }

    @GetMapping("get/{id}")
    public DonationRecordDTO getDonationRecordById(@PathVariable Long id) {
        return donationRecordService.getDonationRecordById(id);
    }

    @GetMapping("/donor/{donorId}")
    public List<DonationRecordDTO> getDonationRecordsByDonor(@PathVariable Integer donorId) {
        return donationRecordService.getDonationRecordsByDonorId(donorId);
    }

    @GetMapping("/blood-bank/{bloodBankId}")
    public List<DonationRecordDTO> getDonationRecordsByBloodBank(@PathVariable Long bloodBankId) {
        return donationRecordService.getDonationRecordsByBloodBankId(bloodBankId);
    }

    @PostMapping("/create")
    public DonationRecordDTO createOrUpdateDonationRecord(@RequestBody DonationRecord donationRecord){
        return donationRecordService.createOrUpdateDonationRecord(donationRecord);
    }

    @DeleteMapping("delete/{id}")
    public void deleteDonationRecord(@PathVariable Long id) {
        donationRecordService.deleteDonationRecord(id);
    }
}
