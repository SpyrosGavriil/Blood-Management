package com.bloodmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public List<DonationRecordDTO> getDonationRecordsByBloodBank(@PathVariable String bloodBankId) {
        return donationRecordService.getDonationRecordsByBloodBankId(bloodBankId);
    }

    @PostMapping("/create")
    public DonationRecordDTO createDonationRecord(@RequestBody DonationRecord donationRecord) {
        return donationRecordService.createDonationRecord(donationRecord);
    }

    @PostMapping("/update")
    public DonationRecordDTO updateDonationRecord(@RequestBody DonationRecord record) {
        return donationRecordService.updateDonationRecord(record);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteDonationRecord(@PathVariable Long id) {
    donationRecordService.deleteDonationRecord(id);
    return ResponseEntity.noContent().build();
}
}
