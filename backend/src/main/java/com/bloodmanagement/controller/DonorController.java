package com.bloodmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.bloodmanagement.dto.DonorDTO;
import com.bloodmanagement.model.Donor;
import com.bloodmanagement.service.DonorService;
import java.util.List;

@RestController
@RequestMapping("/api/donors")
public class DonorController {

    private final DonorService donorService;

    @Autowired
    public DonorController(DonorService donorService) {
        this.donorService = donorService;
    }

    @GetMapping("/getAll")
    public List<DonorDTO> getAllDonors() {
        return donorService.getAllDonors();
    }

    @GetMapping("/get/{politicalId}")
    public DonorDTO getDonorByPoliticalId(@PathVariable Integer politicalId) {
        return donorService.getDonorByPoliticalId(politicalId);
    }

    @PostMapping("/create")
    public DonorDTO createDonor(@RequestBody Donor donor) {
        return donorService.createDonor(donor);
    }

    @PostMapping("/update")
    public DonorDTO updateDonor(@RequestBody Donor donor) {
        return donorService.createDonor(donor);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteDonor(@PathVariable Integer id) {
        donorService.deleteDonor(id);
    }
}
