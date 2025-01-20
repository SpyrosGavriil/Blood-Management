package com.bloodmanagement.service;

import com.bloodmanagement.dto.AdminDTO;
import com.bloodmanagement.model.Admin;
import com.bloodmanagement.model.Role;
import com.bloodmanagement.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AdminService(AdminRepository adminRepository, BCryptPasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Get all admins and return as AdminDTO
    public List<AdminDTO> getAllAdmins() {
        return adminRepository.findAll()
                .stream()
                .map(this::toAdminDTO) // Map each Admin to AdminDTO
                .collect(Collectors.toList());
    }

    // Get an admin by ID and return as AdminDTO
    public Optional<AdminDTO> getAdminById(Integer id) {
        return adminRepository.findById(id)
                .map(this::toAdminDTO); // Map Admin to AdminDTO
    }

    // Create or update an admin and return as AdminDTO
    public AdminDTO saveAdmin(Admin admin) {
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        admin.setRole(Role.ADMIN);
        Admin savedAdmin = adminRepository.save(admin);
        return toAdminDTO(savedAdmin);
    }

    // Delete an admin by ID
    public void deleteAdmin(Integer id) {
        if (!adminRepository.existsById(id)) {
            throw new IllegalArgumentException("Admin not found with ID: " + id);
        }
        adminRepository.deleteById(id);
    }

    // Map Admin to AdminDTO
    private AdminDTO toAdminDTO(Admin admin) {
        return new AdminDTO(
                admin.getPoliticalId(),
                admin.getFirstName(),
                admin.getLastName(),
                admin.getUsername(),
                admin.getRole().toString());
    }
}
