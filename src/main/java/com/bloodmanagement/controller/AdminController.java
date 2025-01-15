package com.bloodmanagement.controller;

import com.bloodmanagement.model.Admin;
import com.bloodmanagement.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<List<Admin>> getAllAdmins() {
        return ResponseEntity.ok(adminService.getAllAdmins());
    }

    // Get a specific admin by ID
    @GetMapping("/get/{id}")
    public ResponseEntity<Admin> getAdminById(@PathVariable Long id) {
        return adminService.getAdminById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create a new admin
    @PostMapping("/create")
    public ResponseEntity<Admin> createAdmin(@RequestBody Admin admin) {
        Admin createdAdmin = adminService.saveAdmin(admin);
        return ResponseEntity.ok(createdAdmin);
    }

    // Update an existing admin
    @PutMapping("/update/{id}")
    public ResponseEntity<Admin> updateAdmin(@PathVariable Long id, @RequestBody Admin admin) {
        if (!adminService.getAdminById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        admin.setId(id);
        Admin updatedAdmin = adminService.saveAdmin(admin);
        return ResponseEntity.ok(updatedAdmin);
    }

    // Delete an admin by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) {
        if (!adminService.getAdminById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        adminService.deleteAdmin(id);
        return ResponseEntity.noContent().build();
    }
}
