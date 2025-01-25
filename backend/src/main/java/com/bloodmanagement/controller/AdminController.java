package com.bloodmanagement.controller;

import com.bloodmanagement.dto.AdminDTO;
import com.bloodmanagement.model.Admin;
import com.bloodmanagement.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admins")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // Get all admins
    @GetMapping("/getAll")
    public ResponseEntity<List<AdminDTO>> getAllAdmins() {
        return ResponseEntity.ok(adminService.getAllAdmins());
    }

    // Get a specific admin by ID
    @GetMapping("/get/{id}")
    public ResponseEntity<AdminDTO> getAdminById(@PathVariable Integer id) {
        return adminService.getAdminById(id)
                .map(admin -> ResponseEntity.ok(toAdminDTO(admin)))
                .orElse(ResponseEntity.notFound().build());
    }

    // Create a new admin
    @PostMapping("/create")
    public ResponseEntity<AdminDTO> createAdmin(@RequestBody Admin admin) {
        AdminDTO createdAdmin = adminService.saveAdmin(admin);
        return ResponseEntity.ok(createdAdmin);
    }

    // Update an existing admin
    @PutMapping("/update/{id}")
    public ResponseEntity<AdminDTO> updateAdmin(@PathVariable Integer id, @RequestBody Admin admin) {
        Optional<Admin> existingAdminOpt = adminService.getAdminById(id);
        if (!existingAdminOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Admin existingAdmin = existingAdminOpt.get();

        // Update the values
        existingAdmin.setFirstName(admin.getFirstName());
        existingAdmin.setLastName(admin.getLastName());
        existingAdmin.setUsername(admin.getUsername());
        existingAdmin.setRole(admin.getRole());

        // Retain the existing password
        existingAdmin.setPassword(existingAdmin.getPassword());

        AdminDTO updatedAdmin = adminService.saveAdmin(existingAdmin);
        return ResponseEntity.ok(updatedAdmin);
    }

    // Delete an admin by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Integer id) {
        if (!adminService.getAdminById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        adminService.deleteAdmin(id);
        return ResponseEntity.noContent().build();
    }

    // Convert Admin entity to AdminDTO
    private AdminDTO toAdminDTO(Admin admin) {
        return new AdminDTO(
                admin.getPoliticalId(),
                admin.getFirstName(),
                admin.getLastName(),
                admin.getUsername(),
                admin.getRole().toString()
        );
    }
}
