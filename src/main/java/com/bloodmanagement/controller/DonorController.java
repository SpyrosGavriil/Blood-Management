package com.bloodmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.bloodmanagement.model.Donor;
import com.bloodmanagement.service.DonorService;

@RestController
@RequestMapping("/api/donors")
public class DonorController {

    private final DonorService donorService;

    @Autowired
    public DonorController(DonorService donorService) {
        this.donorService = donorService;
    }

    @GetMapping("/{politicalId}")
    public Donor getDonorByPoliticalId(@PathVariable Integer politicalId) {
        return donorService.getDonorByPoliticalId(politicalId);
    }

}
